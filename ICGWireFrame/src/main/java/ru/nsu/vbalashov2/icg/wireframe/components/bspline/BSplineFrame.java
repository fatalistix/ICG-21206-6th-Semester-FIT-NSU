package ru.nsu.vbalashov2.icg.wireframe.components.bspline;


import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class BSplineFrame extends JFrame {

    public BSplineFrame(
            PublishSubject<BSplineEditorCloseStatus> closeStatusPublishSubject,
            PublishSubject<List<Point>> anchorPointsPublishSubject,
            PublishSubject<Integer> nPublishSubject,
            PublishSubject<Integer> mPublishSubject,
            PublishSubject<Integer> m1PublishSubject
    ) {
        super("BSpline editor");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(640, 480));
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                setVisible(false);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
            }
        });

        getContentPane().add(new BSplinePanel(
                anchorPointsPublishSubject,
                nPublishSubject,
                mPublishSubject,
                m1PublishSubject
        ));
    }
}
