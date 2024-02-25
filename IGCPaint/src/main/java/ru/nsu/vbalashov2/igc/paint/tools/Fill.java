package ru.nsu.vbalashov2.igc.paint.tools;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ru.nsu.vbalashov2.igc.paint.tools.events.ColorEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Fill implements Tool {

    private Color color = Color.BLACK;

    public Fill(EventBus eventBus) {
        eventBus.register(this);
    }

    @Override
    public void use(BufferedImage image, int x, int y) {
        int originalRGB = image.getRGB(x, y);
        fillArea(image, x, y, originalRGB, color.getRGB());
    }

    private static void fillArea(BufferedImage image, int xStart, int yStart, int originalRGB, int fillRGB) {
        if (!inside(image, xStart, yStart, originalRGB)) {
            return;
        }
        Stack<Point> stack = new Stack<>();
        stack.push(new Point(xStart, yStart));
        while (!stack.empty()) {
            Point spanPoint = stack.pop();
            int leftX = spanPoint.x;
            while (Fill.inside(image, leftX - 1, spanPoint.y, originalRGB)) {
                image.setRGB(leftX - 1, spanPoint.y, fillRGB);
                --leftX;
            }
            int rightX = spanPoint.x;
            while (Fill.inside(image, rightX, spanPoint.y, originalRGB)) {
                image.setRGB(rightX, spanPoint.y, fillRGB);
                ++rightX;
            }
            Fill.scan(image, leftX, rightX - 1, spanPoint.y + 1, stack, originalRGB);
            Fill.scan(image, leftX, rightX - 1, spanPoint.y - 1, stack, originalRGB);
        }
    }

    private static boolean inside(BufferedImage image, int x, int y, int originalRGB) {
        return 0 <= x && x < image.getWidth() &&
                0 <= y && y < image.getHeight() &&
                image.getRGB(x, y) == originalRGB;
    }

    private static void scan(BufferedImage image, int lx, int rx, int y, Stack<Point> stack, int originalRGB) {
        boolean spanAdded = false;
        for (int x = lx; x <= rx; ++x) {
            if (!Fill.inside(image, x, y, originalRGB)) {
                spanAdded = false;
            } else if (!spanAdded) {
                stack.push(new Point(x, y));
                spanAdded = true;
            }
        }
    }

    @Subscribe
    private void handleColorEvent(ColorEvent colorEvent) {
        color = colorEvent.color();
    }
}
