package com.takiku.common;

import com.takiku.lib_router.Call;

import java.util.List;

/**
 * author:chengwl
 * Description: 获取购买订单接口，下沉到公共模块，由具体shopping模块去实现该服务
 * Date:2022/4/14
 */
public interface OrderService extends Call {
   List<Good> getGoods(String userName);
}
