package com.takiku.mine;

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
        try {
           String name =  BuildConfig.class.getPackage().getName();
           System.out.println(name);
            Class c = Class.forName("com.takiku.mine.SimpleRouter$$group$$module_mine");
            Class c1 = Class.forName("com.takiku.simple_router.SimpleRouter$$group$$module_mine");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}