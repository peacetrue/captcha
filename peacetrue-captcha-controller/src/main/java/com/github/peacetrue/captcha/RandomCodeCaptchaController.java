package com.github.peacetrue.captcha;

import com.github.peacetrue.captcha.random_code.RandomCodeCaptchaAnswer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 随机码验证码控制器
 *
 * @author peace
 * @since 1.0
 **/
@Slf4j
@RestController
@RequestMapping(value = "/captcha", params = "type=RANDOM_CODE")
public class RandomCodeCaptchaController {

    @Autowired
    private CaptchaService<String> randomCodeCaptchaService;

    @GetMapping
    public CaptchaQuestion getQuestion(CaptchaQuestionOptions options) {
        return randomCodeCaptchaService.getQuestion(options);
    }

    @PostMapping
    public boolean checkAnswer(RandomCodeCaptchaAnswer answer) {
        return randomCodeCaptchaService.checkAnswer(answer);
    }

}
