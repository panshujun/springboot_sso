package com.sunfund.core.common.exception;

/**
 * Created by jdy on 2017/10/11.
 * 用户中心管理异常
 */
public class UserSysException extends RuntimeException {

    public UserSysException() {
        super();
    }

    public UserSysException(String message) {
        super(message);
    }

    public UserSysException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserSysException(Throwable cause) {
        super(cause);
    }

    protected UserSysException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
