package ru.nsu.vbalashov2.icg.filter.components.options.gaussianblurfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import java.awt.*;

public class GaussianBlurFilterOptionsPanel extends JPanel {

    public GaussianBlurFilterOptionsPanel(
            PublishSubject<Integer> dynamicSizeChangingPublishSubject,
            PublishSubject<Double> dynamicSigmaChangingPublishSubject
    ) {
        setLayout(new GridLayout(2, 3));

        add(new JLabel("Matrix size:"));
        add(new GaussianBlurFilterSizeSlider(dynamicSizeChangingPublishSubject));
        add(new GaussianBlurFilterSizeSpinner(dynamicSizeChangingPublishSubject));

        add(new JLabel("Sigma:"));
        add(new GaussianBlurFilterSigmaSlider(dynamicSigmaChangingPublishSubject));
        add(new GaussianBlurFilterSigmaSpinner(dynamicSigmaChangingPublishSubject));
    }
}
