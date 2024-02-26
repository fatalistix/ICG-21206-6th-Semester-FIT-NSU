package ru.nsu.vbalashov2.igc.paint.components.toolbar;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ru.nsu.vbalashov2.igc.paint.tools.PaintTool;
import ru.nsu.vbalashov2.igc.paint.tools.events.PaintToolEvent;

import javax.swing.*;

public class ToolToggleButton extends JToggleButton {

    private final PaintTool tool;

    public ToolToggleButton(ImageIcon toolImageIcon, PaintTool tool, EventBus eventBus, String toolTipText) {
        super(toolImageIcon);

        this.tool = tool;

        eventBus.register(this);

        addActionListener((event) -> eventBus.post(new PaintToolEvent(tool)));
        setSelected(tool == PaintToolEvent.initialPaintTool());
        setToolTipText(toolTipText);
    }

    @Subscribe
    private void handlePaintToolEvent(PaintToolEvent event) {
        setSelected(tool == event.paintTool());
    }
}
