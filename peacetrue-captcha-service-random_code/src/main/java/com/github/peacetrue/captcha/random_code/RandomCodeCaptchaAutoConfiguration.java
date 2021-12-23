package com.github.peacetrue.captcha.random_code;

import com.github.peacetrue.captcha.CaptchaService;
import com.github.peacetrue.captcha.repository.CaptchaRepository;
import com.github.peacetrue.captcha.repository.MemoryCaptchaRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @author peace
 * @since 1.0
 **/
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RandomCodeCaptchaProperties.class)
public class RandomCodeCaptchaAutoConfiguration {

    private final RandomCodeCaptchaProperties properties;

    public RandomCodeCaptchaAutoConfiguration(RandomCodeCaptchaProperties properties) {
        this.properties = Objects.requireNonNull(properties);
    }

    @Bean
    public CaptchaService<String> randomCodeCaptchaService(CaptchaRepository<String> randomCodeCaptchaRepository) {
        return new RandomCodeCaptchaService(
                randomCodeCaptchaRepository, properties.getExpirePeriod(),
                properties.getMaxFailedCount(), properties.getImageOptions()
        );
    }

    @Bean
    public CaptchaRepository<String> randomCodeCaptchaRepository() {
        return new MemoryCaptchaRepository<>();
    }
}
