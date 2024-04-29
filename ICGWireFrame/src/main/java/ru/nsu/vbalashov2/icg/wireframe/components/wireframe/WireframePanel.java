package ru.nsu.vbalashov2.icg.wireframe.components.wireframe;

import io.reactivex.rxjava3.subjects.PublishSubject;
import org.ejml.simple.SimpleMatrix;
import ru.nsu.vbalashov2.icg.wireframe.tools.*;
import ru.nsu.vbalashov2.icg.wireframe.tools.events.ResetWireframeRotationEvent;
import ru.nsu.vbalashov2.icg.wireframe.tools.events.WireframeChangedEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class WireframePanel extends JPanel {

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final int LOW_COLOR = 0;
    private static final int HIGH_COLOR = 220;

    private static final double SCALE_FACTOR = 10000.;
    private static final double AXES_SCALE = 100.;
    private static final double STROKE_WIDTH = 2.;

    private final Wireframe wireframe;

    private Point previousMouseDragPosition = new Point(0, 0);

    public WireframePanel(
            PublishSubject<List<Point>> anchorPointsPublishSubject,
            PublishSubject<Integer> nPublishSubject,
            PublishSubject<Integer> mPublishSubject,
            PublishSubject<Integer> m1PublishSubject,
            PublishSubject<ResetWireframeRotationEvent> resetWireframeRotationEventPublishSubject
    ) {
        setBackground(BACKGROUND_COLOR);

        PublishSubject<WireframeChangedEvent> wireframeChangedEventPublishSubject = PublishSubject.create();
        wireframeChangedEventPublishSubject.subscribe(e -> repaint());

        wireframe = new Wireframe(
                anchorPointsPublishSubject,
                nPublishSubject,
                mPublishSubject,
                m1PublishSubject,
                PublishSubject.create(),
                wireframeChangedEventPublishSubject
        );

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                previousMouseDragPosition = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                int diffX = e.getX() - previousMouseDragPosition.x;
                int diffY = e.getY() - previousMouseDragPosition.y;
                previousMouseDragPosition = e.getPoint();

                wireframe.addToRotationMatrix(diffX, diffY);
            }
        });

        addMouseWheelListener(e -> {
            if (e.getWheelRotation() < 0) {
                wireframe.zoomOut();
            } else {
                wireframe.zoomIn();
            }
        });

        resetWireframeRotationEventPublishSubject.subscribe(e -> {
            wireframe.resetRotationAngles();
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke((float) STROKE_WIDTH));

        drawAxes(g2d);
        drawWireframe(g2d);
    }

    private void drawWireframe(Graphics2D g2d) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        List<Line3> formingLines = wireframe.getFormingLines();
        List<Line1> projectionLines = wireframe.getProjectionLines();

        List<LineWithColorValue> lines = new ArrayList<>(formingLines.size());
        for (int i = 0; i < formingLines.size(); i++) {
            lines.add(
                    new LineWithColorValue(
                            formingLines.get(i).start(),
                            formingLines.get(i).end(),
                            countColorValue(projectionLines.get(i))
                    )
            );
        }

        lines.sort((o1, o2) -> Double.compare(o2.color(), o1.color()));

        for (int i = 0; i < formingLines.size(); i++) {
            LineWithColorValue formingLine = lines.get(i);
//            g2d.setColor(countColor(projectionLines.get(i)));
            g2d.setColor(new Color(formingLine.color(), formingLine.color(), formingLine.color()));
            g2d.drawLine(
                    centerX + (int) (formingLine.start().x() * SCALE_FACTOR),
                    centerY + (int) (formingLine.start().y() * SCALE_FACTOR),
                    centerX + (int) (formingLine.end().x() * SCALE_FACTOR),
                    centerY + (int) (formingLine.end().y() * SCALE_FACTOR)
            );
        }
    }

    private void drawAxes(Graphics2D g2d) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        SimpleMatrix rotationMatrix = wireframe.getRotationMatrix();

        Vector4 ox = Vector4.fromSimpleMatrix(rotationMatrix.mult(new Vector4(1, 0, 0, 0).toSimpleMatrix()));
        Vector4 oy = Vector4.fromSimpleMatrix(rotationMatrix.mult(new Vector4(0, 1, 0, 0).toSimpleMatrix()));
        Vector4 oz = Vector4.fromSimpleMatrix(rotationMatrix.mult(new Vector4(0, 0, 1, 0).toSimpleMatrix()));

        g2d.setColor(Color.RED);
        g2d.drawLine(centerX, centerY, centerX + (int) (ox.x() * AXES_SCALE), centerY + (int) (ox.y() * AXES_SCALE));
        g2d.setColor(Color.GREEN);
        g2d.drawLine(centerX, centerY, centerX + (int) (oy.x() * AXES_SCALE), centerY + (int) (oy.y() * AXES_SCALE));
        g2d.setColor(Color.BLUE);
        g2d.drawLine(centerX, centerY, centerX + (int) (oz.x() * AXES_SCALE), centerY + (int) (oz.y() * AXES_SCALE));
    }

    private Color countColor(Line1 line) {
        double start = line.start();
        double end = line.end();

        int c = (countColorValue(start) + countColorValue(end)) / 2;
        return new Color(c, c, c);
    }

    private int countColorValue(Line1 line) {
        double start = line.start();
        double end = line.end();

        return countColorValue((start + end) / 2);
    }

    private int countColorValue(double x) {
        // x + 0.5 is a hardcoded value, must be replaced with something that will provide more
        // accurate color value
        return (int) Math.max(LOW_COLOR, Math.min(HIGH_COLOR, (LOW_COLOR + (HIGH_COLOR - LOW_COLOR) * (x + 0.5))));
    }
}
