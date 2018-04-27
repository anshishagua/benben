package com.anshishagua.object;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 下午4:39
 */

public interface Converter<T> {
    T convert(String value);
}