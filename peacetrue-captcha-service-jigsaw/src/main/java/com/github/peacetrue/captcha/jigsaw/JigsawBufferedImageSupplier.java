package com.github.peacetrue.captcha.jigsaw;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * 拼图图片提供者
 *
 * @author peace
 * @since 1.0
 **/
public interface JigsawBufferedImageSupplier {

    /** 获取背景图片 */
    List<BufferedImage> getBackgrounds();

    /** 获取滑块图片 */
    List<BufferedImage> getSlidingBlocks();
}
