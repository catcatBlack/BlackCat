package com.utils.JSONUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class JSONToBean {

    /*public static <T> T toBean(HttpResponse response,Class<T> clazz) throws IOException {
        String jsonstring = EntityUtils.toString(response.getEntity(), "utf-8");
        return JsonToBean(jsonstring,clazz);

    }*/

    public static <T> T JsonToBean(String Jsonstring,Class<T> clazz){
        //JSONObject jsonObject = JSONObject.fromObject(Jsonstring);
        return JSON.parseObject(Jsonstring,clazz);
    }
}
