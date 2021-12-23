package com.github.peacetrue.image;

import lombok.*;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * 尺寸
 *
 * @author peace
 * @since 1.0
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Size implements Serializable {

    public static final Size ZERO = new Size(0, 0);

    private Integer width;
    private Integer height;

    public Size(BufferedImage bufferedImage) {
        this(bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    /** 是否包含 */
    public boolean contains(Size element) {
        return width >= element.getWidth() && height >= element.getHeight();
    }

    /** 合并 */
    public Size concat(Size size) {
        return new Size(size.getWidth() + getWidth(), size.getHeight() + getHeight());
    }
}
