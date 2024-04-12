package ru.nsu.vbalashov2.icg.filter.tools.filters;

import java.awt.image.BufferedImage;

public interface Filter {
    void use(BufferedImage originalImg, BufferedImage targetImg);
}
