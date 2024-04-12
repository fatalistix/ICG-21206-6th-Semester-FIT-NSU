package ru.nsu.vbalashov2.icg.filter.tools.utils;

public class ColorUtils {

    public static int makeRGB(int r, int g, int b) {
        return 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
    }

    public static int makeRed(int rgb) {
        return (rgb >> 16) & 0xff;
    }

    public static int makeGreen(int rgb) {
        return (rgb >> 8) & 0xff;
    }

    public static int makeBlue(int rgb) {
        return (rgb & 0xff);
    }

    public static int addColors(int rgb, double r, double g, double b) {
        int newR = normalize((int) (makeRed(rgb) + r));
        int newG = normalize((int) (makeGreen(rgb) + g));
        int newB = normalize((int) (makeBlue(rgb) + b));
        return makeRGB(newR, newG, newB);
    }

    public static int normalize(int color) {
        return Math.max(Math.min(255, color), 0);
    }

    public static int findNearestColor(int color, double step) {
        int lowBound = (int) (color / step);
        int lowValue = (int) (lowBound * step);
        int highValue = (int) ((lowBound + 1) * step);
        int lowError = Math.abs(lowValue - color);
        int highError = Math.abs(highValue - color);
        if (lowError < highError) {
            return lowValue;
        } else {
            return highValue;
        }
    }
}
