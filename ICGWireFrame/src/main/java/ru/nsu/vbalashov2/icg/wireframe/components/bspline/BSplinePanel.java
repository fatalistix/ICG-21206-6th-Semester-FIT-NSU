package ru.nsu.vbalashov2.icg.wireframe.components.bspline;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BSplinePanel extends JPanel {

    public BSplinePanel(
            PublishSubject<List<Point>> anchorPointsPublishSubject,
            PublishSubject<Integer> newN,
            PublishSubject<Integer> newM,
            PublishSubject<Integer> newM1
    ) {
        setLayout(new GridBagLayout());

        PublishSubject<Integer> newK = PublishSubject.create();

        // Config for editorPanel
        {
            BSplineEditorPanel editorPanel = new BSplineEditorPanel(anchorPointsPublishSubject, newK, newN);
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.weightx = 1.;
            c.weighty = 0.9;
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.PAGE_START;

            add(editorPanel, c);
        }

        // Config for optionsPanel
        {
            BSplineOptionsPanel optionsPanel = new BSplineOptionsPanel(newK, newN, newM, newM1);
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 1;
            c.weightx = 1.;
            c.weighty = 0.1;
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.PAGE_END;

            add(optionsPanel, c);
        }
    }
}
