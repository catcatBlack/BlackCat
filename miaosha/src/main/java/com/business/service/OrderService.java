package com.business.service;

import com.business.error.BusinessException;
import com.business.service.model.OrderModel;

public interface OrderService {

    //通过前端url上传的秒杀活动的id，然后在下单接口内校验对应id是否秒杀活动开始
    OrderModel createOrder(Integer userId,Integer itemId,Integer promoId,Integer amount) throws BusinessException;
}
