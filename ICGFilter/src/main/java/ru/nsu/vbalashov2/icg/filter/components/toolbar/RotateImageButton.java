package ru.nsu.vbalashov2.icg.filter.components.toolbar;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.filter.components.options.rotateimage.RotateImageOptionsPanel;
import ru.nsu.vbalashov2.icg.filter.tools.filters.FilterType;

import javax.swing.*;

public class RotateImageButton extends JToggleButton {

    private int initialAngleDegree = 0;
    private int resultAngleDegree = 0;

    public RotateImageButton(
            Icon icon,
            PublishSubject<Integer> newAngleDegreePublishSubject,
            PublishSubject<FilterType> newFilterTypePublishSubject
    ) {
        super(icon);

        setToolTipText("Rotate image");

        newFilterTypePublishSubject.subscribe(newFilter -> setSelected(newFilter == FilterType.ROTATION));
        newAngleDegreePublishSubject.subscribe(newValue -> initialAngleDegree = newValue);

        PublishSubject<Integer> dynamicAngleDegreeChangingPublishSubject = PublishSubject.create();

        dynamicAngleDegreeChangingPublishSubject.subscribe(value -> resultAngleDegree = value);

        RotateImageOptionsPanel optionsPanel = new RotateImageOptionsPanel(
                dynamicAngleDegreeChangingPublishSubject
        );

        addActionListener(event -> {
            dynamicAngleDegreeChangingPublishSubject.onNext(initialAngleDegree);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    optionsPanel,
                    "Rotation degree",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {
                initialAngleDegree = resultAngleDegree;

                newAngleDegreePublishSubject.onNext(resultAngleDegree);
                newFilterTypePublishSubject.onNext(FilterType.ROTATION); // THIS IS NOT HANDLED BY JImagePanel!!!! Used only for JToggleButtons
            }
        });
    }
}
