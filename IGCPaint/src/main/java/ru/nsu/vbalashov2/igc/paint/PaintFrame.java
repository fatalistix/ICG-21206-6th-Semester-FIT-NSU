package ru.nsu.vbalashov2.igc.paint;

import com.google.common.eventbus.EventBus;
import ru.nsu.vbalashov2.igc.paint.components.PaintMenuBar;
import ru.nsu.vbalashov2.igc.paint.components.PaintPanel;
import ru.nsu.vbalashov2.igc.paint.components.toolbar.PaintToolBar;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PaintFrame extends JFrame {
    private static final Color INITIAL_COLOR = Color.BLACK;
    public PaintFrame() throws IOException {
        super("IGCPaint");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(640, 480));

        var eventBus = new EventBus();

        var paintMenuBar = new PaintMenuBar();
        this.setJMenuBar(paintMenuBar);

        PaintToolBar paintToolBar = new PaintToolBar(INITIAL_COLOR, eventBus);

        PaintPanel paintPanel = new PaintPanel(eventBus);
        JScrollPane scrollPane = new JScrollPane(paintPanel);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.getContentPane().add(paintToolBar, "North");
        this.getContentPane().add(scrollPane);

        this.setVisible(true);
    }
}
