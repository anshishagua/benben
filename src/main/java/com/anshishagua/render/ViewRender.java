package com.anshishagua.render;

import com.anshishagua.object.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: lixiao
 * Date: 2018/4/24
 * Time: 上午11:37
 */

public interface ViewRender {
    void init();
    void render();
    void setModelAndView(ModelAndView modelAndView);
    void setRequest(HttpServletRequest request);
    void setResponse(HttpServletResponse response);
}