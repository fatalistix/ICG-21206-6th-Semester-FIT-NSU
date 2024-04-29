package ru.nsu.vbalashov2.icg.wireframe.components.menubar;

import ru.nsu.vbalashov2.icg.wireframe.components.menubar.aboutmenu.AboutMenu;
import ru.nsu.vbalashov2.icg.wireframe.components.menubar.editmenu.EditMenu;
import ru.nsu.vbalashov2.icg.wireframe.components.menubar.filemenu.FileMenu;
import ru.nsu.vbalashov2.icg.wireframe.tools.events.EventPublishSubjects;

import javax.swing.*;

public class WireframeMenuBar extends JMenuBar {

    public WireframeMenuBar(EventPublishSubjects eventPublishSubjects) {
        add(new FileMenu(eventPublishSubjects));
        add(new EditMenu(eventPublishSubjects));
        add(new AboutMenu());
    }
}
