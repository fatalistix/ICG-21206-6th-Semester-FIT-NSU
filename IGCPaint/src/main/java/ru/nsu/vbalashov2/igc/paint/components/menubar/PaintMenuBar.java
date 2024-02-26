package ru.nsu.vbalashov2.igc.paint.components.menubar;

import com.google.common.eventbus.EventBus;
import ru.nsu.vbalashov2.igc.paint.components.menubar.aboutmenu.AboutMenu;
import ru.nsu.vbalashov2.igc.paint.components.menubar.filemenu.FileMenu;
import ru.nsu.vbalashov2.igc.paint.components.menubar.viewmenu.ViewMenu;

import javax.swing.*;

public class PaintMenuBar extends JMenuBar {
    public PaintMenuBar(EventBus eventBus) {
        add(new FileMenu(eventBus));
        add(new ViewMenu(eventBus));
        add(new AboutMenu());
    }
}
