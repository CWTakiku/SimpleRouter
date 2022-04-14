package com.takiku.shopping;

import com.takiku.common.Good;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * author:chengwl
 * Description: 虚假的服务器
 * Date:2022/4/14
 */
public class ServerMock {
    private static HashMap<String, List<Good>> orderHashMap = new HashMap<>();

    public static void buy(String userName, String goodName) {
        List<Good> goods = orderHashMap.get(userName);
        if (goods == null) {
            goods = new ArrayList<>();
        }
        goods.add(new Good(userName, goodName, System.currentTimeMillis() + ""));
        orderHashMap.put(userName, goods);
    }

    public static List<Good> getGoods(String userName){
        return orderHashMap.get(userName);
    }

}
