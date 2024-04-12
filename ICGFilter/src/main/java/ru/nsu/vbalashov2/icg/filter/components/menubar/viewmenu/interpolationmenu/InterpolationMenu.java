package ru.nsu.vbalashov2.icg.filter.components.menubar.viewmenu.interpolationmenu;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import java.awt.*;

public class InterpolationMenu extends JMenu {

    public InterpolationMenu(PublishSubject<Object> newInterpolationTypePublishSubject) {
        super("Interpolation");

        add(new InterpolationMenuItem(newInterpolationTypePublishSubject, RenderingHints.VALUE_INTERPOLATION_BILINEAR, "Bilinear"));
        add(new InterpolationMenuItem(newInterpolationTypePublishSubject, RenderingHints.VALUE_INTERPOLATION_BICUBIC, "Bicubic"));
        add(new InterpolationMenuItem(newInterpolationTypePublishSubject, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR, "Nearest neighbor"));
    }
}
