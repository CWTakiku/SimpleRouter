package com.stl.simplerrouterdemo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        String path = "asda";
        int a= 3;
        String conte = "D:\\android_project\\simplerouter\\app";
        System.out.println(conte);
        int index = conte.lastIndexOf("\\");
        System.out.println(index);
      //  String content = "   pathMap.put(\""+routerBean.getPath()"\","+"SimpleRouterObj.create("+routerBean.getClazz()","+routerBean.getType()",\""+routerBean.getPath()"\",\""+routerBean.getGroup()+"\"));"
       //ystem.out.println(content);
    }
}