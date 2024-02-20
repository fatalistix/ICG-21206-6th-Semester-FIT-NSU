package ru.nsu.vbalashov2.igc.paint.components.toolbar;

import com.google.common.eventbus.EventBus;
import ru.nsu.vbalashov2.igc.paint.tools.PaintTool;

import javax.swing.*;

public class ToolButton extends JButton {
    public ToolButton(ImageIcon toolImageIcon, PaintTool tool, EventBus eventBus) {
        this.setIcon(toolImageIcon);
        this.addActionListener((event) -> eventBus.post(tool));
    }
}
