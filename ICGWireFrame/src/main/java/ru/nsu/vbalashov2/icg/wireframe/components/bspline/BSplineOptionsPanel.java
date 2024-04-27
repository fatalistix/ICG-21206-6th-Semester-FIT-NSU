package ru.nsu.vbalashov2.icg.wireframe.components.bspline;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import java.awt.*;

public class BSplineOptionsPanel extends JPanel {

    public BSplineOptionsPanel(
            PublishSubject<Integer> newK,
            PublishSubject<Integer> newN,
            PublishSubject<Integer> newM,
            PublishSubject<Integer> newM1
    ) {
        setLayout(new GridLayout(2, 4));

        add(new JLabel("K", SwingConstants.RIGHT));
        add(new BSplineKLabel(newK));

        add(new JLabel("N", SwingConstants.RIGHT));
        add(new BSplineNumberSpinner(newN, 1, 1, 20, 1));

        add(new JLabel("M", SwingConstants.RIGHT));
        add(new BSplineNumberSpinner(newM, 2, 2, 20, 1));

        add(new JLabel("M1", SwingConstants.RIGHT));
        add(new BSplineNumberSpinner(newM1, 1, 1, 20, 1));
    }
}
