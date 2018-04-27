package com.anshishagua.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 下午12:01
 */

public class BeanNode {
    private String beanName;
    private Class<?> beanClass;
    private List<BeanNode> dependencyBeans = new ArrayList<>();

    public BeanNode(String beanName, Class<?> beanClass) {
        Objects.requireNonNull(beanName);
        Objects.requireNonNull(beanClass);

        this.beanName = beanName;
        this.beanClass = beanClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void addDependencyBean(BeanNode beanNode) {
        dependencyBeans.add(beanNode);
    }

    public List<BeanNode> getDependencyBeans() {
        return dependencyBeans;
    }
}