package com.jerry;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jerrychien on 19/12/2016.
 */
public class Generate {

    public static String PACKAGE_NAME;

    public static String OUTPUT_DIR;

    private static Configuration configuration = new Configuration();

    static {
        configuration.setClassForTemplateLoading(Generate.class, "/templates");
    }

    public static void generateModel(List<Table> tableList) {
        String path1 = OUTPUT_DIR + File.separator + PACKAGE_NAME.replace(".", File.separator);
        path1 = path1.replace(File.separator + File.separator, File.separator) + "/model/";
        String myPackage = PACKAGE_NAME + ".model";
        System.out.println(path1);
        System.out.println(myPackage);
        File dir = new File(path1);
        if (!dir.exists()) {
            System.out.println(dir.mkdirs());
        }
        try {
            Template template = configuration.getTemplate("${tableName}.java");
            for (Table table : tableList) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("PACKAGE_NAME", myPackage);
                map.put("table", table);
                map.put("primaryKey", table.getPrimaryKey());
                map.put("columns", table.getColumns());
                FileWriter fileWriter = new FileWriter(new File(path1 + File.separator + table.getTableName() + ".java"));
                template.process(map, fileWriter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
