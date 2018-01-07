package com.sunfund.core.common.utils.Test;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunfund.core.common.utils.HttpUtil;
import com.sunfund.core.common.utils.md5.MD5Util;
import com.sunfund.core.common.utils.SFUserResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 9douyu on 2017/10/18.
 */
@Service("verCodeService")
//@Slf4j
public class smsTest implements SmsSender{

    private static Logger log = Logger.getLogger(smsTest.class);

    //    public static final String account = "";
//
//    public static final String mobile = "";
//
//    public static final String content="";

    public static final String url = "https://api.miaodiyun.com/20150822/user/sms";


//    private static String operation = "/industrySMS/sendSMS";


    /**
     *@Author JackWang [www.coder520.com]
     *@Date 2017/8/5 16:23
     *@Description  秒滴发送短信
     */
    @Override
    public SFUserResult sendSms(Integer account, String mobile, String content){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String timestamp = sdf.format(new Date());

//            String account = "";
//            String mobile = "";
//            String content = "";

            String priKey = "";
            String sig = MD5Util.getMD5("account="+account+"content="+content+"mobile="+mobile+"&"+priKey);

           String accountSid = account.toString();

            Map<String,String> param = new HashMap<>();
            param.put("accountSid",accountSid);
            param.put("to",mobile);
            param.put("param",content);
            param.put("timestamp",timestamp);
            param.put("sig",sig);
            param.put("respDataType","json");
            String result = HttpUtil.post(url,param);
            JSONObject jsonObject = JSON.parseObject(result);
            if(!jsonObject.getString("respCode").equals("0")){
                log.error("success to send sms to "+mobile+":"+param+":"+result);
                return SFUserResult.build(200,"短信发送成功");
            }
        } catch (Exception e) {
            log.error("fail to send sms to "+mobile);

        }
        return SFUserResult.build(400,"短信发送失败");
    }

}
