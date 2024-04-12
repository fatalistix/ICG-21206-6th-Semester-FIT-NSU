package ru.nsu.vbalashov2.icg.filter.components.options.orderedditherfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;

public class OrderedDitherFilterQuantizationNumberSpinner extends JSpinner {

    public OrderedDitherFilterQuantizationNumberSpinner(PublishSubject<Integer> dynamicValueChangingPublishSubject) {
        super(new SpinnerNumberModel(
                2, 2, 128, 1
        ));

        dynamicValueChangingPublishSubject.subscribe(this::setValue);

        addChangeListener(listener -> dynamicValueChangingPublishSubject.onNext((int) getValue()));
    }
}
