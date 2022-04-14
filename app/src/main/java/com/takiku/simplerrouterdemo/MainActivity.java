package com.takiku.simplerrouterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.takiku.lib_router.SimpleRouter;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_KOTLIN = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_user_java).setOnClickListener(this);
        findViewById(R.id.btn_1_java_fragment).setOnClickListener(this);
        findViewById(R.id.btn_1_kotlin).setOnClickListener(this);
        findViewById(R.id.btn_1_kotlin_fragment).setOnClickListener(this);

        findViewById(R.id.btn_2_java).setOnClickListener(this);
        findViewById(R.id.btn_2_kotlin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_user_java) {
            SimpleRouter.getInstance().build("/module_mine/UserActivity").navigation(this);
          //  SimpleRouterApp.get().startActivity("TestActivity1");
        } else if (id == R.id.btn_1_java_fragment) {
          //  SimpleRouterApp.get().startActivity("FragmentActivity");
        } else if (id == R.id.btn_1_kotlin) {
          //  SimpleRouterApp.get().startActivity("KotlinActivity1");
        } else if (id == R.id.btn_1_kotlin_fragment) {
          //  SimpleRouterApp.get().startActivity("FragmentActivity");
        } else if (id == R.id.btn_2_java) {
          //  SimpleRouterApp.get().startActivity("TestActivity2");
        } else if (id == R.id.btn_2_kotlin) {
          //  SimpleRouterApp.get().startActivityForResult(this, "KotlinActivity2", REQUEST_CODE_KOTLIN);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == REQUEST_CODE_KOTLIN && resultCode == RESULT_OK) {
            String backExtra = data.getStringExtra("BACK_KEY");
            Toast.makeText(this, backExtra, Toast.LENGTH_SHORT).show();
        }
    }
}