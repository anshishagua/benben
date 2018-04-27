package com.anshishagua.utils;

import com.anshishagua.configuration.ApplicationProperties;
import com.anshishagua.configuration.BeanInstanceRegistry;
import com.anshishagua.object.BeanNode;
import com.anshishagua.configuration.ConfigurationRegistry;
import com.anshishagua.configuration.InjectionMappingRegistry;
import com.anshishagua.configuration.UrlMappingRegistry;
import com.anshishagua.annotations.Autowired;
import com.anshishagua.annotations.Bean;
import com.anshishagua.annotations.Configuration;
import com.anshishagua.annotations.Controller;
import com.anshishagua.annotations.RequestParam;
import com.anshishagua.annotations.UrlMapping;
import com.anshishagua.annotations.Value;
import com.anshishagua.configuration.MybatisConfig;
import com.anshishagua.constants.HttpMethod;
import org.apache.ibatis.annotations.Mapper;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * User: lixiao
 * Date: 2018/4/12
 * Time: 下午11:10
 */

public class PackageUtils {
    public static String getBasePackageName(Class<?> clazz) {
        String packageName = clazz.getPackage().getName();

        List<String> list = Arrays.asList(packageName.split("\\."));

        for (int i = 0; i < list.size(); ++i) {
            String str = list.subList(0, i + 1).stream().collect(Collectors.joining("/"));

            URL url = Thread.currentThread().getContextClassLoader().getResource(str);

            File file = new File(url.getFile());

            if (file.isDirectory() && file.listFiles().length > 0) {
                return str;
            }
        }

        return null;
    }

    public static String getBasePackageName() {
        String packageName = PackageUtils.class.getPackage().getName();

        List<String> list = Arrays.asList(packageName.split("\\."));

        for (int i = 0; i < list.size(); ++i) {
            String str = list.subList(0, i + 1).stream().collect(Collectors.joining("/"));

            URL url = Thread.currentThread().getContextClassLoader().getResource(str);

            File file = new File(url.getFile());

            if (file.isDirectory() && file.listFiles().length > 0) {
                return str.replace("/", ".");
            }
        }

        return null;
    }

    public static void configController(Class<?> controller) throws Exception {
        String baseUrlPath = "";
        HttpMethod defaultHttpMethod = HttpMethod.GET;

        if (controller.isAnnotationPresent(UrlMapping.class)) {
            UrlMapping urlMapping = controller.getAnnotation(UrlMapping.class);

            if (StringUtils.isNotEmpty(urlMapping.value())) {
                baseUrlPath = urlMapping.value();

                if (!baseUrlPath.startsWith("/")) {
                    baseUrlPath = "/" + baseUrlPath;
                }

                if (!baseUrlPath.endsWith("/")) {
                    baseUrlPath = baseUrlPath + "/";
                }
            }

            defaultHttpMethod = urlMapping.method();
        }

        for (Method method : controller.getDeclaredMethods()) {
            if (method.isAnnotationPresent(UrlMapping.class)) {
                UrlMapping urlMapping = method.getAnnotation(UrlMapping.class);

                String methodUrlPath = urlMapping.value();
                if (methodUrlPath.startsWith("/")) {
                    methodUrlPath = methodUrlPath.substring(1);
                }

                String urlPath = baseUrlPath + methodUrlPath;

                UrlMappingRegistry.register(urlPath, controller, method);

                for (Parameter parameter : method.getParameters()) {
                    if (parameter.isAnnotationPresent(RequestParam.class)) {

                    }
                }
            }
        }
    }

    public static void configInjections(Class<?> clazz, boolean firstScan) throws Exception {
        if (!firstScan) {
            if (InjectionMappingRegistry.containsBean(clazz)) {
                BeanNode beanNode = InjectionMappingRegistry.getBean(clazz);

                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        Autowired autowired = field.getAnnotation(Autowired.class);

                        String beanName = "";

                        if (!autowired.beanName().equals("")) {
                            beanName = autowired.beanName();
                        } else {
                            beanName = field.getName();
                        }

                        Object target = clazz.newInstance();

                        if (BeanInstanceRegistry.containsBean(beanName)) {
                            field.setAccessible(true);
                            field.set(target, BeanInstanceRegistry.getBeanInstance(beanName));
                        }

                        if (!InjectionMappingRegistry.containsBean(beanName)) {
                            throw new RuntimeException("Bean " + beanName + " not found");
                        }

                        beanNode.addDependencyBean(InjectionMappingRegistry.getBean(beanName));
                    }
                }
            }
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Autowired autowired = field.getAnnotation(Autowired.class);

                Object target = clazz.newInstance();

                String beanName = "";

                if (!autowired.beanName().equals("")) {
                    beanName = autowired.beanName();
                } else {
                    beanName = field.getName();

                    beanName = Character.toLowerCase(beanName.charAt(0)) + beanName.substring(1);
                }

                if (BeanInstanceRegistry.containsBean(beanName)) {
                    field.setAccessible(true);
                    field.set(target, BeanInstanceRegistry.getBeanInstance(beanName));
                }

                Class<?> beanClassType = field.getType();

                if (InjectionMappingRegistry.containsBean(beanName)) {
                    Class<?> beanClass = InjectionMappingRegistry.getBeanClass(beanName);

                    if (beanClass != beanClassType) {
                        throw new RuntimeException(String.format("Already registered bean:%s with class:%s", beanName, beanClass));
                    }
                }

                BeanNode beanNode = new BeanNode(beanName, beanClassType);
                InjectionMappingRegistry.registerBean(beanNode);
            }
        }
    }

    public static void configBeans(Class<?> clazz) throws Exception {
        Objects.requireNonNull(clazz);

        if (!clazz.isAnnotationPresent(Configuration.class)) {
            return;
        }

        Configuration configuration = clazz.getAnnotation(Configuration.class);

        String propertyPrefix = configuration.propertyPrefix();

        Object target = clazz.newInstance();

        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(Bean.class)) {
                continue;
            }

            Bean bean = method.getAnnotation(Bean.class);
            String beanName = method.getName();

            if (!bean.name().equals("")) {
                beanName = bean.name();
            }

            if (Character.isUpperCase(beanName.charAt(0))) {
                beanName = Character.toLowerCase(beanName.charAt(0)) + beanName.substring(1);
            }

            if (BeanInstanceRegistry.containsBean(beanName)) {
                throw new RuntimeException(String.format("Bean with name:%s already registered", beanName));
            }

            Object beanObject = method.invoke(target);

            BeanInstanceRegistry.registerBeanInstance(beanName, beanObject);
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Value.class)) {
                continue;
            }

            field.setAccessible(true);

            Value value = field.getAnnotation(Value.class);

            String propertyName = propertyPrefix + "." + value.property();

            field.set(target, ApplicationProperties.getProperty(propertyName, field.getType()));
        }

        String key = Character.toLowerCase(clazz.getSimpleName().charAt(0)) + clazz.getSimpleName().substring(1);

        ConfigurationRegistry.register(key, target);
    }

    public static void configMapper(Class<?> mapperClass) {
        Objects.requireNonNull(mapperClass);

        if (!mapperClass.isAnnotationPresent(Mapper.class)) {
            return;
        }

        MybatisConfig.addMapper(mapperClass.getSimpleName(), mapperClass);
    }

    public static void scanPackage(String packageName, boolean firstScan) throws Exception {
        packageName = packageName.replace('.', '/');

        URL url = Thread.currentThread().getContextClassLoader().getResource(packageName);

        Path path = Paths.get(url.getFile());

        File [] files = path.toFile().listFiles();

        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getAbsolutePath();

                String str = fileName.substring(fileName.indexOf(packageName)).replace('/', '.').replace(".class", "");

                Class<?> clazz = Class.forName(str);

                if (clazz.isAnnotationPresent(Controller.class) && firstScan) {
                    configController(clazz);
                }

                configInjections(clazz, firstScan);
                configBeans(clazz);
                configMapper(clazz);
            } else if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    String string = f.getAbsolutePath();

                    int index = string.indexOf(packageName);

                    String className = string.substring(index);

                    className = className.replace("/", ".");

                    if (f.isDirectory()) {
                        scanPackage(className, firstScan);
                        continue;
                    }

                    if (className.endsWith(".class")) {
                        className = className.substring(0, className.lastIndexOf(".class"));
                    }

                    Class<?> clazz = Class.forName(className);

                    if (clazz.isAnnotationPresent(Controller.class) && firstScan) {
                        configController(clazz);
                    }

                    configInjections(clazz, firstScan);
                    configBeans(clazz);
                    configMapper(clazz);
                }
            }
        }
    }
}