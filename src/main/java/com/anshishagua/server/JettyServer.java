package com.anshishagua.server;

import com.anshishagua.controller.DispatcherServlet;
import com.anshishagua.utils.PackageUtils;
import org.eclipse.jetty.server.Server;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 上午10:37
 */

public class JettyServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8989);
        server.setHandler(new DispatcherServlet());
        PackageUtils.scanPackage(PackageUtils.getBasePackageName(), true);
        server.start();
        server.join();
    }
}
