package com.anshishagua.controller;

import com.anshishagua.configuration.ApplicationProperties;
import com.anshishagua.configuration.BeanInstanceRegistry;
import com.anshishagua.configuration.ConfigurationRegistry;
import com.anshishagua.constants.ContentType;
import com.anshishagua.configuration.UrlMappingRegistry;
import com.anshishagua.annotations.Autowired;
import com.anshishagua.annotations.RequestParam;
import com.anshishagua.annotations.Value;
import com.anshishagua.configuration.ViewConfig;
import com.anshishagua.object.ModelAndView;
import com.anshishagua.render.FreemarkerRender;
import com.anshishagua.utils.ResourceUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 上午10:07
 */

public class DispatcherServlet extends AbstractHandler {
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

    private void handleStaticResource(String path, HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Method method = UrlMappingRegistry.get(target);

        ViewConfig viewConfig = ConfigurationRegistry.getViewConfig();

        int index = target.lastIndexOf(".");

        String suffix = index >= 0 ? target.substring(index) : "";

        if (viewConfig.isStaticResource(suffix)) {
            index = target.indexOf(viewConfig.getStaticResourcePath());

            target = target.substring(index);
            handleStaticResource(target, request, response);

            return;
        }

        if (method == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
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

            //VelocityRender render = new VelocityRender(request, response);
            //ThymeleafRender render = new ThymeleafRender(request, response);
            FreemarkerRender render = new FreemarkerRender(request, response);
            render.setModelAndView(modelAndView);
            render.render();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /*
        System.out.println(target);
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        PrintWriter out = response.getWriter();
        if (target.equals("/favicon.ico")) {
            System.out.println(1);
            out.println("404");
        }
        else {
            System.out.println(2);
            out.println("hello jetty");
            if(request.getParameter("name")!=null)
            {
                out.println(request.getParameter("name"));
            }
        }
        */
    }
}