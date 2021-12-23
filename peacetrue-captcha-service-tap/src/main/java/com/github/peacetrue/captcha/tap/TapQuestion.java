package com.github.peacetrue.captcha.tap;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 拼图问题
 *
 * @author peace
 * @since 1.0
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TapQuestion implements Serializable {

    /** base64 背景图片 */
    private String background;
    /** 字符列表 */
    private List<String> words;

}
