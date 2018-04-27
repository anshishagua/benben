package com.anshishagua.render;

import com.anshishagua.configuration.ConfigurationRegistry;
import com.anshishagua.configuration.ViewConfig;
import com.anshishagua.object.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/26
 * Time: 下午2:15
 */

public class ThymeleafRender extends AbstractRender {
    private static final Logger LOG = LoggerFactory.getLogger(ThymeleafRender.class);

    public static final String DEFAULT_FILE_SUFFIX = ".html";

    private final TemplateEngine templateEngine = new TemplateEngine();

    private ModelAndView modelAndView;

    private ViewConfig viewConfig = ConfigurationRegistry.getViewConfig();

    public ThymeleafRender(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);

        init();
    }

    public void init() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setTemplateMode(TemplateMode.HTML);

        String prefix = viewConfig.getPathPrefix();
        if (!prefix.endsWith("/")) {
            prefix = prefix + "/";
        }

        templateResolver.setPrefix(prefix);
        templateResolver.setSuffix(viewConfig.getSuffix() == null ? DEFAULT_FILE_SUFFIX : viewConfig.getSuffix());
        templateResolver.setCharacterEncoding(viewConfig.getEncoding());
        templateResolver.setCacheable(false);
        templateResolver.setCharacterEncoding("UTF-8");

        templateEngine.setTemplateResolver(templateResolver);
    }

    public void setModelAndView(ModelAndView modelAndView) {
        Objects.requireNonNull(modelAndView);

        this.modelAndView = modelAndView;
    }

    public void render() {
        Context context = new Context();

        for (Map.Entry<String, Object> entry : modelAndView.getObjectMap().entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }

        context.setVariable("helloword","hello thymeleaf,wellcome!");

        try {
            templateEngine.process(modelAndView.getViewName(), context, response.getWriter());
        } catch (IOException ex) {
            LOG.error("Failed to render", ex);

            throw new RuntimeException(ex);
        }
    }
}