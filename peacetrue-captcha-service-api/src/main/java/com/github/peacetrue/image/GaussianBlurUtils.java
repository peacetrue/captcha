package com.github.peacetrue.image;

import java.awt.image.BufferedImage;

/**
 * 图片高斯模糊算法工具类
 *
 * @author peace
 * @see <a href="http://www.ruanyifeng.com/blog/2012/11/gaussian_blur.html">阮一峰的网络日志</a>
 * @since 1.0
 **/
public abstract class GaussianBlurUtils {

    protected GaussianBlurUtils() {
    }

    /** 准备一个像素容器 */
    public static int[][] preparePixels(int radius) {
        int size = size(radius);
        return new int[size][size];
    }

    /** 以 radius 为半径的矩阵大小 */
    public static int size(int radius) {
        return radius * 2 + 1;
    }

    /** 读取图片中某点指定半径附近的像素 */
    public static void readPixels(BufferedImage image, int x, int y, int radius, int[][] pixels) {
        int leftTopX = x - radius;
        int leftTopY = y - radius;
        int space = size(radius);
        for (int i = 0; i < space; i++) {
            for (int j = 0; j < space; j++) {
                pixels[i][j] = image.getRGB(
                        position(leftTopX + i, x, image.getWidth()),
                        position(leftTopY + j, y, image.getHeight())
                );
            }
        }
    }

    /** 求合适位置 */
    private static int position(int current, int middle, int size) {
        if (current < 0) {
            return -current;
        } else {
            return current >= size ? middle : current;
        }
    }

    /** 求矩阵的平均值 */
    public static int avgMatrix(int[][] matrix, int radius) {
        Color total = new Color(0, 0, 0);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                //移除中心点
                if (i == radius && j == radius) {
                    continue;
                }
                Color item = new Color(matrix[i][j]);
                total.setRed(total.getRed() + item.getRed());
                total.setGreen(total.getGreen() + item.getGreen());
                total.setBlue(total.getBlue() + item.getBlue());
            }
        }
        int count = size(radius) * size(radius) - 1;
        total.setRed(total.getRed() / count);
        total.setGreen(total.getGreen() / count);
        total.setBlue(total.getBlue() / count);
        return total.toColor().getRGB();
    }
}
