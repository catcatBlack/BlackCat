package com.business.service.imple;

import com.business.dao.UserDOMapper;
import com.business.dao.userPasswordDOMapper;
import com.business.dataObject.UserDO;
import com.business.dataObject.userPasswordDO;
import com.business.error.BusinessException;
import com.business.error.EMError;
import com.business.service.UserService;
import com.business.service.model.UserModel;
import com.business.validator.Result;
import com.business.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.util.DuplicateFormatFlagsException;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private userPasswordDOMapper userPasswordDOMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(int id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        userPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(id);
        return DOToModel(userDO,userPasswordDO);
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException{
        if(userModel == null){
            throw new BusinessException(EMError.PARAMETER_VALIDATION_ERROR);
        }
        /*if(StringUtils.isEmpty(userModel.getName())
            || userModel.getAge() == null
            || userModel.getGender() == null
            || StringUtils.isEmpty(userModel.getPhone())){
            throw new BusinessException(EMError.PARAMETER_VALIDATION_ERROR);
        }*/
        Result validate = validator.validate(userModel);
        if(validate.isHasErrors()){
            throw new BusinessException(EMError.PARAMETER_VALIDATION_ERROR,validate.getErrMsg());
        }
        //model转成dataobject方法
        UserDO userDO = ModelToDO(userModel);
        try{
            userDOMapper.insertSelective(userDO);
        }catch (DuplicateKeyException e){
            throw  new BusinessException(EMError.PARAMETER_VALIDATION_ERROR,"手机号已注册");
        }
        userModel.setId(userDO.getId());
        userPasswordDO userPasswordDO = ModelToPassDO(userModel);
        //userPasswordDO.setUserId(userDO.getId());
        userPasswordDO.setId(2);
        userPasswordDOMapper.insertSelective(userPasswordDO);

        return;
    }

    @Override
    public UserModel validLogin(String phone, String enscrpassword) throws BusinessException {
        //通过用户手机获取登录信息
        UserDO userDO = userDOMapper.selectByPhone(phone);
        if(userDO == null) throw new BusinessException(EMError.USER_LOGIN_FAIL);
        userPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = DOToModel(userDO,userPasswordDO);
        //比对加密密码是否和传入密码相匹配
        if(!StringUtils.equals(enscrpassword,userModel.getPassword())){
            throw new BusinessException(EMError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    private UserDO ModelToDO(UserModel userModel){
        if(userModel == null) return null;
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }
    private userPasswordDO ModelToPassDO(UserModel userModel){
        if(userModel == null) return null;
        userPasswordDO userPasswordDO = new userPasswordDO();
        userPasswordDO.setPassword(userModel.getPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }


    private UserModel DOToModel(UserDO userDO, userPasswordDO userPasswordDO){
        if(userDO == null) return null;
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        if(userPasswordDO != null) userModel.setPassword(userPasswordDO.getPassword());
        return userModel;
    }

}
