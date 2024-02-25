package ru.nsu.vbalashov2.igc.paint.tools.events;

public record SizeEvent(int radius) {
    public int initialRadius() {
        return 10;
    }
}
