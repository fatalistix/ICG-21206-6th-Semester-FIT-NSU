package ru.nsu.vbalashov2.icg.filter.components.menubar.filemenu;

import ru.nsu.vbalashov2.icg.filter.tools.events.EventPublishSubjects;

import javax.swing.*;

public class FileMenu extends JMenu {

    public FileMenu(EventPublishSubjects eventPublishSubjects) {
        super("File");

        add(new LoadMenuItem(eventPublishSubjects.getNewBufferedImagePublishSubject()));
        add(new SaveMenuItem(eventPublishSubjects.getSaveFilePublishSubject()));
        addSeparator();
        add(new ExitMenuItem());
    }
}
