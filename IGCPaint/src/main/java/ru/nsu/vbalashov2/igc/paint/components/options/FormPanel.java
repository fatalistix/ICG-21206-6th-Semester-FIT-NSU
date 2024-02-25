package ru.nsu.vbalashov2.igc.paint.components.options;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ru.nsu.vbalashov2.igc.paint.tools.events.FormEvent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;

public class FormPanel extends JPanel {

    private final JSlider formIntSlider;
    private final JSpinner formIntSpinner;

    public FormPanel(EventBus eventBus) {
        eventBus.register(this);

        this.setLayout(new GridLayout(1, 3));

        JLabel formLabel = new JLabel("Form (number of angles):");
        this.add(formLabel);

        formIntSlider = new JSlider(
                FormEvent.minNumberOfAngles(),
                FormEvent.maxNumberOfAngles(),
                FormEvent.initialNumberOfAngles()
        );
        formIntSlider.setPaintLabels(true);
        formIntSlider.setMajorTickSpacing(2);
        formIntSlider.setMinorTickSpacing(1);
        formIntSlider.addChangeListener((event) -> eventBus.post(new FormEvent(formIntSlider.getValue())));
        this.add(formIntSlider);

        formIntSpinner = new JSpinner(
                new SpinnerNumberModel(
                        FormEvent.initialNumberOfAngles(),
                        FormEvent.minNumberOfAngles(),
                        FormEvent.maxNumberOfAngles(),
                        1
                )
        );
        formIntSpinner.addChangeListener((event) -> eventBus.post(new FormEvent((Integer) formIntSpinner.getValue())));
        this.add(formIntSpinner);
    }

    @Subscribe
    private void handleFormEvent(FormEvent event) {
        formIntSlider.setValue(event.numberOfAngles());
        formIntSpinner.setValue(event.numberOfAngles());
    }
}
