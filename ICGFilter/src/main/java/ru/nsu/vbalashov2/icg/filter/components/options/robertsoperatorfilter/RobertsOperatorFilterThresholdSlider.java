package ru.nsu.vbalashov2.icg.filter.components.options.robertsoperatorfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;

public class RobertsOperatorFilterThresholdSlider extends JSlider {

    public RobertsOperatorFilterThresholdSlider(PublishSubject<Integer> dynamicValueChangingPublishSubject) {
        super(0, 255, 128);
        setMajorTickSpacing(8);
        setMinorTickSpacing(1);

        dynamicValueChangingPublishSubject.subscribe(this::setValue);

        addChangeListener(listener -> dynamicValueChangingPublishSubject.onNext(getValue()));
    }
}
