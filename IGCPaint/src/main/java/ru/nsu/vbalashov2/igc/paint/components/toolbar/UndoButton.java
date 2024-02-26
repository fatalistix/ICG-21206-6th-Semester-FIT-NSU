package ru.nsu.vbalashov2.igc.paint.components.toolbar;

import com.google.common.eventbus.EventBus;
import ru.nsu.vbalashov2.igc.paint.tools.UndoEvent;

import javax.swing.*;

public class UndoButton extends JButton {

    public UndoButton(ImageIcon icon, EventBus eventBus) {
        super(icon);

        addActionListener((e) -> eventBus.post(new UndoEvent()));
    }
}
