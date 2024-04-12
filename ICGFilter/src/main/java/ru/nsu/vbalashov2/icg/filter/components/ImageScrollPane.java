package ru.nsu.vbalashov2.icg.filter.components;

import javax.swing.*;
import java.awt.*;

public class ImageScrollPane extends JScrollPane {

    private static final int INDENT = 4;
    private static final float DASH_LENGTH = 6;
    private static final float DASH_SPACE = 3;
    private static final Color DASH_COLOR = Color.BLACK;

    public ImageScrollPane() {
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setViewportBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(INDENT, INDENT, INDENT, INDENT),
                BorderFactory.createDashedBorder(DASH_COLOR, DASH_LENGTH, DASH_SPACE)
        ));
    }
}
