package ru.nsu.vbalashov2.igc.paint.components.menubar.viewmenu;

import com.google.common.eventbus.EventBus;
import ru.nsu.vbalashov2.igc.paint.tools.PaintTool;

import javax.swing.*;

public class ViewMenu extends JMenu {

    public ViewMenu(EventBus eventBus) {
        super("View");

        add(new ToolRadioButtonMenuItem(eventBus, "Fill", PaintTool.FILL));
        add(new ToolRadioButtonMenuItem(eventBus, "Line", PaintTool.LINE));
        add(new ToolRadioButtonMenuItem(eventBus, "Polygon", PaintTool.POLYGON));
        add(new ToolRadioButtonMenuItem(eventBus, "Star", PaintTool.STAR));

        addSeparator();

        add(new ClearMenuItem(eventBus));

        addSeparator();

        add(new ResizeMenuItem(eventBus));

        addSeparator();

        add(new UndoMenuItem(eventBus));
    }
}
