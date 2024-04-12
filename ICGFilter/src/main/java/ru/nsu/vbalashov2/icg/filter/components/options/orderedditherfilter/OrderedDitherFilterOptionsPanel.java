package ru.nsu.vbalashov2.icg.filter.components.options.orderedditherfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import java.awt.*;

public class OrderedDitherFilterOptionsPanel extends JPanel {

    public OrderedDitherFilterOptionsPanel(
            PublishSubject<Integer> dynamicRedQuantizationNumberChangingPublishSubject,
            PublishSubject<Integer> dynamicGreenQuantizationNumberChangingPublishSubject,
            PublishSubject<Integer> dynamicBlueQuantizationNumberChangingPublishSubject
    ) {
        setLayout(new GridLayout(3, 3));

        add(new JLabel("Red:"));
        add(new OrderedDitherFilterQuantizationNumberSlider(dynamicRedQuantizationNumberChangingPublishSubject));
        add(new OrderedDitherFilterQuantizationNumberSpinner(dynamicRedQuantizationNumberChangingPublishSubject));

        add(new JLabel("Green:"));
        add(new OrderedDitherFilterQuantizationNumberSlider(dynamicGreenQuantizationNumberChangingPublishSubject));
        add(new OrderedDitherFilterQuantizationNumberSpinner(dynamicGreenQuantizationNumberChangingPublishSubject));

        add(new JLabel("Blue:"));
        add(new OrderedDitherFilterQuantizationNumberSlider(dynamicBlueQuantizationNumberChangingPublishSubject));
        add(new OrderedDitherFilterQuantizationNumberSpinner(dynamicBlueQuantizationNumberChangingPublishSubject));
    }
}
