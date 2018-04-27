package com.anshishagua.render;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: lixiao
 * Date: 2018/4/24
 * Time: 上午11:45
 */

public abstract class AbstractRender {
    protected String viewName;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public AbstractRender() {

    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public AbstractRender(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.response.setCharacterEncoding("UTF-8");
    }
}