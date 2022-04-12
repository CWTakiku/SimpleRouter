package com.takiku.router;

import android.app.Application;

import com.takiku.lib_router.SimpleRouter;
import com.takiku.router.SimpleRouterMembers;

import java.util.Arrays;

/**
 * @author Router
 */
public class SimpleRouterApp {

    /**
     *
     * <p>
     *
     *
     *
     * @param application application
     */
    public static void init(Application application) {
        SimpleRouter.getInstance().init(application, Arrays.asList(SimpleRouterMembers.ROUTER_PATH));
    }

    /**
     * SimpleRouter
     *
     * @return SimpleRouter
     */
    public static SimpleRouter get() {
        return SimpleRouter.getInstance();
    }
}