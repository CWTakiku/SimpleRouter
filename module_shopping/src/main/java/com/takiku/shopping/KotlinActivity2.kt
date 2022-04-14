package com.takiku.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.takiku.lib_router.SimpleRouterClassRegister
import com.takiku.lib_router.SimpleRouterObj

/**
 * 路由测试 KotlinActivity1
 *
 * @author why
 * @since 2021/1/20
 */
@SimpleRouterClassRegister(path = ("KotlinActivity2") , type = SimpleRouterObj.SimpleRouterRegisterType.ACTIVITY)
class KotlinActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin2)
    }
}