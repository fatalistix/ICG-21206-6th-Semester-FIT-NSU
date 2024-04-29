package ru.nsu.vbalashov2.icg.wireframe.components.bspline;


import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.wireframe.components.bspline.buttons.BSplineButtonsPanel;

import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
                int result = JOptionPane.showConfirmDialog(null, "Save changes?", "Save changes", JOptionPane.YES_NO_CANCEL_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    closeStatusPublishSubject.onNext(BSplineEditorCloseStatus.OK);
                    setVisible(false);
                } else if (result == JOptionPane.NO_OPTION) {
                    closeStatusPublishSubject.onNext(BSplineEditorCloseStatus.CANCEL);
                    setVisible(false);
                }
            }
        });

        closeStatusPublishSubject.subscribe(closeStatus -> {
            switch (closeStatus) {
                case OK, CANCEL -> setVisible(false);
                case APPLY -> setVisible(true);
            }
        });

        BSplinePanel bSplinePanel = new BSplinePanel(
                anchorPointsPublishSubject,
                nPublishSubject,
                mPublishSubject,
                m1PublishSubject
        );
        BSplineButtonsPanel bSplineButtonsPanel = new BSplineButtonsPanel(closeStatusPublishSubject);

        getContentPane().add(bSplinePanel);
        getContentPane().add(bSplineButtonsPanel, "South");
    }
}
