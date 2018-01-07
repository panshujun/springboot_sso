package com.sunfund.userSys.service;


import com.sunfund.core.common.utils.SFUserResult;
import com.sunfund.userSys.entity.SfUser;

/**
 * 用户业务接口层
 *
 * Created by bysocket on 07/02/2017.
 */
public interface UserService {

    /**
     * 根据用户名获取用户信息，包括从库的地址信息
     *
     * @param userName
     * @return
     */
    SfUser findByName(String userName);

    /**
     * 检查注册数据是否可用
     */
    SFUserResult dataCheck(String param, Integer type);

    /**
     * 注册
     */
    SFUserResult register(SfUser sfuser);

    /**
     * 用户登录
     */
    SFUserResult login(String  userName, String password);

    /**
     * 根据token获取用户信息
     */
    SFUserResult userCheck(String token);

    /**
     * 发送邮件
     */
    SFUserResult SendEmail();

    /**
     * 通过短信验证码修改密码
     * @param phone，code，password
     * @return
     */
    SFUserResult SmsSender(String phone, String code, String password);
    /**
     * 发送短信验证码
     */

    SFUserResult sendSms(String phone);


}
