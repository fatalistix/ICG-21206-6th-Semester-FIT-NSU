package ru.nsu.vbalashov2.igc.paint.tools.events;

import java.awt.*;

public record ColorEvent(Color color) {
    public static Color initialColor() {
        return Color.BLACK;
    }
}
