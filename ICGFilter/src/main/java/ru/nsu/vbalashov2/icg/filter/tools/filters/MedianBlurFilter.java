package ru.nsu.vbalashov2.icg.filter.tools.filters;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.filter.tools.utils.ColorUtils;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class MedianBlurFilter implements Filter {

    private int size = 3;

    public MedianBlurFilter(
            PublishSubject<Integer> newSizePublishSubject
    ) {
        newSizePublishSubject.subscribe(newValue -> size = newValue);
    }

    @Override
    public void use(BufferedImage originalImg, BufferedImage targetImg) {
        int[] rArr = new int[size * size];
        int[] gArr = new int[size * size];
        int[] bArr = new int[size * size];
        int radius = size >> 1;

        for (int x = radius; x < targetImg.getWidth() - radius; ++x) {
            for (int y = radius; y < targetImg.getHeight() - radius; ++y) {
                int i = 0;
                for (int kernelX = x - radius; kernelX <= x + radius; ++kernelX) {
                    for (int kernelY = y - radius; kernelY <= y + radius; ++kernelY) {
                        int rgb = targetImg.getRGB(kernelX, kernelY);

                        int r = ColorUtils.makeRed(rgb);
                        int g = ColorUtils.makeGreen(rgb);
                        int b = ColorUtils.makeBlue(rgb);

                        rArr[i] = r;
                        gArr[i] = g;
                        bArr[i] = b;

                        ++i;
                    }
                }

                Arrays.sort(rArr);
                Arrays.sort(gArr);
                Arrays.sort(bArr);

                int median = (size * size) >> 1;
                int rMedian = rArr[median];
                int gMedian = gArr[median];
                int bMedian = bArr[median];

                int newRGB = ColorUtils.makeRGB(rMedian, gMedian, bMedian);
                targetImg.setRGB(x, y, newRGB);
            }
        }
    }

    public void useDirect(BufferedImage originalImg, BufferedImage targetImg, int size) {
        int[] rArr = new int[size * size];
        int[] gArr = new int[size * size];
        int[] bArr = new int[size * size];
        int radius = size >> 1;

        for (int x = radius; x < targetImg.getWidth() - radius; ++x) {
            for (int y = radius; y < targetImg.getHeight() - radius; ++y) {
                int i = 0;
                for (int kernelX = x - radius; kernelX <= x + radius; ++kernelX) {
                    for (int kernelY = y - radius; kernelY <= y + radius; ++kernelY) {
                        int rgb = originalImg.getRGB(kernelX, kernelY);

                        int r = ColorUtils.makeRed(rgb);
                        int g = ColorUtils.makeGreen(rgb);
                        int b = ColorUtils.makeBlue(rgb);

                        rArr[i] = r;
                        gArr[i] = g;
                        bArr[i] = b;

                        ++i;
                    }
                }

                Arrays.sort(rArr);
                Arrays.sort(gArr);
                Arrays.sort(bArr);

                int median = (size * size) >> 1;
                int rMedian = rArr[median];
                int gMedian = gArr[median];
                int bMedian = bArr[median];

                int newRGB = ColorUtils.makeRGB(rMedian, gMedian, bMedian);
                targetImg.setRGB(x, y, newRGB);
            }
        }
    }
}
