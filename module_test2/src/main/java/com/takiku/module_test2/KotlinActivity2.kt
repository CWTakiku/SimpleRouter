package com.takiku.module_test2

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stl.module_test2.R
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

        // 测试返回数据
        intent.putExtra("BACK_KEY", "KotlinActivity2 返回信息")
        setResult(Activity.RESULT_OK, intent)
    }
}