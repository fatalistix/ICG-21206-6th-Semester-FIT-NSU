package ru.nsu.vbalashov2.icg.filter.components.options.gammacorrectionfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;

public class GammaCorrectionFilterGammaSpinner extends JSpinner {

    public GammaCorrectionFilterGammaSpinner(PublishSubject<Double> dynamicValueChangingPublishSubject) {
        super(new SpinnerNumberModel(
                1., 0.1, 10.0, 0.1
        ));

        dynamicValueChangingPublishSubject.subscribe(this::setValue);

        addChangeListener(listener -> dynamicValueChangingPublishSubject.onNext((double) getValue()));
    }
}
