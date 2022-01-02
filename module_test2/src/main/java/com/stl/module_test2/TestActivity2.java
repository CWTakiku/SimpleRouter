package com.stl.module_test2;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.stl.lib_router.SimpleRouterClassRegister;
import com.stl.lib_router.SimpleRouterObj;

/**
 * @author chengwl
 * @since 2020/10/10
 */
@SimpleRouterClassRegister(key = "TestActivity2", type = SimpleRouterObj.ACTIVITY)
public class TestActivity2 extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
}
