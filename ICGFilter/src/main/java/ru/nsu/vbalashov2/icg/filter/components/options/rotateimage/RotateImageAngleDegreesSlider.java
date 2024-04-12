package ru.nsu.vbalashov2.icg.filter.components.options.rotateimage;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;

public class RotateImageAngleDegreesSlider extends JSlider {

    public RotateImageAngleDegreesSlider(PublishSubject<Integer> dynamicValueChangingPublishSubject) {
        super(-180, 180, 0);
        setMajorTickSpacing(30);
        setMinorTickSpacing(1);

        dynamicValueChangingPublishSubject.subscribe(this::setValue);

        addChangeListener(listener -> dynamicValueChangingPublishSubject.onNext(getValue()));
    }
}
