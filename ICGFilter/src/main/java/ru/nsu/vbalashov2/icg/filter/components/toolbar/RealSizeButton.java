package ru.nsu.vbalashov2.icg.filter.components.toolbar;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.filter.tools.events.RealSizeEvent;

import javax.swing.*;

public class RealSizeButton extends JButton {

    public RealSizeButton(Icon icon, PublishSubject<RealSizeEvent> realSizePublishSubject) {
        super(icon);
        addActionListener(actionListener -> realSizePublishSubject.onNext(new RealSizeEvent()));
        setToolTipText("Make image fit real size");
    }
}
