package ru.nsu.vbalashov2.icg.wireframe.components.bspline;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;

public class BSplineButton extends JButton {

    public BSplineButton(
            String text,
            PublishSubject<BSplineEditorCloseStatus> statusPublishSubject,
            BSplineEditorCloseStatus closeStatus
    ) {
        super(text);

        addActionListener(e -> statusPublishSubject.onNext(closeStatus));
    }
}
