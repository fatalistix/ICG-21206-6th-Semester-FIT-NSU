package ru.nsu.vbalashov2.icg.wireframe.components.bspline.buttons;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.wireframe.components.bspline.BSplineEditorCloseStatus;

import javax.swing.*;
import java.awt.*;

public class BSplineOkApplyButtonsPanel extends JPanel {

    public BSplineOkApplyButtonsPanel(
            PublishSubject<BSplineEditorCloseStatus> statusPublishSubject
    ) {
        setLayout(new GridLayout(1, 2));

        add(new BSplineButton("Ok", statusPublishSubject, BSplineEditorCloseStatus.OK));
        add(new BSplineButton("Apply", statusPublishSubject, BSplineEditorCloseStatus.APPLY));
    }
}
