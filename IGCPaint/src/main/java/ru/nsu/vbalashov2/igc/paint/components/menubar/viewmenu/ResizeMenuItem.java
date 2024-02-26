package ru.nsu.vbalashov2.igc.paint.components.menubar.viewmenu;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ru.nsu.vbalashov2.igc.paint.tools.events.BufferedImageEvent;
import ru.nsu.vbalashov2.igc.paint.tools.events.ImageResizeEvent;

import javax.swing.*;
import java.awt.*;

public class ResizeMenuItem extends JMenuItem {

    public ResizeMenuItem(EventBus eventBus) {
        super("Resize");

        ResizeOptionsPanel resizeOptionsPanel = new ResizeOptionsPanel(eventBus);

        addActionListener((event) -> {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    resizeOptionsPanel,
                    "Resize image",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {
                Dimension newSize = resizeOptionsPanel.getWidthAndHeightAsDimension();
                eventBus.post(new ImageResizeEvent(newSize.width, newSize.height));
            }
        });
    }


}
