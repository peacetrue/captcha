package com.github.peacetrue.captcha;

import lombok.*;

import java.io.Serializable;

/**
 * 验证码答案
 *
 * @author peace
 * @since 1.0
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaAnswer<T extends Serializable> implements Serializable {

    /** 问题标志 {@link CaptchaQuestion#getId()} */
    private String id;
    /** 答案 */
    private T answer;
    /** 回答失败次数 */
    private Long failedCount = 0L;
    /** 过期时段，单位毫秒 */
    private Long expirePeriod;
    /** 创建时间，单位毫秒 */
    private Long createdTime;

    public CaptchaAnswer(String id, T answer, Long expirePeriod) {
        this(id, answer, 0L, expirePeriod, System.currentTimeMillis());
    }

    /** 是否过期 */
    public boolean isExpired() {
        return isExpired(System.currentTimeMillis());
    }

    /** 是否过期 */
    public boolean isExpired(long current) {
        return expirePeriod != null && getCreatedTime() + expirePeriod < current;
    }
}
