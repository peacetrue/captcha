package com.github.peacetrue.captcha.repository;

import com.github.peacetrue.captcha.CaptchaAnswer;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 基于内存的验证码资源库
 *
 * @author peace
 * @since 1.0
 **/
public class MemoryCaptchaRepository<T extends Serializable> implements CaptchaRepository<T> {

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);
    private static final List<CaptchaAnswer<?>> CAPTCHA_ANSWERS = new CopyOnWriteArrayList<>();

    @Override
    public void saveAnswer(CaptchaAnswer<T> answer) {
        answer.setCreatedTime(System.currentTimeMillis());
        CAPTCHA_ANSWERS.add(answer);
        EXECUTOR_SERVICE.schedule(
                () -> CAPTCHA_ANSWERS.remove(answer),
                answer.getExpirePeriod() + 1, TimeUnit.MILLISECONDS
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public CaptchaAnswer<T> getAnswer(String id) {
        return (CaptchaAnswer<T>) CAPTCHA_ANSWERS.stream()
                .filter(item -> item.getId().equals(id))
                .findAny().orElse(null);
    }

    @Override
    public void setFailedCount(String id, Long failedCount) {
        getAnswer(id).setFailedCount(failedCount);
    }

    @Override
    public void deleteAnswer(String id) {
        CAPTCHA_ANSWERS.removeIf(item -> item.getId().equals(id));
    }
}
