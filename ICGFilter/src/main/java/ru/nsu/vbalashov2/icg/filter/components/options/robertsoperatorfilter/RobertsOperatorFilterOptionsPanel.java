package ru.nsu.vbalashov2.icg.filter.components.options.robertsoperatorfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import java.awt.*;

public class RobertsOperatorFilterOptionsPanel extends JPanel {

    public RobertsOperatorFilterOptionsPanel(
            PublishSubject<Integer> dynamicThresholdChangingPublishSubject
    ) {
        setLayout(new GridLayout(1, 3));

        add(new JLabel("Threshold:"));
        add(new RobertsOperatorFilterThresholdSlider(dynamicThresholdChangingPublishSubject));
        add(new RobertsOperatorFilterThresholdSpinner(dynamicThresholdChangingPublishSubject));
    }
}
