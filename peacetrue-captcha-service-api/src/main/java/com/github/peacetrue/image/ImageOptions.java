package com.github.peacetrue.image;

import lombok.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.function.Function;

/**
 * 图片选项
 *
 * @author peace
 * @since 1.0
 **/
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ImageOptions implements Serializable {

    public static final ImageOptions DEFAULT = ImageOptions.builder()
            .width(80).height(26).imageType(BufferedImage.TYPE_INT_BGR)
            .backgroundColor(new Color(255, 255, 255))
            .front(new Front("Times New Roman", Font.PLAIN, 18))
            .offsetX(13).offsetY(16)
            .charWidth(13).charCount(4).charColorGenerator(i -> Color.generateRandomColorFriendly())
            .lineCount(10).lineColor(Color.buildColor(110, 133))
            .maxLineWidth(13).maxLineHeight(15)
            .build();

    private Integer width;
    private Integer height;
    private Integer imageType;
    private Color backgroundColor;
    private Front front;
    private Integer offsetX;
    private Integer charWidth;
    private Integer charCount;
    private transient Function<Integer, Color> charColorGenerator;
    private transient Function<Integer, AffineTransform> charAffineTransformGenerator;
    private Integer offsetY;
    private Integer lineCount;
    private Color lineColor;
    private Integer maxLineWidth;
    private Integer maxLineHeight;

}
