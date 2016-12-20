package com.jerry;


import com.google.common.base.CaseFormat;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by jerrychien on 16/12/2016.
 */
public class Start {

    private static String DRIVER_CLASS;
    private static String DATABASE_URL;
    private static String DATABASE_NAME;
    private static String DATABASE_USER;
    private static String DATABASE_PASSWORD;
    private static String PACKAGE_NAME;
    public static final String catalog = null;
    public static final String schema = null;

    public static void main(String[] args) {
        System.out.println("Main started");
        PropertiesUtil propertiesUtil = new PropertiesUtil("/config.properties");
        Properties properties = propertiesUtil.getProperties();
//        Iterator<Map.Entry<Object, Object>> iterator = properties.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<Object, Object> entry = iterator.next();
//            System.out.println(entry.getKey() + "=" + entry.getValue());
//        }
        DRIVER_CLASS = properties.getProperty("DRIVER_CLASS");
        DATABASE_URL = properties.getProperty("DATABASE_URL");
        DATABASE_NAME = properties.getProperty("DATABASE_NAME");
        DATABASE_USER = properties.getProperty("DATABASE_USER");
        DATABASE_PASSWORD = properties.getProperty("DATABASE_PASSWORD");
        PACKAGE_NAME = properties.getProperty("PACKAGE_NAME");
        Generate.PACKAGE_NAME = PACKAGE_NAME;
        Generate.OUTPUT_DIR = properties.getProperty("OUTPUT_DIR");
        System.out.println(DRIVER_CLASS + "\n" + DATABASE_URL + "\n" + DATABASE_NAME + "\n" + DATABASE_USER + "\n" + DATABASE_PASSWORD);
        Connection connection = null;
        try {
            Class.forName(DRIVER_CLASS);
            connection = DriverManager.getConnection(DATABASE_URL + "/" + DATABASE_NAME, DATABASE_USER, DATABASE_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection == null) {
            System.out.println("connection 为空！quit");
            return;
        }
        List<Table> tableList = new ArrayList<Table>();
        //获取此数据库下所有表
        DatabaseMetaData databaseMetaData = null;
        try {
            databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(catalog, schema, null, new String[]{"TABLE"});
            while (resultSet.next()) {
                Table table = new Table();
                table.setDbName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, DATABASE_NAME));
                String tableName = resultSet.getString("TABLE_NAME");
                table.setTableName(StringUtils.capitalize(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
                        tableName)));
                System.out.println("===========================tableName:" + tableName);
                System.out.println("------get primaryKey");
                ResultSet primaryKeyResultSet = databaseMetaData.getPrimaryKeys(catalog, schema, tableName);
                if (primaryKeyResultSet.next()) {
                    String pkName = primaryKeyResultSet.getString("PK_NAME");
                    String columnName = primaryKeyResultSet.getString("COLUMN_NAME");
                    short keySeq = primaryKeyResultSet.getShort("KEY_SEQ");
                    System.out.println(columnName + ":" + pkName + ":" + keySeq);
                    Column priColumn = new Column();
                    priColumn.setDbColumnName(columnName);
                    priColumn.setJavaColumnName(StringUtils.capitalize(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
                            columnName)));
                    priColumn.setDbColumnType(keySeq);
                    priColumn.setDbColumnTypeName(pkName);
                    table.setPrimaryKey(priColumn);
                } else {
                    System.out.println("table:" + tableName + ",does not have a primary key!");
                    continue;
                }
                System.out.println("------get unique keys");
                ResultSet indexResultSet = databaseMetaData.getIndexInfo(catalog, schema, tableName, true, true);
                while (indexResultSet.next()) {
                    String indexName = indexResultSet.getString("INDEX_NAME");
                    String schema = indexResultSet.getString("TABLE_SCHEM");
                    String columnName = indexResultSet.getString("COLUMN_NAME");
                    if (indexName == null) {
                        continue;
                    }
                    System.out.println("****************************************");
                    System.out.println("Table: " + schema + "." + tableName);
                    System.out.println("Index Name: " + indexName);
                    System.out.println("Column Name: " + columnName);
                }
                List<Column> columnList = new ArrayList<Column>();
                System.out.println("------get Columns");
                ResultSet columnResultSet = databaseMetaData.getColumns(catalog, null, tableName, null);
                while (columnResultSet.next()) {
                    String columnName = columnResultSet.getString("COLUMN_NAME");
                    String typeName = columnResultSet.getString("TYPE_NAME");
                    int dataType = columnResultSet.getInt("DATA_TYPE");
                    System.out.println(columnName + ":" + typeName + ":" + dataType);
                    Column column = new Column();
                    column.setDbColumnName(columnName);
                    column.setDbColumnType(dataType);
                    column.setDbColumnTypeName(typeName);
                    column.setJavaColumnName(StringUtils.capitalize(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
                            columnName)));
                    System.out.println(column);
                    columnList.add(column);
                }
                table.setColumns(columnList);
                System.out.println(table);
                tableList.add(table);
            }
            Generate.generateModel(tableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
