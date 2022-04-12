package com.takiku.module_test1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stl.module_test1.R
import com.takiku.lib_router.SimpleRouterClassRegister
import com.takiku.lib_router.SimpleRouterObj


/**
 * 路由测试 KotlinActivity1
 *
 * @author why
 * @since 2021/1/20
 */
@SimpleRouterClassRegister(group = ("module_test1"),path = "/KotlinActivity1", type = SimpleRouterObj.SimpleRouterRegisterType.ACTIVITY)
class KotlinActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin1)
    }
}