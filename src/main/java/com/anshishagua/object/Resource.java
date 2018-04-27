package com.anshishagua.object;

import java.io.InputStream;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/26
 * Time: 上午11:29
 */

public class Resource {
    private final String name;
    private final InputStream inputStream;

    public Resource(String name, InputStream inputStream) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(inputStream);

        this.name = name;
        this.inputStream = inputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "name='" + name + '\'' +
                ", inputStream=" + inputStream +
                '}';
    }
}
