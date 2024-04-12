package ru.nsu.vbalashov2.icg.filter.tools.filters;

import java.awt.image.BufferedImage;

public class NegativeFilter implements Filter {

    @Override
    public void use(BufferedImage originalImg, BufferedImage targetImg) {
        for (int x = 0; x < targetImg.getWidth(); ++x) {
            for (int y = 0; y < targetImg.getHeight(); ++y) {
                int rgb = targetImg.getRGB(x, y);
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                rgb &= 0xff000000;
                rgb |= r << 16;
                rgb |= g << 8;
                rgb |= b;
                targetImg.setRGB(x, y, rgb);
            }
        }
    }
}
