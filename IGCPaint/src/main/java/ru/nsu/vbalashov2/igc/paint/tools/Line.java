package ru.nsu.vbalashov2.igc.paint.tools;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Line {
    public static void drawLine(BufferedImage image, Point start, Point end, Color color) {
        int x = start.x;
        int y = start.y;
        int dx = end.x - x;
        int dy = end.y - y;
        int err = - dx;
        for (int i = 0; i < dx; ++i) {
            ++x;
            err += 2 * dy;
            if (err > 0) {
                err -= 2 * dx;
                ++y;
            }
            image.setRGB(x, y, color.getRGB());
        }
    }
}
