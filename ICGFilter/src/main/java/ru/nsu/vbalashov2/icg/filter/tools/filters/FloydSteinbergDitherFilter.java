package ru.nsu.vbalashov2.icg.filter.tools.filters;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.filter.tools.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class FloydSteinbergDitherFilter implements Filter {

    private int redQuantizationNumber = 2;
    private int greenQuantizationNumber = 2;
    private int blueQuantizationNumber = 2;

    public FloydSteinbergDitherFilter(
            PublishSubject<Integer> newRedQuantizationNumberPublishSubject,
            PublishSubject<Integer> newGreenQuantizationNumberPublishSubject,
            PublishSubject<Integer> newBlueQuantizationNumberPublishSubject
    ) {
        newRedQuantizationNumberPublishSubject.subscribe(newNumber -> redQuantizationNumber = newNumber);
        newGreenQuantizationNumberPublishSubject.subscribe(newNumber -> greenQuantizationNumber = newNumber);
        newBlueQuantizationNumberPublishSubject.subscribe(newNumber -> blueQuantizationNumber = newNumber);
    }

    @Override
    public void use(BufferedImage originalImg, BufferedImage targetImg) {
        double redStep = 255. / (redQuantizationNumber - 1);
        double greenStep = 255. / (greenQuantizationNumber - 1);
        double blueStep = 255. / (blueQuantizationNumber - 1);

        for (int y = 0; y < targetImg.getHeight() - 1; ++y) {
            {
                int x = 0;
                int rgb = targetImg.getRGB(x, y);
                int r = ColorUtils.makeRed(rgb);
                int g = ColorUtils.makeGreen(rgb);
                int b = ColorUtils.makeBlue(rgb);

                int newR = ColorUtils.findNearestColor(r, redStep);
                int newG = ColorUtils.findNearestColor(g, greenStep);
                int newB = ColorUtils.findNearestColor(b, blueStep);

                int newRGB = ColorUtils.makeRGB(newR, newG, newB);

                targetImg.setRGB(x, y, newRGB);

                int redError = r - newR;
                int greenError = g - newG;
                int blueError = b - newB;

                applyToE(targetImg, x, y, redError, greenError, blueError);
                applyToS(targetImg, x, y, redError, greenError, blueError);
                applyToSE(targetImg, x, y, redError, greenError, blueError);
            }

            for (int x = 1; x < targetImg.getWidth() - 1; ++x) {
                int rgb = targetImg.getRGB(x, y);
                int r = ColorUtils.makeRed(rgb);
                int g = ColorUtils.makeGreen(rgb);
                int b = ColorUtils.makeBlue(rgb);

                int newR = ColorUtils.findNearestColor(r, redStep);
                int newG = ColorUtils.findNearestColor(g, greenStep);
                int newB = ColorUtils.findNearestColor(b, blueStep);

                int newRGB = ColorUtils.makeRGB(newR, newG, newB);

                targetImg.setRGB(x, y, newRGB);

                int redError = r - newR;
                int greenError = g - newG;
                int blueError = b - newB;

                applyToE(targetImg, x, y, redError, greenError, blueError);
                applyToSE(targetImg, x, y, redError, greenError, blueError);
                applyToSW(targetImg, x, y, redError, greenError, blueError);
                applyToS(targetImg, x, y, redError, greenError, blueError);
            }

            {
                int x = targetImg.getWidth() - 1;
                int rgb = targetImg.getRGB(x, y);
                int r = ColorUtils.makeRed(rgb);
                int g = ColorUtils.makeGreen(rgb);
                int b = ColorUtils.makeBlue(rgb);

                int newR = ColorUtils.findNearestColor(r, redStep);
                int newG = ColorUtils.findNearestColor(g, greenStep);
                int newB = ColorUtils.findNearestColor(b, blueStep);

                int newRGB = ColorUtils.makeRGB(newR, newG, newB);

                targetImg.setRGB(x, y, newRGB);

                int redError = r - newR;
                int greenError = g - newG;
                int blueError = b - newB;

                applyToS(targetImg, x, y, redError, greenError, blueError);
                applyToSW(targetImg, x, y, redError, greenError, blueError);
            }
        }

        {
            int y = targetImg.getHeight() - 1;
            for (int x = 0; x < targetImg.getWidth() - 1; ++x) {
                int rgb = targetImg.getRGB(x, y);
                int r = ColorUtils.makeRed(rgb);
                int g = ColorUtils.makeGreen(rgb);
                int b = ColorUtils.makeBlue(rgb);

                int newR = ColorUtils.findNearestColor(r, redStep);
                int newG = ColorUtils.findNearestColor(g, greenStep);
                int newB = ColorUtils.findNearestColor(b, blueStep);

                int newRGB = ColorUtils.makeRGB(newR, newG, newB);

                targetImg.setRGB(x, y, newRGB);

                int redError = r - newR;
                int greenError = g - newG;
                int blueError = b - newB;

                applyToE(targetImg, x, y, redError, greenError, blueError);
            }
            {
                int x = targetImg.getWidth() - 1;
                int rgb = targetImg.getRGB(x, y);
                int r = ColorUtils.makeRed(rgb);
                int g = ColorUtils.makeGreen(rgb);
                int b = ColorUtils.makeBlue(rgb);

                int newR = ColorUtils.findNearestColor(r, redStep);
                int newG = ColorUtils.findNearestColor(g, greenStep);
                int newB = ColorUtils.findNearestColor(b, blueStep);

                int newRGB = ColorUtils.makeRGB(newR, newG, newB);

                targetImg.setRGB(x, y, newRGB);
            }
        }
    }

    // it will automatically make (x, y) -> (x + 1, y), so pass the coords from point where you are at now
    private static void applyToE(BufferedImage image, int x, int y, int re, int ge, int be) {
        int rgb = image.getRGB(x + 1, y);
        int newRGB = ColorUtils.addColors(
                rgb,
                re * 7. / 16.,
                ge * 7. / 16.,
                be * 7. / 16.
        );

        image.setRGB(x + 1, y, newRGB);
    }

    private static void applyToSW(BufferedImage image, int x, int y, int re, int ge, int be) {
        int rgb = image.getRGB(x - 1, y + 1);
        int newRGB = ColorUtils.addColors(
                rgb,
                re * 3. / 16.,
                ge * 3. / 16.,
                be * 3. / 16.
        );

        image.setRGB(x - 1, y + 1, newRGB);
    }

    private static void applyToS(BufferedImage image, int x, int y, int re, int ge, int be) {
        int rgb = image.getRGB(x, y + 1);
        int newRGB = ColorUtils.addColors(
                rgb,
                re * 5. / 16.,
                ge * 5. / 16.,
                be * 5. / 16.
        );

        image.setRGB(x, y + 1, newRGB);
    }

    private static void applyToSE(BufferedImage image, int x, int y, int re, int ge, int be) {
        int rgb = image.getRGB(x + 1, y + 1);
        int newRGB = ColorUtils.addColors(
                rgb,
                re * 1. / 16.,
                ge * 1. / 16.,
                be * 1. / 16.
        );

        image.setRGB(x + 1, y + 1, newRGB);
    }
}
