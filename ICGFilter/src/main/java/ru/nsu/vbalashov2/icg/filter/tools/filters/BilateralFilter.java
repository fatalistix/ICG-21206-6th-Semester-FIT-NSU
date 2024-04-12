package ru.nsu.vbalashov2.icg.filter.tools.filters;

import ru.nsu.vbalashov2.icg.filter.tools.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class BilateralFilter implements Filter {

    private int diameter = 9;
    private double sigmaI = 20.0;
    private double sigmaS = 20.0;

    @Override
    public void use(BufferedImage originalImg, BufferedImage targetImg) {
        int radius = diameter / 2;

        for (int y = radius; y < targetImg.getHeight() - radius; ++y) {
            for (int x = radius; x < targetImg.getWidth() - radius; ++x) {
                double rIFiltered = 0;
                double gIFiltered = 0;
                double bIFiltered = 0;

                double rWP = 0;
                double gWP = 0;
                double bWP = 0;

                int rgb = originalImg.getRGB(x, y);
                int r = ColorUtils.makeRed(rgb);
                int g = ColorUtils.makeGreen(rgb);
                int b = ColorUtils.makeBlue(rgb);

                for (int i = 0; i < diameter; ++i) {
                    for (int j = 0; j < diameter; ++j) {
                        int neighbourX = x - (radius - i);
                        int neighbourY = y - (radius - j);

                        int neighbourRGB = originalImg.getRGB(neighbourX, neighbourY);
                        int nr = ColorUtils.makeRed(neighbourRGB);
                        int ng = ColorUtils.makeGreen(neighbourRGB);
                        int nb = ColorUtils.makeBlue(neighbourRGB);

                        double rgi = gaussFunction(nr - r, sigmaI);
                        double ggi = gaussFunction(ng - g, sigmaI);
                        double bgi = gaussFunction(nb - b, sigmaI);

                        double gs = gaussFunction(distance(x, y, neighbourX, neighbourY), sigmaS);

                        double rw = rgi * gs;
                        double gw = ggi * gs;
                        double bw = bgi * gs;

                        rIFiltered = rIFiltered + nr * rw;
                        gIFiltered = gIFiltered + ng * gw;
                        bIFiltered = bIFiltered + nb * bw;

                        rWP += rw;
                        gWP += gw;
                        bWP += bw;
                    }
                }

                rIFiltered /= rWP;
                gIFiltered /= gWP;
                bIFiltered /= bWP;

                int rNormalized = ColorUtils.normalize((int) rIFiltered);
                int gNormalized = ColorUtils.normalize((int) gIFiltered);
                int bNormalized = ColorUtils.normalize((int) bIFiltered);

                int newRGB = ColorUtils.makeRGB(rNormalized, gNormalized, bNormalized);
                targetImg.setRGB(x, y, newRGB);
            }
        }
    }

    private static double gaussFunction(double x, double sigma) {
        double expNum = - x * x;
        double expDenum = 2. * sigma * sigma;
        double expExpression = Math.exp(expNum / expDenum);
//        return (expExpression / (sigma * Math.sqrt(2. * Math.PI)));
        return (expExpression / (sigma * sigma * 2. * Math.PI));
    }

    double distance(int x, int y, int i, int j) {
        return Math.sqrt((x - i) * (x - i) + (y - j) * (y - j));
    }
}
