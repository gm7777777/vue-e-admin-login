package com.gm.webadmin.loginserv.service;

import com.gm.webadmin.common.entity.PageRequest;
import com.gm.webadmin.common.entity.PageResult;
import com.gm.webadmin.loginserv.dto.UserDTO;

import java.io.File;
import java.util.List;

public interface LoginServ<T> {

    public boolean doLogin(UserDTO userdto);

    public boolean registerUser(UserDTO userdto);

    int save(T record);

    int delete(T record);

    int delete(List<T> records);

    T findById(String id);

    /**
     * 分页查询
     */
    PageResult findPage(PageRequest pageRequest);

    public File createUserExcelFile(PageRequest pageRequest);
}
