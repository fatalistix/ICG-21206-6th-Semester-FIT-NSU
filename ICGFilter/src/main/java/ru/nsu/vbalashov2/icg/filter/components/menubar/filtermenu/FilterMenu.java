package ru.nsu.vbalashov2.icg.filter.components.menubar.filtermenu;

import ru.nsu.vbalashov2.icg.filter.tools.events.EventPublishSubjects;
import ru.nsu.vbalashov2.icg.filter.tools.filters.FilterType;

import javax.swing.*;

public class FilterMenu extends JMenu {

    public FilterMenu(EventPublishSubjects eventPublishSubjects) {
        super("Filter");

        add(new SimpleFilterMenuItem("Bilaterial", FilterType.BILATERAL, eventPublishSubjects.getNewFilterTypePublishSubject()));
        add(new SimpleFilterMenuItem("Black white", FilterType.BLACK_WHITE, eventPublishSubjects.getNewFilterTypePublishSubject()));
        add(new SimpleFilterMenuItem("Embossing", FilterType.EMBOSSING, eventPublishSubjects.getNewFilterTypePublishSubject()));
        add(new FloydSteinbergDitherFilterMenuItem(
                eventPublishSubjects.getFloydSteinbergDitherNewRedQuantizationNumberPublishSubject(),
                eventPublishSubjects.getFloydSteinbergDitherNewGreenQuantizationNumberPublishSubject(),
                eventPublishSubjects.getFloydSteinbergDitherNewBlueQuantizationNumberPublishSubject(),
                eventPublishSubjects.getNewFilterTypePublishSubject()
        ));
        add(new GammaCorrectionFilterMenuItem(
                eventPublishSubjects.getGammaCorrectionNewGammaPublishSubject(),
                eventPublishSubjects.getNewFilterTypePublishSubject()
        ));
        add(new GaussianBlurFilterMenuItem(
                eventPublishSubjects.getGaussianBlurNewSizePublishSubject(),
                eventPublishSubjects.getGaussianBlurNewSigmaPublishSubject(),
                eventPublishSubjects.getNewFilterTypePublishSubject()
        ));
        add(new SimpleFilterMenuItem("Increase sharpness", FilterType.INCREASING_SHARPNESS, eventPublishSubjects.getNewFilterTypePublishSubject()));
        add(new MedianBlurFilterMenuItem(
                eventPublishSubjects.getMedianBlurNewSizePublishSubject(),
                eventPublishSubjects.getNewFilterTypePublishSubject()
        ));
        add(new SimpleFilterMenuItem("Negative", FilterType.NEGATIVE, eventPublishSubjects.getNewFilterTypePublishSubject()));
        add(new OrderedDitherFilterMenuItem(
                eventPublishSubjects.getOrderedDitherNewRedQuantizationNumberPublishSubject(),
                eventPublishSubjects.getOrderedDitherNewGreenQuantizationNumberPublishSubject(),
                eventPublishSubjects.getOrderedDitherNewBlueQuantizationNumberPublishSubject(),
                eventPublishSubjects.getNewFilterTypePublishSubject()
        ));
        add(new RobertsOperatorFilterMenuItem(
                eventPublishSubjects.getRobertsOperatorNewThresholdPublishSubject(),
                eventPublishSubjects.getNewFilterTypePublishSubject()
        ));
        add(new SobelOperatorFilterMenuItem(
                eventPublishSubjects.getSobelOperatorNewThresholdPublishSubject(),
                eventPublishSubjects.getNewFilterTypePublishSubject()
        ));
        add(new SimpleFilterMenuItem("Watercolorization", FilterType.WATERCOLORIZATION, eventPublishSubjects.getNewFilterTypePublishSubject()));

        addSeparator();

        add(new RotateImageMenuItem(
                eventPublishSubjects.getRotateImageNewAngleDegreesPublishSubject(),
                eventPublishSubjects.getNewFilterTypePublishSubject()
        ));
    }
}
