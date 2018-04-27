package com.anshishagua.render;

import com.anshishagua.configuration.ConfigurationRegistry;
import com.anshishagua.configuration.ViewConfig;
import com.anshishagua.object.ModelAndView;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/24
 * Time: 上午11:22
 */

public class VelocityRender extends AbstractRender {
    private final VelocityEngine engine = new VelocityEngine();

    private ModelAndView modelAndView;

    private ViewConfig viewConfig = ConfigurationRegistry.getViewConfig();

    public VelocityRender(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);

        init();
    }

    public VelocityRender() {
        init();
    }

    public void init() {
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        engine.setProperty(Velocity.INPUT_ENCODING, "utf-8");
        engine.init();
    }

    public void setModelAndView(ModelAndView modelAndView) {
        Objects.requireNonNull(modelAndView);

        this.modelAndView = modelAndView;
    }

    public void render() {
        render(modelAndView);
    }

    private void render(ModelAndView modelAndView) {
        String viewFile = viewConfig.getPathPrefix() + "/" + modelAndView.getViewName() + ".vm";

        Template template = engine.getTemplate(viewFile);

        VelocityContext context = new VelocityContext();

        for (Map.Entry<String, Object> entry : modelAndView.getObjectMap().entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }

        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        System.out.println(writer.toString());

        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        try {
            response.getWriter().println(writer.toString());
            response.getWriter().flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String [] args) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello");
        modelAndView.addObject("name", "benben");

        VelocityRender render = new VelocityRender();
        render.setModelAndView(modelAndView);
        render.render();
    }
}