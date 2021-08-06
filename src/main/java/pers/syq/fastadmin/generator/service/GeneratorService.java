package pers.syq.fastadmin.generator.service;

import cn.hutool.core.io.IoUtil;
import com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.stereotype.Service;
import pers.syq.fastadmin.generator.config.DataSourceProxy;
import pers.syq.fastadmin.generator.entity.ColumnEntity;
import pers.syq.fastadmin.generator.enums.ErrorCode;
import pers.syq.fastadmin.generator.exception.FastAdminException;
import pers.syq.fastadmin.generator.mapper.MySQLGeneratorMapper;
import pers.syq.fastadmin.generator.module.DataSourceEntity;
import pers.syq.fastadmin.generator.entity.TableEntity;

import pers.syq.fastadmin.generator.module.GeneratorData;
import pers.syq.fastadmin.generator.utils.GenUtils;
import pers.syq.fastadmin.generator.vo.GeneratorVo;
import pers.syq.fastadmin.generator.vo.TableInfoVo;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.util.List;
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
        if (dataSource instanceof DataSourceProxy){
            ((DataSourceProxy) dataSource).setDataSource();
            GeneratorData.dataSource = dataSourceEntity;
            return true;
        }
        return false;

    }

    private void injectCustomDataSource(DataSourceEntity dataSourceEntity){
        HikariConfig hikariConfig = new HikariConfig();
        initDatasourceConfig(hikariConfig,dataSourceEntity);
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(HikariDataSource.class)
                .addConstructorArgValue(hikariConfig).getBeanDefinition();
        springService.injectBean("customDataSource", beanDefinition);
    }

    private void initDatasourceConfig(HikariConfig hikariConfig,DataSourceEntity dataSourceEntity) {
        String url = "jdbc:mysql://" + dataSourceEntity.getHost() +
                ":" + dataSourceEntity.getPort() +
                "/" + dataSourceEntity.getDatabase();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(dataSourceEntity.getUsername());
        hikariConfig.setPassword(dataSourceEntity.getPassword());
    }

    private void testInjectDataSource(){
        try {
            springService.getBean("customDataSource");
        } catch (Exception e) {
            if(e instanceof BeanCreationException && "customDataSource".equals(((BeanCreationException) e).getBeanName())){
                if (GeneratorData.dataSource != null){
                    injectCustomDataSource(GeneratorData.dataSource);
                    DataSource dataSource = (DataSource) springService.getBean("dataSource");
                    if (dataSource instanceof DataSourceProxy){
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

    public byte[] generateCode(GeneratorVo generatorVo){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (TableInfoVo tableInfo : generatorVo.getTableInfos()) {
            TableEntity tableEntity = generatorMapper.selectTableByTableName(tableInfo.getTableName());
            List<ColumnEntity> columnEntities = generatorMapper.selectColumnListByTableName(tableInfo.getTableName());
            GenUtils.generatorCode(generatorVo.getGlobalConfig(),tableInfo.getColumnFillVos(),tableEntity,columnEntities,zip);
        }
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }
}
