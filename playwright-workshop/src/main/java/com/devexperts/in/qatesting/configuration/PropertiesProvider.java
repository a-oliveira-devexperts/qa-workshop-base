package com.devexperts.in.qatesting.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * PropertiesProvider class provides access to the properties file
 */
public class PropertiesProvider {

    private static final String configFilePath = "src/test/resources/config.properties";
    private static Properties prop = new Properties();

    private PropertiesProvider() {}

    /**
     * Initialize properties
     */
    private static void init() {
        try {
            FileInputStream propsInput = new FileInputStream(configFilePath);
            prop = new Properties();
            prop.load(propsInput);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + configFilePath, e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + configFilePath, e);
        }
    }

    /**
     * Get property value by key
     * @param key property key
     * @return property value
     */
    public static String getProperty(String key) {
        if (prop.isEmpty()) {
            init();
        }
        if (prop.containsKey(key)) {
            return prop.getProperty(key);
        } else {
            throw new RuntimeException("Property not found: " + key);
        }
    }
}
