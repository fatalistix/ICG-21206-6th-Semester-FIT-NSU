package ru.nsu.vbalashov2.icg.filter.components.menubar.viewmenu;

import ru.nsu.vbalashov2.icg.filter.components.menubar.viewmenu.interpolationmenu.InterpolationMenu;
import ru.nsu.vbalashov2.icg.filter.tools.events.EventPublishSubjects;

import javax.swing.*;

public class ViewMenu extends JMenu {

    public ViewMenu(EventPublishSubjects eventPublishSubjects) {
        super("View");

        add(new FitToSizeMenuItem(eventPublishSubjects.getFitToSizePublishSubject()));
        add(new RealSizeMenuItem(eventPublishSubjects.getRealSizePublishSubject()));

        addSeparator();

        add(new ShowOriginalImageMenuItem(eventPublishSubjects.getUseModifiedImagePublishSubject()));
        add(new ShowModifiedImageMenuItem(eventPublishSubjects.getUseModifiedImagePublishSubject()));

        addSeparator();

        add(new InterpolationMenu(eventPublishSubjects.getNewInterpolationTypePublishSubject()));
    }
}
