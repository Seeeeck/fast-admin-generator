package pers.syq.fastadmin.generator.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TableEntity {
	//表的名称
	private String tableName;
	//表的备注
	private String comment;
	private Date createTime;
}
