package ru.nsu.vbalashov2.icg.wireframe.tools.events;

import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.Getter;

import java.awt.*;
import java.io.File;
import java.util.List;

@Getter
public class EventPublishSubjects {

    private final PublishSubject<List<Point>> bSplineAnchorPointsPublishSubject = PublishSubject.create();
    private final PublishSubject<Integer> bSplineNPublishSubject = PublishSubject.create();
    private final PublishSubject<Integer> bSplineMPublishSubject = PublishSubject.create();
    private final PublishSubject<Integer> bSplineM1PublishSubject = PublishSubject.create();

    private final PublishSubject<File> saveFilePublishSubject = PublishSubject.create();
    private final PublishSubject<File> loadFilePublishSubject = PublishSubject.create();
}
