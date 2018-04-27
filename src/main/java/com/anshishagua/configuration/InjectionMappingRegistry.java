package com.anshishagua.configuration;

import com.anshishagua.object.BeanNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 上午11:54
 */

public class InjectionMappingRegistry {
    public static final Map<Class<?>, BeanNode> beanClassMap = new HashMap<>();
    public static final Map<String, BeanNode> beanNameMap = new HashMap<>();
    public static final Map<String, Object> instanceMap = new HashMap<>();

    public static Map<String, BeanNode> getBeanNameMap() {
        return beanNameMap;
    }

    public static Map<String, Object> getInstanceMap() {
        return instanceMap;
    }

    public static boolean containsBean(String beanName) {
        Objects.requireNonNull(beanName);

        return beanNameMap.containsKey(beanName);
    }

    public static boolean containsBean(Class<?> beanClass) {
        Objects.requireNonNull(beanClass);

        return beanClassMap.containsKey(beanClass);
    }

    public static Class<?> getBeanClass(String beanName) {
        if (!containsBean(beanName)) {
            return null;
        }

        return beanNameMap.get(beanName).getBeanClass();
    }

    public static void registerBean(BeanNode beanNode) {
        Objects.requireNonNull(beanNode);

        beanNameMap.put(beanNode.getBeanName(), beanNode);
        beanClassMap.put(beanNode.getBeanClass(), beanNode);
    }

    public static BeanNode getBean(Class<?> beanClass) {
        return beanClassMap.get(beanClass);
    }

    public static BeanNode getBean(String beanName) {
        return beanNameMap.get(beanName);
    }
}