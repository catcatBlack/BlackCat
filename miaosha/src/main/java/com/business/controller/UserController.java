package com.business.controller;

import com.business.controller.viewobject.UserVO;
import com.business.error.BusinessException;
import com.business.error.EMError;
import com.business.response.CommonReturnType;
import com.business.service.OrderService;
import com.business.service.UserService;
import com.business.service.imple.UserServiceImp;
import com.business.service.model.OrderModel;
import com.business.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/user")
@CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;



    //用户登录接口
    @RequestMapping(value = "/login",method = RequestMethod.POST,consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "phone") String phone,
                                  @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        //入参校验
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)){
            throw new BusinessException(EMError.PARAMETER_VALIDATION_ERROR);
        }

        //用户登录服务，校验登录是否合法
        UserModel userModel = userService.validLogin(phone, this.EncodeByMD5(password));

        //将登录凭证加入用户登录成功的session内
        httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        return CommonReturnType.create(null);

    }

    //用户注册接口
    @RequestMapping(value = "/register",method = RequestMethod.POST,consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "phone") String phone,
                                     @RequestParam(name = "otpCode") String otpCode,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "gender") Byte gender,
                                     @RequestParam(name = "age") Integer age,
                                     @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        //验证手机号与otp相符
        String code = (String) this.httpServletRequest.getSession().getAttribute(phone);
        if(otpCode == null || code == null || !otpCode.equals(code)){
            throw new BusinessException(EMError.PARAMETER_VALIDATION_ERROR,"验证码错误");
        }
        //用户注册流程
        UserModel userModel = new UserModel();
        userModel.setPhone(phone);
        userModel.setName(name);
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setRegisterMode("byphone");

        userModel.setPassword(this.EncodeByMD5(password));
        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    public String EncodeByMD5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        return base64Encoder.encode(md5.digest(str.getBytes("utf-8")));

    }

    //用户获取OTP短信接口
    @RequestMapping(value = "/getOtp",method = RequestMethod.POST,consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "phone") String phone){
        //按照一定规则生成otp验证码
        Random random = new Random();
        int i = random.nextInt(99999);
        i += 10000;
        String OtpCode = String.valueOf(i);
        //将验证码与对应手机用户关连,使用httpsession方式绑定
        httpServletRequest.getSession().setAttribute(phone,OtpCode);
        //将OTP验证码通过短信通道发送给用户，省略
        System.out.println(phone + " + " + OtpCode);

        return CommonReturnType.create(null);
    }

    @RequestMapping(value = {"/get"})
    @ResponseBody
    public CommonReturnType getUser(@RequestParam Integer id) throws BusinessException {
        //调用service服务获取对应id的用户对象并返回给前端
        UserModel userById = userService.getUserById(id);
        if(userById == null){
            throw new BusinessException(EMError.USER_NOT_EXIST);
        }
        UserVO userVO = ModelToVO(userById);
        //转换为固定的返回合适，包括status
        return CommonReturnType.create(userVO);
    }

    //将核心模型用户对象转化为前端使用的VO对象
    private UserVO ModelToVO(UserModel userModel){
        if(userModel == null) return null;
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }


}
