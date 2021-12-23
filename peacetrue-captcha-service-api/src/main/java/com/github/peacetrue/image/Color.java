package com.github.peacetrue.image;

import lombok.*;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 颜色
 *
 * @author peace
 * @since 1.0
 **/
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Color implements Serializable {

    private Integer red;
    private Integer green;
    private Integer blue;

    public Color(Integer value) {
        java.awt.Color color = new java.awt.Color(value);
        setRed(color.getRed());
        setGreen(color.getGreen());
        setBlue(color.getBlue());
    }

    public java.awt.Color toColor() {
        return new java.awt.Color(red, green, blue);
    }

    /** 生成随机颜色 */
    public static Color generateRandomColor() {
        Random random = ThreadLocalRandom.current();
        return new Color(
                randomColor(random),
                randomColor(random),
                randomColor(random)
        );
    }

    private static int randomColor(Random random) {
        return random.nextInt(255);
    }

    /** 生成随机颜色(能够比较好的展示在白色背景上) */
    public static Color generateRandomColorFriendly() {
        Random random = ThreadLocalRandom.current();
        return new Color(
                random.nextInt(101),
                random.nextInt(200),
                random.nextInt(121)
        );
    }

    /** 构建指定颜色（便于识别？） */
    public static Color buildColor(int fc, int bc) {
        Random random = ThreadLocalRandom.current();
        return new Color(
                fc + random.nextInt(bc - fc - 16),
                fc + random.nextInt(bc - fc - 14),
                fc + random.nextInt(bc - fc - 18)
        );
    }

}
