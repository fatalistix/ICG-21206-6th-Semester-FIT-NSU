package ru.nsu.vbalashov2.icg.filter.components.menubar.viewmenu;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;

public class ShowModifiedImageMenuItem extends JRadioButtonMenuItem {

    public ShowModifiedImageMenuItem(
            PublishSubject<Boolean> showModifiedImagePublishSubject
    ) {
        super("Show modified image");

        showModifiedImagePublishSubject.subscribe(this::setSelected);

        addActionListener(listener -> showModifiedImagePublishSubject.onNext(true));
    }
}
