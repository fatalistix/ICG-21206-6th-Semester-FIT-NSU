package ru.nsu.vbalashov2.igc.paint.components.toolbar;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ru.nsu.vbalashov2.igc.paint.tools.events.ColorEvent;

import javax.swing.*;
import java.awt.*;

public class ColorChooserButton extends JButton {
    private Color currentColor;

    public ColorChooserButton(ImageIcon colorChooserImageIcon, EventBus eventBus) {
        currentColor = ColorEvent.initialColor();
        eventBus.register(this);

        setIcon(colorChooserImageIcon);
        setToolTipText("Choose color...");
        addActionListener((event) -> {
            Color newColor = JColorChooser.showDialog(this, "Choose a color", currentColor);
            if (newColor != null) {
                eventBus.post(new ColorEvent(newColor));
            }
        });
    }

    @Subscribe
    private void handleNewColorEvent(ColorEvent newColor) {
        currentColor = newColor.color();
    }
}
