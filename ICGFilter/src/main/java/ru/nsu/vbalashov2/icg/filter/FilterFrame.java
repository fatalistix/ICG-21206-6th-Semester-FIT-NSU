package ru.nsu.vbalashov2.icg.filter;

import ru.nsu.vbalashov2.icg.filter.components.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FilterFrame extends JFrame implements FrameWork {

    public FilterFrame() throws Exception {
        super("ICGFilter");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(640, 480));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        FilterFrame.this,
                        "Are you sure you want to exit? All unsaved data will be lost.",
                        "Exit?",
                        JOptionPane.YES_NO_OPTION
                );

                if (result == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JImagePanel imagePanel = new JImagePanel(scrollPane, this);

        getContentPane().add(scrollPane);

        setVisible(true);
    }

    @Override
    public void changeViewedImage() {

    }

    @Override
    public void clickImage(int x, int y) {

    }
}
