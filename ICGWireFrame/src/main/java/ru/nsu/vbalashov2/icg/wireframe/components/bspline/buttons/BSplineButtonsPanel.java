package ru.nsu.vbalashov2.icg.wireframe.components.bspline.buttons;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.wireframe.components.bspline.BSplineEditorCloseStatus;

import javax.swing.*;
import java.awt.*;

public class BSplineButtonsPanel extends JPanel {

    public BSplineButtonsPanel(
            PublishSubject<BSplineEditorCloseStatus> statusPublishSubject
    ) {
        setLayout(new BorderLayout());

        add(new BSplineOkApplyButtonsPanel(statusPublishSubject), "West");
        add(new BSplineButton("Cancel", "Close edit window without saving changes", statusPublishSubject, BSplineEditorCloseStatus.CANCEL), "East");
    }
}
