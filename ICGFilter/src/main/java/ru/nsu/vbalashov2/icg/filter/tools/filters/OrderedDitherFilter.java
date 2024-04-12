package ru.nsu.vbalashov2.icg.filter.tools.filters;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.filter.tools.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class OrderedDitherFilter implements Filter {


    private int redQuantizationNumber = 2;
    private int greenQuantizationNumber = 2;
    private int blueQuantizationNumber = 2;

    public OrderedDitherFilter(
            PublishSubject<Integer> newRedQuantizationNumberPublishSubject,
            PublishSubject<Integer> newGreenQuantizationNumberPublishSubject,
            PublishSubject<Integer> newBlueQuantizationNumberPublishSubject
    ) {
        newRedQuantizationNumberPublishSubject.subscribe(newNumber -> redQuantizationNumber = newNumber);
        newGreenQuantizationNumberPublishSubject.subscribe(newNumber -> greenQuantizationNumber = newNumber);
        newBlueQuantizationNumberPublishSubject.subscribe(newNumber -> blueQuantizationNumber = newNumber);
    }

    private static final int[][] initialBayer = {
            { 0, 2 },
            { 3, 1 },
    };

    @Override
    public void use(BufferedImage originalImg, BufferedImage targetImg) {
        int size = countMatrixSize();
        int[][] bayer = createBayerWithSize(size);
        double redMatrixStep = 256. / (redQuantizationNumber);
        double greenMatrixStep = 256. / (greenQuantizationNumber);
        double blueMatrixStep = 256. / (blueQuantizationNumber);

        double redPaletteStep = 255. / (redQuantizationNumber - 1);
        double greenPaletteStep = 255. / (greenQuantizationNumber - 1);
        double bluePaletteStep = 255. / (blueQuantizationNumber - 1);

        int divider = (size * size);

        for (int x = 0; x < targetImg.getWidth(); ++x) {
            for (int y = 0; y < targetImg.getHeight(); ++y) {
                int rgb = targetImg.getRGB(x, y);
                int r = ColorUtils.makeRed(rgb);
                int g = ColorUtils.makeGreen(rgb);
                int b = ColorUtils.makeBlue(rgb);

                int matPosX = x % size;
                int matPosY = y % size;

                int ditherR = (int) (r - 0.5 * redMatrixStep + (redMatrixStep * bayer[matPosY][matPosX] / divider));
                int ditherG = (int) (g - 0.5 * greenMatrixStep + (greenMatrixStep * bayer[matPosY][matPosX] / divider));
                int ditherB = (int) (b - 0.5 * blueMatrixStep + (blueMatrixStep * bayer[matPosY][matPosX] / divider));

                int newR = ColorUtils.findNearestColor(ColorUtils.normalize(ditherR), redPaletteStep);
                int newG = ColorUtils.findNearestColor(ColorUtils.normalize(ditherG), greenPaletteStep);
                int newB = ColorUtils.findNearestColor(ColorUtils.normalize(ditherB), bluePaletteStep);

                int newRGB = ColorUtils.makeRGB(newR, newG, newB);
                targetImg.setRGB(x, y, newRGB);
            }
        }
    }

    private int countMatrixSize() {
        double rStep = 256. / redQuantizationNumber;
        double gStep = 256. / greenQuantizationNumber;
        double bStep = 256. / blueQuantizationNumber;
        double maxStep = Math.max(Math.max(rStep, gStep), bStep);

//        int[] possibleSizes = {2, 4, 8, 16};
        for (int ps = 2; ps <= Short.MAX_VALUE; ps *= 2) {
            if (maxStep <= ps) {
                return ps;
            }
        }
        return 0;
    }

    private static int[][] createBayerWithSize(int size) {
        if (size == 2) {
            return initialBayer;
        }

        int[][] result = new int[size][size];
        int[][] previousLayer = createBayerWithSize(size / 2);
        for (int y = 0; y < previousLayer.length; ++y) {
            for (int x = 0; x < previousLayer.length; ++x) {
                int cell = previousLayer[y][x];
                int fac = 4;
                result[y][x] = fac * cell;
                result[y][x + previousLayer.length] = fac * cell + 2;
                result[y + previousLayer.length][x] = fac * cell + 3;
                result[y + previousLayer.length][x + previousLayer.length] = fac * cell + 1;
            }
        }
        return result;
    }
}
