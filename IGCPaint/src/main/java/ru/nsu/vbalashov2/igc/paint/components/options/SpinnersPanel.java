package ru.nsu.vbalashov2.igc.paint.components.options;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ru.nsu.vbalashov2.igc.paint.tools.events.FormEvent;
import ru.nsu.vbalashov2.igc.paint.tools.events.RotateEvent;
import ru.nsu.vbalashov2.igc.paint.tools.events.SizeEvent;
import ru.nsu.vbalashov2.igc.paint.tools.events.ThicknessEvent;

import javax.swing.*;
import java.awt.*;

public class SpinnersPanel extends JPanel {

    private final JSpinner formIntSpinner;
    private final JSpinner sizeIntSpinner;
    private final JSpinner rotateIntSpinner;
    private final JSpinner thicknessIntSpinner;

    public SpinnersPanel(EventBus eventBus) {
        eventBus.register(this);

        this.setLayout(new GridLayout(4, 1));

        formIntSpinner = new JSpinner(
                new SpinnerNumberModel(
                        FormEvent.initialNumberOfAngles(),
                        FormEvent.minNumberOfAngles(),
                        FormEvent.maxNumberOfAngles(),
                        1
                )
        );
        formIntSpinner.addChangeListener(
                (event) -> eventBus.post(new FormEvent((Integer) formIntSpinner.getValue()))
        );
        this.add(formIntSpinner);

        sizeIntSpinner = new JSpinner(
                new SpinnerNumberModel(
                        SizeEvent.initialRadius(),
                        SizeEvent.minRadius(),
                        SizeEvent.maxRadius(),
                        1
                )
        );
        sizeIntSpinner.addChangeListener(
                (event) -> eventBus.post(new SizeEvent((Integer) sizeIntSpinner.getValue()))
        );
        this.add(sizeIntSpinner);

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

        thicknessIntSpinner = new JSpinner(
                new SpinnerNumberModel(
                        ThicknessEvent.initialThickness(),
                        ThicknessEvent.minThickness(),
                        ThicknessEvent.maxThickness(),
                        1
                )
        );
        thicknessIntSpinner.addChangeListener((event) -> eventBus.post(
                new ThicknessEvent((Integer) thicknessIntSpinner.getValue())
        ));
        this.add(thicknessIntSpinner);
    }

    @Subscribe
    private void handleFormEvent(FormEvent event) {
        formIntSpinner.setValue(event.numberOfAngles());
    }

    @Subscribe
    private void handleSizeEvent(SizeEvent event) {
        sizeIntSpinner.setValue(event.radius());
    }

    @Subscribe
    private void handleRotationEvent(RotateEvent event) {
        rotateIntSpinner.setValue(event.angle());
    }

    @Subscribe
    private void handleThicknessEvent(ThicknessEvent event) {
        thicknessIntSpinner.setValue(event.thickness());
    }
}
