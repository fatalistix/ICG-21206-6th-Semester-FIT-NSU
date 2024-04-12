package ru.nsu.vbalashov2.icg.filter.components.options.rotateimage;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import java.awt.*;

public class RotateImageOptionsPanel extends JPanel {

    public RotateImageOptionsPanel(
            PublishSubject<Integer> dynamicAngleDegreeChangingPublishSubject
    ) {
        setLayout(new GridLayout(1, 3));

        add(new JLabel("angle (degrees):"));
        add(new RotateImageAngleDegreesSlider(dynamicAngleDegreeChangingPublishSubject));
        add(new RotateImageAngleDegreesSpinner(dynamicAngleDegreeChangingPublishSubject));
    }
}
