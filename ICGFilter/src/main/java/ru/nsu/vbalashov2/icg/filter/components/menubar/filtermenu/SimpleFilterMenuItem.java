package ru.nsu.vbalashov2.icg.filter.components.menubar.filtermenu;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.filter.tools.filters.FilterType;

import javax.swing.*;

public class SimpleFilterMenuItem extends JRadioButtonMenuItem {

    public SimpleFilterMenuItem(String text, FilterType filterType, PublishSubject<FilterType> newFilterTypePublishSubject) {
        super(text);

        newFilterTypePublishSubject.subscribe(newFilter -> setSelected(newFilter == filterType));

        addActionListener(actionListener -> newFilterTypePublishSubject.onNext(filterType));
    }
}
