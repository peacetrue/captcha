package com.github.peacetrue.captcha.jigsaw;

import lombok.*;

import java.io.Serializable;

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
public class JigsawQuestion implements Serializable {

    /** base64 背景图片 */
    private String background;
    /** base64 滑块图片 */
    private String slidingBlock;

}
