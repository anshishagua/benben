package com.anshishagua.server;

import com.anshishagua.configuration.BeanInstanceRegistry;
import com.anshishagua.configuration.ConfigurationRegistry;
import com.anshishagua.configuration.InjectionMappingRegistry;
import com.anshishagua.configuration.MybatisConfig;
import com.anshishagua.configuration.ServerConfig;
import com.anshishagua.configuration.ViewConfig;
import com.anshishagua.controller.DispatcherServlet;
import com.anshishagua.utils.PackageUtils;
import org.eclipse.jetty.server.Server;

/**
 * User: lixiao
 * Date: 2018/4/24
 * Time: 上午11:18
 */

public class Bootstrap {
    private static ServerConfig serverConfig;
    private static ViewConfig viewConfig;
    private static MybatisConfig mybatisConfig;

    private static void init() {
        initServerConfig();
        initViewConfig();
        initMybatisConfig();
    }

    private static void initServerConfig() {
        serverConfig =  ConfigurationRegistry.getServerConfig();
    }

    private static void initViewConfig() {

    }

    private static void initMybatisConfig() {
        mybatisConfig = ConfigurationRegistry.getMybatisConfig();
        mybatisConfig.config();
    }

    public static void run(String [] args) throws Exception {
        String basePackageName = PackageUtils.getBasePackageName();
        PackageUtils.scanPackage(basePackageName, true);

        ServerConfig serverConfig = ConfigurationRegistry.getServerConfig();

        Server server = new Server(serverConfig.getPort());
        server.setHandler(new DispatcherServlet());

        System.out.println(ConfigurationRegistry.getMap());

        Bootstrap.init();

        server.start();
        server.join();
    }

    public static void run() throws Exception {
        ServerConfig serverConfig = ConfigurationRegistry.getServerConfig();
        Server server = new Server(8090);
        server.setHandler(new DispatcherServlet());
        String basePackageName = PackageUtils.getBasePackageName();
        PackageUtils.scanPackage(basePackageName, true);
        //PackageUtils.scanPackage(basePackageName, false);

        System.out.println(BeanInstanceRegistry.instanceMap);

        System.out.println(InjectionMappingRegistry.getBeanNameMap());

        server.start();
        server.join();
    }

    public static void main(String [] args) throws Exception {
        run();
    }
}
