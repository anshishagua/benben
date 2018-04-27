package com.anshishagua.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 下午3:31
 */

public class PropertyUtils {
    private static final Logger LOG = LoggerFactory.getLogger(PropertyUtils.class);

    public static final String PROPERTY_FILE_NAME = "application.properties";

    public static Properties load() {
        try {
            InputStream inputStream = PropertyUtils.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME);
            Properties properties = new Properties();
            properties.load(inputStream);

            return properties;
        } catch (IOException ex) {
            String message = "Failed to load property file:" + PROPERTY_FILE_NAME;

            LOG.error(message);

            throw new RuntimeException(message);
        }
    }
}
