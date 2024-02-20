package ru.nsu.vbalashov2.igc.paint.components.options;

import com.google.common.eventbus.EventBus;
import ru.nsu.vbalashov2.igc.paint.tools.events.FormEvent;

import javax.swing.*;
import java.awt.*;

public class FormPanel extends JPanel {
    public FormPanel(EventBus eventBus) {
        this.setLayout(new GridLayout(1, 2));

        JLabel formLabel = new JLabel("Form:");
        this.add(formLabel);

        JSpinner formIntSpinner = new JSpinner(new SpinnerNumberModel(3, 3, 2000, 1));
        formIntSpinner.addChangeListener((event) -> eventBus.post(new FormEvent((Integer) formIntSpinner.getValue())));
        this.add(formIntSpinner);
    }
}
