package com.business.service;

import com.business.error.BusinessException;
import com.business.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;

public interface UserService {
    UserModel getUserById(int id);
    void register(UserModel userModel) throws BusinessException;
    UserModel validLogin(String phone,String enscrpassword) throws BusinessException;
}
