package com.takiku.shopping.buy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.takiku.lib_router.SimpleRouterClassRegister;
import com.takiku.lib_router.SimpleRouterObj;
import com.takiku.shopping.R;
import com.takiku.shopping.ServerMock;

/**
 * 购买
 * @author chengwl
 * @since 2020/10/10
 */
@SimpleRouterClassRegister(path = "BuyActivity" , type = SimpleRouterObj.SimpleRouterRegisterType.ACTIVITY)
public class BuyActivity extends Activity {

    private EditText editText;
    private  String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
         username = getIntent().getStringExtra("username");
        TextView textView = findViewById(R.id.tv_user);
        editText = findViewById(R.id.et_buy);
        textView.setText("用户名："+username+"正在购物");
        Button button = findViewById(R.id.btn_confirm);
        button.setOnClickListener(v -> {
            //模拟购买
            buy();
        });
    }

    private void buy() {
        ServerMock.buy(username,editText.getText().toString());
        finish();
    }
}
