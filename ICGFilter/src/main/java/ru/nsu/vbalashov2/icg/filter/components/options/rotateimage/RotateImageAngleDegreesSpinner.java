package ru.nsu.vbalashov2.icg.filter.components.options.rotateimage;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;

public class RotateImageAngleDegreesSpinner extends JSpinner {

    public RotateImageAngleDegreesSpinner(PublishSubject<Integer> dynamicValueChangingPublishSubject) {
        super(new SpinnerNumberModel(
                0, -180, 180, 1
        ));

        dynamicValueChangingPublishSubject.subscribe(this::setValue);

        addChangeListener(listener -> dynamicValueChangingPublishSubject.onNext((int) getValue()));
    }
}
