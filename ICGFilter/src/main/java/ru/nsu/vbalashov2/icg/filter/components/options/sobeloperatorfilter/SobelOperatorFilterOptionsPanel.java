package ru.nsu.vbalashov2.icg.filter.components.options.sobeloperatorfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import java.awt.*;

public class SobelOperatorFilterOptionsPanel extends JPanel {

    public SobelOperatorFilterOptionsPanel(
            PublishSubject<Integer> dynamicThresholdChangingPublishSubject
    ) {
        setLayout(new GridLayout(1, 3));

        add(new JLabel("Threshold:"));
        add(new SobelOperatorFilterThresholdSlider(dynamicThresholdChangingPublishSubject));
        add(new SobelOperatorFilterThresholdSpinner(dynamicThresholdChangingPublishSubject));
    }
}
