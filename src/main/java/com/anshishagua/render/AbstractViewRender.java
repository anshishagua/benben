package com.anshishagua.render;

import com.anshishagua.object.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: lixiao
 * Date: 2018/4/24
 * Time: 上午11:45
 */

public abstract class AbstractViewRender implements ViewRender {
    protected ModelAndView modelAndView;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public AbstractViewRender() {

    }

    public AbstractViewRender(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.response.setCharacterEncoding("UTF-8");
    }

    public void init() {

    }

    public void setModelAndView(ModelAndView modelAndView) {
        this.modelAndView = modelAndView;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public abstract void render();
}