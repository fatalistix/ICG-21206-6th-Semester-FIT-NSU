package ru.nsu.vbalashov2.igc.paint.components.toolbar;

import com.google.common.eventbus.EventBus;
import ru.nsu.vbalashov2.igc.paint.tools.events.ClearEvent;

import javax.swing.*;

public class ClearButton extends JButton {
    public ClearButton(ImageIcon clearImageIcon, EventBus eventBus) {
        setIcon(clearImageIcon);
        addActionListener((event) -> eventBus.post(new ClearEvent()));
        setToolTipText("Clear area");
    }
}
