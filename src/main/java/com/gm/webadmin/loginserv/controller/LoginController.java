package com.gm.webadmin.loginserv.controller;

import com.gm.webadmin.common.ao.BizRetAO;
import com.gm.webadmin.common.entity.PageRequest;
import com.gm.webadmin.common.entity.PageResult;
import com.gm.webadmin.common.utils.FileUtils;
import com.gm.webadmin.loginserv.dto.UserDTO;
import com.gm.webadmin.loginserv.service.LoginServ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

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

    /**
     * {"pageNum":"1","pageSize":"5","param":{}}
     * 测试分页
     * @param pageRequest
     * @return
     */
    @RequestMapping("/finduser")
    public BizRetAO find(@RequestBody PageRequest pageRequest, HttpServletResponse respon) {
        logger.debug("--finduser--");
        BizRetAO bizRetAO = new BizRetAO();
        PageResult result = loginServ.findPage(pageRequest);
        bizRetAO.setRetcode(0);
        bizRetAO.setMsg("success");
        bizRetAO.setData(result);
        return bizRetAO;
    }

    @PostMapping(value="/exportExcelUser")
    public void exportExcelUser(@RequestBody PageRequest pageRequest, HttpServletResponse res) {
        File file = loginServ.createUserExcelFile(pageRequest);
        FileUtils.downloadFile(res, file, file.getName());
    }

}
