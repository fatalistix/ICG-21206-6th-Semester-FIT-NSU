package ru.nsu.vbalashov2.icg.filter.components.menubar.viewmenu;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.filter.tools.events.FitToSizeEvent;

import javax.swing.*;

public class FitToSizeMenuItem extends JMenuItem {


    public FitToSizeMenuItem(PublishSubject<FitToSizeEvent> fitToSizePublishSubject) {
        super("Fit to size");
        addActionListener(actionListener -> fitToSizePublishSubject.onNext(new FitToSizeEvent()));
        setToolTipText("Fit image's size to current panel size");
    }
}
