package com.anshishagua.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 下午2:50
 */

public class BeanInstanceRegistry {
    public static final Map<String, Object> instanceMap = new HashMap<>();

    public static boolean containsBean(String beanName) {
        Objects.requireNonNull(beanName);

        return instanceMap.containsKey(beanName);
    }

    public static void registerBeanInstance(String beanName, Object beanObject) {
        Objects.requireNonNull(beanName);

        instanceMap.put(beanName, beanObject);
    }

    public static Object getBeanInstance(String beanName) {
        Objects.requireNonNull(beanName);

        return instanceMap.get(beanName);
    }
}