package com.takiku.module_test1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.stl.module_test1.R;
import com.takiku.lib_router.SimpleRouter;

/**
 * 路由测试 TestActivity1
 *
 * @author chengwl
 * @since 2020/10/10
 */
public class TestActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        Button button1 = findViewById(R.id.btn_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleRouter.getInstance().startActivity("TestActivity2");
            }
        });

        Button button2 = findViewById(R.id.btn_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleRouter.getInstance().startActivity("KotlinActivity2");
            }
        });


        // 测试返回数据
        getIntent().putExtra("BACK_KEY", "TestActivity1 返回信息");
        setResult(Activity.RESULT_OK, getIntent());


    }
}
