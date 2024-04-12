package ru.nsu.vbalashov2.icg.filter.tools.filters;

import io.reactivex.rxjava3.subjects.PublishSubject;

import java.awt.image.BufferedImage;

public class GaussianBlurFilter implements Filter {

    private double[][] kernel = null;
    private double sigma = 1.;
    private int size = 3;

    public GaussianBlurFilter(
            PublishSubject<Double> newSigmaPublishSubject,
            PublishSubject<Integer> newSizePublishSubject
    ) {
        newSigmaPublishSubject.subscribe(newSigma -> sigma = newSigma);
        newSizePublishSubject.subscribe(newSize -> size = newSize);
    }

    @Override
    public void use(BufferedImage originalImg, BufferedImage targetImg) {
        buildKernel();
        int radius = size >> 1;

        for (int x = radius; x < targetImg.getWidth() - radius; ++x) {
            for (int y = radius; y < targetImg.getHeight() - radius; ++y) {

                double r = 0.;
                double g = 0.;
                double b = 0.;

                for (int kernelX = -radius; kernelX <= radius; ++kernelX) {
                    for (int kernelY = -radius; kernelY <= radius; ++kernelY) {
                        double kernelValue = kernel[kernelX + radius][kernelY + radius];
                        int rgb = originalImg.getRGB(x + kernelX, y + kernelY);

                        r += ((rgb >> 16) & 0xff) * kernelValue;
                        g += ((rgb >> 8) & 0xff) * kernelValue;
                        b += (rgb & 0xff) * kernelValue;
                    }
                }

                int rgb = 0xff000000;
                rgb |= (((byte) r) & 0xff) << 16;
                rgb |= (((byte) g) & 0xff) << 8;
                rgb |= ((byte) b) & 0xff;
                targetImg.setRGB(x, y, rgb);
            }
        }
    }

    private void buildKernel() {
        kernel = new double[size][size];
        double sum = 0.;
        int radius = size >> 1;

        for (int x = -radius; x <= radius; ++x) {
            for (int y = -radius; y <= radius; ++y) {
                double kernelValue = gaussFunction(x, y, sigma);
                kernel[x + radius][y + radius] = kernelValue;
                sum += kernelValue;
            }
        }

        int kernelSize = 2 * radius + 1;
        for (int x = 0; x < kernelSize; ++x) {
            for (int y = 0; y < kernelSize; ++y) {
                kernel[x][y] /= sum;
            }
        }
    }

    private static double gaussFunction(int x, int y, double sigma) {
        double expNum = - (x * x + y * y);
        double expDenum = 2. * sigma * sigma;
        double expExpression = Math.pow(Math.E, expNum / expDenum);
        return (expExpression / (2. * Math.PI * sigma * sigma));
    }
}
