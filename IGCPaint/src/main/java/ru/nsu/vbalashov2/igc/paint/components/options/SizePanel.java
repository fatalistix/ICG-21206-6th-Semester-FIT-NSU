package ru.nsu.vbalashov2.igc.paint.components.options;

import com.google.common.eventbus.EventBus;
import ru.nsu.vbalashov2.igc.paint.tools.events.SizeEvent;

import javax.swing.*;
import java.awt.*;

public class SizePanel extends JPanel {
    public SizePanel(EventBus eventBus) {
        this.setLayout(new GridLayout(1, 2));

        this.add(new JLabel("Size:"));

        JSpinner sizeIntSpinner = new JSpinner(new SpinnerNumberModel(
                10, 1, 2000, 1
        ));
        sizeIntSpinner.addChangeListener((event) -> eventBus.post(new SizeEvent((Integer) sizeIntSpinner.getValue())));
        this.add(sizeIntSpinner);
    }
}
