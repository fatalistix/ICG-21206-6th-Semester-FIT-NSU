package ru.nsu.vbalashov2.igc.paint;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ru.nsu.vbalashov2.igc.paint.components.PaintPanel;
import ru.nsu.vbalashov2.igc.paint.components.menubar.PaintMenuBar;
import ru.nsu.vbalashov2.igc.paint.components.toolbar.PaintToolBar;
import ru.nsu.vbalashov2.igc.paint.tools.events.ScrollRevalidateEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class PaintFrame extends JFrame {

    private final JScrollPane scrollPane;

    public PaintFrame() throws IOException {
        super("IGCPaint");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(640, 480));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        PaintFrame.this,
                        "Are you sure you want to exit? All unsaved data will be lost.",
                        "Exit?",
                        JOptionPane.YES_NO_OPTION
                );

                if (result == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });

        var eventBus = new EventBus();

        eventBus.register(this);

        var paintMenuBar = new PaintMenuBar(eventBus);
        this.setJMenuBar(paintMenuBar);

        PaintToolBar paintToolBar = new PaintToolBar(eventBus);

        PaintPanel paintPanel = new PaintPanel(eventBus);
        scrollPane = new JScrollPane(paintPanel);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.getContentPane().add(paintToolBar, "North");
        this.getContentPane().add(scrollPane);

        this.setVisible(true);
    }

    @Subscribe
    private void handleScrollRevalidateEvent(ScrollRevalidateEvent event) {
        scrollPane.revalidate();
        scrollPane.updateUI();
    }
}
