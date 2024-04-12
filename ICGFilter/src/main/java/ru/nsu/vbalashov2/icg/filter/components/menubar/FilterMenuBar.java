package ru.nsu.vbalashov2.icg.filter.components.menubar;

import ru.nsu.vbalashov2.icg.filter.components.menubar.aboutmenu.AboutMenu;
import ru.nsu.vbalashov2.icg.filter.components.menubar.filemenu.FileMenu;
import ru.nsu.vbalashov2.icg.filter.components.menubar.filtermenu.FilterMenu;
import ru.nsu.vbalashov2.icg.filter.components.menubar.viewmenu.ViewMenu;
import ru.nsu.vbalashov2.icg.filter.tools.events.EventPublishSubjects;

import javax.swing.*;

public class FilterMenuBar extends JMenuBar {

    public FilterMenuBar(EventPublishSubjects eventPublishSubjects) {
        add(new FileMenu(eventPublishSubjects));
        add(new FilterMenu(eventPublishSubjects));
        add(new ViewMenu(eventPublishSubjects));
        add(new AboutMenu());
    }
}
