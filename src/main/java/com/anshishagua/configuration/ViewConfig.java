package com.anshishagua.configuration;

import com.anshishagua.annotations.Configuration;
import com.anshishagua.annotations.Value;

import java.util.Objects;
import java.util.Set;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 下午10:25
 */

@Configuration(propertyPrefix = "view.config")
public class ViewConfig {
    @Value(property = "path.prefix")
    private String pathPrefix;

    @Value(property = "staticResource.path")
    private String staticResourcePath;

    @Value(property = "encoding")
    private String encoding;

    @Value(property = "suffix")
    private String suffix;

    @Value(property = "staticResource.types")
    private Set<String> staticResourceTypes;

    public String getPathPrefix() {
        return pathPrefix;
    }

    public String getStaticResourcePath() {
        return staticResourcePath;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getSuffix() {
        return suffix;
    }

    public Set<String> getStaticResourceTypes() {
        return staticResourceTypes;
    }

    public boolean isStaticResource(String type) {
        Objects.requireNonNull(type);

        return staticResourceTypes.contains(type);
    }
}