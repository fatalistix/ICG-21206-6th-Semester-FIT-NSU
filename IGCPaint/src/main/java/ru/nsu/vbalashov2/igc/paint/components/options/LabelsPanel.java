package ru.nsu.vbalashov2.igc.paint.components.options;

import javax.swing.*;
import java.awt.*;

public class LabelsPanel extends JPanel {

    public LabelsPanel() {
        this.setLayout(new GridLayout(4, 1));

        {
            JLabel formLabel = new JLabel("Form (number of angles):");
            this.add(formLabel);
        }

        {
            JLabel sizeLabel = new JLabel("Size (radius in px):");
            this.add(sizeLabel);
        }

        {
            JLabel rotateLabel = new JLabel("Rotation (degrees):");
            this.add(rotateLabel);
        }

        {
            JLabel thicknessLabel = new JLabel("Thickness (in px):");
            this.add(thicknessLabel);
        }
    }
}
