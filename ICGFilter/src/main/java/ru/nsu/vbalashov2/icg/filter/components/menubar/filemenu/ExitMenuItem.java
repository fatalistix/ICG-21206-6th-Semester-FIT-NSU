package ru.nsu.vbalashov2.icg.filter.components.menubar.filemenu;

import ru.nsu.vbalashov2.icg.filter.components.dialogs.Dialogs;

import javax.swing.*;

public class ExitMenuItem extends JMenuItem {

    public ExitMenuItem() {
        super("Exit");

        addActionListener((e) -> Dialogs.showExitDialog(ExitMenuItem.this));
    }
}
