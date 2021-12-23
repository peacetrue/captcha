package com.github.peacetrue.captcha;

import lombok.*;

import java.io.Serializable;

/**
 * 验证码问题选项
 *
 * @author peace
 * @since 1.0
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaQuestionOptions implements Serializable {

    /** 设备标志 */
    private String deviceId;
    /** 客户端 IP */
    private String clientIp;

}
