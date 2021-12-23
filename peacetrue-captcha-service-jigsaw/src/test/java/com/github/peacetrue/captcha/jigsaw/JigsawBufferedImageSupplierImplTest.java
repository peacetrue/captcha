package com.github.peacetrue.captcha.jigsaw;

import com.github.peacetrue.image.BufferedImageUtils;
import com.github.peacetrue.image.Point;
import com.github.peacetrue.image.Size;
import com.github.peacetrue.test.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author peace
 * @since 1.0
 **/
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        JigsawCaptchaAutoConfiguration.class
})
class JigsawBufferedImageSupplierImplTest {

    @Autowired
    private JigsawBufferedImageSupplier jigsawBufferedImageSupplier;

    @Test
    void getBackgrounds() {
        List<BufferedImage> backgrounds = jigsawBufferedImageSupplier.getBackgrounds();
        Assertions.assertNotNull(backgrounds);
        Assertions.assertEquals(20, backgrounds.size());
    }

    @Test
    void getSlidingBlocks() {
        List<BufferedImage> slidingBlocks = jigsawBufferedImageSupplier.getSlidingBlocks();
        Assertions.assertNotNull(slidingBlocks);
        Assertions.assertEquals(11, slidingBlocks.size());
    }

    @Test
    void gaussianBlur() throws IOException {
        List<BufferedImage> backgrounds = jigsawBufferedImageSupplier.getBackgrounds();
        BufferedImage background = backgrounds.get(0);
        List<BufferedImage> slidingBlocks = jigsawBufferedImageSupplier.getSlidingBlocks();
        BufferedImage slidingBlock = slidingBlocks.get(0);
        Point position = BufferedImageUtils.randomElementPosition(new Size(background), new Size(slidingBlock), Size.ZERO);
        BufferedImage element = BufferedImageUtils.cloneTemplate(background, slidingBlock, position);
        String path = TestUtils.getSourceFolderAbsolutePath(JigsawBufferedImageSupplierImplTest.class) + "/images";
        Files.createDirectories(Paths.get(path));
        ImageIO.write(background, "png", new File(path + "/background.png"));
        ImageIO.write(element, "png", new File(path + "/element.png"));
    }
}
