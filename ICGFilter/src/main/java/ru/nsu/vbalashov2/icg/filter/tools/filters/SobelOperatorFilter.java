package ru.nsu.vbalashov2.icg.filter.tools.filters;

import io.reactivex.rxjava3.subjects.PublishSubject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SobelOperatorFilter implements Filter {

    private static final double[][] kernelGx = {
            { -1., -2., -1. },
            {  0.,  0.,  0. },
            {  1.,  2.,  1. },
    };
    private static final double[][] kernelGy = {
            { -1.,  0.,  1. },
            { -2.,  0.,  2. },
            { -1.,  0.,  1. },
    };

    private static final int kernelRadius = 1;

    private int threshold = 128;

    public SobelOperatorFilter(
            PublishSubject<Integer> newThresholdPublishSubject
    ) {
        newThresholdPublishSubject.subscribe(newThreshold -> threshold = newThreshold);
    }

    @Override
    public void use(BufferedImage originalImg, BufferedImage targetImg) {
        for (int x = kernelRadius; x < targetImg.getWidth() - kernelRadius; ++x) {
            for (int y = kernelRadius; y < targetImg.getHeight() - kernelRadius; ++y) {

                double xr = 0.;
                double xg = 0.;
                double xb = 0.;
                double yr = 0.;
                double yg = 0.;
                double yb = 0.;

                for (int kernelX = -kernelRadius; kernelX <= kernelRadius; ++kernelX) {
                    for (int kernelY = -kernelRadius; kernelY <= kernelRadius; ++kernelY) {
                        double xKernelValue = kernelGx[kernelX + kernelRadius][kernelY + kernelRadius];
                        double yKernelValue = kernelGy[kernelX + kernelRadius][kernelY + kernelRadius];

                        int rgb = originalImg.getRGB(x + kernelX, y + kernelY);

                        int r = rgb >> 16 & 0xff;
                        int g = rgb >> 8 & 0xff;
                        int b = rgb & 0xff;

                        xr += r * xKernelValue;
                        xg += g * xKernelValue;
                        xb += b * xKernelValue;

                        yr += r * yKernelValue;
                        yg += g * yKernelValue;
                        yb += b * yKernelValue;
                    }
                }

//                xr = normalizeColor(xr);
//                xg = normalizeColor(xg);
//                xb = normalizeColor(xb);
//                yr = normalizeColor(yr);
//                yg = normalizeColor(yg);
//                yb = normalizeColor(yb);

                int resultR = (int) Math.sqrt(xr * xr + yr * yr);
                int resultG = (int) Math.sqrt(xg * xg + yg * yg);
                int resultB = (int) Math.sqrt(xb * xb + yb * yb);

                if (resultR > threshold ||
                        resultG > threshold ||
                        resultB > threshold
                ) {
                    targetImg.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    targetImg.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
    }

    private static double normalizeColor(double color) {
        return (int) Math.max(Math.min(color, 255.), 0.);
    }
}
