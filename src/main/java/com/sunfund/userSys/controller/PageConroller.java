package com.sunfund.userSys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 实现页面跳转
 * Created by 9douyu on 2017/10/12.
 */

@Controller
public class PageConroller {

    /**
     * 跳转注册页面
     * @return string
     */
    @RequestMapping(value = "/page/login",method = RequestMethod.GET)
    public String  showRegister(){

        return "login";
    }

    /**
     * 跳转登录页面
     */
//    @RequestMapping(value = "/{param}",method = RequestMethod.GET)
//    public String showLogin(@PathVariable String param){
//        return param;
//    }


}
