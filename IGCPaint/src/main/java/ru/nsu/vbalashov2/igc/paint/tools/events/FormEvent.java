package ru.nsu.vbalashov2.igc.paint.tools.events;

public record FormEvent(int numberOfAngles) {
    public static int initialNumberOfAngles() {
        return 3;
    }
}
