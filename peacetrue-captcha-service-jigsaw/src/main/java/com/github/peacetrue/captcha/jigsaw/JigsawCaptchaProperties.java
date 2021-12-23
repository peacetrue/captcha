package com.github.peacetrue.captcha.jigsaw;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * @author peace
 * @since 1.0
 **/
@Getter
@Setter
@ConfigurationProperties("peacetrue.captcha.jigsaw")
public class JigsawCaptchaProperties {

    /** 背景路径 */
    private String backgroundPath = "classpath:public/images/captcha/jigsaw/background/*";
    /** 滑块路径 */
    private String slidingBlockPath = "classpath:public/images/captcha/jigsaw/sliding-block/*";
    /** 过期时段 */
    private Long expirePeriod = TimeUnit.MINUTES.toMillis(2);
    /** 最大失败次数 */
    private Integer maxFailedCount = 5;

}
