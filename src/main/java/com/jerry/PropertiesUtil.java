package com.jerry;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by jerrychien on 16/12/2016.
 */
public class PropertiesUtil {

    private Properties properties;

    public PropertiesUtil(String config) {
        properties = new Properties();
        InputStream inputStream = PropertiesUtil.class.getResourceAsStream(config);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
