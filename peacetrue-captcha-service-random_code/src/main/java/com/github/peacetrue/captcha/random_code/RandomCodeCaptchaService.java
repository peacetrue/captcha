package com.github.peacetrue.captcha.random_code;

import com.github.peacetrue.captcha.AbstractCaptchaService;
import com.github.peacetrue.captcha.CaptchaQuestionOptions;
import com.github.peacetrue.captcha.repository.CaptchaRepository;
import com.github.peacetrue.image.BufferedImageUtils;
import com.github.peacetrue.image.ImageOptions;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Objects;

/**
 * 随机码验证码服务实现
 *
 * @author peace
 * @since 1.0
 **/
@Slf4j
@Setter
@AllArgsConstructor
public class RandomCodeCaptchaService extends AbstractCaptchaService<String> {

    private ImageOptions imageOptions;

    public RandomCodeCaptchaService(CaptchaRepository<String> captchaRepository,
                                    Long expirePeriod, Integer maxFailedCount,
                                    ImageOptions imageOptions) {
        super(captchaRepository, expirePeriod, maxFailedCount);
        this.imageOptions = Objects.requireNonNull(imageOptions);
    }

    @Override
    protected QuestionAnswer<Serializable, String> generateQuestionAnswer(CaptchaQuestionOptions options) {
        String answer = RandomStringUtils.randomAlphanumeric(imageOptions.getCharCount());
        log.debug("get a random answer '{}'", answer);

        BufferedImage question = BufferedImageUtils.buildBufferedImage(answer, imageOptions);
        log.debug("build a question '{}' for answer '{}'", question, answer);

        return new QuestionAnswer<>(BufferedImageUtils.toBase64(question), answer);
    }

//    private ImageOptions getImageOptions(RandomCodeCaptchaQuestionOptions options) {
//        ImageOptions imageOptions = options.getImageOptions();
//        if (imageOptions == null) {
//            imageOptions = this.defaultImageOptions;
//        } else {
//            ImageOptions copy = BeanUtils.map(this.defaultImageOptions, ImageOptions.class);
//            BeanUtils.copyProperties(imageOptions, copy, BeanUtils.EMPTY_PROPERTY_VALUE);
//            imageOptions = copy;
//        }
//        return imageOptions;
//    }

    @Override
    protected boolean isAnswerMatched(String expected, String actual) {
        return expected.equalsIgnoreCase(actual);
    }
}
