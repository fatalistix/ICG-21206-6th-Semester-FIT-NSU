package ru.nsu.vbalashov2.icg.wireframe.components.menubar.filemenu;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class SaveMenuItem extends JMenuItem {

    public SaveMenuItem(PublishSubject<File> filePublishSubject) {
        super("Save");

        FileNameExtensionFilter jsonExtensionFilter = new FileNameExtensionFilter("JSON", "json");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(jsonExtensionFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        addActionListener(e -> {
            int result = fileChooser.showSaveDialog(null);

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
