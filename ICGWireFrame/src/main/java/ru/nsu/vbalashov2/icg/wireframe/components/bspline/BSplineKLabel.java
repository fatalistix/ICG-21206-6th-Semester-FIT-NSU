package ru.nsu.vbalashov2.icg.wireframe.components.bspline;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;

public class BSplineKLabel extends JLabel {

    public BSplineKLabel(PublishSubject<Integer> kPublishSubject) {
        super("0", SwingConstants.CENTER);

        kPublishSubject.subscribe(t -> setText(Integer.toString(t)));
    }
}
