package pers.syq.fastadmin.generator.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.dialect.PropsUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.stereotype.Service;
import pers.syq.fastadmin.generator.config.DataSourceProxy;
import pers.syq.fastadmin.generator.entity.ColumnContext;
import pers.syq.fastadmin.generator.entity.ColumnEntity;
import pers.syq.fastadmin.generator.entity.TableContext;
import pers.syq.fastadmin.generator.enums.ErrorCode;
import pers.syq.fastadmin.generator.enums.FieldFill;
import pers.syq.fastadmin.generator.enums.IdType;
import pers.syq.fastadmin.generator.exception.FastAdminException;
import pers.syq.fastadmin.generator.mapper.MySQLGeneratorMapper;
import pers.syq.fastadmin.generator.module.DataSourceEntity;
import pers.syq.fastadmin.generator.entity.TableEntity;

import pers.syq.fastadmin.generator.module.GeneratorData;
import pers.syq.fastadmin.generator.template.AbstractCommonTemplate;
import pers.syq.fastadmin.generator.template.AbstractMultipleTemplate;
import pers.syq.fastadmin.generator.vo.ColumnInfoVo;
import pers.syq.fastadmin.generator.vo.GeneratorVo;
import pers.syq.fastadmin.generator.vo.TableInfoVo;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.zip.ZipOutputStream;

@Service
public class GeneratorService {
    @Autowired
    private MySQLGeneratorMapper generatorMapper;

    @Autowired
    private SpringService springService;

    public List<TableEntity> listTables() {
        return generatorMapper.selectTableList();
    }

    public boolean injectDataSource(DataSourceEntity dataSourceEntity) {
        injectCustomDataSource(dataSourceEntity);
        testInjectDataSource();
        DataSource dataSource = (DataSource) springService.getBean("dataSource");
        if (dataSource instanceof DataSourceProxy) {
            ((DataSourceProxy) dataSource).setDataSource();
            GeneratorData.dataSource = dataSourceEntity;
            return true;
        }
        return false;

    }

    private void injectCustomDataSource(DataSourceEntity dataSourceEntity) {
        HikariConfig hikariConfig = new HikariConfig();
        initDatasourceConfig(hikariConfig, dataSourceEntity);
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(HikariDataSource.class)
                .addConstructorArgValue(hikariConfig).getBeanDefinition();
        springService.injectBean("customDataSource", beanDefinition);
    }

    private void initDatasourceConfig(HikariConfig hikariConfig, DataSourceEntity dataSourceEntity) {
        String url = "jdbc:mysql://" + dataSourceEntity.getHost() +
                ":" + dataSourceEntity.getPort() +
                "/" + dataSourceEntity.getDatabase();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(dataSourceEntity.getUsername());
        hikariConfig.setPassword(dataSourceEntity.getPassword());
    }

    private void testInjectDataSource() {

        try {
            springService.getBean("customDataSource");
        } catch (Exception e) {
            if (e instanceof BeanCreationException && "customDataSource".equals(((BeanCreationException) e).getBeanName())) {
                if (GeneratorData.dataSource != null) {
                    injectCustomDataSource(GeneratorData.dataSource);
                    DataSource dataSource = (DataSource) springService.getBean("dataSource");
                    if (dataSource instanceof DataSourceProxy) {
                        ((DataSourceProxy) dataSource).setDataSource();
                    }
                }
                throw new FastAdminException(ErrorCode.SQL_EXCEPTION);
            }
            throw new FastAdminException();
        }
    }

    public List<ColumnEntity> listColumnsByTableName(String tableName) {
        return generatorMapper.selectColumnListByTableName(tableName);
    }

    public byte[] generateCode(GeneratorVo generatorVo) {
        List<TableInfoVo> tableInfos = generatorVo.getTableInfoVos();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        Collection<AbstractMultipleTemplate> templates = springService.getBeans(AbstractMultipleTemplate.class);
        for (TableInfoVo tableInfo : tableInfos) {
            TableContext tableContext = createTableContext(tableInfo);
            Map<String, Object> objectMap = BeanUtil.beanToMap(tableContext);
            for (AbstractMultipleTemplate template : templates) {
                template.generateCode(objectMap, zip);
            }
        }
        if (generatorVo.getSingleton()) {
            generateCommonFiles(zip);
        }
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }

    private void generateCommonFiles(ZipOutputStream zip) {
        Collection<AbstractCommonTemplate> commonTemplates = springService.getBeans(AbstractCommonTemplate.class);
        for (AbstractCommonTemplate commonTemplate : commonTemplates) {
            commonTemplate.generateCode(new HashMap<>(), zip);
        }
    }


    private TableContext createTableContext(TableInfoVo tableInfo) {
        TableContext tableContext = new TableContext();
        TableEntity tableEntity = generatorMapper.selectTableByTableName(tableInfo.getTableName());
        List<ColumnEntity> columnEntities = generatorMapper.selectColumnListByTableName(tableInfo.getTableName());
        List<ColumnInfoVo> columnInfoVos = tableInfo.getColumnInfoVos();
        setTableContextProperties(tableContext, tableEntity);
        tableContext.setIdType(IdType.getIdType(tableInfo.getIdTypeCode()));
        List<ColumnContext> columnContexts = new ArrayList<>();
        Props convertProps = PropsUtil.get("generator.properties");
        for (ColumnEntity columnEntity : columnEntities) {
            ColumnContext columnContext = new ColumnContext();
            String attrType = convertProps.getStr(columnEntity.getDataType(), "String");
            setColumnContextProperties(columnContext, columnEntity, columnInfoVos, attrType);
            if (!tableContext.isHasBigDecimal() && attrType.equals("BigDecimal")) {
                tableContext.setHasBigDecimal(true);
            }
            if (!tableContext.isHasDate() && attrType.equals("Date")) {
                tableContext.setHasDate(true);
            }
            if (tableContext.getPk() == null && "PRI".equalsIgnoreCase(columnEntity.getColumnKey())) {
                tableContext.setPk(columnContext);
            }
            columnContexts.add(columnContext);
        }
        tableContext.setColumns(columnContexts);
        if (tableContext.getPk() == null && CollectionUtil.isNotEmpty(tableContext.getColumns())) {
            tableContext.setPk(tableContext.getColumns().get(0));
        }
        return tableContext;
    }


    private void setTableContextProperties(TableContext tableContext, TableEntity tableEntity) {
        BeanUtil.copyProperties(tableEntity, tableContext);
        String tableName = tableEntity.getTableName();
        if (StrUtil.isNotBlank(GeneratorData.globalConfig.getTablePrefix())) {
            tableName = tableName.replaceFirst(GeneratorData.globalConfig.getTablePrefix(), "");
        }
        String classname = StrUtil.toCamelCase(tableName);
        tableContext.setClassname(classname);
        tableContext.setClassName(StrUtil.upperFirst(classname));
        tableContext.setPathName(classname.toLowerCase());
    }

    private void setColumnContextProperties(ColumnContext columnContext, ColumnEntity columnEntity, List<ColumnInfoVo> columnInfoVos, String attrType) {
        BeanUtil.copyProperties(columnEntity, columnContext);
        String attrname = StrUtil.toCamelCase(columnEntity.getColumnName());
        columnContext.setAttrname(attrname);
        columnContext.setAttrName(StrUtil.upperFirst(attrname));
        columnContext.setAttrType(attrType);
        for (ColumnInfoVo columnInfoVo : columnInfoVos) {
            if (columnEntity.getColumnName().equals(columnInfoVo.getColumnName())) {
                columnContext.setFill(FieldFill.getFieldFill(columnInfoVo.getFillCode()));
                columnContext.setVersion(columnInfoVo.getVersion());
                columnContext.setLogic(columnInfoVo.getLogic());
                break;
            }
        }

    }


}
