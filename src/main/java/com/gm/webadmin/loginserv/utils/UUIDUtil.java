package com.gm.webadmin.loginserv.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @program: esgov-bysbxt-webapi
 * @description: uuid生成工具类
 * @author: winney
 **/
@Component
public class UUIDUtil {

    public  static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}