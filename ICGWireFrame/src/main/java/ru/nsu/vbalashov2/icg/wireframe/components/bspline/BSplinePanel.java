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
        setLayout(new BorderLayout());

        PublishSubject<Integer> newK = PublishSubject.create();

        // Config for editorPanel
        {
            BSplineEditorPanel editorPanel = new BSplineEditorPanel(anchorPointsPublishSubject, newK, newN);
            add(editorPanel, "Center");
        }

        // Config for optionsPanel
        {
            BSplineOptionsPanel optionsPanel = new BSplineOptionsPanel(newK, newN, newM, newM1);
            add(optionsPanel, "South");
        }
    }
}
