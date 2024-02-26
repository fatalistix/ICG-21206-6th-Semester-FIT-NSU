package ru.nsu.vbalashov2.igc.paint.components.menubar.filemenu;

import com.google.common.eventbus.EventBus;
import ru.nsu.vbalashov2.igc.paint.tools.events.SaveImageToFileEvent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveMenuItem extends JMenuItem {

    public SaveMenuItem(EventBus eventBus) {
        super("Save");

        FileNameExtensionFilter pngFileFilter = new FileNameExtensionFilter("PNG", "png");

        JFileChooser fileChooser = new JFileChooser();

        addActionListener((event) -> {
            fileChooser.setDialogTitle("Save file");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(pngFileFilter);

            int result = fileChooser.showSaveDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                eventBus.post(new SaveImageToFileEvent(fileChooser.getSelectedFile()));
            } else if (result == JFileChooser.ERROR_OPTION) {
                JOptionPane.showMessageDialog(
                        this, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
