package ru.nsu.vbalashov2.igc.paint.components;

import javax.swing.*;

public class PaintMenuBar extends JMenuBar {
    public PaintMenuBar() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadFileMenuItem = new JMenuItem("Load");
        JMenuItem saveFileMenuItem = new JMenuItem("Save");
        JMenuItem exitFileMenuItem = new JMenuItem("Exit");
        fileMenu.add(loadFileMenuItem);
        fileMenu.add(saveFileMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitFileMenuItem);

        JMenu viewMenu = new JMenu("View");


        JMenu helpMenu = new JMenu("Help");

        this.add(fileMenu);
        this.add(viewMenu);
        this.add(helpMenu);
    }
}
