package ru.nsu.vbalashov2.icg.filter.components.options.robertsoperatorfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;

public class RobertsOperatorFilterThresholdSpinner extends JSpinner {

    public RobertsOperatorFilterThresholdSpinner(PublishSubject<Integer> dynamicValueChangingPublishSubject) {
        super(new SpinnerNumberModel(
                128, 0, 255, 1
        ));

        dynamicValueChangingPublishSubject.subscribe(this::setValue);

        addChangeListener(listener -> dynamicValueChangingPublishSubject.onNext((int) getValue()));
    }
}
