package com.jerry;


import java.util.List;

/**
 * Created by jerrychien on 19/12/2016.
 */
public class Table extends BaseObject {

    private static final long serialVersionUID = -702425435554689088L;

    private String dbName;

    private Column primaryKey;

    private String tableName;

    private List<Column> columns;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public Column getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Column primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
