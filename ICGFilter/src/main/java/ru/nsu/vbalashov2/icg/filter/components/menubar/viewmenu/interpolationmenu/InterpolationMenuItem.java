package ru.nsu.vbalashov2.icg.filter.components.menubar.viewmenu.interpolationmenu;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;

public class InterpolationMenuItem extends JRadioButtonMenuItem {

    public InterpolationMenuItem(PublishSubject<Object> newInterpolationTypePublishSubject, Object interpolationType, String text) {
        super(text);

        newInterpolationTypePublishSubject.subscribe(newValue -> setSelected(newValue == interpolationType));

        addActionListener(actionListener -> newInterpolationTypePublishSubject.onNext(interpolationType));
    }
}
