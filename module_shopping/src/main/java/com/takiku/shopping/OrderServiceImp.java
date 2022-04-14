package com.takiku.shopping;

import com.takiku.common.Good;
import com.takiku.common.OrderService;
import com.takiku.lib_router.SimpleRouterClassRegister;
import com.takiku.lib_router.SimpleRouterObj;

import java.util.List;

/**
 * author:chengwl
 * Description: 订单服务的具体实现，可供其他模块调用,一定要注册是CALL类型
 * Date:2022/4/14
 */
@SimpleRouterClassRegister(path = "/shopping/OrderServiceImp" , type = SimpleRouterObj.SimpleRouterRegisterType.CALL)
public class OrderServiceImp implements OrderService {
    @Override
    public List<Good> getGoods(String userName) {
        return ServerMock.getGoods(userName);
    }
}
