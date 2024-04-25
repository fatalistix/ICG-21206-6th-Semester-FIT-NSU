package ru.nsu.vbalashov2.icg.wireframe.components.menubar.filemenu;

import ru.nsu.vbalashov2.icg.wireframe.tools.events.EventPublishSubjects;

import javax.swing.*;

public class FileMenu extends JMenu {

    public FileMenu(EventPublishSubjects eventPublishSubjects) {
        super("File");

        add(new LoadMenuItem(eventPublishSubjects.getLoadFilePublishSubject()));
        add(new SaveMenuItem(eventPublishSubjects.getSaveFilePublishSubject()));

        addSeparator();

        add(new ExitMenuItem());
    }
}
