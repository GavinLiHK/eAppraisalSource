package com.hkha.ea.dto.assess;

//added on 20170320 for size limit of unicode characters
public class UserTableColumn {
	private String tableName;
	private String columnName;
	private String dataType;
	private int dataLength;
	
	public String getTableName(){
		return tableName;
	}
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
	
	public String getColumnName(){
		return columnName;
	}
	public void setColumnName(String columnName){
		this.columnName = columnName;
	}
	
	public String getDataType(){
		return dataType;
	}
	public void setDataType(String dataType){
		this.dataType = dataType;
	}
	
	public int getDataLength(){
		return dataLength;
	}
	public void setDataLength(int dataLength){
		this.dataLength = dataLength;
	}
}
//end added on 20170320