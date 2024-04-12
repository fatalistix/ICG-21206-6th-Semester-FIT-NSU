package ru.nsu.vbalashov2.icg.filter.components.menubar.filtermenu;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.filter.components.options.rotateimage.RotateImageOptionsPanel;
import ru.nsu.vbalashov2.icg.filter.tools.filters.FilterType;

import javax.swing.*;

public class RotateImageMenuItem extends JRadioButtonMenuItem {

    private int initialAngleDegree = 0;
    private int resultAngleDegree = 0;

    public RotateImageMenuItem(
            PublishSubject<Integer> newAngleDegreePublishSubject,
            PublishSubject<FilterType> newFilterTypePublishSubject
    ) {
        super("Rotate image");

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
