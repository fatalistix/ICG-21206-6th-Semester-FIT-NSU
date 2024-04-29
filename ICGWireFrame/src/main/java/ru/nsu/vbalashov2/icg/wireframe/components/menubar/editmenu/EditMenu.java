package ru.nsu.vbalashov2.icg.wireframe.components.menubar.editmenu;

import ru.nsu.vbalashov2.icg.wireframe.tools.events.EventPublishSubjects;

import javax.swing.*;

public class EditMenu extends JMenu {

    public EditMenu(EventPublishSubjects eventPublishSubjects) {
        super("Edit");

        add(new EditBSplineMenuItem(
                eventPublishSubjects.getBSplineAnchorPointsEventPublishSubject(),
                eventPublishSubjects.getBSplineNPublishSubject(),
                eventPublishSubjects.getBSplineMPublishSubject(),
                eventPublishSubjects.getBSplineM1PublishSubject()
        ));

        addSeparator();

        add(new ResetWireframeRotationMenuItem(eventPublishSubjects.getResetWireframeRotationEventPublishSubject()));
    }
}
