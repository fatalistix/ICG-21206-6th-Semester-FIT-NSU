package ru.nsu.vbalashov2.icg.filter.tools.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class ImageUtils {

    public static BufferedImage bufferedImageDeepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static BufferedImage bufferedImageRotate(BufferedImage originalImg, int angleDegrees, Color fillColor) {
        double angleRadians = Math.toRadians(angleDegrees);

        double angleSin = Math.sin(angleRadians);
        double angleCos = Math.cos(angleRadians);

        int newWidth = (int) (originalImg.getWidth() * Math.abs(angleCos) + originalImg.getHeight() * Math.abs(angleSin));
        int newHeight = (int) (originalImg.getWidth() * Math.abs(angleSin) + originalImg.getHeight() * Math.abs(angleCos));

        BufferedImage newImg = new BufferedImage(newWidth, newHeight, originalImg.getType());
        Graphics2D newG2d = newImg.createGraphics();
        newG2d.setColor(fillColor);
        newG2d.fillRect(0, 0, newWidth, newHeight);
        newG2d.dispose();

        int newHalfWidth = newWidth >> 1;
        int newHalfHeight = newHeight >> 1;
        int oldHalfWidth = originalImg.getWidth() >> 1;
        int oldHalfHeight = originalImg.getHeight() >> 1;

        for (int newX = 0; newX < newWidth; ++newX) {
            for (int newY = 0; newY < newHeight; ++newY) {
                int oldX = (int) ((newX - newHalfWidth) * angleCos + (newY - newHalfHeight) * angleSin) + oldHalfWidth;
                int oldY = (int) ((newX - newHalfWidth) * (-angleSin) + (newY - newHalfHeight) * angleCos) + oldHalfHeight;

                int rgb;
                if (isInBounds(oldX, oldY, originalImg.getWidth(), originalImg.getHeight())) {
                    rgb = originalImg.getRGB(oldX, oldY);
                } else {
                    rgb = fillColor.getRGB();
                }

                newImg.setRGB(newX, newY, rgb);
            }
        }

        return newImg;
    }

    private static boolean isInBounds(int x, int y, int width, int height) {
        return 0 <= x && x < width && 0 <= y && y < height;
    }
}
