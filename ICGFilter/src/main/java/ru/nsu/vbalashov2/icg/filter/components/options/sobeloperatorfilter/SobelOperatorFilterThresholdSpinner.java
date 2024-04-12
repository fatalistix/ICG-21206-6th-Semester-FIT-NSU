package ru.nsu.vbalashov2.icg.filter.components.options.sobeloperatorfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;

public class SobelOperatorFilterThresholdSpinner extends JSpinner {

    public SobelOperatorFilterThresholdSpinner(PublishSubject<Integer> dynamicValueChangingPublishSubject) {
        super(new SpinnerNumberModel(
                128, 0, 255, 1
        ));

        dynamicValueChangingPublishSubject.subscribe(this::setValue);

        addChangeListener(listener -> dynamicValueChangingPublishSubject.onNext((int) getValue()));
    }
}
