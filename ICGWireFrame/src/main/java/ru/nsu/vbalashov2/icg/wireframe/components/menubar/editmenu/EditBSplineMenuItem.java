package ru.nsu.vbalashov2.icg.wireframe.components.menubar.editmenu;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.wireframe.components.bspline.BSplineFrame;
import ru.nsu.vbalashov2.icg.wireframe.components.bspline.BSplineEditorCloseStatus;
import ru.nsu.vbalashov2.icg.wireframe.tools.PointUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EditBSplineMenuItem extends JMenuItem {

    private List<Point> initialAnchorPoints = new ArrayList<>();
    private List<Point> resultAnchorPoints = initialAnchorPoints;

    private int initialN = 1;
    private int resultN = 1;

    private int initialM = 2;
    private int resultM = 2;

    private int initialM1 = 1;
    private int resultM1 = 1;

    public EditBSplineMenuItem(
            PublishSubject<List<Point>> bSplineAnchorPointsPublishSubject,
            PublishSubject<Integer> bSplineNPublishSubject,
            PublishSubject<Integer> bSplineMPublishSubject,
            PublishSubject<Integer> bSplineM1PublishSubject
    ) {
        super("Edit BSpline");

        PublishSubject<BSplineEditorCloseStatus> bSplineEditorCloseStatusPublishSubject = PublishSubject.create();

        bSplineEditorCloseStatusPublishSubject.subscribe(status -> {
            switch (status) {
                case APPLY, OK -> {
                    initialAnchorPoints = resultAnchorPoints;
                    initialN = resultN;
                    initialM = resultM;
                    initialM1 = resultM1;
                }
                case CANCEL -> {
                    bSplineAnchorPointsPublishSubject.onNext(initialAnchorPoints);
                    bSplineNPublishSubject.onNext(initialN);
                    bSplineMPublishSubject.onNext(initialM);
                    bSplineM1PublishSubject.onNext(initialM1);
                }
            }
        });


        PublishSubject<List<Point>> bSplineDynamicAnchorPointsPublishSubject = PublishSubject.create();
        PublishSubject<Integer> bSplineDynamicNPublishSubject = PublishSubject.create();
        PublishSubject<Integer> bSplineDynamicMPublishSubject = PublishSubject.create();
        PublishSubject<Integer> bSplineDynamicM1PublishSubject = PublishSubject.create();

        bSplineDynamicAnchorPointsPublishSubject.subscribe(points -> {
            resultAnchorPoints = points;
            bSplineAnchorPointsPublishSubject.onNext(points);
        });

        bSplineDynamicNPublishSubject.subscribe(n -> {
            resultN = n;
            bSplineNPublishSubject.onNext(n);
        });

        bSplineDynamicMPublishSubject.subscribe(m -> {
            resultM = m;
            bSplineMPublishSubject.onNext(m);
        });

        bSplineDynamicM1PublishSubject.subscribe(m1 -> {
            resultM1 = m1;
            bSplineM1PublishSubject.onNext(m1);
        });

        bSplineAnchorPointsPublishSubject.subscribe(points -> {
            if (!PointUtilities.pointListsEqual(points, resultAnchorPoints)) {
                initialAnchorPoints = points;
                bSplineDynamicAnchorPointsPublishSubject.onNext(points);
            }
        });

        bSplineNPublishSubject.subscribe(n -> {
            if (n != resultN) {
                initialN = n;
                bSplineDynamicNPublishSubject.onNext(n);
            }
        });

        bSplineMPublishSubject.subscribe(m -> {
            if (m != resultM) {
                initialM = m;
                bSplineDynamicMPublishSubject.onNext(m);
            }
        });

        bSplineM1PublishSubject.subscribe(m1 -> {
            if (m1 != resultM1) {
                initialM1 = m1;
                bSplineDynamicM1PublishSubject.onNext(m1);
            }
        });

        BSplineFrame bSplineFrame = new BSplineFrame(
                bSplineEditorCloseStatusPublishSubject,
                bSplineDynamicAnchorPointsPublishSubject,
                bSplineDynamicNPublishSubject,
                bSplineDynamicMPublishSubject,
                bSplineDynamicM1PublishSubject
        );

        addActionListener(e -> {
            if (bSplineFrame.isVisible()) {
                bSplineFrame.transferFocus();
            } else {
                bSplineFrame.setVisible(true);

                bSplineDynamicAnchorPointsPublishSubject.onNext(initialAnchorPoints);
                bSplineDynamicNPublishSubject.onNext(initialN);
                bSplineDynamicMPublishSubject.onNext(initialM);
                bSplineDynamicM1PublishSubject.onNext(initialM1);
            }
        });
    }
}
