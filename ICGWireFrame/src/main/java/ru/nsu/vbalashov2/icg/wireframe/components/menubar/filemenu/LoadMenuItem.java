package ru.nsu.vbalashov2.icg.wireframe.components.menubar.filemenu;

import io.reactivex.rxjava3.subjects.PublishSubject;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class LoadMenuItem extends JMenuItem {

    public LoadMenuItem(PublishSubject<File> filePublishSubject) {
        super("Load");

        FileNameExtensionFilter jsonExtensionFilter = new FileNameExtensionFilter("JSON", "json");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(jsonExtensionFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        addActionListener(e -> {
            int result = fileChooser.showOpenDialog(this);

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
