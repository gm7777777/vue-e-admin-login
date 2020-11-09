package com.gm.webadmin.loginserv.service;

import com.gm.webadmin.loginserv.dto.UserDTO;

public interface LoginServ {

    public boolean doLogin(UserDTO userdto);

    public boolean registerUser(UserDTO userdto);
}
