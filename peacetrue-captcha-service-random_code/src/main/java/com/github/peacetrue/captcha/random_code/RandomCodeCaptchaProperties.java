package com.github.peacetrue.captcha.random_code;

import com.github.peacetrue.image.ImageOptions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * 随机码验证码配置属性
 *
 * @author peace
 * @since 1.0
 **/
@Getter
@Setter
@ConfigurationProperties("peacetrue.captcha.random-code")
public class RandomCodeCaptchaProperties {

    /** 过期时段 */
    private Long expirePeriod = TimeUnit.MINUTES.toMillis(2);
    /** 最大失败次数 */
    private Integer maxFailedCount = 1;
    /** 图片选项 */
    private ImageOptions imageOptions = ImageOptions.DEFAULT;

}
