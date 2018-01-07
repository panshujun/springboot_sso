package com.sunfund.core.common.utils.Test;

import com.sunfund.core.common.utils.SFUserResult;

/**
 * Created by 9douyu on 2017/10/18.
 */
public interface SmsSender {



    SFUserResult sendSms(Integer account, String mobile, String content);
}
