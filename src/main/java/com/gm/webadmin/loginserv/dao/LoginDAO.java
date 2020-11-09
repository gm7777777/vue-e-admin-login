package com.gm.webadmin.loginserv.dao;

import com.gm.webadmin.loginserv.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LoginDAO {

    User getUser(String accountnum);

    int addUser(User user);

    int updUser(User user);
    int updUserforlogin(User user);
}
