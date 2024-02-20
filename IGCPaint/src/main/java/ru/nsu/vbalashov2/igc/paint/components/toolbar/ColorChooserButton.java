package ru.nsu.vbalashov2.igc.paint.components.toolbar;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import javax.swing.*;
import java.awt.*;

public class ColorChooserButton extends JButton {
    private Color currentColor;

    public ColorChooserButton(ImageIcon colorChooserImageIcon, Color initialColor, EventBus eventBus) {
        this.currentColor = initialColor;
        eventBus.register(this);

        this.setIcon(colorChooserImageIcon);
        this.addActionListener((event) -> {
            Color newColor = JColorChooser.showDialog(this, "Choose a color", currentColor);
            if (newColor != null) {
                eventBus.post(newColor);
            }
        });
    }

    @Subscribe
    private void handleNewColorEvent(Color newColor) {
        this.currentColor = newColor;
    }
}
