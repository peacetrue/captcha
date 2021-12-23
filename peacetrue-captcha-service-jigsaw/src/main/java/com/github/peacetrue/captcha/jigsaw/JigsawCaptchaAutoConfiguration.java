package com.github.peacetrue.captcha.jigsaw;

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
@EnableConfigurationProperties(JigsawCaptchaProperties.class)
public class JigsawCaptchaAutoConfiguration {

    private final JigsawCaptchaProperties properties;

    public JigsawCaptchaAutoConfiguration(JigsawCaptchaProperties properties) {
        this.properties = Objects.requireNonNull(properties);
    }

    @Bean
    public JigsawBufferedImageSupplier jigsawBufferedImageSupplier(ResourcePatternResolver resolver) {
        return new JigsawBufferedImageSupplierImpl(resolver, properties);
    }

    @Bean
    public CaptchaService<Point> blockPuzzleCaptchaService(CaptchaRepository<Point> captchaRepository,
                                                           JigsawBufferedImageSupplier supplier) {
        return new JigsawCaptchaService(
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
