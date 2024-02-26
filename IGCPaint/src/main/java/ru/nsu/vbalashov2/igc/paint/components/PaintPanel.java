package ru.nsu.vbalashov2.igc.paint.components;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ru.nsu.vbalashov2.igc.paint.tools.Polygon;
import ru.nsu.vbalashov2.igc.paint.tools.*;
import ru.nsu.vbalashov2.igc.paint.tools.events.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;

public class PaintPanel extends JPanel {
    private static final Color DEFAULT_BACKGROUND_COLOR = new Color(230, 230, 255);
    private static final Color IMAGE_INIT_COLOR = Color.WHITE;
    private final EventBus eventBus;

    private BufferedImage image = new BufferedImage(
            ImageResizeEvent.initialWidth(),
            ImageResizeEvent.initialHeight(),
            BufferedImage.TYPE_INT_RGB
    );

    private Graphics2D graphics2D = image.createGraphics();
    private PaintTool currentTool = PaintToolEvent.initialPaintTool();
    private final Line line;
    private final Fill fill;
    private final Star star;
    private final Polygon polygon;
    private final History history = new History();

    public PaintPanel(EventBus eventBus) {
        this.eventBus = eventBus;

        eventBus.register(this);

        line = new Line(eventBus);
        fill = new Fill(eventBus);
        star = new Star(eventBus);
        polygon = new Polygon(eventBus);

        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        setBackground(DEFAULT_BACKGROUND_COLOR);

        clearImage();

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                history.save(image.getData());
                switch (currentTool) {
                    case FILL -> fill.use(image, e.getX(), e.getY());
                    case LINE -> line.use(image, e.getX(), e.getY());
                    case STAR -> star.use(image, e.getX(), e.getY());
                    case POLYGON -> polygon.use(image, e.getX(), e.getY());
                }
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, this);
    }

    @Subscribe
    private void handlePaintToolEvent(PaintToolEvent paintToolEvent) {
        this.currentTool = paintToolEvent.paintTool();
    }

    @Subscribe
    private void handleClearEvent(ClearEvent event) {
        history.save(image.getData());
        clearImage();
        repaint();
    }

    private void clearImage() {
        graphics2D.setColor(IMAGE_INIT_COLOR);
        graphics2D.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    @Subscribe
    private void handleBufferedImageEvent(BufferedImageEvent event) {
        history.save(image.getData());
        image = event.image();
        graphics2D = image.createGraphics();
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        revalidate();
        repaint();
        eventBus.post(new ScrollRevalidateEvent());
    }

    @Subscribe
    private void handleSaveImageToFileEvent(SaveImageToFileEvent event) {
        try {
            ImageIO.write(image, "PNG", event.file());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this, e.getMessage(), "Error saving file", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Subscribe
    private void handleImageResizeEvent(ImageResizeEvent event) {
        history.save(image.getData());
        BufferedImage newImage = new BufferedImage(event.width(), event.height(), BufferedImage.TYPE_INT_RGB);
        graphics2D = newImage.createGraphics();

        graphics2D.setColor(IMAGE_INIT_COLOR);
        graphics2D.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());

        newImage.setData(image.getData());
        image = newImage;
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        revalidate();
        repaint();
        eventBus.post(new ScrollRevalidateEvent());

        repaint();
    }

    @Subscribe
    private void handleUndoEvent(UndoEvent event) {
        Raster r = history.getPrevious();
        if (r == null) {
            return;
        }

        if (r.getWidth() != image.getWidth() || r.getHeight() != image.getHeight()) {
            BufferedImage newImage = new BufferedImage(r.getWidth(), r.getHeight(), BufferedImage.TYPE_INT_RGB);
            graphics2D = newImage.createGraphics();
            image = newImage;
            setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            revalidate();
            repaint();
            eventBus.post(new ScrollRevalidateEvent());
        }

        image.setData(r);
        repaint();
    }
}
