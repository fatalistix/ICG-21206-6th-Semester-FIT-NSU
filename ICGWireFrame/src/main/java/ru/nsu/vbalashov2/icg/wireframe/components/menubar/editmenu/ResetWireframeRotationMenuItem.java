package ru.nsu.vbalashov2.icg.wireframe.components.menubar.editmenu;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.wireframe.tools.events.ResetWireframeRotationEvent;

import javax.swing.*;

public class ResetWireframeRotationMenuItem extends JMenuItem {

    public ResetWireframeRotationMenuItem(
            PublishSubject<ResetWireframeRotationEvent> resetWireframeRotationEventPublishSubject
    ) {
        super("Reset Wireframe Rotation");
        addActionListener(e -> resetWireframeRotationEventPublishSubject.onNext(new ResetWireframeRotationEvent()));
    }
}
