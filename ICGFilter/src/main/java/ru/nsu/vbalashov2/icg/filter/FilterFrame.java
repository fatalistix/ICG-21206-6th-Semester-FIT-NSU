package ru.nsu.vbalashov2.icg.filter;

import ru.nsu.vbalashov2.icg.filter.components.ImageScrollPane;
import ru.nsu.vbalashov2.icg.filter.components.JImagePanel;
import ru.nsu.vbalashov2.icg.filter.components.dialogs.Dialogs;
import ru.nsu.vbalashov2.icg.filter.components.menubar.FilterMenuBar;
import ru.nsu.vbalashov2.icg.filter.components.toolbar.FilterToolBar;
import ru.nsu.vbalashov2.icg.filter.tools.events.EventPublishSubjects;

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
                Dialogs.showExitDialog(FilterFrame.this);
            }
        });

        EventPublishSubjects eventPublishSubjects = new EventPublishSubjects();

        setJMenuBar(new FilterMenuBar(eventPublishSubjects));

        ImageScrollPane imageScrollPane = new ImageScrollPane();
        new JImagePanel(
                imageScrollPane,
                this,
                eventPublishSubjects
        );

        FilterToolBar filterToolBar = new FilterToolBar(eventPublishSubjects);

        getContentPane().add(filterToolBar, "North");
        getContentPane().add(imageScrollPane);

        setVisible(true);
    }

    @Override
    public void clickImage(int x, int y) {
        System.out.println("CLICK IMAGE CLICKED");
    }
}
