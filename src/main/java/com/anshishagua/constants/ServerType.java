package com.anshishagua.constants;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/27
 * Time: 上午10:18
 */

public enum ServerType {
    TOMCAT,
    JETTY,
    UNKNOWN;

    public static ServerType parse(String string) {
        Objects.requireNonNull(string);

        for (ServerType serverType : values()) {
            if (serverType.toString().equalsIgnoreCase(string)) {
                return serverType;
            }
        }

        return UNKNOWN;
    }
}