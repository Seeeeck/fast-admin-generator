package pers.syq.fastadmin.generator.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;

import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.dialect.PropsUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import pers.syq.fastadmin.generator.entity.ColumnContext;
import pers.syq.fastadmin.generator.entity.ColumnEntity;
import pers.syq.fastadmin.generator.entity.TableContext;
import pers.syq.fastadmin.generator.entity.TableEntity;
import pers.syq.fastadmin.generator.enums.FieldFill;
import pers.syq.fastadmin.generator.module.GeneratorData;
import pers.syq.fastadmin.generator.vo.ColumnFillVo;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GenUtils {

    public static List<String> getTemplates(){
        List<String> templates = new ArrayList<>();
        templates.add("template/Entity.java.vm");
        templates.add("template/Mapper.xml.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/ServiceImpl.java.vm");
        templates.add("template/Controller.java.vm");
        templates.add("template/Mapper.java.vm");
        return templates;
    }


    public static void generatorCode(List<ColumnFillVo> columnFillVos, TableEntity tableEntity, List<ColumnEntity> columnEntities, ZipOutputStream zip) {
        TableContext tableContext = createTableContext(tableEntity, columnEntities, columnFillVos);
        initVelocity();
        VelocityContext context = createVelocityContext(tableContext);
        List<String> templates = getTemplates();
        for (String template : templates) {
            generateFile2Zip(context,tableContext,template,zip);
        }
    }

    private static void generateFile2Zip(VelocityContext context, TableContext tableContext, String template, ZipOutputStream zip){
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(template, "UTF-8");
        tpl.merge(context, sw);
        try {
            String fileName = getFileName(template, tableContext.getClassName(), GeneratorData.globalConfig.getPkg(), GeneratorData.globalConfig.getModuleName());
            zip.putNextEntry(new ZipEntry(fileName));
            IoUtil.write(zip, StandardCharsets.UTF_8,false,sw.toString());
        } catch (IOException e) {
            // TODO ex
            e.printStackTrace();
        }finally {
            IoUtil.close(sw);
            try {
                zip.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static TableContext createTableContext(TableEntity tableEntity,List<ColumnEntity> columnEntities,List<ColumnFillVo> columnFillVos){
        TableContext tableContext = new TableContext();
        setTableContextProperties(tableContext,tableEntity);
        tableContext.setHasFillField(columnFillVos != null && !columnFillVos.isEmpty());
        List<ColumnContext> columnContexts = new ArrayList<>();
        Props convertProps = PropsUtil.get("generator.properties");
        for (ColumnEntity columnEntity : columnEntities) {
            ColumnContext columnContext = new ColumnContext();
            String attrType = convertProps.getStr(columnEntity.getDataType(), "String");
            setColumnContextProperties(columnContext,columnEntity,columnFillVos,attrType);
            setTableContextProperties(tableContext,attrType,columnContext,columnEntity);
            columnContexts.add(columnContext);
        }
        tableContext.setColumns(columnContexts);
        if (tableContext.getPk() == null && !tableContext.getColumns().isEmpty()){
            tableContext.setPk(tableContext.getColumns().get(0));
        }
        return tableContext;
    }

    private static VelocityContext createVelocityContext(TableContext tableContext) {
        Map<String, Object> map = BeanUtil.beanToMap(tableContext);
        map.put("datetime", DateUtil.today());
        map.putAll(BeanUtil.beanToMap(GeneratorData.globalConfig));
        return new VelocityContext(map);
    }

    private static void setTableContextProperties(TableContext tableContext, TableEntity tableEntity){
        BeanUtil.copyProperties(tableEntity,tableContext);
        String tableName = tableEntity.getTableName();
        if (StrUtil.isNotEmpty(GeneratorData.globalConfig.getTablePrefix())){
            tableName = tableName.replaceFirst(GeneratorData.globalConfig.getTablePrefix(),"");
        }
        String classname = StrUtil.toCamelCase(tableName);
        tableContext.setClassname(classname);
        tableContext.setClassName(StrUtil.upperFirst(classname));
        tableContext.setPathName(classname.toLowerCase());
    }

    private static void setColumnContextProperties(ColumnContext columnContext,ColumnEntity columnEntity,List<ColumnFillVo> columnFillVos,String attrType){
        BeanUtil.copyProperties(columnEntity,columnContext);
        String attrname = StrUtil.toCamelCase(columnEntity.getColumnName());
        columnContext.setAttrname(attrname);
        columnContext.setAttrName(StrUtil.upperFirst(attrname));
        columnContext.setAttrType(attrType);
        if (columnFillVos != null){
            for (ColumnFillVo columnFillVo : columnFillVos) {
                if (columnEntity.getColumnName().equals(columnFillVo.getColumnName())){
                    columnContext.setFill(FieldFill.getFieldFill(columnFillVo.getFillCode()));
                    break;
                }
            }
        }

    }

    private static void setTableContextProperties(TableContext tableContext,String attrType,ColumnContext columnContext,ColumnEntity columnEntity){
        if (!tableContext.isHasBigDecimal() && attrType.equals("BigDecimal")){
            tableContext.setHasBigDecimal(true);
        }
        if (!tableContext.isHasDate() && attrType.equals("Date")){
            tableContext.setHasDate(true);
        }
        if (tableContext.getPk() == null && "PRI".equalsIgnoreCase(columnEntity.getColumnKey())) {
            tableContext.setPk(columnContext);
        }
    }
    private static void initVelocity(){
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
    }

    public static String getFileName(String template,String className,String packageName,String moduleName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StrUtil.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }

        if (template.contains("Entity.java.vm")) {
            return packagePath + "entity" + File.separator + className + "Entity.java";
        } else if (template.contains("Mapper.java.vm")) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        } else if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        } else if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        } else if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        } else if (template.contains("Mapper.xml.vm")) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + moduleName + File.separator + className + "Mapper.xml";
        }
        return "";
    }



}
