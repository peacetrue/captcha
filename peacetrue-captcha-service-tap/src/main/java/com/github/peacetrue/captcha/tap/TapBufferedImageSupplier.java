package com.github.peacetrue.captcha.tap;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * 拼图图片提供者
 *
 * @author peace
 * @since 1.0
 **/
public interface TapBufferedImageSupplier {

    /** 获取背景图片 */
    List<BufferedImage> getBackgrounds();

}
