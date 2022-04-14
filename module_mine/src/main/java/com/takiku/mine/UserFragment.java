package com.takiku.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import com.takiku.lib_router.SimpleRouterClassRegister;
import com.takiku.lib_router.SimpleRouterObj;


@SimpleRouterClassRegister(group = ("mine"),path = "/mine/UserFragment", type = SimpleRouterObj.SimpleRouterRegisterType.CALL)
public class UserFragment extends Fragment {

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