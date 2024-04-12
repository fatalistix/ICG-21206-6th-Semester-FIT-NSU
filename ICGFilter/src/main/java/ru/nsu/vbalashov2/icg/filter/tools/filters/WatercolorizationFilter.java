package ru.nsu.vbalashov2.icg.filter.tools.filters;

import ru.nsu.vbalashov2.icg.filter.tools.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WatercolorizationFilter implements Filter {

    private final Filter sharpnessFilter;
    private final MedianBlurFilter medianBlurFilter;

    public WatercolorizationFilter(
            MedianBlurFilter medianBlurFilter,
            Filter sharpnessFilter
    ) {
        this.medianBlurFilter = medianBlurFilter;
        this.sharpnessFilter = sharpnessFilter;
    }

    @Override
    public void use(BufferedImage originalImg, BufferedImage targetImg) {
        medianBlurFilter.useDirect(originalImg, targetImg, 5);
        BufferedImage blurOriginalImg = ImageUtils.bufferedImageDeepCopy(targetImg);
        sharpnessFilter.use(blurOriginalImg, targetImg);
    }
}
