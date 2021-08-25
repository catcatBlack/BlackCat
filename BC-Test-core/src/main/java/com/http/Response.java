package com.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Response {
    private static Logger logger = Logger.getLogger(Response.class);

    /**获取响应的状态码，进行下一步断言
     * @param response
     * @return int
     */
    public static int getStatusCode(HttpResponse response){
        int statusCode = response.getStatusLine().getStatusCode();
        logger.info("得到响应状态码：" + statusCode);
        return statusCode;
    }

    /**
     * 将响应对象转化为json对象，接下来调用util进行解析json
     */
    public static JSONObject getResponseJson(HttpResponse response) throws IOException {
        logger.info("得到响应对象的String格式,并转换为json");
        String s = EntityUtils.toString(response.getEntity(), "utf-8");
        JSONObject jsonObject = JSON.parseObject(s);
        return jsonObject;
    }

    /**
     * jsonString 转换为指定类型的 实体bean
     */
    public static <T> T getResponseJsonToBean(HttpResponse response,Class<T> clazz) throws IOException {
        logger.info("得到响应对象的String格式,并转换为java bean");
        String s = EntityUtils.toString(response.getEntity(), "utf-8");
        return JSON.parseObject(s,clazz);
    }

}
