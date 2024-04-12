package ru.nsu.vbalashov2.icg.filter.components.options.gammacorrectionfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import java.awt.*;

public class GammaCorrectionFilterOptionsPanel extends JPanel {

    public GammaCorrectionFilterOptionsPanel(
            PublishSubject<Double> dynamicGammaChangingPublishSubject
    ) {
        setLayout(new GridLayout(1, 3));

        add(new JLabel("Gamma:"));
        add(new GammaCorrectionFilterGammaSlider(dynamicGammaChangingPublishSubject));
        add(new GammaCorrectionFilterGammaSpinner(dynamicGammaChangingPublishSubject));
    }
}
