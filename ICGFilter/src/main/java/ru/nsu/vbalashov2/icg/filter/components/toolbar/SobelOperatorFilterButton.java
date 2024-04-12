package ru.nsu.vbalashov2.icg.filter.components.toolbar;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.filter.components.options.sobeloperatorfilter.SobelOperatorFilterOptionsPanel;
import ru.nsu.vbalashov2.icg.filter.tools.filters.FilterType;

import javax.swing.*;

public class SobelOperatorFilterButton extends JToggleButton {

    private int initialThreshold = 128;
    private int resultThreshold = 128;

    public SobelOperatorFilterButton(
            Icon icon,
            PublishSubject<Integer> sobelOperatorNewThresholdPublishSubject,
            PublishSubject<FilterType> newFilterTypePublishSubject
    ) {
        super(icon);

        setToolTipText("Highlight contours (Sobel operator)");

        newFilterTypePublishSubject.subscribe(newFilter -> setSelected(newFilter == FilterType.SOBEL_OPERATOR));
        sobelOperatorNewThresholdPublishSubject.subscribe(newValue -> initialThreshold = newValue);

        PublishSubject<Integer> dynamicThresholdChangingPublishSubject = PublishSubject.create();

        dynamicThresholdChangingPublishSubject.subscribe(value -> resultThreshold = value);

        SobelOperatorFilterOptionsPanel optionsPanel = new SobelOperatorFilterOptionsPanel(
                dynamicThresholdChangingPublishSubject
        );

        addActionListener(event -> {
            dynamicThresholdChangingPublishSubject.onNext(initialThreshold);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    optionsPanel,
                    "Sobel operator settings",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {
                initialThreshold = resultThreshold;
                sobelOperatorNewThresholdPublishSubject.onNext(resultThreshold);
                newFilterTypePublishSubject.onNext(FilterType.SOBEL_OPERATOR);
            }
        });
    }
}
