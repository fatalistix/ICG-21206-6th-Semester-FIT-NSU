package ru.nsu.vbalashov2.igc.paint.tools;

import java.awt.image.BufferedImage;

public interface Tool {
    void use(BufferedImage image, int x, int y);
}
