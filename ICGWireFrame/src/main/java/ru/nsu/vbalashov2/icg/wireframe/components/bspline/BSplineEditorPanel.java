package ru.nsu.vbalashov2.icg.wireframe.components.bspline;

import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.nsu.vbalashov2.icg.wireframe.tools.BSpline;
import ru.nsu.vbalashov2.icg.wireframe.tools.events.BSplineChangedEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class BSplineEditorPanel extends JPanel {

    private final static Color BACKGROND_COLOR = Color.BLACK;
    private final static Color GRID_COLOR = new Color(60, 60, 60);
    private final static Color AXES_COLOR = new Color(180, 180, 180);
    private final static Color BSPLINE_COLOR = Color.YELLOW;
    private final static Color POLYLINE_COLOR = Color.PINK;
    private final static Color ANCHOR_POINT_CIRCLE_COLOR = Color.PINK;
    private final static Color SELECTED_ANCHOR_POINT_CIRCLE_COLOR = Color.GREEN;

    private final static int DIVISION_LENGTH = 30;
    private final static int MINOR_SERIF_LENGTH = 2;
    private final static int MAJOR_SERIF_LENGTH = 4;
    private final static int ANCHOR_POINT_CIRCLE_RADIUS = 10;

    private final BSpline bSpline;
    private double zoom = 1.;
    // null only before first repaint call for centering axes
    private Point centerPosition = null;
    private int selectedAnchorPointIndex = -1;
    // (0, 0) only for not being null, is updated at press events
    private Point previousMouseDragPosition = new Point(0, 0);

    public BSplineEditorPanel(
            PublishSubject<List<Point>> anchorPointsPublishSubject,
            PublishSubject<Integer> nPublishSubject,
            PublishSubject<Integer> kPublishSubject
    ) {
        setBackground(BACKGROND_COLOR);

        PublishSubject<BSplineChangedEvent> bSplineChangedEventPublishSubject = PublishSubject.create();
        bSplineChangedEventPublishSubject.subscribe(be -> repaint());

        bSpline = new BSpline(
                anchorPointsPublishSubject,
                nPublishSubject,
                bSplineChangedEventPublishSubject
        );

        kPublishSubject.subscribe(k -> {
            while (k < bSpline.getAnchorPoints().size()) {
                bSpline.removeLastAnchorPoint();
            }
            while (k > bSpline.getAnchorPoints().size()) {
                int x = getRandomNumber(0, getWidth());
                int y = getRandomNumber(0, getHeight());
                bSpline.addAnchorPoint(mapToCoord(x, y));
            }
            repaint();
        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                previousMouseDragPosition = e.getPoint();
                Point coordPoint = mapToCoord(e.getPoint());
                if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK) {
                    selectedAnchorPointIndex = bSpline.getPointIDByCoords(
                            coordPoint,
                            ANCHOR_POINT_CIRCLE_RADIUS
                    );
                    if (selectedAnchorPointIndex == -1) {
                        bSpline.addAnchorPoint(coordPoint);
                        kPublishSubject.onNext(bSpline.getAnchorPoints().size());
                    }
                } else if (e.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK) {
                    bSpline.removeAnchorPoint(coordPoint, (int) (ANCHOR_POINT_CIRCLE_RADIUS * zoom));
                    kPublishSubject.onNext(bSpline.getAnchorPoints().size());
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedAnchorPointIndex = -1;
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {

                if (e.getModifiersEx() == MouseEvent.BUTTON2_DOWN_MASK) {
                    int diffX = e.getX() - previousMouseDragPosition.x;
                    int diffY = e.getY() - previousMouseDragPosition.y;
                    centerPosition.x += diffX;
                    centerPosition.y += diffY;
                } else if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK && selectedAnchorPointIndex != -1) {
                    Point previousMouseDragPositionCoord = mapToCoord(previousMouseDragPosition);
                    Point currentMouseDragPositionCoord = mapToCoord(e.getPoint());
                    int diffX = currentMouseDragPositionCoord.x - previousMouseDragPositionCoord.x;
                    int diffY = currentMouseDragPositionCoord.y - previousMouseDragPositionCoord.y;
                    bSpline.addToAnchorPoint(selectedAnchorPointIndex, diffX, diffY);
                }

                previousMouseDragPosition = e.getPoint();
                repaint();
            }
        });

        addMouseWheelListener(e -> {
            if (e.getWheelRotation() < 0) {
                zoom *= 1.1;
            } else {
                zoom /= 1.1;
            }
            repaint();
        });
    }

    private Point mapToScreen(Point p) {
        return new Point((int) (p.x * zoom + centerPosition.x), (int) (p.y * zoom + centerPosition.y));
    }

    private Point mapToScreen(int x, int y) {
        return new Point((int) (x * zoom + centerPosition.x), (int) (y * zoom + centerPosition.y));
    }

    private Point mapToCoord(Point p) {
        return new Point((int) ((p.x - centerPosition.x) / zoom), (int) ((p.y - centerPosition.y) / zoom));
    }

    private Point mapToCoord(int x, int y) {
        return new Point((int) ((x - centerPosition.x) / zoom), (int) ((y - centerPosition.y) / zoom));
    }

    private static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // null only in first repaint for centering axes
        if (centerPosition == null) {
            centerPosition = new Point(getWidth() / 2, getHeight() / 2);
        }

        drawGrid(g);
        drawAxes(g);
        drawAnchorPointsWithPolyline(g);
        drawBSpline(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(GRID_COLOR);

        int centerX = centerPosition.x;
        int centerY = centerPosition.y;

        int divisionLength = (int) (zoom * DIVISION_LENGTH);

        // drawing background grid:
        // right vertical lines
        for (int x = centerX; x < getWidth(); x += divisionLength) {
            g.drawLine(x, 0, x, getHeight());
        }
        // left vertical lines
        for (int x = centerX - divisionLength; x >= 0; x -= divisionLength) {
            g.drawLine(x, 0, x, getHeight());
        }
        // down horizontal lines
        for (int y = centerY; y < getHeight(); y += divisionLength) {
            g.drawLine(0, y, getWidth(), y);
        }
        // up horizontal lines
        for (int y = centerY - divisionLength; y >= 0; y -= divisionLength) {
            g.drawLine(0, y, getWidth(), y);
        }
    }

    private void drawAxes(Graphics g) {
        g.setColor(AXES_COLOR);

        int centerX = centerPosition.x;
        int centerY = centerPosition.y;

        // drawing axes
        g.drawLine(0, centerY, getWidth(), centerY);
        g.drawLine(centerX, 0, centerX, getHeight());

        int majorSerifLength = (int) (zoom * MAJOR_SERIF_LENGTH);
        int minorSerifLength = (int) (zoom * MINOR_SERIF_LENGTH);
        int divisionLength = (int) (zoom * DIVISION_LENGTH);

        // drawing serifs
        // right vertical serifs
        for (int x = centerX + divisionLength, i = 0; x < getWidth(); x += divisionLength, ++i) {
            if (i % 5 == 0) {
                g.drawLine(x, centerY - majorSerifLength, x, centerY + majorSerifLength);
            } else {
                g.drawLine(x, centerY - minorSerifLength, x, centerY + minorSerifLength);
            }
        }
        // left vertical serifs
        for (int x = centerX - divisionLength, i = -1; x >= 0; x -= divisionLength, --i) {
            if (i % 5 == 0) {
                g.drawLine(x, centerY - majorSerifLength, x, centerY + majorSerifLength);
            } else {
                g.drawLine(x, centerY - minorSerifLength, x, centerY + minorSerifLength);
            }
        }
        // down horizontal serifs
        for (int y = centerY, i = 1; y < getHeight(); y += divisionLength, ++i) {
            if (i % 5 == 0) {
                g.drawLine(centerX - majorSerifLength, y, centerX + majorSerifLength, y);
            } else {
                g.drawLine(centerX - minorSerifLength, y, centerX + minorSerifLength, y);
            }
        }
        // up horizontal serifs
        for (int y = centerY - divisionLength, i = -1; y >= 0; y -= divisionLength, --i) {
            if (i % 5 == 0) {
                g.drawLine(centerX - majorSerifLength, y, centerX + majorSerifLength, y);
            } else {
                g.drawLine(centerX - minorSerifLength, y, centerX + minorSerifLength, y);
            }
        }
    }

    private void drawAnchorPointsWithPolyline(Graphics g) {
        List<Point> anchorPoints = bSpline.getAnchorPoints();

        g.setColor(POLYLINE_COLOR);
        drawPolyline(g, anchorPoints);

        g.setColor(ANCHOR_POINT_CIRCLE_COLOR);
        for (int i = 0; i < anchorPoints.size(); ++i) {
            if (i == selectedAnchorPointIndex) {
                g.setColor(SELECTED_ANCHOR_POINT_CIRCLE_COLOR);
            }
            Point p = mapToScreen(anchorPoints.get(i));
            g.drawOval(
                    p.x - ANCHOR_POINT_CIRCLE_RADIUS,
                    p.y - ANCHOR_POINT_CIRCLE_RADIUS,
                    2 * ANCHOR_POINT_CIRCLE_RADIUS,
                    2 * ANCHOR_POINT_CIRCLE_RADIUS
            );
            if (i == selectedAnchorPointIndex) {
                g.setColor(ANCHOR_POINT_CIRCLE_COLOR);
            }
        }
    }

    private void drawBSpline(Graphics g) {
        if (bSpline.canBuild()) {
            List<Point> bSplinePoints = bSpline.getBSplinePoints();

            g.setColor(BSPLINE_COLOR);
            drawPolyline(g, bSplinePoints);
        }
    }

    private void drawPolyline(Graphics g, List<Point> polylinePoints) {
        for (int i = 0; i < polylinePoints.size() - 1; ++i) {
            Point start = mapToScreen(polylinePoints.get(i));
            Point end = mapToScreen(polylinePoints.get(i+1));
            g.drawLine(start.x, start.y, end.x, end.y);
        }
    }
}
