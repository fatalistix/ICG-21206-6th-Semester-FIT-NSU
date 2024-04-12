package ru.nsu.vbalashov2.icg.filter.components.dialogs;

import javax.swing.*;
import java.awt.*;

public class Dialogs {

    public static void showExitDialog(Component parentComponent) {
        int result = JOptionPane.showConfirmDialog(
                parentComponent,
                "Are you sure you want to exit? All unsaved data will be lost.",
                "Exit?",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }
}
