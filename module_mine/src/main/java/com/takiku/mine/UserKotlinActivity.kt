package com.takiku.mine

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
@SimpleRouterClassRegister(group = ("mine"),path = "/mine/UserKotlinActivity", type = SimpleRouterObj.SimpleRouterRegisterType.ACTIVITY)
class UserKotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin1)
    }
}