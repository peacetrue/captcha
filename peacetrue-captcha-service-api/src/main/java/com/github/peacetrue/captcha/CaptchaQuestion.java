package com.github.peacetrue.captcha;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 验证码问题
 *
 * @author peace
 * @since 1.0
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaQuestion implements Serializable {

    /** 问题标识，验证时需要传入此标识 */
    private String id;

    /** 问题内容，可供展示在界面上 */
    private Serializable question;
}
