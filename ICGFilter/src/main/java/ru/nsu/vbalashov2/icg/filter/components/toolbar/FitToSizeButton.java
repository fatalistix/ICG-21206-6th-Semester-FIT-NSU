package ru.nsu.vbalashov2.icg.filter.components.toolbar;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.filter.tools.events.FitToSizeEvent;

import javax.swing.*;

public class FitToSizeButton extends JButton {

    public FitToSizeButton(Icon icon, PublishSubject<FitToSizeEvent> fitToSizePublishSubject) {
        super(icon);
        addActionListener(actionListener -> fitToSizePublishSubject.onNext(new FitToSizeEvent()));
        setToolTipText("Fit image's size to current panel size");
    }
}
