package ru.nsu.vbalashov2.icg.filter.tools.filters;

import java.awt.image.BufferedImage;

public class EmbossingFilter implements Filter {

    private static final double[][] kernel = {
            {  0.,  1.,  0. },
            { -1.,  0.,  1. },
            {  0., -1.,  0. },
    };

    private static final int radius = 1;

    @Override
    public void use(BufferedImage originalImg, BufferedImage targetImg) {
        for (int x = radius; x < targetImg.getWidth() - radius; ++x) {
            for (int y = radius; y < targetImg.getHeight() - radius; ++y) {

                double resR = 0.;
                double resG = 0.;
                double resB = 0.;

                for (int kernelX = -radius; kernelX <= radius; ++kernelX) {
                    for (int kernelY = -radius; kernelY <= radius; ++kernelY) {
                        double kernelValue = kernel[kernelX + radius][kernelY + radius];
                        int rgb = originalImg.getRGB(x - kernelX, y - kernelY);

                        int r = (rgb >> 16) & 0xff;
                        int g = (rgb >> 8) & 0xff;
                        int b = rgb & 0xff;

                        resR += r * kernelValue;
                        resG += g * kernelValue;
                        resB += b * kernelValue;
                    }
                }

                resR += 128.;
                resG += 128.;
                resB += 128.;

                resR = Math.max(Math.min(resR, 255.), 0.);
                resG = Math.max(Math.min(resG, 255.), 0.);
                resB = Math.max(Math.min(resB, 255.), 0.);

                int rgb = 0xff000000;
                rgb |= (((byte) resR) & 0xff) << 16;
                rgb |= (((byte) resG) & 0xff) << 8;
                rgb |= ((byte) resB) & 0xff;
                targetImg.setRGB(x, y, rgb);
            }
        }
    }
}
