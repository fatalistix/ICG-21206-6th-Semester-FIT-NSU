package ru.nsu.vbalashov2.igc.paint.components.menubar.viewmenu;

import com.google.common.eventbus.EventBus;
import ru.nsu.vbalashov2.igc.paint.tools.events.ClearEvent;

import javax.swing.*;

public class ClearMenuItem extends JMenuItem {

    public ClearMenuItem(EventBus eventBus) {
        super("Clear");

        addActionListener((event) -> eventBus.post(new ClearEvent()));
    }
}
