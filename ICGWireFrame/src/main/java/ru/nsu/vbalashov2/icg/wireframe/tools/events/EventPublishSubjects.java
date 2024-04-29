package ru.nsu.vbalashov2.icg.wireframe.tools.events;

import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.Getter;

import java.util.List;
import java.awt.*;
import java.io.File;

@Getter
public class EventPublishSubjects {

    private final PublishSubject<List<Point>> bSplineAnchorPointsEventPublishSubject = PublishSubject.create();
    private final PublishSubject<Integer> bSplineNPublishSubject = PublishSubject.create();
    private final PublishSubject<Integer> bSplineMPublishSubject = PublishSubject.create();
    private final PublishSubject<Integer> bSplineM1PublishSubject = PublishSubject.create();

    private final PublishSubject<File> saveFilePublishSubject = PublishSubject.create();
    private final PublishSubject<File> loadFilePublishSubject = PublishSubject.create();

    private final PublishSubject<ResetWireframeRotationEvent> resetWireframeRotationEventPublishSubject = PublishSubject.create();
}
