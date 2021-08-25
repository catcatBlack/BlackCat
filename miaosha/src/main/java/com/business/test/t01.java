package com.business.test;

import com.business.dao.UserDOMapper;
import com.business.dataObject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@MapperScan("com.business.dao")
@RequestMapping("/")
public class t01 {
    @Autowired
    public UserDOMapper userDOMapper;

    public static void main(String[] args) {
        t01 t01 = new t01();
        System.out.println(t01.userinfo());
    }
    public String userinfo(){
        UserDO userDO = userDOMapper.selectByPrimaryKey(1);
        if(userDO == null){
            return "null";
        }else {
            return userDO.getName();
        }
    }
}
