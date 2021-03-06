package com.business.error;

public enum EMError implements CommonError{
    //通用错误类型00001
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),

    UNKNOWN_ERROR(10002,"未知错误"),
    //10000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_LOGIN_FAIL(20002,"用户手机号或密码错误"),
    USER_NOT_LOGININ(20003,"用户还未登录"),
    //交易错误
    STOCK_NOT_ENOUGH(30001,"库存不足")
    ;

    private EMError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;


    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String getErrMsg) {
        this.errMsg = getErrMsg;
        return this;
    }
}
