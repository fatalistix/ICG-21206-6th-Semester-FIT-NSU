package ru.nsu.vbalashov2.icg.filter.components.menubar.viewmenu;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.filter.tools.events.RealSizeEvent;

import javax.swing.*;

public class RealSizeMenuItem extends JMenuItem {

    public RealSizeMenuItem(PublishSubject<RealSizeEvent> realSizePublishSubject) {
        super("Real size");
        addActionListener(actionListener -> realSizePublishSubject.onNext(new RealSizeEvent()));
        setToolTipText("Make image fit real size");
    }
}
