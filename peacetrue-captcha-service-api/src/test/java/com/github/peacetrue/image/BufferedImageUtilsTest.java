package com.github.peacetrue.image;

import com.github.peacetrue.test.TestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @author peace
 * @since 1.0
 **/
class BufferedImageUtilsTest {

    static String path;
    static BufferedImage bufferedImage;

    @BeforeAll
    static void beforeAll() throws Exception {
        System.setProperty("java.awt.headless", "true");
        path = TestUtils.getSourceFolderAbsolutePath(BufferedImageUtilsTest.class) + "/images";
        Files.createDirectories(Paths.get(path));

        InputStream resource = BufferedImageUtilsTest.class.getResourceAsStream("/default.png");
        Assertions.assertNotNull(resource);
        bufferedImage = ImageIO.read(resource);
    }

    @Test
    void testClone() throws IOException {
        InputStream resource = this.getClass().getResourceAsStream("/default.png");
        Assertions.assertNotNull(resource);
        BufferedImage bufferedImage = BufferedImageUtils.clone(ImageIO.read(resource));
        String path = TestUtils.getSourceFolderAbsolutePath(BufferedImageUtilsTest.class) + "/images";
        Files.createDirectories(Paths.get(path));
        ImageIO.write(bufferedImage, "png", new File(path + "/default.png"));
    }

    @Test
    void name() {
        BufferedImageUtils.buildBufferedImage(new ImageOptions());
    }

    @Test
    void drawString() throws IOException {
        Size size = new Size(bufferedImage);
        List<Point> points = Arrays.asList(
                new Point(0, 0),
                new Point(0, size.getHeight()),
                new Point(size.getWidth(), 0),
                new Point(size.getWidth(), size.getHeight()),
                new Point(size.getWidth() / 2, size.getHeight() / 2)
        );
        for (Point point : points) {
            BufferedImage bufferedImage = BufferedImageUtils.clone(BufferedImageUtilsTest.bufferedImage);
            Graphics2D graphics = bufferedImage.createGraphics();
            graphics.setColor(Color.BLACK);
            graphics.drawString("我", point.getX(), point.getY());
            ImageIO.write(bufferedImage, "png", new File(String.format("%s/%s-%s.png", path, point.getX(), point.getY())));
        }
    }

    @Test
    void buildBufferedImage() throws IOException {
        System.setProperty("java.awt.headless", "true");
        String path = TestUtils.getSourceFolderAbsolutePath(BufferedImageUtilsTest.class) + "/images";
        Files.createDirectories(Paths.get(path));

        ImageOptions options = ImageOptions.DEFAULT;
        System.out.println(options);
        for (int i = 0; i < 5; i++) {
            String randomCode = RandomStringUtils.randomAlphanumeric(4);
            BufferedImage bufferedImage = BufferedImageUtils.buildBufferedImage(randomCode, options);
            ImageIO.write(bufferedImage, "png", new File(path + "/" + randomCode + ".png"));
        }
    }
}
