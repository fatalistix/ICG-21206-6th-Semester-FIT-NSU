package ru.nsu.vbalashov2.icg.filter.components.toolbar;

import ru.nsu.vbalashov2.icg.filter.tools.events.EventPublishSubjects;
import ru.nsu.vbalashov2.icg.filter.tools.filters.FilterType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class FilterToolBar extends JToolBar {

    private static final String fitToSizeIconLocation = "icons/fit_to_size.png";
    private static final String realSizeIconLocation = "icons/real_size.png";
    private static final String openFileIconLocation = "icons/open_file.png";
    private static final String blackWhiteFilterIconLocation = "icons/filters/black_white_filter.png";
    private static final String negativeFilterIconLocation = "icons/filters/negative_filter.png";
    private static final String gaussianBlurFilterIconLocation = "icons/filters/gaussian_blur_filter.png";
    private static final String medianBlurFilterIconLocation = "icons/filters/median_blur_filter.png";
    private static final String increasingSharpnessFilterIconLocation = "icons/filters/increasing_sharpness_filter.png";
    private static final String embossingFilterIconLocation = "icons/filters/embossing_filter.png";
    private static final String gammaCorrectionFilterIconLocation = "icons/filters/gamma_correction_filter.png";
    private static final String robertsOperatorFilterIconLocation = "icons/filters/roberts_operator_filter.png";
    private static final String sobelOperatorFilterIconLocation = "icons/filters/sobel_operator_filter.png";
    private static final String floydSteinbergDitherFilterIconLocation = "icons/filters/floyd_steinberg_dither_filter.png";
    private static final String orderedDitherFilterIconLocation = "icons/filters/ordered_dither_filter.png";
    private static final String watercolorizationFilterIconLocation = "icons/filters/watercolorization_filter.png";
    private static final String rotateImageIconLocation = "icons/rotate.png";
    private static final String bilateralFilterImageIconLocation = "icons/filters/bilateral_filter.png";

    private static final int ICON_SIZE = 16;

    public FilterToolBar(EventPublishSubjects eventPublishSubjects) throws IOException {
        ImageIcon fitToSizeImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(fitToSizeIconLocation)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon realSizeImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(realSizeIconLocation)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon openFileImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(openFileIconLocation)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon blackWhiteFilterImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(blackWhiteFilterIconLocation)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon negativeFilterImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(negativeFilterIconLocation)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon gaussianBlurFilterImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(gaussianBlurFilterIconLocation)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon medianBlurFilterImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(medianBlurFilterIconLocation)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon increasingSharpnessFilterImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(increasingSharpnessFilterIconLocation)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon embossingFilterImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(embossingFilterIconLocation)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon gammaCorrectionFilterImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(gammaCorrectionFilterIconLocation)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon robertsOperatorFilterImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(robertsOperatorFilterIconLocation)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon sobelOperatorFilterImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(sobelOperatorFilterIconLocation)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon floydSteinbergDitherFilterImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(floydSteinbergDitherFilterIconLocation)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon orderedDitherFilterImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(orderedDitherFilterIconLocation)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon watercolorizationFilterImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(watercolorizationFilterIconLocation)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon rotateImageImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(rotateImageIconLocation)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon bilateralFilterImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(bilateralFilterImageIconLocation)).getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));

        setFloatable(false);

        add(new LoadNewImageButton(openFileImageIcon, eventPublishSubjects.getNewBufferedImagePublishSubject()));

        addSeparator();

        add(new SimpleFilterButton(blackWhiteFilterImageIcon, FilterType.BLACK_WHITE, eventPublishSubjects.getNewFilterTypePublishSubject(), "Convert to black-white"));
        add(new SimpleFilterButton(negativeFilterImageIcon, FilterType.NEGATIVE, eventPublishSubjects.getNewFilterTypePublishSubject(), "Inverse colors"));
        add(new GaussianBlurFilterButton(
                gaussianBlurFilterImageIcon,
                eventPublishSubjects.getGaussianBlurNewSizePublishSubject(),
                eventPublishSubjects.getGaussianBlurNewSigmaPublishSubject(),
                eventPublishSubjects.getNewFilterTypePublishSubject()
        ));
        add(new MedianBlurFilterButton(
                medianBlurFilterImageIcon,
                eventPublishSubjects.getMedianBlurNewSizePublishSubject(),
                eventPublishSubjects.getNewFilterTypePublishSubject()
        ));
        add(new SimpleFilterButton(increasingSharpnessFilterImageIcon, FilterType.INCREASING_SHARPNESS, eventPublishSubjects.getNewFilterTypePublishSubject(), "Increase sharpness"));
        add(new SimpleFilterButton(embossingFilterImageIcon, FilterType.EMBOSSING, eventPublishSubjects.getNewFilterTypePublishSubject(), "Apply embossing"));
        add(new GammaCorrectionFilterButton(
                gammaCorrectionFilterImageIcon,
                eventPublishSubjects.getGammaCorrectionNewGammaPublishSubject(),
                eventPublishSubjects.getNewFilterTypePublishSubject()
        ));
        add(new RobertsOperatorFilterButton(
                robertsOperatorFilterImageIcon,
                eventPublishSubjects.getRobertsOperatorNewThresholdPublishSubject(),
                eventPublishSubjects.getNewFilterTypePublishSubject()
        ));
        add(new SobelOperatorFilterButton(
                sobelOperatorFilterImageIcon,
                eventPublishSubjects.getSobelOperatorNewThresholdPublishSubject(),
                eventPublishSubjects.getNewFilterTypePublishSubject()
        ));
        add(new FloydSteinbergDitherFilterButton(
                floydSteinbergDitherFilterImageIcon,
                eventPublishSubjects.getFloydSteinbergDitherNewRedQuantizationNumberPublishSubject(),
                eventPublishSubjects.getFloydSteinbergDitherNewGreenQuantizationNumberPublishSubject(),
                eventPublishSubjects.getFloydSteinbergDitherNewBlueQuantizationNumberPublishSubject(),
                eventPublishSubjects.getNewFilterTypePublishSubject()
        ));
        add(new OrderedDitherFilterButton(
                orderedDitherFilterImageIcon,
                eventPublishSubjects.getOrderedDitherNewRedQuantizationNumberPublishSubject(),
                eventPublishSubjects.getOrderedDitherNewGreenQuantizationNumberPublishSubject(),
                eventPublishSubjects.getOrderedDitherNewBlueQuantizationNumberPublishSubject(),
                eventPublishSubjects.getNewFilterTypePublishSubject()
        ));
        add(new SimpleFilterButton(watercolorizationFilterImageIcon, FilterType.WATERCOLORIZATION, eventPublishSubjects.getNewFilterTypePublishSubject(), "Apply watercolorization"));
        add(new SimpleFilterButton(bilateralFilterImageIcon, FilterType.BILATERAL, eventPublishSubjects.getNewFilterTypePublishSubject(), "Apply noise filtering (Bilateral)"));

        addSeparator();

        add(new RotateImageButton(
                rotateImageImageIcon,
                eventPublishSubjects.getRotateImageNewAngleDegreesPublishSubject(),
                eventPublishSubjects.getNewFilterTypePublishSubject()
        ));

        addSeparator();

        add(new FitToSizeButton(fitToSizeImageIcon, eventPublishSubjects.getFitToSizePublishSubject()));
        add(new RealSizeButton(realSizeImageIcon, eventPublishSubjects.getRealSizePublishSubject()));
    }
}
