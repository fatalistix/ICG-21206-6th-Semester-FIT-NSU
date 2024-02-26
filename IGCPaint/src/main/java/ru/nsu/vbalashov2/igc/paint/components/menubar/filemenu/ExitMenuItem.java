package ru.nsu.vbalashov2.igc.paint.components.menubar.filemenu;

import javax.swing.*;

public class ExitMenuItem extends JMenuItem {

    public ExitMenuItem() {
        super("Exit");

        addActionListener((event) -> {
            int result = JOptionPane.showConfirmDialog(
                    ExitMenuItem.this,
                    "Are you sure you want to exit? All unsaved data will be lost.",
                    "Exit?",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        });
    }
}
