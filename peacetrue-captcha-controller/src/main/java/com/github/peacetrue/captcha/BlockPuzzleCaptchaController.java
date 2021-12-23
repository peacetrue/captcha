package com.github.peacetrue.captcha;

import com.github.peacetrue.captcha.jigsaw.JigsawCaptchaAnswer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 方块难题验证码控制器
 *
 * @author peace
 * @since 1.0
 **/
@Slf4j
@RestController
@RequestMapping(value = "/captcha", params = "type=BLOCK_PUZZLE")
public class BlockPuzzleCaptchaController {

    @Autowired
    private CaptchaService blockPuzzleCaptchaService;

    @GetMapping
    public CaptchaQuestion getQuestion(CaptchaQuestionOptions options) {
        return blockPuzzleCaptchaService.getQuestion(options);
    }

    @PostMapping
    public boolean checkAnswer(JigsawCaptchaAnswer answer) {
        return blockPuzzleCaptchaService.checkAnswer(answer);
    }

}
