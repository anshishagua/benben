package com.anshishagua.controller;

import com.anshishagua.annotations.Autowired;
import com.anshishagua.annotations.RequestParam;
import com.anshishagua.annotations.Value;
import com.anshishagua.configuration.ApplicationProperties;
import com.anshishagua.configuration.BeanInstanceRegistry;
import com.anshishagua.configuration.ConfigurationRegistry;
import com.anshishagua.configuration.UrlMappingRegistry;
import com.anshishagua.configuration.ViewConfig;
import com.anshishagua.constants.ContentType;
import com.anshishagua.object.ModelAndView;
import com.anshishagua.render.FreemarkerViewRender;
import com.anshishagua.render.ViewRender;
import com.anshishagua.render.ViewRenderFactory;
import com.anshishagua.utils.ResourceUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * User: lixiao
 * Date: 2018/4/27
 * Time: 下午4:13
 */

public class DispatcherServlet extends HttpServlet {
    private void initController(Object object) throws Exception {
        Class<?> clazz = object.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(Autowired.class)) {
                field.set(object, BeanInstanceRegistry.getBeanInstance(field.getName()));
            } else if (field.isAnnotationPresent(Value.class)) {
                Value value = field.getAnnotation(Value.class);

                String propertyName = value.property();

                field.set(object, ApplicationProperties.getProperty(propertyName, field.getType()));
            }
        }
    }

    private void handleStaticResource(String path, HttpServletResponse response) throws IOException {
        ViewConfig viewConfig = ConfigurationRegistry.getViewConfig();

        String fileExtension = path.substring(path.lastIndexOf("."));
        ContentType contentType = ContentType.parseByFileExtension(fileExtension);

        if (contentType == null) {
            throw new RuntimeException("Unknown file extension:" + fileExtension);
        }

        response.setContentType(contentType.getMimeType());
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding(viewConfig.getEncoding());

        InputStream inputStream = ResourceUtils.loadResource(viewConfig.getPathPrefix() + "/" + path);

        byte [] bytes = new byte[1024];
        int len = 0;

        while ((len = inputStream.read(bytes)) != -1) {
            response.getOutputStream().write(bytes, 0, len);
        }

        response.getOutputStream().close();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String target = request.getRequestURI();

        System.out.println(target);

        ViewConfig viewConfig = ConfigurationRegistry.getViewConfig();

        int index = target.lastIndexOf(".");

        String suffix = index >= 0 ? target.substring(index) : "";

        if (viewConfig.isStaticResource(suffix)) {
            index = target.indexOf(viewConfig.getStaticResourcePath());

            target = target.substring(index);
            handleStaticResource(target, response);

            return;
        }

        Method method = UrlMappingRegistry.get(target);

        if (method == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("404");

            return;
        }

        Class<?> controllerClazz = method.getDeclaringClass();
        Object controller = null;


        try {
            controller = controllerClazz.newInstance();
            initController(controller);

            Object [] parameters = new Object[method.getParameters().length];

            for (int i = 0; i < method.getParameterCount(); ++i) {
                Parameter parameter = method.getParameters()[i];

                if (parameter.isAnnotationPresent(RequestParam.class)) {
                    RequestParam requestParam = parameter.getAnnotation(RequestParam.class);

                    String requestParamName = requestParam.name();
                    String defaultValue = requestParam.defaultValue();
                    boolean required = requestParam.required();

                    Class<?> paramClass = parameter.getType();

                    if (paramClass == int.class || paramClass == Integer.class) {
                        String paramValue = request.getParameter(requestParamName);

                        if (required) {
                            if (paramValue == null) {
                                throw new RuntimeException("Param " + requestParamName + " not found in request");
                            }
                        } else {
                            if (paramValue == null) {
                                paramValue = defaultValue;
                            }
                        }

                        parameters[i] = Integer.parseInt(paramValue);
                    } else if (paramClass == String.class) {
                        String paramValue = request.getParameter(requestParamName);

                        if (required) {
                            if (paramValue == null) {
                                throw new RuntimeException("Param " + requestParamName + " not found in request");
                            }
                        } else {
                            if (paramValue == null) {
                                paramValue = defaultValue;
                            }
                        }

                        parameters[i] = paramValue;
                    }
                }
            }

            ModelAndView modelAndView = (ModelAndView) method.invoke(controller, parameters);

            ViewRender render = ViewRenderFactory.getViewRender(viewConfig.getViewRenderType());

            render.setModelAndView(modelAndView);
            render.setRequest(request);
            render.setResponse(response);
            render.render();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}