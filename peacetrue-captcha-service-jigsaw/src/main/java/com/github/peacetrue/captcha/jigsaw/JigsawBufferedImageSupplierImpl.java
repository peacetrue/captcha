package com.github.peacetrue.captcha.jigsaw;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 拼图图片提供者实现
 *
 * @author peace
 * @since 1.0
 **/
@Slf4j
public class JigsawBufferedImageSupplierImpl implements JigsawBufferedImageSupplier {

    private static final Map<String, List<BufferedImage>> CACHE = new HashMap<>(2);
    private final ResourcePatternResolver resourcePatternResolver;
    private final JigsawCaptchaProperties properties;

    public JigsawBufferedImageSupplierImpl(ResourcePatternResolver resolver,
                                           JigsawCaptchaProperties properties) {
        this.resourcePatternResolver = Objects.requireNonNull(resolver);
        this.properties = Objects.requireNonNull(properties);
    }

    @Override
    public List<BufferedImage> getBackgrounds() {
        return CACHE.computeIfAbsent(
                properties.getBackgroundPath(),
                key -> convertResources(key, this::toBufferedImage)
        );
    }

    private <T> List<T> convertResources(String path, Function<Resource, T> convertor) {
        Resource[] resources = getResources(path);
        log.debug("get {} resources matched '{}'", resources.length, path);
        return Arrays.stream(resources).map(convertor).collect(Collectors.toList());
    }

    private Resource[] getResources(String path) {
        try {
            return resourcePatternResolver.getResources(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private BufferedImage toBufferedImage(Resource resource) {
        try {
            return ImageIO.read(resource.getInputStream());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String toBase64(Resource resource) {
        try {
            byte[] bytes = IOUtils.toByteArray(resource.getInputStream());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public List<BufferedImage> getSlidingBlocks() {
        return CACHE.computeIfAbsent(
                properties.getSlidingBlockPath(),
                key -> convertResources(key, this::toBufferedImage)
        );

    }
}
