package com.takiku.module_test2.p2;

import androidx.appcompat.app.AppCompatActivity;

import com.takiku.lib_router.SimpleRouterClassRegister;
import com.takiku.lib_router.SimpleRouterObj;

/**
 * @author chengwl
 * @des
 * @date:2022/4/8
 */
@SimpleRouterClassRegister(path = ("/p2") ,group = ("p2"), type = SimpleRouterObj.SimpleRouterRegisterType.ACTIVITY)
public class p2 extends AppCompatActivity {
}
