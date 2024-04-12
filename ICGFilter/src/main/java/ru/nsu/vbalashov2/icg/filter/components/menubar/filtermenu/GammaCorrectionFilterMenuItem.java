package ru.nsu.vbalashov2.icg.filter.components.menubar.filtermenu;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.filter.components.options.gammacorrectionfilter.GammaCorrectionFilterOptionsPanel;
import ru.nsu.vbalashov2.icg.filter.tools.filters.FilterType;

import javax.swing.*;

public class GammaCorrectionFilterMenuItem extends JRadioButtonMenuItem {

    private double initialGamma = 1.;
    private double resultGamma = 1.;

    public GammaCorrectionFilterMenuItem(
            PublishSubject<Double> gammaCorrectionNewGammaPublishSubject,
            PublishSubject<FilterType> newFilterTypePublishSubject
    ) {
        super("Gamma correction");

        newFilterTypePublishSubject.subscribe(newFilter -> setSelected(newFilter == FilterType.GAMMA_CORRECTION));
        gammaCorrectionNewGammaPublishSubject.subscribe(newValue -> initialGamma = newValue);

        PublishSubject<Double> dynamicSigmaChangingPublishSubject = PublishSubject.create();

        dynamicSigmaChangingPublishSubject.subscribe(value -> resultGamma = value);

        GammaCorrectionFilterOptionsPanel optionsPanel = new GammaCorrectionFilterOptionsPanel(
                dynamicSigmaChangingPublishSubject
        );

        addActionListener(event -> {
            dynamicSigmaChangingPublishSubject.onNext(initialGamma);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    optionsPanel,
                    "Gamma correction settings",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {
                initialGamma = resultGamma;
                gammaCorrectionNewGammaPublishSubject.onNext(resultGamma);
                newFilterTypePublishSubject.onNext(FilterType.GAMMA_CORRECTION);
            }
        });
    }
}
