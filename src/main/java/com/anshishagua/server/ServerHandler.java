package com.anshishagua.server;

import com.anshishagua.controller.DispatcherServlet;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 上午9:52
 */

public class ServerHandler extends AbstractHandler {
    private final HttpServlet servlet = new DispatcherServlet();

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        servlet.service(request, response);
    }
}