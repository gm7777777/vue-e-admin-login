package com.gm.webadmin.loginserv.entity;

import com.gm.webadmin.common.entity.BaseEnt;

public class User extends BaseEnt {
    public String accountnum;

    public String password;
    public int state;

    public String getAccountnum() {
        return accountnum;
    }

    public void setAccountnum(String accountnum) {
        this.accountnum = accountnum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
