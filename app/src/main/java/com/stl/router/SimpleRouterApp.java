package com.stl.router;

import android.app.Application;

import com.stl.lib_router.SimpleRouter;

import java.util.Arrays;

/**
 * @author Router
 */
public class SimpleRouterApp {

    /**
     * 路由初始化类，自动生成
     * <p>
     * 请勿使用 SimpleRouter.getInstance().init()进行初始化
     * 使用 SimpleRouterApp.init() 即可
     *
     * @param application application
     */
    public static void init(Application application) {
        SimpleRouter.getInstance().init(application, Arrays.asList(SimpleRouterMembers.ROUTER_PATH));
    }

    /**
     * 代理 SimpleRouter
     *
     * @return SimpleRouter
     */
    public static SimpleRouter get() {
        return SimpleRouter.getInstance();
    }
}