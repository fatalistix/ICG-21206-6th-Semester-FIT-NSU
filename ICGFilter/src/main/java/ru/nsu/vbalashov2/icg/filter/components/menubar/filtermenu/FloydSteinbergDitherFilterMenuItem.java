package ru.nsu.vbalashov2.icg.filter.components.menubar.filtermenu;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.filter.components.options.floydsteinbergditherfilter.FloydSteinbergDitherFilterOptionsPanel;
import ru.nsu.vbalashov2.icg.filter.tools.filters.FilterType;

import javax.swing.*;

public class FloydSteinbergDitherFilterMenuItem extends JRadioButtonMenuItem {

    private int initialRedQN = 2;
    private int resultRedQN = 2;

    private int initialGreenQN = 2;
    private int resultGreenQN = 2;

    private int initialBlueQN = 2;
    private int resultBlueQN = 2;

    public FloydSteinbergDitherFilterMenuItem(
            PublishSubject<Integer> newRedQuantizationNumberPublishSubject,
            PublishSubject<Integer> newGreenQuantizationNumberPublishSubject,
            PublishSubject<Integer> newBlueQuantizationNumberPublishSubject,
            PublishSubject<FilterType> newFilterTypePublishSubject
    ) {
        super("Floyd Steinberg dither");

        newFilterTypePublishSubject.subscribe(newFilter -> setSelected(newFilter == FilterType.FLOYD_STEINBERG_DITHER));
        newRedQuantizationNumberPublishSubject.subscribe(newValue -> initialRedQN = newValue);
        newGreenQuantizationNumberPublishSubject.subscribe(newValue -> initialGreenQN = newValue);
        newBlueQuantizationNumberPublishSubject.subscribe(newValue -> initialBlueQN = newValue);

        PublishSubject<Integer> dynamicRedQuantizationNumberChangingPublishSubject = PublishSubject.create();
        PublishSubject<Integer> dynamicGreenQuantizationNumberChangingPublishSubject = PublishSubject.create();
        PublishSubject<Integer> dynamicBlueQuantizationNumberChangingPublishSubject = PublishSubject.create();

        dynamicRedQuantizationNumberChangingPublishSubject.subscribe(value -> resultRedQN = value);
        dynamicGreenQuantizationNumberChangingPublishSubject.subscribe(value -> resultGreenQN = value);
        dynamicBlueQuantizationNumberChangingPublishSubject.subscribe(value -> resultBlueQN = value);

        FloydSteinbergDitherFilterOptionsPanel optionsPanel = new FloydSteinbergDitherFilterOptionsPanel(
                dynamicRedQuantizationNumberChangingPublishSubject,
                dynamicGreenQuantizationNumberChangingPublishSubject,
                dynamicBlueQuantizationNumberChangingPublishSubject
        );

        addActionListener(event -> {
            dynamicRedQuantizationNumberChangingPublishSubject.onNext(initialRedQN);
            dynamicGreenQuantizationNumberChangingPublishSubject.onNext(initialGreenQN);
            dynamicBlueQuantizationNumberChangingPublishSubject.onNext(initialBlueQN);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    optionsPanel,
                    "Quantization numbers",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {
                initialRedQN = resultRedQN;
                initialGreenQN = resultGreenQN;
                initialBlueQN = resultBlueQN;

                newRedQuantizationNumberPublishSubject.onNext(resultRedQN);
                newGreenQuantizationNumberPublishSubject.onNext(resultGreenQN);
                newBlueQuantizationNumberPublishSubject.onNext(resultBlueQN);
                newFilterTypePublishSubject.onNext(FilterType.FLOYD_STEINBERG_DITHER);
            }
        });
    }
}
