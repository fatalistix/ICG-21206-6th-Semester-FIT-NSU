package ru.nsu.vbalashov2.igc.paint.components.menubar.viewmenu;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ru.nsu.vbalashov2.igc.paint.tools.events.BufferedImageEvent;
import ru.nsu.vbalashov2.igc.paint.tools.events.ImageResizeEvent;

import javax.swing.*;
import java.awt.*;

public class ResizeOptionsPanel extends JPanel {

    private final JSpinner widthSpinner;
    private final JSpinner heightSpinner;
    public ResizeOptionsPanel(EventBus eventBus) {
        eventBus.register(this);

        setLayout(new GridLayout(2, 2));

        add(new JLabel("Width:"));

        widthSpinner = new JSpinner(new SpinnerNumberModel(
                ImageResizeEvent.initialWidth(),
                ImageResizeEvent.minWidth(),
                ImageResizeEvent.maxWidth(),
                1
        ));

        add(widthSpinner);

        add(new JLabel("Height:"));

        heightSpinner = new JSpinner(new SpinnerNumberModel(
                ImageResizeEvent.initialHeight(),
                ImageResizeEvent.minHeight(),
                ImageResizeEvent.maxHeight(),
                1
        ));

        add(heightSpinner);
    }

    public Dimension getWidthAndHeightAsDimension() {
        return new Dimension(
                (Integer) widthSpinner.getValue(),
                (Integer) heightSpinner.getValue()
        );
    }

    @Subscribe
    private void handleBufferedImageEvent(BufferedImageEvent event) {
        widthSpinner.setValue(event.image().getWidth());
        heightSpinner.setValue(event.image().getHeight());
    }
}
