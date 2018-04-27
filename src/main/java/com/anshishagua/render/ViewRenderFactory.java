package com.anshishagua.render;

/**
 * User: lixiao
 * Date: 2018/4/27
 * Time: 下午4:57
 */

public class ViewRenderFactory {
    public static ViewRender getViewRender(ViewRenderType type) {
        ViewRender render;

        switch (type) {
            case JSP:
                render = new JspViewRender();
                break;
            case VELOCITY:
                render = new VelocityViewRender();
                break;
            case THYMELEAF:
                render = new ThymeleafViewRender();
                break;
            case FREEMARKER:
                render = new FreemarkerViewRender();
                break;
            default:
                throw new RuntimeException("Unsupported view type:" + type);
        }

        render.init();

        return render;
    }
}