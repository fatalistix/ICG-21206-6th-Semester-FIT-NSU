package ru.nsu.vbalashov2.igc.paint.tools.events;

public record SizeEvent(int radius) {
    public static int initialRadius() {
        return 10;
    }

    public static int minRadius() {
        return 1;
    }

    public static int maxRadius() {
        return 500;
    }
}
