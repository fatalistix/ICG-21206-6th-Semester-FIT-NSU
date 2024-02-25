package ru.nsu.vbalashov2.igc.paint.components.options;

import com.google.common.eventbus.EventBus;

import javax.swing.*;
import java.awt.*;

public class OptionsPanel extends JPanel {
    public OptionsPanel(EventBus eventBus) {
//        this.setLayout(new GridLayout(0, 1));
//
//        this.add(new FormPanel(eventBus));
//        this.add(new SizePanel(eventBus));
//        this.add(new RotatePanel(eventBus));

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        this.add(new LabelsPanel());
        this.add(new SlidersPanel(eventBus));
        this.add(new SpinnersPanel(eventBus));
    }
}
