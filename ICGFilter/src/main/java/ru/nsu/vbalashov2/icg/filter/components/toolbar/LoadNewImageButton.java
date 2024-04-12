package ru.nsu.vbalashov2.icg.filter.components.toolbar;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LoadNewImageButton extends JButton {

    public LoadNewImageButton(Icon icon, PublishSubject<BufferedImage> newBufferedImagePublishSubject) {
        super(icon);

        setToolTipText("Load new image");

        FileNameExtensionFilter imagesFileFilter = new FileNameExtensionFilter("Images", "png", "jpeg", "bmp", "gif", "jpg");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(imagesFileFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        addActionListener(actionEvent -> {
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                BufferedImage newImage;
                try {
                    newImage = ImageIO.read(fileChooser.getSelectedFile());
                    newBufferedImagePublishSubject.onNext(newImage);
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
