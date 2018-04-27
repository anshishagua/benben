package com.anshishagua.utils;

import com.anshishagua.object.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 下午10:49
 */

public class ResourceUtils {
    public static InputStream loadResource(String file) {
        return ResourceUtils.class.getClassLoader().getResourceAsStream(file);
    }

    public static List<Resource> loadResources(String path) {
        List<Resource> resources = new ArrayList<>();

        loadResources(path, resources, new ArrayList<>());

        return resources;
    }

    private static void loadResources(String path, List<Resource> resources, List<String> parents) {
        URL url = ResourceUtils.class.getClassLoader().getResource(path);

        File f = new File(url.getPath());

        String resource = null;

        if (f.isFile()) {
            resource = parents.stream().collect(Collectors.joining("/")) + "/" + f.getName();

            resources.add(new Resource(resource, ResourceUtils.loadResource(resource)));
        } else if (f.isDirectory()) {
            parents.add(f.getName());

            for (File file : f.listFiles()) {
                resource = parents.stream().collect(Collectors.joining("/")) + "/" + file.getName();

                loadResources(resource, resources, parents);
            }
        }
    }

    public static void main(String [] args) throws IOException {
        loadResources("mybatis").forEach(it -> System.out.println(it));

    }
}