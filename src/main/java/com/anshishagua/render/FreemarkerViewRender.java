package com.anshishagua.render;

import com.anshishagua.configuration.ConfigurationRegistry;
import com.anshishagua.configuration.ViewConfig;
import com.anshishagua.object.ModelAndView;
import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/4/26
 * Time: 下午4:40
 */

public class FreemarkerViewRender extends AbstractViewRender {
    private static final Logger LOG = LoggerFactory.getLogger(ThymeleafViewRender.class);

    public static final String DEFAULT_FILE_SUFFIX = ".html";

    private final TemplateEngine templateEngine = new TemplateEngine();

    private Configuration configuration;

    private ViewConfig viewConfig = ConfigurationRegistry.getViewConfig();

    public FreemarkerViewRender() {

    }

    public FreemarkerViewRender(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);

        init();
    }

    @Override
    public void init() {
        configuration = new Configuration(Configuration.VERSION_2_3_28);

        try {
            configuration.setDirectoryForTemplateLoading(new File(FreemarkerViewRender.class.getClassLoader().getResource(viewConfig.getPathPrefix()).getFile()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);
        configuration.setTemplateUpdateDelayMilliseconds(0);
        configuration.setCacheStorage(new NullCacheStorage());
    }

    public void setModelAndView(ModelAndView modelAndView) {
        Objects.requireNonNull(modelAndView);

        this.modelAndView = modelAndView;
    }

    @Override
    public void render() {
        String suffix = viewConfig.getSuffix();
        String view = modelAndView.getViewName() + suffix;

        try {
            Template template = configuration.getTemplate(view);

            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Cache-control", "no-cache");
            template.process(modelAndView.getObjectMap(), response.getWriter());
        } catch (IOException | TemplateException ex) {
            LOG.error("Failed to render", ex);

            throw new RuntimeException(ex);
        }
    }
}