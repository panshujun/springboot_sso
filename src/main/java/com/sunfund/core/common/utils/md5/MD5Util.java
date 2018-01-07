package com.sunfund.core.common.utils.md5;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by 9douyu on 2017/10/18.
 */
public class MD5Util {


    public static String getMD5(String source){
        return DigestUtils.md5Hex(source);
    }

}
