package ru.nsu.vbalashov2.icg.filter.components.options.medianblurfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import java.util.Hashtable;

public class MedianBlurFilterSizeSlider extends JSlider {

    public MedianBlurFilterSizeSlider(PublishSubject<Integer> dynamicValueChangingPublishSubject) {
        super(1, 5, 1);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(1, new JLabel("3"));
        labelTable.put(2, new JLabel("5"));
        labelTable.put(3, new JLabel("7"));
        labelTable.put(4, new JLabel("9"));
        labelTable.put(5, new JLabel("11"));
        setLabelTable(labelTable);
        setPaintLabels(true);

        dynamicValueChangingPublishSubject.subscribe(newValue -> setValue(newValue >> 1));

        addChangeListener(listener -> dynamicValueChangingPublishSubject.onNext(getValue() * 2 + 1));
    }
}
