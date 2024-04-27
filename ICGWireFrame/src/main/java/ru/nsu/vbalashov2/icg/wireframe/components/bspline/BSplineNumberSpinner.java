package ru.nsu.vbalashov2.icg.wireframe.components.bspline;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;

public class BSplineNumberSpinner extends JSpinner {

    public BSplineNumberSpinner(PublishSubject<Integer> dynamicValueChangingPublishSubject, int init, int min, int max, int step) {
        super(new SpinnerNumberModel(
                init, min, max, step
        ));

        dynamicValueChangingPublishSubject.subscribe(this::setValue);

        addChangeListener(l -> dynamicValueChangingPublishSubject.onNext((int) getValue()));
    }
}
