package pers.syq.fastadmin.generator.entity;

import lombok.Data;
import java.util.List;

public class TableContext {
    //表的名称
    private String tableName;
    //表的备注
    private String comment;
    //表的主键
    private ColumnContext pk;
    //表的列名(包含主键)
    private List<ColumnContext> columns;
    //类名(第一个字母大写)，如：sys_user => SysUser
    private String className;
    //类名(第一个字母小写)，如：sys_user => sysUser
    private String classname;

    private String pathName;

    private boolean hasBigDecimal;

    private boolean hasDate;

    private boolean hasFillField;




    public boolean getHasFillField() {
        return hasFillField;
    }

    public void setHasFillField(boolean hasFillField) {
        this.hasFillField = hasFillField;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ColumnContext getPk() {
        return pk;
    }

    public void setPk(ColumnContext pk) {
        this.pk = pk;
    }

    public List<ColumnContext> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnContext> columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public boolean isHasBigDecimal() {
        return hasBigDecimal;
    }

    public void setHasBigDecimal(boolean hasBigDecimal) {
        this.hasBigDecimal = hasBigDecimal;
    }

    public boolean isHasDate() {
        return hasDate;
    }

    public void setHasDate(boolean hasDate) {
        this.hasDate = hasDate;
    }
}
