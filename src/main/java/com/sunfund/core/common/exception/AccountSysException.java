package com.sunfund.core.common.exception;

/**
 * Created by jdy on 2017/10/11.
 * 账户中心管理异常
 */
public class AccountSysException extends RuntimeException {

    public AccountSysException() {
        super();
    }

    public AccountSysException(String message) {
        super(message);
    }

    public AccountSysException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountSysException(Throwable cause) {
        super(cause);
    }

    protected AccountSysException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
