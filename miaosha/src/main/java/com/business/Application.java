package com.business;

import com.business.dao.UserDOMapper;
import com.business.dataObject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.business"})
@MapperScan({"com.business.dao"})
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
