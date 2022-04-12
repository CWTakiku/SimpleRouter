package com.takiku.lib_router;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单的跨组件跳转功能，如果将来有复杂的，就再引入新的源码
 * <p>
 * 1、跨组件间的Activity直接跳转（类似startActivity）
 * 2、跨组件间的Fragment的使用
 * 3、跨组件间的Method的使用
 *
 * @author kingpang
 * @update chengwl
 * @version v1.0.2
 * @since 2021/1/20
 */
public class SimpleRouter {
    private static final String TAG = "SimpleRouter";
    private static SimpleRouter simpleRouter = null;
    private static Map<String, SimpleRouterObj> routerObjMap = null;
    private static Application application;

    public static SimpleRouter getInstance() {
        if (null == simpleRouter) {
            synchronized (SimpleRouter.class) {
                if (null == simpleRouter) {
                    simpleRouter = new SimpleRouter();
                }
            }
        }
        return simpleRouter;
    }

    /**
     * 弃用
     *
     * @param app app
     * @deprecated 请使用 SimpleRouterApp.init() 代替
     */
    @Deprecated
    private void init(Application app) {
        init(app, new ArrayList<String>());
    }

    /**
     * 初始化
     * <p>
     * 请勿继续使用 SimpleRouter.getInstance().init()进行初始化
     * <p>
     * 使用 SimpleRouterApp.init() 即可
     *
     * @param app         app
     * @param memberClass memberClass
     */
    public void init(Application app, @NonNull List<String> memberClass) {
        application = app;
        if (null == routerObjMap) {
            routerObjMap = new HashMap<>(10);
        }
        findAllClass(memberClass);
    }

    /**
     * 通过动态生成的类全路径集合中反射查询 KEY
     */
    private void findAllClass(@NonNull List<String> memberClass) {
        String className;
        for (int i = 0; i < memberClass.size(); i++) {
            className = memberClass.get(i);
            Class<?> clazz;
            try {
                clazz = Class.forName(className);
                if (clazz != null) {
                    if (clazz.isAnnotationPresent(SimpleRouterClassRegister.class)) {
                        String key = clazz.getAnnotation(SimpleRouterClassRegister.class).path();
                        SimpleRouterObj type = SimpleRouterObj.create(clazz, clazz.getAnnotation(SimpleRouterClassRegister.class).type());
                        routerObjMap.put(key, type);
                    }
                }
            } catch (ClassNotFoundException e) {
                Log.e(TAG, "SimpleRouter Class NotFound: " + className);
            }
        }
    }

    /**
     * 获取路由对象
     *
     * @param strKey Key
     * @return 路由对象
     */
    private SimpleRouterObj getSimpleRouterObj(String strKey) {
        if (null != routerObjMap) {
            return routerObjMap.get(strKey);
        }
        return null;
    }

    /**
     * 跳转
     *
     * @param key key
     */
    public void startActivity(String key) {
        startActivity(key, null);
    }

    /**
     * 跳转
     *
     * @param key    key
     * @param bundle bundle
     */
    public void startActivity(String key, Bundle bundle) {
        startActivity(key, bundle, Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 跳转
     *
     * @param key    key
     * @param bundle bundle
     * @param flags  flags
     */
    public void startActivity(String key, Bundle bundle, int flags) {
        SimpleRouterObj curSimpleRouterObj = getSimpleRouterObj(key);
        if (curSimpleRouterObj != null && curSimpleRouterObj.type == SimpleRouterObj.SimpleRouterRegisterType.ACTIVITY) {
            Intent intent = new Intent(application, curSimpleRouterObj.getClazz());
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            intent.addFlags(flags);
            application.startActivity(intent);
            return;
        }
        Toast.makeText(application, "无法加载路由 ACTIVITY：" + key, Toast.LENGTH_LONG).show();
    }


    /**
     * Activity 启动 Activity，并获取结果
     *
     * @param activity    activity
     * @param key         路由 KEY
     * @param requestCode requestCode
     */
    public void startActivityForResult(@NonNull Activity activity, String key, int requestCode) {
        startActivityForResult(activity, key, requestCode, null);
    }

    /**
     * Activity 启动 Activity，并获取结果
     *
     * @param activity    activity
     * @param key         路由 KEY
     * @param requestCode requestCode
     * @param bundle      bundle
     */
    public void startActivityForResult(@NonNull Activity activity, String key, int requestCode, Bundle bundle) {
        SimpleRouterObj routerObj = getSimpleRouterObj(key);
        if (routerObj != null && activity != null && routerObj.type == SimpleRouterObj.SimpleRouterRegisterType.ACTIVITY) {
            Intent intent = new Intent(activity, routerObj.clazz);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            activity.startActivityForResult(intent, requestCode);
            return;
        }
        Toast.makeText(application, "无法加载路由 ACTIVITY：" + key, Toast.LENGTH_LONG).show();
    }

    /**
     * Fragment 启动 Activity，并获取结果
     *
     * @param fragment    fragment
     * @param key         路由 KEY
     * @param requestCode requestCode
     */
    public void startActivityForResult(@NonNull Fragment fragment, String key, int requestCode) {
        startActivityForResult(fragment, key, requestCode, null);
    }

    /**
     * Fragment 启动 Activity，并获取结果
     *
     * @param fragment    fragment
     * @param key         路由 KEY
     * @param requestCode requestCode
     * @param bundle      bundle
     */
    public void startActivityForResult(@NonNull Fragment fragment, String key, int requestCode, Bundle bundle) {
        SimpleRouterObj routerObj = getSimpleRouterObj(key);
        FragmentActivity activity = fragment.getActivity();
        if (routerObj != null && activity != null && routerObj.type == SimpleRouterObj.SimpleRouterRegisterType.ACTIVITY) {
            Intent intent = new Intent(activity, routerObj.clazz);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            fragment.startActivityForResult(intent, requestCode);
            return;
        }
        Toast.makeText(application, "无法加载路由 Fragment：" + key, Toast.LENGTH_LONG).show();
    }

    /**
     * 通过 KEY 获取 Fragment
     *
     * @param key key
     * @return Fragment
     */
    @Nullable
    public Fragment getFragment(String key) {
        return getFragment(key, null);
    }

    /**
     * 通过 KEY 获取 Fragment，带参数
     *
     * @param key    key
     * @param bundle bundle
     * @return Fragment
     */
    @Nullable
    public Fragment getFragment(String key, Bundle bundle) {
//        Fragment retFragment = null;
//        SimpleRouterObj curSimpleRouterObj = getSimpleRouterObj(key);
//        if (curSimpleRouterObj != null && curSimpleRouterObj.type == SimpleRouterObj.SimpleRouterRegisterType.ACTIVITY) {
//            try {
//                retFragment = (Fragment) curSimpleRouterObj.getSupportInstance();
//                if (null != bundle) {
//                    retFragment.setArguments(bundle);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (null == retFragment) {
//            Toast.makeText(application, "无法加载路由 Fragment：" + key, Toast.LENGTH_LONG).show();
//        }
//        return retFragment;
        return null;
    }

//
//    /**
//     * 执行跨组件静态方法
//     *
//     * @param key    key
//     * @param method 方法名
//     * @param args   参数
//     * @param <T>    T
//     * @return T
//     */
//    public <T> T doMethod(String key, String method, Object... args) {
//        String strCurMsg;
//        SimpleRouterObj curSimpleRouterObj = getSimpleRouterObj(key);
//        if (curSimpleRouterObj != null && curSimpleRouterObj.isSupportMethod()) {
//            try {
//                Class<?>[] parameterTypes = new Class<?>[args.length];
//                for (int i = 0; i < args.length; i++) {
//                    parameterTypes[i] = SimpleRouterObj.paramObj2ClassType(args[i]);
//                }
//
//                Method curMethod = curSimpleRouterObj.clazz.getMethod(method, parameterTypes);
//                //noinspection unchecked
//                return (T) curMethod.invoke(curSimpleRouterObj.getSupportInstance(), args);
//
//            } catch (InstantiationException e) {
//                strCurMsg = "[Instantiation]" + e.getMessage();
//            } catch (IllegalAccessException e) {
//                strCurMsg = "[Illegal Access]" + e.getMessage();
//            } catch (InvocationTargetException e) {
//                // 函数体内部异常捕获
//                strCurMsg = "[Invocation Target]" + e.getMessage();
//            } catch (NoSuchMethodException e) {
//                strCurMsg = "[No Such Method]" + e.getMessage();
//            }
//        } else {
//            strCurMsg = "class is not exist or type is not METHOD";
//        }
//
//        if (null != strCurMsg) {
//            Toast.makeText(application, String.format("无法执行静态方法\n * key：%s * \n 方法：%s \n 错误信息：%s", key, method, strCurMsg), Toast.LENGTH_LONG).show();
//        }
//        return null;
//    }
}
