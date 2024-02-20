package ru.nsu.vbalashov2.igc.paint.components;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ru.nsu.vbalashov2.igc.paint.tools.Fill;
import ru.nsu.vbalashov2.igc.paint.tools.PaintTool;
import ru.nsu.vbalashov2.igc.paint.tools.events.ClearEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class PaintPanel extends JPanel {
    private static final int DEFAULT_IMAGE_SIZE = 10000;
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;

    private EventBus eventBus;
    private BufferedImage image = new BufferedImage(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE, BufferedImage.TYPE_INT_RGB);
    private Graphics2D graphics2D = image.createGraphics();
    private boolean mouseIsPressed = false;
    private Color currentColor = Color.BLACK;
    private PaintTool currentTool = PaintTool.PEN;
    private int currentThickness = 5;

    public PaintPanel(EventBus eventBus) {
        this.eventBus = eventBus;
        this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        eventBus.register(this);

        graphics2D.setColor(DEFAULT_BACKGROUND_COLOR);
        graphics2D.fillRect(0, 0, DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseIsPressed = true;
                graphics2D.setColor(currentColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseIsPressed = false;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                switch (currentTool) {
                    case PEN -> {
                        graphics2D.fillOval(e.getX(), e.getY(), currentThickness, currentThickness);
                    }
                    case FILL -> {
                        System.out.println("FILLING");
                        Fill.fillArea(
                                image,
                                e.getX(),
                                e.getY(),
                                new Color(image.getRGB(e.getX(), e.getY())),
                                currentColor
                        );
                    }
                    case LINE -> {

                    }
                    case STAR -> {

                    }
                    case POLYGON -> {

                    }
                }
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mouseIsPressed &&
                        0 <= e.getX() && e.getX() < image.getWidth() &&
                        0 <= e.getY() && e.getY() < image.getHeight()) {
                    switch (currentTool) {
                        case PEN -> {
                            graphics2D.fillOval(e.getX(), e.getY(), currentThickness, currentThickness);
                        }
                        case FILL -> {

                        }
                        case LINE -> {

                        }
                        case STAR -> {

                        }
                        case POLYGON -> {

                        }
                    }
                    repaint();
                }
            }
        });
    }

    public void setNewImage(BufferedImage newImage) {
        this.image = newImage;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

    @Subscribe
    private void handleNewColorEvent(Color newColor) {
        this.currentColor = newColor;
    }

    @Subscribe
    private void handleNewToolEvent(PaintTool newTool) {
        this.currentTool = newTool;
    }

    @Subscribe
    private void handleClearEvent(ClearEvent event) {
        this.graphics2D.setColor(DEFAULT_BACKGROUND_COLOR);
        this.graphics2D.fillRect(0, 0, image.getWidth(), image.getHeight());
        this.graphics2D.setColor(currentColor);
        repaint();
    }
}
