package ru.nsu.vbalashov2.icg.filter.components.options.floydsteinbergditherfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class FloydSteinbergDitherFilterQuantizationNumberSpinner extends JSpinner {

    public FloydSteinbergDitherFilterQuantizationNumberSpinner(PublishSubject<Integer> dynamicValueChangingPublishSubject) {
        super(new SpinnerNumberModel(
                2, 2, 128, 1
        ));

        dynamicValueChangingPublishSubject.subscribe(this::setValue);

        addChangeListener(listener -> dynamicValueChangingPublishSubject.onNext((int) getValue()));
    }
}
