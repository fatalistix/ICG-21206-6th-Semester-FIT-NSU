package ru.nsu.vbalashov2.igc.paint.tools.events;

public record ThicknessEvent(int thickness) {
    public static int initialThickness() {
        return 1;
    }

    public static int minThickness() {
        return 1;
    }

    public static int maxThickness() {
        return 30;
    }
}
