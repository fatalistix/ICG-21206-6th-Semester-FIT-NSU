package ru.nsu.vbalashov2.igc.paint.tools.events;

public record ImageResizeEvent(int width, int height) {

    public static int initialWidth() {
        return 1_000;
    }

    public static int initialHeight() {
        return 1_000;
    }

    public static int minWidth() {
        return 1;
    }

    public static int minHeight() {
        return 1;
    }

    public static int maxWidth() {
        return 10_000;
    }

    public static int maxHeight() {
        return 10_000;
    }
}
