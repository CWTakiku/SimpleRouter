package com.stl.module_test1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stl.lib_router.SimpleRouterClassRegister
import com.stl.lib_router.SimpleRouterObj


/**
 * 路由测试 KotlinActivity1
 *
 * @author why
 * @since 2021/1/20
 */
@SimpleRouterClassRegister(key = "KotlinActivity1", type = SimpleRouterObj.ACTIVITY)
class KotlinActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin1)
    }
}