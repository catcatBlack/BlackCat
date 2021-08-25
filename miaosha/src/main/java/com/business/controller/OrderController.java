package com.business.controller;

import com.business.error.BusinessException;
import com.business.error.EMError;
import com.business.response.CommonReturnType;
import com.business.service.OrderService;
import com.business.service.model.OrderModel;
import com.business.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
@CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
public class OrderController extends BaseController{
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    //用户下单接口
    @RequestMapping("/createorder")
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "itemId") Integer itemId,
                                        @RequestParam(name = "amount") Integer amount,
                                        @RequestParam(name = "promoId",required = false) Integer promoId) throws BusinessException {
        //获取用户登录信息
        Boolean is_login = (Boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(is_login == null || !is_login.booleanValue()){
            throw new BusinessException(EMError.USER_NOT_LOGININ);
        }
        UserModel login_user = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        int id = login_user.getId();
        OrderModel order = orderService.createOrder(id, itemId,promoId, amount);
        return CommonReturnType.create(order);
    }
}
