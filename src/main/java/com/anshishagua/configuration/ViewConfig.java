package com.anshishagua.configuration;

import com.anshishagua.annotations.Configuration;
import com.anshishagua.annotations.Value;
import com.anshishagua.render.ViewRenderType;

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

    @Value(property = "render.type")
    private ViewRenderType viewRenderType;

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

    public ViewRenderType getViewRenderType() {
        return viewRenderType;
    }

    public boolean isStaticResource(String type) {
        Objects.requireNonNull(type);

        return staticResourceTypes.contains(type);
    }
}