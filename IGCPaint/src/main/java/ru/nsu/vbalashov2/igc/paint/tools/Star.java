package ru.nsu.vbalashov2.igc.paint.tools;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ru.nsu.vbalashov2.igc.paint.tools.events.ColorEvent;
import ru.nsu.vbalashov2.igc.paint.tools.events.FormEvent;
import ru.nsu.vbalashov2.igc.paint.tools.events.RotateEvent;
import ru.nsu.vbalashov2.igc.paint.tools.events.SizeEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Star implements Tool {

    private Color color = Color.BLACK;
    private int numberOfAngles = 6;
    private int radius = 100;
    private int rotateAngle = 0;
    private final Line line;

    public Star(Line line, EventBus eventBus) {
        eventBus.register(this);
        this.line = line;
    }

    @Override
    public void use(BufferedImage image, int x, int y) {
        double pickDegreeShift = Math.PI * 2 / numberOfAngles;
        double pitDegreeShift = Math.PI / numberOfAngles;
        double rotateDegree = rotateAngle * Math.PI / 180;
        int halfRadius = radius >> 1;

        List<Point> points = new ArrayList<>(numberOfAngles * 2);

        for (int i = 0; i < numberOfAngles; ++i) {
            int h = (int) Math.round(Math.sin(i * pickDegreeShift + rotateDegree) * radius);
            int l = (int) Math.round(Math.cos(i * pickDegreeShift + rotateDegree) * radius);
            points.add(new Point(x + l, y + h));

            h = (int) Math.round(Math.sin(i * pickDegreeShift + pitDegreeShift + rotateDegree) * halfRadius);
            l = (int) Math.round(Math.cos(i * pickDegreeShift + pitDegreeShift + rotateDegree) * halfRadius);
            points.add(new Point(x + l, y + h));
        }

        for (int i = 1; i < points.size(); ++i) {
            Point start = points.get(i - 1);
            Point end = points.get(i);
            line.drawLine(start.x, start.y, end.x, end.y, image, color);
        }

        Point start = points.get(points.size() - 1);
        Point end = points.get(0);
        line.drawLine(start.x, start.y, end.x, end.y, image, color);
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
}
