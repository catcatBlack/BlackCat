package com.business.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Result {

    //校验是否有错
    private boolean hasErrors;

    //存放错误信息
    private Map<String,String> errMsgMap;

    public Result(){
        hasErrors = false;
        errMsgMap = new HashMap<>();
    }

    //实现通过格式化字符串信息获取错误结果的Msg的方法
    public String getErrMsg(){
        return StringUtils.join(errMsgMap.values(),",");
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrMsgMap() {
        return errMsgMap;
    }

    public void setErrMsgMap(Map<String, String> errMsgMap) {
        this.errMsgMap = errMsgMap;
    }
}
