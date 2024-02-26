package ru.nsu.vbalashov2.igc.paint.components.menubar.viewmenu;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ru.nsu.vbalashov2.igc.paint.tools.PaintTool;
import ru.nsu.vbalashov2.igc.paint.tools.events.PaintToolEvent;

import javax.swing.*;

public class ToolRadioButtonMenuItem extends JRadioButtonMenuItem {

    private final PaintTool tool;

    public ToolRadioButtonMenuItem(EventBus eventBus, String toolName, PaintTool tool) {
        super(toolName);

        this.tool = tool;

        eventBus.register(this);

        addActionListener((event) -> eventBus.post(new PaintToolEvent(tool)));
        setSelected(tool == PaintToolEvent.initialPaintTool());
    }

    @Subscribe
    private void handlePaintToolEvent(PaintToolEvent event) {
        setSelected(tool == event.paintTool());
    }
}
