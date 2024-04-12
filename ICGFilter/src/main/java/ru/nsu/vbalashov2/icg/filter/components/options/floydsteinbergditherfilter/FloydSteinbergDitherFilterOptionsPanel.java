package ru.nsu.vbalashov2.icg.filter.components.options.floydsteinbergditherfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import java.awt.*;

public class FloydSteinbergDitherFilterOptionsPanel extends JPanel {

    public FloydSteinbergDitherFilterOptionsPanel(
            PublishSubject<Integer> dynamicRedQuantizationNumberChangingPublishSubject,
            PublishSubject<Integer> dynamicGreenQuantizationNumberChangingPublishSubject,
            PublishSubject<Integer> dynamicBlueQuantizationNumberChangingPublishSubject
    ) {
        setLayout(new GridLayout(3, 3));

        add(new JLabel("Red:"));
        add(new FloydSteinbergDitherFilterQuantizationNumberSlider(dynamicRedQuantizationNumberChangingPublishSubject));
        add(new FloydSteinbergDitherFilterQuantizationNumberSpinner(dynamicRedQuantizationNumberChangingPublishSubject));

        add(new JLabel("Green:"));
        add(new FloydSteinbergDitherFilterQuantizationNumberSlider(dynamicGreenQuantizationNumberChangingPublishSubject));
        add(new FloydSteinbergDitherFilterQuantizationNumberSpinner(dynamicGreenQuantizationNumberChangingPublishSubject));

        add(new JLabel("Blue:"));
        add(new FloydSteinbergDitherFilterQuantizationNumberSlider(dynamicBlueQuantizationNumberChangingPublishSubject));
        add(new FloydSteinbergDitherFilterQuantizationNumberSpinner(dynamicBlueQuantizationNumberChangingPublishSubject));
    }
}
