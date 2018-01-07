package com.sunfund.userSys.service.impl;

import com.sunfund.core.common.utils.SHA256;
import com.sunfund.core.common.utils.Test.smsTest;
import com.sunfund.core.common.utils.TokenProcessor;
import com.sunfund.core.common.utils.mail.MailUtil;
import com.sunfund.core.common.utils.mail.MimeMessageDTO;
import com.sunfund.core.common.utils.SFUserResult;
import com.sunfund.core.utils.RedisComponent;
import com.sunfund.userSys.dao.master.SfUserMapper;
import com.sunfund.userSys.dao.slave.RoleDao;
import com.sunfund.userSys.entity.Role;
import com.sunfund.userSys.entity.SfUser;
import com.sunfund.userSys.entity.SfUserExample;
import com.sunfund.userSys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户业务实现层
 * <p>
 * Created by bysocket on 07/02/2017.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SfUserMapper userDao; // 主数据源

    @Autowired
    private RoleDao roleDao; // 从数据源

    @Autowired
    private RedisComponent redis;

    @Autowired
    private  smsTest smsTest;

    @Value("${REDIS_SESSION_ID_PRE}")
    private String REDIS_SESSION_ID_PRE;

    @Value("${REDIS_SESSION_USER_KEY}")
    private String REDIS_SESSION_USER_KEY;

    @Value("${REDIS_SESSION_EXPIRE}")
    private long REDIS_SESSION_EXPIRE;

    @Value("${SENDSMS_ACCOUNT}")
    private Integer SENDSMS_ACCOUNT;

    @Value("${SENDSMS_CONTEXT}")
    private String SENDSMS_CONTEXT;

    @Override
    public SfUser findByName(String userName) {
        Role role = roleDao.findByName();
        SfUser user = userDao.findByName(userName);
        try {
            redis.set("11111", "aaaaa");
            redis.get("11111");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        user.setRole(role);
        return user;
    }

    /**
     * 需求：校验注册用户数据是否可用
     *
     * @param param
     * @param type
     * @return 校验：type=1，校验用户是否被占用
     * type=2，校验电话号码
     * type=3，校验邮箱
     */

    @Override
    public SFUserResult dataCheck(String param, Integer type) {

        //创建example对象
        SfUserExample example = new SfUserExample();
        //创建criteria对象
        SfUserExample.Criteria createCriteris = example.createCriteria();

        if (type == 1) {
            createCriteris.andUsernameEqualTo(param);
        } else if (type == 2) {
            createCriteris.andPhoneEqualTo(param);
        } else if (type == 3) {
            createCriteris.andEmailEqualTo(param);
        }
        //执行查询
        List<SfUser> list = userDao.selectByExample(example);

        //判断注册数据是否有效
        if (list != null && list.size() > 0) {
            return SFUserResult.ok(false);
        }

        return SFUserResult.ok(true);
    }

    /**
     * 用户注册
     *
     * @param sfuser
     * @return sFuserResult
     */

    @Override
    public SFUserResult register(SfUser sfuser) {

        try {
            //加入时间属性
            Date date = new Date();

            sfuser.setCreatetime(date);

            //加密密码

            String password = SHA256.encryptText(sfuser.getPassword());

            sfuser.setPassword(password);

            userDao.insert(sfuser);

        } catch (Exception e) {
            e.printStackTrace();

            return SFUserResult.build(400, "注册失败，请稍后重试");
        }
        //保存成功
        return SFUserResult.ok();

    }

    /**
     * 用户登录
     *
     * @param userName
     * @param password
     * @return
     */

    @Override
    public SFUserResult login(String userName, String password) {

        //根据用户名查询数据，判断用户是否存在
        SfUserExample example = new SfUserExample();

        SfUserExample.Criteria criteria = example.createCriteria();

        criteria.andUsernameEqualTo(userName);

        List<SfUser> list = userDao.selectByExample(example);

        if (list == null || list.size() == 0) {
            return SFUserResult.build(400, "用户名错误");
        }
        //获取用户对密码进行判断
        SfUser sfUser = list.get(0);
        String shaPassword = SHA256.encryptText(password);

        if (!shaPassword.equals(sfUser.getPassword())) {
            return SFUserResult.build(400, "密码错误");
        }
        //利用TokenProcessor生成Token
        TokenProcessor processor = TokenProcessor.getInstance();
        String token = processor.generateToken("2011cj7067");

        //把用户身份信息写入redis，把密码设置为null
        sfUser.setPassword(null);
        String id = sfUser.getId().toString();
        try {
            // 7、调用jedis的hset方法，将登录用户信息，存储到redis缓存中
            redis.set(id, token);
            // 8、设置有效期
            redis.set(id, token, REDIS_SESSION_EXPIRE);


        } catch (Exception e) {
            e.printStackTrace();
            return SFUserResult.build(400, "用户身份信息已过期");
        }
        ArrayList<String> array = new ArrayList<>();
        array.add(token);
        array.add(id);
        return SFUserResult.build(200, "存储成功", array);
    }

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return sFuser
     */
    @Override
    public SFUserResult userCheck(String token) {

        try {
            String userid = redis.get(token);

            Integer id = Integer.parseInt(userid);

            SfUser users = userDao.selectByPrimaryKey(id);

            if (users != null) {

                //重置身份过期时间
//              redis.setExpire(token,20,);
                return SFUserResult.ok(users);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return SFUserResult.build(400, "用户身份信息已过期");

    }

    /**
     * 发送邮件
     */
    @Override
    public SFUserResult SendEmail() {

        // //测试163邮箱
        // 测试qq邮箱
        // 新浪邮箱 13077403326m@sina.cn
        // 139邮箱 13077403326@139.com

        String userName = "15210495787@163.com"; // 用户邮箱地址
        String password = "jj15210495787"; // 密码或者授权码
        String targetAddress = "1191163977@qq.com"; // 接受者邮箱地址

        // 设置邮件内容
        MimeMessageDTO mimeDTO = new MimeMessageDTO();
        mimeDTO.setSentDate(new Date());
        mimeDTO.setSubject("邮件的标题");
        mimeDTO.setText("邮件的内容<a href='http://localhost/user/emai'>请点击" +
                "</a>"
        );

        // 发送单邮件
        if (MailUtil.sendEmail(userName, password, targetAddress, mimeDTO)) {
            System.out.println("邮件发送成功！");
            return SFUserResult.build(200,"邮件发送成功");
        } else {
            System.out.println("邮件发送失败!!!");
            return SFUserResult.build(200,"邮件发送失败");
        }
//		// 发送单邮件(附件)
//		List<String> filepath=new ArrayList<String>();
//		filepath.add("D:/temple.xls");
//		filepath.add("D:/test.xls");
//		if (MailUtil.sendEmailByFile(userName, password, targetAddress, mimeDTO,filepath)) {
//			System.out.println("邮件发送成功！");
//		} else {
//        System.out.println("邮件发送失败!!!");//  }
//        // 群发邮件
//        targetAddress = "791120662@qq.com,1466209469@qq.com";
//		if (MailUtil.sendGroupEmail(userName, password, targetAddress, mimeDTO)) {
//			System.out.println("邮件发送成功！");
//		} else {
//
//			System.out.println("邮件发送失败!!!");	    }
//		// 群发邮件(附件)
//		if (MailUtil.sendGroupEmailByFile(userName, password, targetAddress, mimeDTO,filepath)) {
//			System.out.println("邮件发送成功！");
//		} else {
//			System.out.println("邮件发送失败!!!");
//		}
//
//    }

    }

    /**
     * 通过短信验证码修改密码
     *
     * @param phone ，code，password
     * @param code
     * @param password @return
     */
    @Override
    public SFUserResult SmsSender(String phone, String code, String password) {

        //验证码验证，如果通过则调用dao修改密码

        //修改密码

        SfUserExample example = new SfUserExample();

        SfUserExample.Criteria criteria = example.createCriteria();

        criteria.andPhoneLike(phone);

        List<SfUser> list = userDao.selectByExample(example);

        if (list == null || list.size() == 0) {
            return SFUserResult.build(400, "电话号码错误");
        }
        SfUser sfUser = list.get(0);

//        SfUser sfUser = userDao.selectByPhone(phone);
       String newpassword = SHA256.encryptText(password);
       sfUser.setPassword(newpassword);

       userDao.updateByPrimaryKey(sfUser);


        return SFUserResult.build(200,"修改成功，请重新登录");
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     */
    @Override
    public SFUserResult sendSms(String phone) {

       SFUserResult SFUserResult = smsTest.sendSms(SENDSMS_ACCOUNT,phone,SENDSMS_CONTEXT);

        return SFUserResult;

    }


}



