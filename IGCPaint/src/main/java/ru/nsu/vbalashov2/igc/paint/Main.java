package ru.nsu.vbalashov2.igc.paint;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        SwingUtilities.invokeLater(() -> {
            try {
                new PaintFrame();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        });
    }
}