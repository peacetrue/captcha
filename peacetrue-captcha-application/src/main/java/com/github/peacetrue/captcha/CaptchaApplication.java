package com.github.peacetrue.captcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 验证码应用
 *
 * @author peace
 * @since 1.0
 **/
@SpringBootApplication
public class CaptchaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaptchaApplication.class, args);
    }

    @Configuration
    public static class WebConfiguration implements WebMvcConfigurer {
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addRedirectViewController("/", "/index.html");
            registry.addViewController("/random-code").setViewName("random-code");
            registry.addViewController("/jigsaw").setViewName("jigsaw");
        }
    }
}
