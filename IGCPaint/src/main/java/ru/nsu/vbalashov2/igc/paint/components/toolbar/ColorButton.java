package ru.nsu.vbalashov2.igc.paint.components.toolbar;

import com.google.common.eventbus.EventBus;
import ru.nsu.vbalashov2.igc.paint.tools.events.ColorEvent;

import javax.swing.*;
import java.awt.*;

public class ColorButton extends JButton {
    public ColorButton(ImageIcon colorImageIcon, Color color, EventBus eventBus) {
        this.setIcon(colorImageIcon);
        this.addActionListener((event) -> eventBus.post(new ColorEvent(color)));
    }
}
