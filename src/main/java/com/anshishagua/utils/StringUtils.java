package com.anshishagua.utils;

/**
 * User: lixiao
 * Date: 2018/4/24
 * Time: 上午10:02
 */

public class StringUtils {
    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

    public static boolean isNotEmpty(String string) {
        return string != null && string.trim().length() > 0;
    }
}