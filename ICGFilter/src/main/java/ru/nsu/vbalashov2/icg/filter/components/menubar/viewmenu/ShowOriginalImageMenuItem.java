package ru.nsu.vbalashov2.icg.filter.components.menubar.viewmenu;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;

public class ShowOriginalImageMenuItem extends JRadioButtonMenuItem {

    public ShowOriginalImageMenuItem(
            PublishSubject<Boolean> showModifiedImagePublishSubject
    ) {
        super("Show original image");

        showModifiedImagePublishSubject.subscribe(newValue -> setSelected(!newValue));

        addActionListener(listener -> showModifiedImagePublishSubject.onNext(false));
    }
}
