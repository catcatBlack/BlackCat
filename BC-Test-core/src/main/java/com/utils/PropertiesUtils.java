package com.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertiesUtils {
    private static ResourceBundle bundle;

    public PropertiesUtils(){
        bundle =ResourceBundle.getBundle("application", Locale.CHINA);
    }
    public PropertiesUtils(String path){
        bundle = ResourceBundle.getBundle(path,Locale.CHINA);
    }

    public String getValue(String uri){
        return bundle.getString(uri);
    }


    /*private static Logger logger = Logger.getLogger(PropertiesUtils.class);
    private static String configPath;
    private static Properties p;

    public PropertiesUtils(){
        configPath = "application.properties";
        p = new Properties();
    }
    public PropertiesUtils(String path){
        configPath = path;
        p = new Properties();
    }

    //这些方法只能放在代码块中才不报错
    //加载配置文件
    {
        InputStream is = null;
        try {
            is = PropertiesUtils.class.getClassLoader().getResourceAsStream(configPath);
            p.load(is);
        } catch (IOException e) {
            logger.warn("加载配置文件：" + configPath +",失败," + e.getMessage());
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //获取配置文件中对应key的value
    public String getValue(String key){
        String val = null;
        try{
            val = p.getProperty(key);
            if(val.equals("")){
                return null;
            }
        }catch (Exception e){
            logger.error("读取配置文件出错，" + e.getMessage());
        }
        return val;
    }*/


}
