package com.jerry;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jerrychien on 16/12/2016.
 */
public class Column extends BaseObject {

    public static final Map<Integer, String> TYPE_MAP = new HashMap<Integer, String>();

    static {
        TYPE_MAP.put(Types.DATE, "Date");
        TYPE_MAP.put(Types.TIME, "Date");
        TYPE_MAP.put(Types.TIMESTAMP, "Date");
        TYPE_MAP.put(Types.INTEGER, "Long");
        TYPE_MAP.put(Types.TINYINT, "Integer");
        TYPE_MAP.put(Types.SMALLINT, "Integer");
        TYPE_MAP.put(Types.VARCHAR, "String");
        TYPE_MAP.put(Types.CHAR, "String");
        TYPE_MAP.put(Types.LONGNVARCHAR, "String");
        TYPE_MAP.put(Types.LONGVARCHAR, "String");
        TYPE_MAP.put(Types.BIT, "String");
        TYPE_MAP.put(Types.FLOAT, "Double");
        TYPE_MAP.put(Types.REAL, "Double");
        TYPE_MAP.put(Types.DOUBLE, "Double");
        TYPE_MAP.put(Types.BIGINT, "Long");
        TYPE_MAP.put(Types.BIT, "Integer");
        TYPE_MAP.put(Types.BOOLEAN, "Boolean");
        TYPE_MAP.put(Types.DECIMAL, "Double");
        TYPE_MAP.put(Types.BLOB, "String");
    }

    /**
     * db名称
     */
    private String dbColumnName;

    /**
     * java名称
     */
    private String javaColumnName;

    /**
     * db字段类型名称
     */
    private String dbColumnTypeName;

    /**
     * db字段类型
     */
    private int dbColumnType;

    /**
     * java字段类型类名称
     */
    private String javaColumnTypeName;

    public String getDbColumnName() {
        return dbColumnName;
    }

    public void setDbColumnName(String dbColumnName) {
        this.dbColumnName = dbColumnName;
    }

    public String getJavaColumnName() {
        return javaColumnName;
    }

    public void setJavaColumnName(String javaColumnName) {
        this.javaColumnName = javaColumnName;
    }

    public String getDbColumnTypeName() {
        return dbColumnTypeName;
    }

    public void setDbColumnTypeName(String dbColumnTypeName) {
        this.dbColumnTypeName = dbColumnTypeName;
    }

    public int getDbColumnType() {
        return dbColumnType;
    }

    public void setDbColumnType(int dbColumnType) {
        this.dbColumnType = dbColumnType;
        String javaType = TYPE_MAP.get(dbColumnType);
        if (javaType == null) {
            System.out.println("dbColumn type error, dbColumnType" + dbColumnType);
        } else {
            setJavaColumnTypeName(javaType);
        }
    }

    public String getJavaColumnTypeName() {
        return javaColumnTypeName;
    }

    public void setJavaColumnTypeName(String javaColumnTypeName) {
        this.javaColumnTypeName = javaColumnTypeName;
    }

}
