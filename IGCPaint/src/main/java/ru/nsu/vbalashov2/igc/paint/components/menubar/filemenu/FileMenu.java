package ru.nsu.vbalashov2.igc.paint.components.menubar.filemenu;

import com.google.common.eventbus.EventBus;

import javax.swing.*;

public class FileMenu extends JMenu {

    public FileMenu(EventBus eventBus) {
        super("File");

        add(new LoadMenuItem(eventBus));
        add(new SaveMenuItem(eventBus));
        addSeparator();
        add(new ExitMenuItem());
    }
}
