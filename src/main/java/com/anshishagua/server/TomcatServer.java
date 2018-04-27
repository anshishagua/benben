package com.anshishagua.server;

import com.anshishagua.configuration.ConfigurationRegistry;
import com.anshishagua.configuration.ServerConfig;
import com.anshishagua.controller.DispatcherServlet;
import com.anshishagua.exceptions.ServerException;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.Tomcat.FixContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: lixiao
 * Date: 2018/4/27
 * Time: 上午9:51
 */

public class TomcatServer implements Server {
    private static final Logger LOG = LoggerFactory.getLogger(TomcatServer.class);

    private final ServerConfig serverConfig = ConfigurationRegistry.getServerConfig();
    private Tomcat tomcat = new Tomcat();

    private static class HomeServlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.getWriter().print("hello tomcat");
        }
    }

    public TomcatServer() {

    }

    @Override
    public void init() {
        tomcat.setPort(serverConfig.getPort());
        tomcat.setBaseDir(serverConfig.getDocBase());
        tomcat.getHost().setAutoDeploy(serverConfig.isAutoDeploy());

        StandardContext context = new StandardContext();
        context.setPath(serverConfig.getContextPath());
        context.addLifecycleListener(new FixContextListener());
        tomcat.getHost().addChild(context);

        tomcat.addServlet(serverConfig.getContextPath(), "dispatcherServlet", new DispatcherServlet());
        context.addServletMappingDecoded("/", "dispatcherServlet");
    }

    @Override
    public void start() {
        try {
            tomcat.start();
            LOG.info("Started tomcat server at port:{}", serverConfig.getPort());
            tomcat.getServer().await();
        } catch (LifecycleException ex) {
            LOG.error("Failed to start embed tomcat server:", ex);

            throw new ServerException(ex);
        }
    }

    @Override
    public void stop() {
        try {
            tomcat.getServer().stop();
            tomcat.stop();
        } catch (LifecycleException ex) {
            LOG.error("Failed to stop embed tomcat server:", ex);

            throw new ServerException(ex);
        }
    }

    @Override
    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public static void main(String [] args) throws Exception {
        int port = 8080;
        String docBase = ".";
        String contextPath = "";

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.setBaseDir(docBase);
        tomcat.getHost().setAutoDeploy(false);

        StandardContext context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new FixContextListener());
        tomcat.getHost().addChild(context);

        tomcat.addServlet(contextPath, "homeServlet", new HomeServlet());
        context.addServletMappingDecoded("/home", "homeServlet");
        tomcat.start();
        tomcat.getServer().await();
    }
}
