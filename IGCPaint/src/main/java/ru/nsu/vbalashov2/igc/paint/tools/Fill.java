package ru.nsu.vbalashov2.igc.paint.tools;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Fill {
    public static void fillArea(BufferedImage image, int x, int y, Color originalColor, Color fillColor) {
        if (!Fill.inside(image, x, y, originalColor)) {
            return;
        }
        Stack<Point> s = new Stack<>();
        s.push(new Point(x, y));
        while (!s.empty()) {
            Point p = s.pop();
            int lx = p.x;
            while (Fill.inside(image, lx - 1, p.y, originalColor)) {
                image.setRGB(lx - 1, y, fillColor.getRGB());
                --lx;
            }
            int rx = p.x;
            while (Fill.inside(image, rx, p.y, originalColor)) {
                image.setRGB(rx, p.y, fillColor.getRGB());
                ++rx;
            }
            Fill.scan(image, lx, rx - 1, p.y + 1, s, originalColor);
            Fill.scan(image, lx, rx - 1, p.y - 1, s, originalColor);
        }
    }

    private static boolean inside(BufferedImage image, int x, int y, Color originalColor) {
        return 0 <= x && x < image.getWidth() &&
                0 <= y && y < image.getHeight() &&
                image.getRGB(x, y) == originalColor.getRGB();
    }

    private static void scan(BufferedImage image, int lx, int rx, int y, Stack<Point> stack, Color originalColor) {
        boolean spanAdded = false;
        for (int x = lx; x <= rx; ++x) {
            if (!Fill.inside(image, x, y, originalColor)) {
                spanAdded = false;
            } else if (!spanAdded) {
                stack.add(new Point(x, y));
                spanAdded = true;
            }
        }
    }
}
