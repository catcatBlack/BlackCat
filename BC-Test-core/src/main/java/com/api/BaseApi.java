package com.api;

import com.Cases.UserTest;
import com.Model.ConfigModel.Config;
import com.Model.ConfigModel.Http;

import com.utils.PropertiesUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class BaseApi {
    @BeforeTest
    public void setUp(){
        PropertyConfigurator.configure("D:\\测试\\BlackCat\\BC-Test-core\\src\\main\\resources\\log4j.properties");
        Http.cookieStore = new BasicCookieStore();
        Http.httpClient = HttpClients.custom().setDefaultCookieStore(Http.cookieStore).build();
    }

}
