package com.sunfund.userSys.controller;

import com.alibaba.fastjson.JSONObject;
import com.sunfund.core.common.BaseController;
import com.sunfund.core.common.utils.SFUserResult;
import com.sunfund.userSys.entity.SfUser;
import com.sunfund.userSys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 用户控制层
 * <p>
 * Created by bysocket on 07/02/2017.
 */
@Slf4j
@RestController
public class UserRestController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 用过平台发送短信验证码
     */
    @RequestMapping("/user/sendsms")
    public SFUserResult sendSms(String phone){

       SFUserResult SFUserResult = userService.sendSms(phone);

        return SFUserResult;

    }

    /**
     * 通过短信验证码修改密码
     *
     * @param phone    ，code，password
     * @param code
     * @param password @return
     */
    @RequestMapping("user/update")
    public SFUserResult SmsSender(String phone, String code, String password) {

       SFUserResult SFUserResult = userService.SmsSender(phone,code,password);
        logger.info("ssssssss");
        return SFUserResult;

    }
        /**
         * 发送邮件
         */
    @RequestMapping("user/mail")
    public SFUserResult testSendEmail(){

       SFUserResult SFUserResult = userService.SendEmail();

       return SFUserResult;
    }




    /**
     * 用token获取用户身份信息
     */
    @RequestMapping("/user/token")
    public Object userCheck(@RequestParam String token){

       SFUserResult SFUserResult = userService.userCheck(token);

        return SFUserResult;

    }


    /**
     * 用户登录
     * @param userName,password
     * @return sFuserResult
     */
    @RequestMapping("user/login" )
    public SFUserResult login(@RequestParam("userName") String userName, @RequestParam("password") String password){

       SFUserResult login = userService.login(userName,password);


       return login;

    }


    /**
     * 用户注册
     * @param
     * @return  sFuser
     *
     */
    @RequestMapping("/user/register")
    public SFUserResult register(SfUser sfUser){

        SFUserResult register  =  userService.register(sfUser);
        return register;
    }



    /**
     * 数据校验
     *
     * @param param,type,type=1为用户名，2为：邮箱，3：电话
     * @return
     */
    @RequestMapping(value = "/{param}/{type}",method = RequestMethod.GET)
    public SFUserResult dataCheck(@PathVariable String param, @PathVariable Integer type) {

        SFUserResult dataCheck = userService.dataCheck(param,type);


        return dataCheck;

    }


    /**
     * 根据用户名获取用户信息，包括从库的地址信息
     *
     * @param userName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public JSONObject findByName(@RequestParam(value = "userName", required = true) String userName) {
        SfUser user = userService.findByName(userName);
        JSONObject obj = JSONObject.parseObject(JSONObject.toJSONString(user));
        logger.info("obj=" + obj.toJSONString());
        return obj;
    }

    @ResponseBody
    @RequestMapping(value = "/api/userTest", method = RequestMethod.POST)
    public JSONObject testUser(HttpServletRequest request) {
        String admin = request.getParameter("userName");
        logger.info("sssss=" + admin);
        JSONObject obj = new JSONObject();
        obj.put("aa", "1111111");
        return obj;
    }

//    //mock数据
//    @ResponseBody
//    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
//    public JSONObject login1(HttpServletRequest request) {
//        String admin = request.getParameter("userName");
//        logger.info("sssss=" + admin);
//        JSONObject obj = new JSONObject();
//        obj.put("aa", "1111111");
//        return obj;
//    }

//    @ResponseBody
//    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
//    public JSONObject register(HttpServletRequest request) {
//        String admin = request.getParameter("userName");
//        logger.info("sssss=" + admin);
//        JSONObject obj = new JSONObject();
//        obj.put("aa", "1111111");
//        return obj;
//    }

}
