package com.business.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.xml.transform.Source;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    //实现校验方法并返回校验结果
    public Result validate(Object bean){
        Result result = new Result();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if(constraintViolationSet.size() > 0){
            result.setHasErrors(true);
            for (ConstraintViolation<Object> constraintViolation : constraintViolationSet) {
                String propertyname = constraintViolation.getPropertyPath().toString();
                String message = constraintViolation.getMessage();
                result.getErrMsgMap().put(propertyname,message);
            }
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //通过工厂的初始化方式使hibernate validator实例化
        this.validator = (Validator) Validation.buildDefaultValidatorFactory().getValidator();
    }
}
