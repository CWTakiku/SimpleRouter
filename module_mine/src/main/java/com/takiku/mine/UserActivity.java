package com.takiku.mine;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.takiku.common.Good;
import com.takiku.common.OrderService;
import com.takiku.lib_router.SimpleRouter;
import com.takiku.lib_router.SimpleRouterClassRegister;
import com.takiku.lib_router.SimpleRouterObj;

import java.util.List;

/**
 * 路由测试 TestActivity1
 * @author chengwl
 * @since 2020/10/10
 */
@SimpleRouterClassRegister(group = ("mine"),path = "/mine/UserActivity", type = SimpleRouterObj.SimpleRouterRegisterType.ACTIVITY)
public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        Button btnBuy = findViewById(R.id.btn_buy);
        TextView tvUsername = findViewById(R.id.tv_username);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleRouter.getInstance().build("/shopping/BuyActivity")
                        .withString("username",tvUsername.getText().toString()).navigation(UserActivity.this);
            }
        });
       EditText editText = findViewById(R.id.et_username);

       editText.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               tvUsername.setText(s);
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });

       Button btnGetOrders = findViewById(R.id.btn_get_goods);
       TextView tvOrderDes = findViewById(R.id.tv_goods_order);
       btnGetOrders.setOnClickListener(v -> {
          OrderService orderService = (OrderService) SimpleRouter.getInstance().build("/shopping/OrderServiceImp").navigation(this);
          if (orderService!=null){
              List<Good> goods = orderService.getGoods(tvUsername.getText().toString());
              if (goods==null){
                  Toast.makeText(this, "你还没有购物，快去购物吧！", Toast.LENGTH_SHORT).show();
              }else {
                  String goodListStr = "";
                  for (Good good :goods){
                      goodListStr = goodListStr  +good.toString()+"\n";
                  }
                  tvOrderDes.setText(goodListStr);
              }
          }
       });
    }
}
