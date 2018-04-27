package com.anshishagua.object;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/24
 * Time: 上午10:40
 */

public class ModelAndView {
    private String viewName;
    private Map<String, Object> objectMap = new HashMap<>();

    public ModelAndView() {

    }

    public void setViewName(String viewName) {
        Objects.requireNonNull(viewName);

        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void addObject(String key, Object value) {
        Objects.requireNonNull(key);

        objectMap.put(key, value);
    }

    public Map<String, Object> getObjectMap() {
        return Collections.unmodifiableMap(objectMap);
    }

    @Override
    public String toString() {
        return "ModelAndView{" +
                "viewName='" + viewName + '\'' +
                ", objectMap=" + objectMap +
                '}';
    }
}