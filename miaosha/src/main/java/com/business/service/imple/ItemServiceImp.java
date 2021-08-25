package com.business.service.imple;

import com.business.dao.itemDOMapper;
import com.business.dao.itemStockDOMapper;
import com.business.dataObject.itemDO;
import com.business.dataObject.itemStockDO;
import com.business.error.BusinessException;
import com.business.error.EMError;
import com.business.service.ItemService;
import com.business.service.PromoService;
import com.business.service.model.ItemModel;
import com.business.service.model.PromoModel;
import com.business.validator.Result;
import com.business.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImp implements ItemService {
    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private itemDOMapper itemDOMapper;
    @Autowired
    private itemStockDOMapper itemStockDOMapper;
    @Autowired
    private PromoService promoService;

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //校验入参
        Result validate = validator.validate(itemModel);
        if(validate.isHasErrors()){
            throw new BusinessException(EMError.PARAMETER_VALIDATION_ERROR,validate.getErrMsg());
        }
        //model->DO
        itemDO itemDO = ModelToDO(itemModel);

        //写入数据库
        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());
        itemStockDO itemStockDO = ModelToStockDO(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);
        //返回创建完成的对象
        return getItemById(itemModel.getId());
    }
    private itemStockDO ModelToStockDO(ItemModel itemModel){
        if(itemModel == null) return null;
        itemStockDO itemStockDO = new itemStockDO();
        itemStockDO.setStock(itemModel.getStock());
        itemStockDO.setItemId(itemModel.getId());
        return itemStockDO;
    }
    private itemDO ModelToDO(ItemModel itemModel){
        if(itemModel == null) return null;
        itemDO itemDO = new itemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        //double传到前端会有精度问题，所以要在内部引入BIgDem..
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }

    @Override
    public List<ItemModel> listItem() {
        List<itemDO> list = itemDOMapper.listItem();
        List<ItemModel> collect = list.stream().map(itemDO -> {
            itemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel = DOToModel(itemDO, itemStockDO);
            return itemModel;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        itemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if(itemDO == null) return null;
        itemStockDO itemStockDO = itemStockDOMapper.selectByItemId(id);

        ItemModel itemModel = DOToModel(itemDO, itemStockDO);
        PromoModel promoByItemId = promoService.getPromoByItemId(itemModel.getId());
        if(promoByItemId != null && promoByItemId.getStatus() != 3){
            itemModel.setPromoModel(promoByItemId);
        }

        return itemModel;

    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) {
        int i = itemStockDOMapper.decreaseStock(itemId, amount);
        if(i > 0) return true;
        return false;
    }

    @Override
    public void increaseSales(Integer itemId, Integer amount) {
        itemDOMapper.increaseSales(itemId,amount);
    }

    private ItemModel DOToModel(itemDO itemDO,itemStockDO itemStockDO){
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }
}
