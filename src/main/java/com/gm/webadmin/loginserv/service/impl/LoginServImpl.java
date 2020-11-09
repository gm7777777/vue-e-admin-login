package com.gm.webadmin.loginserv.service.impl;

import com.gm.webadmin.loginserv.dao.LoginDAO;
import com.gm.webadmin.loginserv.dto.UserDTO;
import com.gm.webadmin.loginserv.entity.User;
import com.gm.webadmin.loginserv.service.LoginServ;
import com.gm.webadmin.loginserv.utils.CryptionUtil;
import com.gm.webadmin.loginserv.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class LoginServImpl implements LoginServ {


    public User packageUser(UserDTO dto){
        User user = new User();
        user.setState(0);
        user.setAccountnum(dto.getAccount());
        user.setPassword(dto.getPassword());
        return user;
    }
    @Autowired
    LoginDAO loginDao;

    @Override
    public boolean doLogin(UserDTO userdto) {
        User user = packageUser(userdto);
        int state = 0;
        User localuser = loginDao.getUser(user.getAccountnum());
        if(localuser==null){
            return false;
        }else{
            if(new String(CryptionUtil.base64Decrypt(localuser.getPassword().getBytes())).equals(user.getPassword())){
                user.setId(localuser.getId());
                user.setState(1);
                user.setUpdateTime(new Date());
                state = loginDao.updUserforlogin(user);
            }
        }

        return state>0?true:false;
    }

    @Override
    public boolean registerUser(UserDTO userdto) {
        User user = packageUser(userdto);
        User localuser = loginDao.getUser(user.getAccountnum());
        int num = 0;
        if(localuser==null){
            user.setId(UUIDUtil.getUUID());
            user.setPassword(new String(CryptionUtil.base64Encrypt(user.getPassword())));
            user.setCreateTime(new Date());
            num = loginDao.addUser(user);
        }
        return num>0?true:false;
    }
}
