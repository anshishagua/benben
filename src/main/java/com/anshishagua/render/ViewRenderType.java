package com.anshishagua.render;

/**
 * User: lixiao
 * Date: 2018/4/27
 * Time: 下午4:58
 */

public enum ViewRenderType {
    VELOCITY,
    THYMELEAF,
    JSP,
    FREEMARKER,
    UNKNOWN;

    public static ViewRenderType parse(String string) {
        for (ViewRenderType type : values()) {
            if (type.toString().equalsIgnoreCase(string)) {
                return type;
            }
        }

        return UNKNOWN;
    }
}