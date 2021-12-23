package com.github.peacetrue.captcha.repository;

import com.github.peacetrue.captcha.CaptchaAnswer;

import java.io.Serializable;

/**
 * 验证码资源库接口
 *
 * @author peace
 * @since 1.0
 **/
public interface CaptchaRepository<T extends Serializable> {

    /** 保存答案 */
    void saveAnswer(CaptchaAnswer<T> answer);

    /** 获取答案 */
    CaptchaAnswer<T> getAnswer(String id);

    /** 设置回答失败次数 */
    void setFailedCount(String id, Long failedCount);

    /** 删除答案 */
    void deleteAnswer(String id);

}
