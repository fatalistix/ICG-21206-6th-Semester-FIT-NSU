package ru.nsu.vbalashov2.icg.filter.components.options.gaussianblurfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;

public class GaussianBlurFilterSizeSpinner extends JSpinner {

    public GaussianBlurFilterSizeSpinner(PublishSubject<Integer> dynamicValueChangingPublishSubject) {
        super(new SpinnerNumberModel(
                3, 3, 11, 2
        ));

        dynamicValueChangingPublishSubject.subscribe(this::setValue);

        addChangeListener(listener -> dynamicValueChangingPublishSubject.onNext((int) getValue()));
    }
}
