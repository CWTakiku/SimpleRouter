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
@SimpleRouterClassRegister(group = ("module_test1"),path = "/TestActvity1_new", type = SimpleRouterObj.SimpleRouterRegisterType.ACTIVITY)
public class TestActvity1_new  extends AppCompatActivity {
}
