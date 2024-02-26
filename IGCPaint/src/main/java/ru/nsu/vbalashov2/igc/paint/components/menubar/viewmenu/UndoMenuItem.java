package ru.nsu.vbalashov2.igc.paint.components.menubar.viewmenu;

import com.google.common.eventbus.EventBus;
import ru.nsu.vbalashov2.igc.paint.tools.UndoEvent;

import javax.swing.*;

public class UndoMenuItem extends JMenuItem {

    public UndoMenuItem(EventBus eventBus) {
        super("Undo");

        addActionListener((event) -> eventBus.post(new UndoEvent()));
    }
}
