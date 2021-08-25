package com.Cases;

import com.Model.ServiceModel.userGetModel;
import com.Response.ReturnType;
import com.api.BaseApi;

import com.utils.DatabaseUtil;
import com.utils.PropertiesUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.http.RestClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import static com.http.RestClient.post;
import static com.utils.JSONUtils.JSONToBean.JsonToBean;



public class UserTest extends BaseApi {

    public static Logger logger = Logger.getLogger(UserTest.class);
    private static String getUser;
    @BeforeClass
    public void beforeclass(){
        PropertiesUtils propertiesUtils = new PropertiesUtils();
        String url = propertiesUtils.getValue("test.uri");
        String getUserUri = propertiesUtils.getValue("getUser.uri");
        getUser = url + getUserUri;
        logger.debug("获得指定用户的接口地址：" + getUser);
    }

    @Test
    public void userGetTest() throws IOException {
        //获得实际结果
        Map<String,String> param = new HashMap();
        param.put("id","14");
        HttpResponse geturl = post(getUser,new HashMap<>(),param);
        String res = EntityUtils.toString(geturl.getEntity(), "utf-8");

        //System.out.println(res);
        //不能将Response继续传入toBean，会显示IO Stream关闭的错误
        ReturnType returnType = JsonToBean(res,ReturnType.class);
        String result = returnType.getStatus();
        logger.debug("获得指定用户的实际状态：" + result);
        //System.out.println(returnType.getStatus());

        //获得数据库里的预期结果
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        userGetModel userget = sqlSession.selectOne("userget", 14);
        String exp = userget.getExpected();
        logger.debug("获得指定用户的预期状态：" + exp);
        //System.out.println(userget.getExpected());
        Assert.assertEquals(result,exp,"未成功获得user");
    }
}
