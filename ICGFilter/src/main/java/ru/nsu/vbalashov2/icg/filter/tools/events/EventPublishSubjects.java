package ru.nsu.vbalashov2.icg.filter.tools.events;

import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.Getter;
import ru.nsu.vbalashov2.icg.filter.tools.filters.FilterType;

import java.awt.image.BufferedImage;
import java.io.File;

@Getter
public class EventPublishSubjects {

    private final PublishSubject<File> saveFilePublishSubject = PublishSubject.create();
    private final PublishSubject<BufferedImage> newBufferedImagePublishSubject = PublishSubject.create();
    private final PublishSubject<FitToSizeEvent> fitToSizePublishSubject = PublishSubject.create();
    private final PublishSubject<RealSizeEvent> realSizePublishSubject = PublishSubject.create();
    private final PublishSubject<Integer> rotateImageNewAngleDegreesPublishSubject = PublishSubject.create();
    private final PublishSubject<Boolean> useModifiedImagePublishSubject = PublishSubject.create();

    private final PublishSubject<Object> newInterpolationTypePublishSubject = PublishSubject.create();

    private final PublishSubject<FilterType> newFilterTypePublishSubject = PublishSubject.create();

    private final PublishSubject<Integer> gaussianBlurNewSizePublishSubject = PublishSubject.create();
    private final PublishSubject<Double> gaussianBlurNewSigmaPublishSubject = PublishSubject.create();
    private final PublishSubject<Integer> medianBlurNewSizePublishSubject = PublishSubject.create();
    private final PublishSubject<Double> gammaCorrectionNewGammaPublishSubject = PublishSubject.create();
    private final PublishSubject<Integer> robertsOperatorNewThresholdPublishSubject = PublishSubject.create();
    private final PublishSubject<Integer> sobelOperatorNewThresholdPublishSubject = PublishSubject.create();
    private final PublishSubject<Integer> floydSteinbergDitherNewRedQuantizationNumberPublishSubject = PublishSubject.create();
    private final PublishSubject<Integer> floydSteinbergDitherNewGreenQuantizationNumberPublishSubject = PublishSubject.create();
    private final PublishSubject<Integer> floydSteinbergDitherNewBlueQuantizationNumberPublishSubject = PublishSubject.create();
    private final PublishSubject<Integer> orderedDitherNewRedQuantizationNumberPublishSubject = PublishSubject.create();
    private final PublishSubject<Integer> orderedDitherNewGreenQuantizationNumberPublishSubject = PublishSubject.create();
    private final PublishSubject<Integer> orderedDitherNewBlueQuantizationNumberPublishSubject = PublishSubject.create();
}
