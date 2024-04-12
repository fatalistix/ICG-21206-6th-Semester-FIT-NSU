package ru.nsu.vbalashov2.igc.paint.components.menubar.filemenu;

import com.google.common.eventbus.EventBus;
import ru.nsu.vbalashov2.igc.paint.tools.events.BufferedImageEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LoadMenuItem extends JMenuItem {

    public LoadMenuItem(EventBus eventBus) {
        super("Load");

        FileNameExtensionFilter imageFileFilter = new FileNameExtensionFilter("Images", "png", "jpeg", "bmp", "gif");

        JFileChooser fileChooser = new JFileChooser();

        addActionListener((event) -> {
            fileChooser.setDialogTitle("Select file");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(imageFileFilter);
	    fileChooser.setAcceptAllFileFilterUsed(false);

            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                BufferedImage newImage;
                try {
                    newImage = ImageIO.read(fileChooser.getSelectedFile());
                    eventBus.post(new BufferedImageEvent(newImage));
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(
                            this, e.getMessage(), "Error reading file", JOptionPane.ERROR_MESSAGE
                    );
                }
            } else if (result == JFileChooser.ERROR_OPTION) {
                JOptionPane.showMessageDialog(
                        this, "Error choosing file", "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
