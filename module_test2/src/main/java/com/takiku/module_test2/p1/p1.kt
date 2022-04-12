package com.takiku.module_test2.p1

import androidx.appcompat.app.AppCompatActivity
import com.takiku.lib_router.SimpleRouterClassRegister
import com.takiku.lib_router.SimpleRouterObj

/**
 *@author chengwl
 *@des
 *@date:2022/4/8
 */
@SimpleRouterClassRegister(path = ("/p1") ,group = ("p1"), type = SimpleRouterObj.SimpleRouterRegisterType.ACTIVITY)
class p1 : AppCompatActivity() {
}