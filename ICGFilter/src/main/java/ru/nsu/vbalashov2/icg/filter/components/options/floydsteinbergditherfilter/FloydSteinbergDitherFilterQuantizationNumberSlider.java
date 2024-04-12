package ru.nsu.vbalashov2.icg.filter.components.options.floydsteinbergditherfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;

public class FloydSteinbergDitherFilterQuantizationNumberSlider extends JSlider {

    public FloydSteinbergDitherFilterQuantizationNumberSlider(PublishSubject<Integer> dynamicValueChangingPublishSubject) {
        super(2, 128, 2);
        setMajorTickSpacing(4);
        setMinorTickSpacing(1);

        dynamicValueChangingPublishSubject.subscribe(this::setValue);

        addChangeListener(listener -> dynamicValueChangingPublishSubject.onNext(getValue()));
    }
}
