package ru.nsu.vbalashov2.igc.paint.components.options;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ru.nsu.vbalashov2.igc.paint.tools.events.FormEvent;
import ru.nsu.vbalashov2.igc.paint.tools.events.RotateEvent;
import ru.nsu.vbalashov2.igc.paint.tools.events.SizeEvent;
import ru.nsu.vbalashov2.igc.paint.tools.events.ThicknessEvent;

import javax.swing.*;
import java.awt.*;

public class SlidersPanel extends JPanel {

    private final JSlider formSlider;
    private final JSlider sizeSlider;
    private final JSlider rotateSlider;
    private final JSlider thicknessSlider;

    public SlidersPanel(EventBus eventBus) {
        eventBus.register(this);

        this.setLayout(new GridLayout(4, 1));

        formSlider = new JSlider(
                FormEvent.minNumberOfAngles(),
                FormEvent.maxNumberOfAngles(),
                FormEvent.initialNumberOfAngles()
        );
        formSlider.setPaintLabels(true);
        formSlider.setMajorTickSpacing(2);
        formSlider.setMinorTickSpacing(1);
        formSlider.addChangeListener((event) -> eventBus.post(new FormEvent(formSlider.getValue())));
        formSlider.setPreferredSize(
                new Dimension(600, formSlider.getPreferredSize().height)
        );
        this.add(formSlider);

        sizeSlider = new JSlider(
                SizeEvent.minRadius(),
                SizeEvent.maxRadius(),
                SizeEvent.initialRadius()
        );
        sizeSlider.setPaintLabels(true);
        sizeSlider.setMajorTickSpacing(20);
        sizeSlider.setMinorTickSpacing(1);
        sizeSlider.addChangeListener(
                (event) -> eventBus.post(new SizeEvent(sizeSlider.getValue()))
        );
        this.add(sizeSlider);

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

        thicknessSlider = new JSlider(
                ThicknessEvent.minThickness(),
                ThicknessEvent.maxThickness(),
                ThicknessEvent.initialThickness()
        );
        thicknessSlider.setPaintLabels(true);
        thicknessSlider.setMajorTickSpacing(2);
        thicknessSlider.setMinorTickSpacing(1);
        thicknessSlider.addChangeListener(
                (event) -> eventBus.post(new ThicknessEvent(thicknessSlider.getValue()))
        );
        this.add(thicknessSlider);
    }

    @Subscribe
    private void handleFormEvent(FormEvent event) {
        formSlider.setValue(event.numberOfAngles());
    }

    @Subscribe
    private void handleSizeEvent(SizeEvent event) {
        sizeSlider.setValue(event.radius());
    }

    @Subscribe
    private void handleRotateEvent(RotateEvent event) {
        rotateSlider.setValue(event.angle());
    }

    @Subscribe
    private void handleThicknessEvent(ThicknessEvent event) {
        thicknessSlider.setValue(event.thickness());
    }
}
