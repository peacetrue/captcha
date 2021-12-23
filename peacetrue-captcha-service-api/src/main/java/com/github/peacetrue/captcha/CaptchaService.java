package com.github.peacetrue.captcha;

import java.io.Serializable;

/**
 * 验证码服务接口
 *
 * @author peace
 * @since 1.0
 **/
public interface CaptchaService<T extends Serializable> {

    /** 获取问题 */
    CaptchaQuestion getQuestion(CaptchaQuestionOptions options);

    /** 获取答案，测试时可用，自问自答 */
    CaptchaAnswer<T> getAnswer(String id);

    /** 检查答案 */
    boolean checkAnswer(CaptchaAnswer<T> params);

}
