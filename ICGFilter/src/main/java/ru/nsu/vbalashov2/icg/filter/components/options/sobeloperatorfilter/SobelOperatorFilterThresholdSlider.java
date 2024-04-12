package ru.nsu.vbalashov2.icg.filter.components.options.sobeloperatorfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;

public class SobelOperatorFilterThresholdSlider extends JSlider {

    public SobelOperatorFilterThresholdSlider(PublishSubject<Integer> dynamicValueChangingPublishSubject) {
        super(0, 255, 128);
        setMajorTickSpacing(8);
        setMinorTickSpacing(1);

        dynamicValueChangingPublishSubject.subscribe(this::setValue);

        addChangeListener(listener -> dynamicValueChangingPublishSubject.onNext(getValue()));
    }
}
