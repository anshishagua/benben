package com.anshishagua.configuration;

import com.alibaba.fastjson.JSON;
import com.anshishagua.object.Converter;
import com.anshishagua.examples.Person;
import com.anshishagua.utils.PropertyUtils;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 下午3:35
 */

public class ApplicationProperties {
    private static final Properties properties;

    static {
        properties = PropertyUtils.load();
    }

    public static boolean getBooleanProperty(String propertyName) {
        Objects.requireNonNull(propertyName);

        String value = properties.getProperty(propertyName);

        if (value == null) {
            throw new RuntimeException("Property " + propertyName + " not found");
        }

        return Boolean.parseBoolean(value);
    }

    public static short getShortProperty(String propertyName) {
        Objects.requireNonNull(propertyName);

        String value = properties.getProperty(propertyName);

        if (value == null) {
            throw new RuntimeException("Property " + propertyName + " not found");
        }

        return Short.parseShort(value);
    }

    public static int getIntProperty(String propertyName) {
        Objects.requireNonNull(propertyName);

        String value = properties.getProperty(propertyName);

        if (value == null) {
            throw new RuntimeException("Property " + propertyName + " not found");
        }

        return Integer.parseInt(value);
    }

    public static long getLongProperty(String propertyName) {
        Objects.requireNonNull(propertyName);

        String value = properties.getProperty(propertyName);

        if (value == null) {
            throw new RuntimeException("Property " + propertyName + " not found");
        }

        return Long.parseLong(value);
    }

    public static float getFloatProperty(String propertyName) {
        Objects.requireNonNull(propertyName);

        String value = properties.getProperty(propertyName);

        if (value == null) {
            throw new RuntimeException("Property " + propertyName + " not found");
        }

        return Float.parseFloat(value);
    }

    public static double getDoubleProperty(String propertyName) {
        Objects.requireNonNull(propertyName);

        String value = properties.getProperty(propertyName);

        if (value == null) {
            throw new RuntimeException("Property " + propertyName + " not found");
        }

        return Double.parseDouble(value);
    }

    public static LocalDate getLocalDateProperty(String propertyName) {
        Objects.requireNonNull(propertyName);

        String value = properties.getProperty(propertyName);

        if (value == null) {
            throw new RuntimeException("Property " + propertyName + " not found");
        }

        return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static LocalTime getLocalTimeProperty(String propertyName) {
        Objects.requireNonNull(propertyName);

        String value = properties.getProperty(propertyName);

        if (value == null) {
            throw new RuntimeException("Property " + propertyName + " not found");
        }

        return LocalTime.parse(value, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public static LocalDateTime getLocalDateTimeProperty(String propertyName) {
        Objects.requireNonNull(propertyName);

        String value = properties.getProperty(propertyName);

        if (value == null) {
            throw new RuntimeException("Property " + propertyName + " not found");
        }

        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static <T> T getProperty(String propertyName, Converter<T> converter) {
        Objects.requireNonNull(propertyName);

        String value = properties.getProperty(propertyName);

        if (value == null) {
            throw new RuntimeException("Property " + propertyName + " not found");
        }

        return converter.convert(value);
    }

    public static <T> T getProperty(String propertyName, Converter<T> converter, T defaultValue) {
        Objects.requireNonNull(propertyName);

        String value = properties.getProperty(propertyName);

        if (value == null) {
            return defaultValue;
        }

        return converter.convert(value);
    }

    public static String getStringProperty(String propertyName) {
        Objects.requireNonNull(propertyName);

        String value = properties.getProperty(propertyName);

        if (value == null) {
            throw new RuntimeException("Property " + propertyName + " not found");
        }

        return value;
    }

    public static String getProperty(String propertyName, String defaultValue) {
        Objects.requireNonNull(propertyName);

        return properties.getProperty(propertyName, defaultValue);
    }

    public static int getProperty(String propertyName, int defaultValue) {
        Objects.requireNonNull(propertyName);

        try {
            return Integer.parseInt(properties.getProperty(propertyName, String.valueOf(defaultValue)));
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    public static long getProperty(String propertyName, long defaultValue) {
        Objects.requireNonNull(propertyName);

        try {
            return Long.parseLong(properties.getProperty(propertyName, String.valueOf(defaultValue)));
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    public static boolean getProperty(String propertyName, boolean defaultValue) {
        Objects.requireNonNull(propertyName);

        return Boolean.parseBoolean(properties.getProperty(propertyName, String.valueOf(defaultValue)));
    }

    public static LocalDate getProperty(String propertyName, LocalDate defaultValue) {
        Objects.requireNonNull(propertyName);

        try {
            return LocalDate.parse(properties.getProperty(propertyName, defaultValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        } catch (DateTimeParseException ex) {
            return defaultValue;
        }
    }

    public static <T> T getProperty(String propertyName, T defaultValue) {
        Objects.requireNonNull(propertyName);

        try {
            return JSON.parseObject(properties.getProperty(propertyName, JSON.toJSONString(defaultValue)), (Class<T> ) defaultValue.getClass());
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public static Object getProperty(String propertyName, Class<?> type) {
        Objects.requireNonNull(propertyName);

        if (type == boolean.class || type == Boolean.class) {
            return ApplicationProperties.getBooleanProperty(propertyName);
        } else if (type == short.class || type == Short.class) {
            return ApplicationProperties.getShortProperty(propertyName);
        } else if (type == int.class || type == Integer.class) {
            return ApplicationProperties.getIntProperty(propertyName);
        } else if (type == long.class || type == Long.class) {
            return ApplicationProperties.getLongProperty(propertyName);
        } else if (type == float.class || type == Float.class) {
            return ApplicationProperties.getFloatProperty(propertyName);
        } else if (type == double.class || type == Double.class) {
            return ApplicationProperties.getDoubleProperty(propertyName);
        } else if (type == String.class) {
            return ApplicationProperties.getStringProperty(propertyName);
        } else if (type == LocalDate.class) {
            return ApplicationProperties.getLocalDateProperty(propertyName);
        } else if (type == LocalTime.class) {
            return ApplicationProperties.getLocalTimeProperty(propertyName);
        } else if (type == LocalDateTime.class) {
            return ApplicationProperties.getLocalDateTimeProperty(propertyName);
        } else if (List.class.isAssignableFrom(type)) {
            String value = properties.getProperty(propertyName);

            if (value == null || value.equals("")) {
                return Collections.emptyList();
            }

            return Arrays.asList(value.split(","));
        } else if (Set.class.isAssignableFrom(type)) {
            String value = properties.getProperty(propertyName);

            if (value == null || value.equals("")) {
                return Collections.emptySet();
            }

            return new HashSet<>(Arrays.asList(value.split(",")));
        } else if (Map.class.isAssignableFrom(type)) {
            String value = properties.getProperty(propertyName);

            if (value == null || value.equals("")) {
                return Collections.emptyMap();
            }

            Map<String, String> map = new HashMap<>();

            for (String keyValue : value.split(",")) {
                map.put(keyValue.split("=")[0], keyValue.split("=")[1]);
            }

            return map;
        } else if (type.isEnum()) {
            String value = properties.getProperty(propertyName);

            if (value == null || value.equals("")) {
                return null;
            }

            try {
                Method method = type.getMethod("values");
                method.setAccessible(true);

                Object [] values = (Object []) method.invoke(type, null);

                for (Object object : values) {
                    if (value.equalsIgnoreCase(object.toString())) {
                        return object;
                    }
                }

                return null;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {
            String value = properties.getProperty(propertyName);

            if (value == null) {
                return null;
            }

            try {
                return JSON.parseObject(value, type);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void main(String [] args) {
        System.out.println(ApplicationProperties.getProperty("a.affff", 3));
        System.out.println(ApplicationProperties.getProperty("a.fffggg", new Person()));

        System.out.println(Set.class.isAssignableFrom(HashSet.class));

        List<String> a = new ArrayList<>();

        System.out.println(a.getClass());
    }
}