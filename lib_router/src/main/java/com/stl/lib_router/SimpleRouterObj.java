package com.stl.lib_router;

import android.content.Context;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 路由跳转
 *
 * @author cwl
 * @date 2018/9/21
 */

public class SimpleRouterObj {

    /**
     * 未知
     */
    public static final int UNKNOWN = -1;
    /**
     * ACTIVITY
     */
    public static final int ACTIVITY = 0;
    /**
     * FRAGMENT
     */
    public static final int FRAGMENT = 1;
    /**
     * 静态方法
     */
    public static final int STATIC_METHOD = 2;

    @IntDef({UNKNOWN, ACTIVITY, FRAGMENT, STATIC_METHOD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SimpleRouterRegisterType {

    }

    public Class<?> clazz;

    @SimpleRouterRegisterType
    public int type = UNKNOWN;

    public Object getSupportInstance() throws IllegalAccessException, InstantiationException {
        if (type == STATIC_METHOD) {
            return null;
        }
        return clazz.newInstance();
    }

    /**
     * @return 是否为静态方法
     */
    public boolean isSupportMethod() {
        return type == STATIC_METHOD;
    }


    public static SimpleRouterObj create(Class<?> cls, @SimpleRouterRegisterType int nType) {
        SimpleRouterObj objNew = new SimpleRouterObj();
        objNew.clazz = cls;
        objNew.type = nType;
        return objNew;
    }


    /**
     * Java的封装类和基本数据类型之间不能识别，需要手动配置，今后可能需要拓展补充
     *
     * @param obj obj
     * @return Class
     */
    public static Class<?> paramObj2ClassType(Object obj) {
        Class<?> retType = obj.getClass();
        if (obj instanceof Integer) {
            retType = int.class;
        } else if (obj instanceof Boolean) {
            retType = boolean.class;
        } else if (obj instanceof Long) {
            retType = long.class;
        } else if (obj instanceof Double) {
            retType = double.class;
        } else if (obj instanceof Float) {
            retType = float.class;
        } else if (obj instanceof Byte) {
            retType = byte.class;
        } else if (obj instanceof Character) {
            retType = char.class;
        } else if (obj instanceof Short) {
            retType = short.class;
        } else if (obj instanceof CommLibCallback) {
            retType = CommLibCallback.class;
        } else if (obj instanceof Context) {
            retType = Context.class;
        }
        return retType;
    }
}
