package ru.nsu.vbalashov2.icg.filter.components.menubar.filtermenu;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.filter.components.options.gaussianblurfilter.GaussianBlurFilterOptionsPanel;
import ru.nsu.vbalashov2.icg.filter.tools.filters.FilterType;

import javax.swing.*;

public class GaussianBlurFilterMenuItem extends JRadioButtonMenuItem {

    private int initialSize = 3;
    private int resultSize = 3;
    private double initialSigma = 1.;
    private double resultSigma = 1.;

    public GaussianBlurFilterMenuItem(
            PublishSubject<Integer> gaussianBlurNewSizePublishSubject,
            PublishSubject<Double> gaussianBlurNewSigmaPublishSubject,
            PublishSubject<FilterType> newFilterTypePublishSubject
    ) {
        super("Gaussian blur");

        newFilterTypePublishSubject.subscribe(newFilter -> setSelected(newFilter == FilterType.GAUSSIAN_BLUR));
        gaussianBlurNewSigmaPublishSubject.subscribe(newValue -> initialSigma = newValue);
        gaussianBlurNewSizePublishSubject.subscribe(newValue -> initialSize = newValue);

        PublishSubject<Integer> dynamicSizeChangingPublishSubject = PublishSubject.create();
        PublishSubject<Double> dynamicSigmaChangingPublishSubject = PublishSubject.create();

        dynamicSizeChangingPublishSubject.subscribe(value -> resultSize = value);
        dynamicSigmaChangingPublishSubject.subscribe(value -> resultSigma = value);

        GaussianBlurFilterOptionsPanel optionsPanel = new GaussianBlurFilterOptionsPanel(
                dynamicSizeChangingPublishSubject,
                dynamicSigmaChangingPublishSubject
        );

        addActionListener(event -> {
            dynamicSizeChangingPublishSubject.onNext(initialSize);
            dynamicSigmaChangingPublishSubject.onNext(initialSigma);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    optionsPanel,
                    "Gaussian blur settings",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {
                initialSize = resultSize;
                initialSigma = resultSigma;
                gaussianBlurNewSizePublishSubject.onNext(resultSize);
                gaussianBlurNewSigmaPublishSubject.onNext(resultSigma);
                newFilterTypePublishSubject.onNext(FilterType.GAUSSIAN_BLUR);
            }
        });
    }
}
