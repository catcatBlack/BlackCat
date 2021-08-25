package com.http;

import com.Model.ConfigModel.Http;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestClient {
    private static Logger logger = Logger.getLogger(RestClient.class);

    //1.GET

    public static HttpResponse get(String url) throws IOException {
        return get(url,null);
    }
    public static HttpResponse get(String url, Map<String,String> headers) throws IOException {
        HttpGet get = new HttpGet(url);
        //处理请求头
        if(headers != null && headers.size() > 0){
            for(Map.Entry<String,String> entry : headers.entrySet()){
                get.addHeader(entry.getKey(), entry.getValue());
            }
        }
        //执行请求
        CloseableHttpResponse response = Http.httpClient.execute(get);
        return response;
    }

    //2.POST
    public static HttpResponse post(String url,Map<String,String> headers,String jsonString) throws IOException {
        headers.put("Content-Type","application/json");
        return post(url,headers,jsonString,null);

    }
    public static HttpResponse post(String url,Map<String,String> headers,Map<String,String> parameters) throws IOException {
        headers.put("Content-Type","application/x-www-form-urlencoded");
        return post(url,headers,null,parameters);
    }
    public static HttpResponse post(String url,Map<String,String> headers,String jsonString,Map<String,String> parameters) throws IOException {
        HttpPost post = new HttpPost(url);

        //用json的String形式创建参数队列
        if(jsonString != null && !jsonString.equals("")){
            post.setEntity(new StringEntity(jsonString,"utf-8"));
        }else {
            //构造请求体，创建参数队列
            List<NameValuePair> nvps = new ArrayList<>();
            if(parameters != null && parameters.size() > 0){
                for(Map.Entry<String,String> entry : parameters.entrySet()){
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            post.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
        }
        //加载请求头
        if(headers != null && headers.size() > 0){
            for(Map.Entry<String,String> entry : headers.entrySet()){
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }

        CloseableHttpResponse response = Http.httpClient.execute(post);
        return response;
    }

    //3.PUT
    public static HttpResponse put(String url,Map<String,String> headers,String jsonString) throws IOException {
        headers.put("Content-Type","application/json");
        return put(url,headers,jsonString,null);
    }
    public static HttpResponse put(String url,Map<String,String> headers,Map<String,String> parameters) throws IOException {
        return put(url,headers,null,parameters);
    }
    public static HttpResponse put(String url,Map<String,String> headers,String jsonString,Map<String,String> parameters) throws IOException {
        HttpPut put = new HttpPut(url);

        if(headers != null && headers.size() > 0){
            for(Map.Entry<String,String> entry : headers.entrySet()){
                put.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if(jsonString != null && !jsonString.equals("")){
            put.setEntity(new StringEntity(jsonString,"utf-8"));
        }else {
            //构造请求体，创建参数队列
            List<NameValuePair> nvps = new ArrayList<>();
            if(parameters != null && parameters.size() > 0){
                for(Map.Entry<String,String> entry : parameters.entrySet()){
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            put.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
        }

        CloseableHttpResponse response = Http.httpClient.execute(put);
        return response;
    }

    //4.DELETE
    public static HttpResponse delete(String url) throws IOException {
        HttpDelete delete  = new HttpDelete(url);
        CloseableHttpResponse response = Http.httpClient.execute(delete);
        return response;
    }

}


