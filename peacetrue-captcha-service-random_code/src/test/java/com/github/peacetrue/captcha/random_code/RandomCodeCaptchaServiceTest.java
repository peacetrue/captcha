package com.github.peacetrue.captcha.random_code;

import com.github.peacetrue.captcha.CaptchaQuestion;
import com.github.peacetrue.captcha.repository.MemoryCaptchaRepository;
import com.github.peacetrue.image.ImageOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author peace
 * @since 1.0
 **/
class RandomCodeCaptchaServiceTest {

    private RandomCodeCaptchaService captchaService = new RandomCodeCaptchaService(
            new MemoryCaptchaRepository<>(), 1_000L, 5, ImageOptions.DEFAULT
    );

    @Test
    void getQuestion() {
        System.setProperty("java.awt.headless", "true");

        CaptchaQuestion question = captchaService.getQuestion(new RandomCodeCaptchaQuestionOptions(ImageOptions.DEFAULT));
        boolean correct = captchaService.checkAnswer(captchaService.getAnswer(question.getId()));
        Assertions.assertTrue(correct);
    }

}
