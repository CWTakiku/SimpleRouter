package com.takiku.module_test2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.stl.module_test2.R
import com.takiku.lib_router.SimpleRouter
import com.takiku.lib_router.SimpleRouterClassRegister
import com.takiku.lib_router.SimpleRouterObj


private const val REQUEST_CODE = 350

@SimpleRouterClassRegister(path = "TestFragment2", type = SimpleRouterObj.SimpleRouterRegisterType.ACTIVITY)
class TestFragment2 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_test2, container, false)
        view.findViewById<TextView>(R.id.bt1).setOnClickListener {
            SimpleRouter.getInstance().startActivityForResult(this@TestFragment2, "TestActivity1", REQUEST_CODE)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null && requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val backExtra = data.getStringExtra("BACK_KEY")
            Toast.makeText(activity, backExtra, Toast.LENGTH_LONG).show()
        }
    }
}