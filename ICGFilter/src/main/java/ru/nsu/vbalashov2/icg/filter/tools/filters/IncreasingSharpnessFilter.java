package ru.nsu.vbalashov2.icg.filter.tools.filters;

import java.awt.image.BufferedImage;

public class IncreasingSharpnessFilter implements Filter {

    private static final double[][] kernel = {
            {  0., -1.,  0. },
            { -1.,  5., -1. },
            {  0., -1.,  0. },
    };

    private static final int radius = 1;

    @Override
    public void use(BufferedImage originalImg, BufferedImage targetImg) {
        for (int x = radius; x < targetImg.getWidth() - radius; ++x) {
            for (int y = radius; y < targetImg.getHeight() - radius; ++y) {

                double r = 0.;
                double g = 0.;
                double b = 0.;

                for (int kernelX = -radius; kernelX <= radius; ++kernelX) {
                    for (int kernelY = -radius; kernelY <= radius; ++kernelY) {
                        double kernelValue = kernel[kernelX + radius][kernelY + radius];
                        int rgb = originalImg.getRGB(x - kernelX, y - kernelY);

                        r += ((rgb >> 16) & 0xff) * kernelValue;
                        g += ((rgb >> 8) & 0xff) * kernelValue;
                        b += (rgb & 0xff) * kernelValue;
                    }
                }

                r = Math.max(Math.min(r, 255.), 0.);
                g = Math.max(Math.min(g, 255.), 0.);
                b = Math.max(Math.min(b, 255.), 0.);

                int rgb = 0xff000000;
                rgb |= (((byte) r) & 0xff) << 16;
                rgb |= (((byte) g) & 0xff) << 8;
                rgb |= ((byte) b) & 0xff;
                targetImg.setRGB(x, y, rgb);
            }
        }
    }
}
