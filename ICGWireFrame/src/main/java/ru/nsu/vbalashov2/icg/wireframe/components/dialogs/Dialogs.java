package ru.nsu.vbalashov2.icg.wireframe.components.dialogs;

import javax.swing.*;

public class Dialogs {

    public static void showExitDialog() {
        int result = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to exit? All unsaved data will be lost.",
                "Exit?",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }
}
