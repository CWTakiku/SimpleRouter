package com.stl.router;

import android.app.Application;

import com.stl.lib_router.SimpleRouter;

import java.util.Arrays;

/**
 * @author Router
 */
public class SimpleRouterApp {

    /**
     * ·�ɳ�ʼ���࣬�Զ�����
     * <p>
     * ����ʹ�� SimpleRouter.getInstance().init()���г�ʼ��
     * ʹ�� SimpleRouterApp.init() ����
     *
     * @param application application
     */
    public static void init(Application application) {
        SimpleRouter.getInstance().init(application, Arrays.asList(SimpleRouterMembers.ROUTER_PATH));
    }

    /**
     * ���� SimpleRouter
     *
     * @return SimpleRouter
     */
    public static SimpleRouter get() {
        return SimpleRouter.getInstance();
    }
}