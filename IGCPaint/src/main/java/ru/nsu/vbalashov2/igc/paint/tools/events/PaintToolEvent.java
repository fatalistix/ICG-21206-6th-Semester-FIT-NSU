package ru.nsu.vbalashov2.igc.paint.tools.events;

import ru.nsu.vbalashov2.igc.paint.tools.PaintTool;

public record PaintToolEvent(PaintTool paintTool) {
    public PaintTool initialPaintTool() {
        return PaintTool.PEN;
    }
}