package ru.nsu.vbalashov2.igc.paint.tools.events;

public record RotateEvent(int angle) {
    public static int initialAngle() {
        return 0;
    }

    public static int minAngle() {
        return 0;
    }

    public static int maxAngle() {
        return 360;
    }
}
