package ru.nsu.vbalashov2.icg.filter.components.options.medianblurfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import java.awt.*;

public class MedianBlurFilterOptionsPanel extends JPanel {

    public MedianBlurFilterOptionsPanel(
            PublishSubject<Integer> dynamicSizeChangingPublishSubject
    ) {
        setLayout(new GridLayout(1, 3));

        add(new JLabel("Matrix size:"));
        add(new MedianBlurFilterSizeSlider(dynamicSizeChangingPublishSubject));
        add(new MedianBlurFilterSizeSpinner(dynamicSizeChangingPublishSubject));
    }
}
