package ru.nsu.vbalashov2.icg.wireframe.components.bspline.buttons;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.wireframe.components.bspline.BSplineEditorCloseStatus;

import javax.swing.*;

public class BSplineButton extends JButton {

    public BSplineButton(
            String text,
            String toolTipText,
            PublishSubject<BSplineEditorCloseStatus> statusPublishSubject,
            BSplineEditorCloseStatus closeStatus
    ) {
        super(text);

        setToolTipText(toolTipText);

        addActionListener(e -> statusPublishSubject.onNext(closeStatus));
    }
}
