package com.github.peacetrue.captcha.jigsaw;

import com.github.peacetrue.captcha.AbstractCaptchaService;
import com.github.peacetrue.captcha.CaptchaQuestionOptions;
import com.github.peacetrue.captcha.repository.CaptchaRepository;
import com.github.peacetrue.image.BufferedImageUtils;
import com.github.peacetrue.image.Point;
import com.github.peacetrue.image.Size;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

/**
 * 随机码验证码服务实现
 *
 * @author peace
 * @since 1.0
 **/
@Slf4j
@Setter
@AllArgsConstructor
public class JigsawCaptchaService extends AbstractCaptchaService<Point> {

    private JigsawBufferedImageSupplier bufferedImageSupplier;

    public JigsawCaptchaService(CaptchaRepository<Point> captchaRepository, Long expirePeriod,
                                Integer maxFailedCount, JigsawBufferedImageSupplier bufferedImageSupplier) {
        super(captchaRepository, expirePeriod, maxFailedCount);
        this.bufferedImageSupplier = bufferedImageSupplier;
    }

    @Override
    public QuestionAnswer<Serializable, Point> generateQuestionAnswer(CaptchaQuestionOptions options) {
        log.info("get a jigsaw question");

        List<BufferedImage> backgrounds = bufferedImageSupplier.getBackgrounds();
        log.debug("get total {} backgrounds", backgrounds.size());
        int backgroundIndex = RandomUtils.nextInt(0, backgrounds.size());
        log.debug("random the index {} background", backgroundIndex);
        BufferedImage background = backgrounds.get(backgroundIndex);
        background = BufferedImageUtils.clone(background);

        List<BufferedImage> slidingBlocks = bufferedImageSupplier.getSlidingBlocks();
        log.debug("get total {} slidingBlocks", slidingBlocks.size());
        int slidingBlockIndex = RandomUtils.nextInt(0, slidingBlocks.size());
        log.debug("random the index {} slidingBlock", slidingBlockIndex);
        BufferedImage slidingBlock = slidingBlocks.get(slidingBlockIndex);

        Point answer = BufferedImageUtils.randomElementPosition(
                new Size(background), new Size(slidingBlock), new Size(100, 0)
        );
        log.debug("random a jigsaw answer '{}'", answer);
        BufferedImage element = BufferedImageUtils.cloneTemplate(background, slidingBlock, answer);

        return new QuestionAnswer<>(new JigsawQuestion(
                BufferedImageUtils.toBase64(background),
                BufferedImageUtils.toBase64(element)
        ), answer);
    }

}
