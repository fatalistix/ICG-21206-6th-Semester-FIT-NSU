package ru.nsu.vbalashov2.igc.paint.components.options;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ru.nsu.vbalashov2.igc.paint.tools.events.SizeEvent;

import javax.swing.*;
import java.awt.*;

public class SizePanel extends JPanel {

    private final JSlider sizeSlider;
    private final JSpinner sizeIntSpinner;

    public SizePanel(EventBus eventBus) {
        eventBus.register(this);

        this.setLayout(new GridLayout(1, 3));

        JLabel sizeLabel = new JLabel("Size (radius in px):");
        this.add(sizeLabel);

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
    }

    @Subscribe
    private void handleSizeEvent(SizeEvent sizeEvent) {
        sizeSlider.setValue(sizeEvent.radius());
        sizeIntSpinner.setValue(sizeEvent.radius());
    }
}
