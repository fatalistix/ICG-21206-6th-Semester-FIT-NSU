package ru.nsu.vbalashov2.igc.paint.tools;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ru.nsu.vbalashov2.igc.paint.tools.events.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Polygon implements Tool {

    private Color color = ColorEvent.initialColor();
    private int numberOfAngles = FormEvent.initialNumberOfAngles();
    private int radius = SizeEvent.initialRadius();
    private int rotateAngle = RotateEvent.initialAngle();
    private int thickness = ThicknessEvent.initialThickness();

    public Polygon(EventBus eventBus) {
        eventBus.register(this);
    }

    @Override
    public void use(BufferedImage image, int x, int y) {
        double pickDegreeShift = Math.PI * 2 / numberOfAngles;

        List<Point> points = new ArrayList<>(numberOfAngles);

        for (int i = 0; i < numberOfAngles; ++i) {
            int h = (int) Math.round(Math.sin(i * pickDegreeShift + rotateAngle) * radius);
            int l = (int) Math.round(Math.cos(i * pickDegreeShift + rotateAngle) * radius);
            points.add(new Point(x + l, y + h));
        }

        for (int i = 1; i < points.size(); ++i) {
            Point start = points.get(i - 1);
            Point end = points.get(i);
            Line.drawLine(start.x, start.y, end.x, end.y, image, color, thickness);
        }

        Point start = points.get(points.size() - 1);
        Point end = points.get(0);
        Line.drawLine(start.x, start.y, end.x, end.y, image, color, thickness);
    }

    @Subscribe
    private void handleColorEvent(ColorEvent colorEvent) {
        color = colorEvent.color();
    }

    @Subscribe
    private void handleFormEvent(FormEvent formEvent) {
        numberOfAngles = formEvent.numberOfAngles();
    }

    @Subscribe
    private void handleRotateEvent(RotateEvent rotateEvent) {
        rotateAngle = rotateEvent.angle();
    }

    @Subscribe
    private void handleSizeEvent(SizeEvent sizeEvent) {
        radius = sizeEvent.radius();
    }

    @Subscribe
    private void handleThicknessEvent(ThicknessEvent thicknessEvent) {
        thickness = thicknessEvent.thickness();
    }
}
