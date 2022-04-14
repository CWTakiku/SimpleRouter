package com.takiku.lib_router;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

import androidx.annotation.RequiresApi;

/**
 * author:chengwl
 * Description:
 * Date:2022/4/14
 */
public class SimpleRouter {
    private volatile static SimpleRouter instance;
    private LruCache<String, RouterGroup> groupLruCache;
    private LruCache<String, RouterPath> pathLruCache;

    private String group; // 路由的组名
    private String path; // 路由的路径
    private final static String FILE_GROUP_NAME = "SimpleRouter$$group$$";
    private SimpleRouter(){
        groupLruCache = new LruCache<>(100);
        pathLruCache = new LruCache<>(100);
    }

    public static SimpleRouter getInstance() {
        if (instance == null){
            synchronized (SimpleRouter.class){
                if (instance == null){
                    instance = new SimpleRouter();
                }
            }
        }
        return instance;
    }

    public BundleManager build(String path) {
        if (TextUtils.isEmpty(path) || !path.startsWith("/")) {
            throw new IllegalArgumentException("路径非法");
        }

        if (path.lastIndexOf("/") == 0) { // 只写了一个 /
            throw new IllegalArgumentException("路径非法");
        }

        // 截取组名  /order/Order_MainActivity  finalGroup=order
        String finalGroup = path.substring(1, path.indexOf("/", 1)); // finalGroup = order

        if (TextUtils.isEmpty(finalGroup)) {
            throw new IllegalArgumentException("路径非法");
        }

        // 证明没有问题，没有抛出异常
        this.path =  path;  // 最终的效果：如 /order/Order_MainActivity
        this.group = finalGroup; // 例如：order，personal

        // TODO 走到这里后  grooup 和 path 没有任何问题   app，order，personal      /app/MainActivity

        return new BundleManager(); // Builder设计模式 之前是写里面的， 现在写外面吧
    }

    // 真正的导航
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Object navigation(Context context, BundleManager bundleManager) {
        // 例如：寻找 ARouter$$Group$$personal  寻址   ARouter$$Group$$order   ARouter$$Group$$app
        String groupClassName = "com.takiku.simple_router" + "." + FILE_GROUP_NAME + group;
        Log.e("derry >>>", "navigation: groupClassName = " + groupClassName);


        try {
            RouterGroup loadGroup = groupLruCache.get(group);
            if (null == loadGroup) { // 缓存里面没有东东
                // 加载APT路由组Group类文件 例如：ARouter$$Group$$order
                Class<?> aClass = Class.forName(groupClassName);
                // 初始化类文件
                loadGroup = (RouterGroup) aClass.newInstance();

                // 保存到缓存
                groupLruCache.put(group, loadGroup);
            }

            if (loadGroup.getGroupMap().isEmpty()) {
                throw new RuntimeException("路由表Group出错..."); // Group这个类 加载失败
            }

            // TODO 第二步 读取路由Path类文件
            RouterPath loadPath = pathLruCache.get(path);
            if (null == loadPath) { // 缓存里面没有东东 Path
                // 1.invoke loadGroup
                // 2.Map<String, Class<? extends RouterLoadPath>>
                Class<? extends RouterPath> clazz = loadGroup.getGroupMap().get(group);

                // 3.从map里面获取 Router$$Path$$personal.class
                loadPath = clazz.newInstance();

                // 保存到缓存
                pathLruCache.put(path, loadPath);
            }

            // TODO 第三步 跳转
            if (loadPath != null) { // 健壮
                if (loadPath.getPathMap().isEmpty()) { // pathMap.get("key") == null
                    throw new RuntimeException("路由表Path出错...");
                }

                // 最后才执行操作
                SimpleRouterObj obj = loadPath.getPathMap().get(path);

                if (obj != null) {
                    switch (obj.getType()) {
                        case SimpleRouterObj.SimpleRouterRegisterType.ACTIVITY:
                            Intent intent = new Intent(context, obj.getClazz());
                            intent.putExtras(bundleManager.getBundle()); // 携带参数
                            context.startActivity(intent);
                            break;

                        case SimpleRouterObj.SimpleRouterRegisterType.CALL:
                            // OrderAddressImpl.class  OrderBean getOrderBean
                            Class<?> clazz = obj.getClazz(); //
                            Call call = (Call) clazz.newInstance();
                            bundleManager.setCall(call);
                            return bundleManager.getCall();

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



}
