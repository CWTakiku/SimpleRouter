package com.stl.simplerrouterdemo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.stl.lib_router.SimpleRouter
import com.stl.lib_router.SimpleRouterClassRegister
import com.stl.lib_router.SimpleRouterObj

@SimpleRouterClassRegister(key = "FragmentActivity", type = SimpleRouterObj.ACTIVITY)
class FragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        init()
    }

    private fun init() {
        val fragment1 = SimpleRouter.getInstance().getFragment("TestFragment1")
        val fragment2 = SimpleRouter.getInstance().getFragment("TestFragment2")


        val beginTransaction = supportFragmentManager.beginTransaction()
        fragment1?.let {
            beginTransaction.add(R.id.container, it)
            beginTransaction.hide(it)
        }
        fragment2?.let {
            beginTransaction.add(R.id.container, it)
            beginTransaction.hide(it)
        }
        beginTransaction.commit()

        findViewById<Button>(R.id.bt1).setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            fragment1?.let {
                transaction.show(it)
            }
            fragment2?.let {
                transaction.hide(it)
            }
            transaction.commit()
        }

        findViewById<Button>(R.id.bt2).setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            fragment2?.let {
                transaction.show(it)
            }
            fragment1?.let {
                transaction.hide(it)
            }
            transaction.commit()
        }
    }
}