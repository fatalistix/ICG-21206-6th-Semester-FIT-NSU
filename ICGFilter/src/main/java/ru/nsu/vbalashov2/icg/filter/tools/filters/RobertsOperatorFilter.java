package ru.nsu.vbalashov2.icg.filter.tools.filters;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.filter.tools.utils.ColorUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RobertsOperatorFilter implements Filter {

    private static final double[][] kernelGx = {
            {  1.,  0. },
            {  0., -1. },
    };
    private static final double[][] kernelGy = {
            {   0.,  1. },
            {  -1.,  0. },
    };

    private int threshold = 10;

    public RobertsOperatorFilter(
            PublishSubject<Integer> newThresholdPublishSubject
    ) {
        newThresholdPublishSubject.subscribe(newThreshold -> threshold = newThreshold);
    }

    @Override
    public void use(BufferedImage originalImg, BufferedImage targetImg) {
        for (int x = 0; x < targetImg.getWidth() - 1; ++x) {
            for (int y = 0; y < targetImg.getHeight() - 1; ++y) {

                double xr = 0.;
                double xg = 0.;
                double xb = 0.;
                double yr = 0.;
                double yg = 0.;
                double yb = 0.;

                for (int kernelY = 0; kernelY <= 1; ++kernelY) {
                    for (int kernelX = 0; kernelX <= 1; ++kernelX) {
                        double xKernelValue = kernelGx[kernelY][kernelX];
                        double yKernelValue = kernelGy[kernelY][kernelX];

                        int rgb = originalImg.getRGB(x + kernelX, y + kernelY);

                        int r = ColorUtils.makeRed(rgb);
                        int g = ColorUtils.makeGreen(rgb);
                        int b = ColorUtils.makeBlue(rgb);

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
