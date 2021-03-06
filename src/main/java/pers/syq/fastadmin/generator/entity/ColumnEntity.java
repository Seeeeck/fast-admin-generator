package pers.syq.fastadmin.generator.entity;

import lombok.Data;

@Data
public class ColumnEntity {
    //列名
    private String columnName;
    //列名类型
    private String dataType;
    //列名备注
    private String comment;
    //PRI
    private String columnKey;
    //auto_increment
    private String extra;
}
