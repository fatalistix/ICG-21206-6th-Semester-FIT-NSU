package ru.nsu.vbalashov2.igc.paint.components.options;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ru.nsu.vbalashov2.igc.paint.tools.events.FormEvent;
import ru.nsu.vbalashov2.igc.paint.tools.events.RotateEvent;

import javax.swing.*;
import java.awt.*;

public class RotatePanel extends JPanel {

    private final JSlider rotateSlider;
    private final JSpinner rotateIntSpinner;

    public RotatePanel(EventBus eventBus) {
        eventBus.register(this);

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JLabel rotateLabel = new JLabel("Rotation (degrees):");
        this.add(rotateLabel);

        rotateSlider = new JSlider(
                RotateEvent.minAngle(),
                RotateEvent.maxAngle(),
                RotateEvent.initialAngle()
        );
        rotateSlider.setPaintLabels(true);
        rotateSlider.setMajorTickSpacing(20);
        rotateSlider.setMinorTickSpacing(1);
        rotateSlider.addChangeListener(
                (event) -> eventBus.post(new RotateEvent(rotateSlider.getValue()))
        );
        this.add(rotateSlider);

        rotateIntSpinner = new JSpinner(
                new SpinnerNumberModel(
                        RotateEvent.initialAngle(),
                        RotateEvent.minAngle(),
                        RotateEvent.maxAngle(),
                        1
                )
        );
        rotateIntSpinner.addChangeListener((event) -> eventBus.post(
                new RotateEvent((Integer) rotateIntSpinner.getValue())
        ));
        this.add(rotateIntSpinner);
    }

    @Subscribe
    private void handleRotateEvent(RotateEvent rotateEvent) {
        rotateSlider.setValue(rotateEvent.angle());
        rotateIntSpinner.setValue(rotateEvent.angle());
    }
}
