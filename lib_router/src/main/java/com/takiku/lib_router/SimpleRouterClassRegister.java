package com.takiku.lib_router;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chengwl
 * @since 2021/1/20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SimpleRouterClassRegister {
    /**
     * 跳转KEY
     *
     * @return KEY
     */
    String path();

     String group()  default "";

    /**
     * 类型是SimpleRouter里的定义
     *
     * @return 类型
     */
    @SimpleRouterObj.SimpleRouterRegisterType
     int type();
}
