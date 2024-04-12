package ru.nsu.vbalashov2.icg.filter.components.menubar.filtermenu;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.filter.components.options.medianblurfilter.MedianBlurFilterOptionsPanel;
import ru.nsu.vbalashov2.icg.filter.tools.filters.FilterType;

import javax.swing.*;

public class MedianBlurFilterMenuItem extends JRadioButtonMenuItem {

    private int initialSize = 3;
    private int resultSize = 3;

    public MedianBlurFilterMenuItem(
            PublishSubject<Integer> medianBlurNewSizePublishSubject,
            PublishSubject<FilterType> newFilterTypePublishSubject
    ) {
        super("Median blur");

        newFilterTypePublishSubject.subscribe(newFilter -> setSelected(newFilter == FilterType.GAUSSIAN_BLUR));
        medianBlurNewSizePublishSubject.subscribe(newValue -> initialSize = newValue);

        PublishSubject<Integer> dynamicSizeChangingPublishSubject = PublishSubject.create();

        dynamicSizeChangingPublishSubject.subscribe(value -> resultSize = value);

        MedianBlurFilterOptionsPanel optionsPanel = new MedianBlurFilterOptionsPanel(
                dynamicSizeChangingPublishSubject
        );

        addActionListener(event -> {
            dynamicSizeChangingPublishSubject.onNext(initialSize);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    optionsPanel,
                    "Gaussian blur settings",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {
                initialSize = resultSize;
                medianBlurNewSizePublishSubject.onNext(resultSize);
                newFilterTypePublishSubject.onNext(FilterType.MEDIAN_BLUR);
            }
        });
    }
}
