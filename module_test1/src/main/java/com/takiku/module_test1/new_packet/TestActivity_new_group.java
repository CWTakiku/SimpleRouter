package com.takiku.module_test1.new_packet;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.takiku.lib_router.SimpleRouterClassRegister;
import com.takiku.lib_router.SimpleRouterObj;

/**
 * @author chengwl
 * @des
 * @date:2022/4/7
 */
@SimpleRouterClassRegister(group = ("module_test_new_group"),path = "/TestActivity_new_group", type = SimpleRouterObj.SimpleRouterRegisterType.ACTIVITY)
public class TestActivity_new_group extends AppCompatActivity {
}
