package ru.nsu.vbalashov2.igc.paint.components.options;

import com.google.common.eventbus.EventBus;
import ru.nsu.vbalashov2.igc.paint.tools.events.RotateEvent;

import javax.swing.*;
import java.awt.*;

public class RotatePanel extends JPanel {
    public RotatePanel(EventBus eventBus) {
        this.setLayout(new GridLayout(1, 2));

        JLabel rotateLabel = new JLabel("Rotation:");
        this.add(rotateLabel);

        JSpinner rotateIntSpinner = new JSpinner(
                new SpinnerNumberModel(
                        0, 0, 360, 1
                )
        );
        rotateIntSpinner.addChangeListener((event) -> eventBus.post(
                new RotateEvent((Integer) rotateIntSpinner.getValue())
        ));
    }
}
