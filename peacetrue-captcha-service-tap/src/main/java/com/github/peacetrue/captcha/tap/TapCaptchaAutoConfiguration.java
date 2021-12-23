package com.github.peacetrue.captcha.tap;

import com.github.peacetrue.captcha.CaptchaService;
import com.github.peacetrue.captcha.repository.CaptchaRepository;
import com.github.peacetrue.captcha.repository.MemoryCaptchaRepository;
import com.github.peacetrue.image.Point;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.Objects;

/**
 * @author peace
 * @since 1.0
 **/
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(TapCaptchaProperties.class)
public class TapCaptchaAutoConfiguration {

    private final TapCaptchaProperties properties;

    public TapCaptchaAutoConfiguration(TapCaptchaProperties properties) {
        this.properties = Objects.requireNonNull(properties);
    }

    @Bean
    public TapBufferedImageSupplier jigsawBufferedImageSupplier(ResourcePatternResolver resolver) {
        return new TapBufferedImageSupplierImpl(resolver, properties);
    }

    @Bean
    public CaptchaService<Point> blockPuzzleCaptchaService(CaptchaRepository<Point> captchaRepository,
                                                           TapBufferedImageSupplier supplier) {
        return new TapCaptchaService(
                captchaRepository, properties.getExpirePeriod(),
                properties.getMaxFailedCount(), supplier
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public CaptchaRepository<Point> captchaRepository() {
        return new MemoryCaptchaRepository<>();
    }
}
