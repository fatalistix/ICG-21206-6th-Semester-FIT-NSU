package ru.nsu.vbalashov2.icg.filter.tools.filters;

import java.awt.image.BufferedImage;

public class BlackWhiteFilter implements Filter {

    @Override
    public void use(BufferedImage originalImg, BufferedImage targetImg) {
        for (int x = 0; x < targetImg.getWidth(); ++x) {
            for (int y = 0; y < targetImg.getHeight(); ++y) {
                int rgb = targetImg.getRGB(x, y);
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;
                int grey = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                rgb &= 0xff000000;
                rgb |= grey << 16;
                rgb |= grey << 8;
                rgb |= grey;
                targetImg.setRGB(x, y, rgb);
            }
        }
    }
}
