package ru.nsu.vbalashov2.icg.filter.tools.filters;

import io.reactivex.rxjava3.subjects.PublishSubject;

import java.awt.image.BufferedImage;

public class GammaCorrectionFilter implements Filter {

    private double gamma = 1.;

    public GammaCorrectionFilter(
            PublishSubject<Double> newGammaPublishSubject
    ) {
        newGammaPublishSubject.subscribe(newGamma -> gamma = newGamma);
    }

    @Override
    public void use(BufferedImage originalImg, BufferedImage targetImg) {
        for (int x = 0; x < targetImg.getWidth(); ++x) {
            for (int y = 0; y < targetImg.getHeight(); ++y) {

                int rgb = originalImg.getRGB(x, y);
                double r = ((rgb >> 16) & 0xff) / 255.0;
                double g = ((rgb >> 8) & 0xff) / 255.0;
                double b = (rgb & 0xff) / 255.0;

                r = Math.pow(r, gamma) * 255.0;
                g = Math.pow(g, gamma) * 255.0;
                b = Math.pow(b, gamma) * 255.0;

                rgb = 0xff000000;
                rgb |= (((byte) r) & 0xff) << 16;
                rgb |= (((byte) g) & 0xff) << 8;
                rgb |= ((byte) b) & 0xff;
                targetImg.setRGB(x, y, rgb);
            }
        }
    }
}
