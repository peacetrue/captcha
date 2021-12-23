package com.github.peacetrue.captcha;

import com.github.peacetrue.captcha.repository.CaptchaRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 抽象的验证码服务
 *
 * @author peace
 * @since 1.0
 **/
@Slf4j
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractCaptchaService<T extends Serializable> implements CaptchaService<T> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private CaptchaRepository<T> captchaRepository;
    private Long expirePeriod;
    private Integer maxFailedCount;

    /** 问答 */
    @Data
    @AllArgsConstructor
    protected static class QuestionAnswer<Q, A> {
        private Q question;
        private A answer;
    }

    @Override
    public CaptchaQuestion getQuestion(CaptchaQuestionOptions options) {
        log.info("get a random question");
        QuestionAnswer<Serializable, T> questionAnswer = generateQuestionAnswer(options);
        log.info("generate a random question '{}'", questionAnswer);
        String id = generateQuestionId();
        log.debug("generate a random question id '{}'", id);
        captchaRepository.saveAnswer(new CaptchaAnswer<>(id, questionAnswer.getAnswer(), expirePeriod));
        return new CaptchaQuestion(id, questionAnswer.getQuestion());
    }

    /** 生成一个问题标志 */
    protected String generateQuestionId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /** 生成一个问题答案 */
    protected abstract QuestionAnswer<Serializable, T> generateQuestionAnswer(CaptchaQuestionOptions options);

    @Override
    public CaptchaAnswer<T> getAnswer(String id) {
        CaptchaAnswer<T> answer = captchaRepository.getAnswer(id);
        log.debug("get the answer '{}' of question '{}'", answer, id);

        if (answer == null) {
            throw new CaptchaException(
                    "the question '" + id + "' not exists." +
                            " you never asked the question " +
                            "or the question is expired " +
                            "or you've answered before"
            );
        }

        long currentTimeMillis = System.currentTimeMillis();
        if (answer.isExpired(currentTimeMillis)) {
            captchaRepository.deleteAnswer(id);
            throw new CaptchaException("you exceed deadline of the question '" + id + "'. " +
                    "you ask at '" + DATE_FORMAT.format(new Date(answer.getCreatedTime())) + "'," +
                    " valid for '" + answer.getExpirePeriod() + "' milliseconds," +
                    " expect before '" + DATE_FORMAT.format(new Date(answer.getCreatedTime() + answer.getExpirePeriod())) + "'," +
                    " but current '" + DATE_FORMAT.format(new Date(currentTimeMillis)) + "'");
        }

        return answer;
    }

    @Override
    public boolean checkAnswer(CaptchaAnswer<T> params) {
        log.info("check answer '{}' of question '{}'", params.getAnswer(), params.getId());

        CaptchaAnswer<T> answer = this.getAnswer(params.getId());
        Long failedCount = answer.getFailedCount();
        log.debug("get failed count '{}' of question '{}'", failedCount, params.getId());
        checkFailedCount(params, failedCount);

        boolean matched = isAnswerMatched(answer.getAnswer(), params.getAnswer());
        if (!matched) {
            failedCount++;
            //TODO 达到失败次数阈值，提前提示结果，避免再次访问
            //checkFailedCount(params, failedCount);
            captchaRepository.setFailedCount(params.getId(), failedCount);
        }

        return matched;
    }

    /** 检查回答失败次数 */
    protected void checkFailedCount(CaptchaAnswer<T> answer, Long failedCount) {
        if (failedCount >= maxFailedCount) {
            captchaRepository.deleteAnswer(answer.getId());
            throw new CaptchaException("you exceed the failed answer threshold '" + maxFailedCount + "'" +
                    " of question '" + answer.getId() + "'");
        }
    }

    protected boolean isAnswerMatched(T expected, T actual) {
        return expected.equals(actual);
    }
}
