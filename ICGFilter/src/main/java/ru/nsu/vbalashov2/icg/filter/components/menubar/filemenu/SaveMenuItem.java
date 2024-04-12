package ru.nsu.vbalashov2.icg.filter.components.menubar.filemenu;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class SaveMenuItem extends JMenuItem {

    public SaveMenuItem(PublishSubject<File> filePublishSubject) {
        super("Save");

        FileNameExtensionFilter pngFileFilter = new FileNameExtensionFilter("PNG", "png");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(pngFileFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        addActionListener(event -> {
            int result = fileChooser.showSaveDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                filePublishSubject.onNext(fileChooser.getSelectedFile());
            } else if (result == JFileChooser.ERROR_OPTION) {
                JOptionPane.showMessageDialog(
                        this, "Error choosing file", "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
