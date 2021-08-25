package com.business.service.imple;

import com.business.dao.orderDOMapper;
import com.business.dao.sequenceDOMapper;
import com.business.dataObject.orderDO;
import com.business.dataObject.sequenceDO;
import com.business.error.BusinessException;
import com.business.error.EMError;
import com.business.service.ItemService;
import com.business.service.OrderService;
import com.business.service.UserService;
import com.business.service.model.ItemModel;
import com.business.service.model.OrderModel;
import com.business.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private orderDOMapper orderDOMapper;
    @Autowired
    private sequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId,Integer amount) throws BusinessException {
        //校验下单状态，商品是否存在，用户是否合法，购买数量是否正确
        ItemModel itemById = itemService.getItemById(itemId);
        if(itemById == null){
            throw new BusinessException(EMError.PARAMETER_VALIDATION_ERROR,"商品信息不存在");
        }
        UserModel userById = userService.getUserById(userId);
        if(userById == null){
            throw new BusinessException(EMError.PARAMETER_VALIDATION_ERROR,"用户信息不存在");
        }
        if(amount <= 0 || amount > 99){
            throw new BusinessException(EMError.PARAMETER_VALIDATION_ERROR,"数量信息不正确");
        }
        //校验活动信息
        if(promoId != null){
            if(promoId.intValue() != itemById.getPromoModel().getId()){
                throw new BusinessException(EMError.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
            }else if(itemById.getPromoModel().getStatus() != 2){
                throw new BusinessException(EMError.PARAMETER_VALIDATION_ERROR,"活动还未开始");
            }
        }
        //下单减库存
        boolean b = itemService.decreaseStock(itemId, amount);
        if(!b) throw new BusinessException(EMError.STOCK_NOT_ENOUGH);
        //订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setItemId(itemId);
        orderModel.setUserId(userId);
        orderModel.setAmount(amount);
        if(promoId != null){
            orderModel.setItemPrice(itemById.getPromoModel().getPromoItemPrice());
        }else {
            orderModel.setItemPrice(itemById.getPrice());
        }

        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));
        orderModel.setPromoId(promoId);
        //生成交易流水号
        orderModel.setId(generateOrderNo());
        orderDO orderDO = ModelToDO(orderModel);
        orderDOMapper.insertSelective(orderDO);
        //增加销量
        itemService.increaseSales(itemId, amount);
        //返回前端
        return orderModel;
    }
    private String generateOrderNo(){
        //订单号16位，前8位时间信息，中间6位自增序列，最后两位为分库分表位
        StringBuilder stringBuilder = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(nowDate);

        int sequence = 0;
        sequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        //拼接
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 6 - sequenceStr.length(); i++) {
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);

        stringBuilder.append("00");

        return stringBuilder.toString();
    }

    private orderDO ModelToDO(OrderModel orderModel){
        if(orderModel == null) return null;
        orderDO orderDO = new orderDO();
        BeanUtils.copyProperties(orderModel,orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDO;
    }
}
