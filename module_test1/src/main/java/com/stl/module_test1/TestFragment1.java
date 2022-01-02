package com.stl.module_test1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.stl.lib_router.SimpleRouterClassRegister;
import com.stl.lib_router.SimpleRouterObj;

/**
 * 路由测试 TestFragment1
 *
 * @author why
 * @since 2021/1/20
 */
@SimpleRouterClassRegister(key = "TestFragment1", type = SimpleRouterObj.FRAGMENT)
public class TestFragment1 extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test1, container, false);
    }
}