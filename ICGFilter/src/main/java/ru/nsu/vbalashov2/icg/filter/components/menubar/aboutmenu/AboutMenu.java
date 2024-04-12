package ru.nsu.vbalashov2.icg.filter.components.menubar.aboutmenu;

import javax.swing.*;

public class AboutMenu extends JMenu {

    public AboutMenu() {
        super("About");

        add(new AboutMenuItem());
    }
}
