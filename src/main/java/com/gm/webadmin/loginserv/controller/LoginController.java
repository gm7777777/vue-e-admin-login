package com.gm.webadmin.loginserv.controller;

import com.alibaba.fastjson.JSON;
import com.gm.webadmin.loginserv.ao.BizRetAO;
import com.gm.webadmin.loginserv.dto.UserDTO;
import com.gm.webadmin.loginserv.service.LoginServ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    LoginServ loginServ;

    @RequestMapping("/user")
    public BizRetAO login(@RequestBody UserDTO user) {
        logger.debug("--user--");
        BizRetAO bizRetAO = new BizRetAO();
        boolean flag = loginServ.doLogin(user);

        if(flag){
            bizRetAO.setRetcode(0);
            bizRetAO.setMsg("success");

        }else{
            bizRetAO.setRetcode(1);
            bizRetAO.setMsg("fail");
        }
        bizRetAO.setData(flag);
        return bizRetAO;
    }

    @RequestMapping("/register")
    public BizRetAO register(@RequestBody UserDTO user) {
        logger.debug("--register--");
        BizRetAO bizRetAO = new BizRetAO();
        boolean flag = loginServ.registerUser(user);

        if(flag){
            bizRetAO.setRetcode(0);
            bizRetAO.setMsg("success");

        }else{
            bizRetAO.setRetcode(1);
            bizRetAO.setMsg("fail");
        }
        bizRetAO.setData(flag);
        return bizRetAO;
    }
}
