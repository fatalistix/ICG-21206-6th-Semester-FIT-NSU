package ru.nsu.vbalashov2.icg.filter.components.options.gammacorrectionfilter;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import java.util.Hashtable;

public class GammaCorrectionFilterGammaSlider extends JSlider {

    public GammaCorrectionFilterGammaSlider(PublishSubject<Double> dynamicValueChangingPublishSubject) {
        super(1, 100, 10);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(1, new JLabel("0.1"));
        labelTable.put(25, new JLabel("2.5"));
        labelTable.put(50, new JLabel("5"));
        labelTable.put(75, new JLabel("7.5"));
        labelTable.put(100, new JLabel("10"));
        setLabelTable(labelTable);
        setPaintLabels(true);

        dynamicValueChangingPublishSubject.subscribe(value -> setValue((int) (value * 10.)));

        addChangeListener(listener -> dynamicValueChangingPublishSubject.onNext(((double) getValue()) / 10.));
    }
}
