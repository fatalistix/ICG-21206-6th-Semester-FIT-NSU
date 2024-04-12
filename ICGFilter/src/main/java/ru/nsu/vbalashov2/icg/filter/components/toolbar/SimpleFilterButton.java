package ru.nsu.vbalashov2.icg.filter.components.toolbar;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.filter.tools.filters.FilterType;

import javax.swing.*;

public class SimpleFilterButton extends JToggleButton {

    public SimpleFilterButton(Icon icon, FilterType filterType, PublishSubject<FilterType> newFilterTypePublishSubject, String toolTipText) {
        super(icon);

        setToolTipText(toolTipText);

        newFilterTypePublishSubject.subscribe(newFilter -> setSelected(newFilter == filterType));

        addActionListener(actionListener -> newFilterTypePublishSubject.onNext(filterType));
    }
}
