package com.github.peacetrue.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * 图片工具类
 *
 * @author peace
 * @since 1.0
 **/
public abstract class BufferedImageUtils {

    protected BufferedImageUtils() {
    }

    /** 拷贝一份 */
    public static BufferedImage clone(BufferedImage bufferedImage) {
        ColorModel cm = bufferedImage.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bufferedImage.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /** 图片转换为 base64 字符串 */
    public static String toBase64(BufferedImage bufferedImage) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", outputStream);
        } catch (IOException e) {
            throw new UncheckedIOException("unexpect exception", e);
        }
        byte[] bytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
    }

    /** 随机产生元素位置 */
    public static Point randomElementPosition(Size container, Size element, Size offset) {
        Random random = ThreadLocalRandom.current();
        Size concat = element.concat(offset);
        if (!container.contains(concat)) {
            throw new IllegalArgumentException("the container must contains element");
        }
        int widthSpace = container.getWidth() - concat.getWidth();
        int heightSpace = container.getHeight() - concat.getHeight();
        return new Point(
                offset.getWidth() + widthSpace == 0 ? 0 : random.nextInt(widthSpace),
                offset.getHeight() + heightSpace == 0 ? 0 : random.nextInt(heightSpace)
        );
    }

    /** 模板在容器上印一份：模版的形状，容器的背景 */
    public static BufferedImage cloneTemplate(BufferedImage container, BufferedImage template, Point position) {
        BufferedImage target = new BufferedImage(template.getWidth(), template.getHeight(), template.getType());
        Graphics2D graphics = target.createGraphics();
        //如果需要生成RGB格式，需要做如下配置：Transparency 设置透明
        target = graphics.getDeviceConfiguration().createCompatibleImage(template.getWidth(), template.getHeight(), Transparency.TRANSLUCENT);
        //临时数组遍历用于高斯模糊存周边像素值
        int radius = 1;
        int[][] pixels = GaussianBlurUtils.preparePixels(radius);

        int width = template.getWidth();
        int height = template.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // 如果元素图像当前像素点不是透明色，拷贝容器元素到目标图片中
                int rgb = template.getRGB(i, j);
                if (rgb < 0) {
                    int containerX = position.getX() + i;
                    int containerY = position.getY() + j;
                    target.setRGB(i, j, container.getRGB(containerX, containerY));
                    GaussianBlurUtils.readPixels(container, containerX, containerY, radius, pixels);
                    container.setRGB(containerX, containerY, GaussianBlurUtils.avgMatrix(pixels, radius));
                }

                //防止数组越界判断
                if (i == (width - radius) || j == (height - radius)) {
                    continue;
                }
                int rightRgb = template.getRGB(i + radius, j);
                int downRgb = template.getRGB(i, j + radius);
                //描边处理，取带像素和无像素的界点，判断该点是不是临界轮廓点,如果是设置该坐标像素是白色
                if ((rgb >= 0 && rightRgb < 0) || (rgb < 0 && rightRgb >= 0) || (rgb >= 0 && downRgb < 0) || (rgb < 0 && downRgb >= 0)) {
                    target.setRGB(i, j, java.awt.Color.white.getRGB());
                    container.setRGB(position.getX() + i, position.getY() + j, java.awt.Color.white.getRGB());
                }
            }
        }
        return target;
    }

    public static BufferedImage buildBufferedImage(ImageOptions options) {
        BufferedImage bufferedImage = new BufferedImage(options.getWidth(), options.getHeight(), options.getImageType());
        Graphics graphics = bufferedImage.createGraphics();
        drawBackground(graphics, options);
        drawLines(graphics, options);
        return bufferedImage;
    }

    /** 构建图片 */
    public static BufferedImage buildBufferedImage(String content, ImageOptions options) {
        BufferedImage bufferedImage = new BufferedImage(
                options.getWidth(),
                options.getHeight(),
                options.getImageType()
        );
        Graphics graphics = bufferedImage.createGraphics();
        drawBackground(graphics, options);
        drawContent(graphics, getContent(content, options), options);
        drawLines(graphics, options);
        return bufferedImage;
    }

    private static String getContent(String content, ImageOptions options) {
        return content.length() > options.getCharCount()
                ? content.substring(0, options.getCharCount())
                : content;
    }


    private static void drawBackground(Graphics graphics, ImageOptions options) {
        if (options.getBackgroundColor() != null) {
            graphics.setColor(options.getBackgroundColor().toColor());
        }
        graphics.fillRect(0, 0, options.getWidth(), options.getHeight());
    }

    public static void drawContent(Graphics graphics, String content, ImageOptions options) {
        graphics.setFont(options.getFront().toFont());
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < content.length(); i++) {
            graphics.setColor(Color.generateRandomColorFriendly().toColor());
            graphics.translate(random.nextInt(3), random.nextInt(3));
            graphics.drawString(
                    String.valueOf(content.charAt(i)),
                    options.getOffsetX() + i * options.getCharWidth(),
                    options.getOffsetY()
            );
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StringContext {
        private String value;
        private Point point;
        private Front front;
        private Color color;
        private AffineTransform affineTransform;
    }

    public static void drawString(Graphics graphics, StringContext... contexts) {
        for (StringContext context : contexts) {
            drawString(graphics, context);
        }
    }

    public static void drawString(Graphics graphics, StringContext context) {
        graphics.setFont(context.getFront().toFont().deriveFont(context.affineTransform));
        graphics.setColor(context.getColor().toColor());
        graphics.drawString(
                context.value,
                context.point.getX(),
                context.point.getY()
        );
    }

    public static void drawLines(Graphics graphics, ImageOptions options) {
        if (options.getLineCount() != null) {
            graphics.setColor(options.getLineColor().toColor());
            IntStream.range(0, options.getLineCount()).forEach(i -> drawRandomLine(graphics, options));
        }
    }

    /** 画一条随机长度的线 */
    public static void drawRandomLine(Graphics graphics, ImageOptions options) {
        Random random = ThreadLocalRandom.current();
        int x = random.nextInt(options.getWidth());
        int y = random.nextInt(options.getHeight());
        int lineWidth = random.nextInt(options.getMaxLineWidth());
        int lineHeight = random.nextInt(options.getMaxLineHeight());
        graphics.drawLine(x, y, x + lineWidth, y + lineHeight);
    }
}
