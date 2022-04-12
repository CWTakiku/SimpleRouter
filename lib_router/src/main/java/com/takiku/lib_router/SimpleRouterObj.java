package com.takiku.lib_router;

import android.content.Context;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 路由跳转
 *
 * @author chengwl
 * @date 2022/4/4
 */

public class SimpleRouterObj {

    public Class<?> clazz; // 被注解的 Class对象 例如： MainActivity.class
    private String path; // 路由地址
    private String group; // 路由组
    @SimpleRouterRegisterType
    public int type = SimpleRouterRegisterType.ACTIVITY;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getPath() {
        return path == null ? "" : path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getGroup() {
        return group == null ? "" : group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public @interface SimpleRouterRegisterType {
        /**
         * ACTIVITY
         */
        public static final int ACTIVITY = 0;
        /**
         * Call
         */
        public static final int CALL = 1;
    }
  private SimpleRouterObj(@SimpleRouterRegisterType int type,Class<?> clazz,String path,String group){
        this.type = type;
        this.clazz = clazz;
        this.path = path;
        this.group = group;
  }

   public static SimpleRouterObj create(Class<?> clazz,@SimpleRouterRegisterType int type,String path,String group){
     return new SimpleRouterObj(type,clazz,path,group);
   }
    public static SimpleRouterObj create(Class<?> cls, @SimpleRouterRegisterType int nType) {
        SimpleRouterObj objNew = new SimpleRouterObj(nType,cls,"","");
        objNew.clazz = cls;
        objNew.type = nType;
        return objNew;
    }

}
