package com.jerry;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * Created by jerrychien on 19/12/2016.
 */
public class FreemarkerTest {

    public static void main(String[] args) {
        System.out.println("==============");
        String rootpath = FreemarkerTest.class.getClassLoader().getResource("").getPath();
        System.out.println("rootPath:" + rootpath);
        String path1 = FreemarkerTest.class.getClassLoader().getResource("freemarkerTemplate").getPath();
        System.out.println("path1:" + path1);
        File file = new File(path1);
        System.out.println(file.exists());
        Iterator<File> fileIterator = FileUtils.iterateFiles(file, TrueFileFilter.TRUE, TrueFileFilter.TRUE);
        while (fileIterator.hasNext()) {
            File temp = fileIterator.next();
            String currentPath = temp.getPath();
            String filePath = currentPath.replace(path1, "");
            System.out.println(temp.getName() + "," + filePath);
        }
    }

    public static void template() {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(FreemarkerTest.class, "/templates");
        configuration.setEncoding(Locale.CHINA, "UTF-8");
        String tableName = "HelloWorldTOTO";
        try {
            Template template = configuration.getTemplate("${tableName}.java");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("tableName", tableName);
            File ff = new File("/Users/jerrychien/Documents/myDemo/com/jerry/demo/model/");
            if (!ff.exists()) {
                System.out.println(ff.mkdirs());
            }
            FileWriter fileWriter = new FileWriter(new File("/Users/jerrychien/Documents/myDemo/com/jerry/demo/model/" + tableName + ".java"));
            template.process(map, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
