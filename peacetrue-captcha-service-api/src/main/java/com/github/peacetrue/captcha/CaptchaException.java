package com.github.peacetrue.captcha;

/**
 * 验证码异常
 *
 * @author peace
 * @since 1.0
 **/
public class CaptchaException extends RuntimeException {

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }
}
