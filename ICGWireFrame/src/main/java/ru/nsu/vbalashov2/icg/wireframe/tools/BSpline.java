package ru.nsu.vbalashov2.icg.wireframe.tools;

import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.Getter;
import org.ejml.simple.SimpleMatrix;
import ru.nsu.vbalashov2.icg.wireframe.tools.events.BSplineChangedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BSpline {

    private final static SimpleMatrix m = new SimpleMatrix(
            new double[][] {
                    { -1./6.,  3./6., -3./6.,  1./6. },
                    {  3./6., -6./6.,  3./6.,  0./6. },
                    { -3./6.,  0./6.,  3./6.,  0./6. },
                    {  1./6.,  4./6.,  1./6.,  0./6. },
            });

    @Getter
    private final List<Point> anchorPoints = new ArrayList<>();

    @Getter
    private final List<Point> bSplinePoints = new ArrayList<>();

    private int segmentCount = 1;

    private final PublishSubject<BSplineChangedEvent> bSplineChangedEventPublishSubject;

    public BSpline(
            PublishSubject<List<Point>> anchorPointsPublishSubject,
            PublishSubject<Integer> nPublishSubject,
            PublishSubject<BSplineChangedEvent> bSplineChangedEventPublishSubject
    ) {
        this.bSplineChangedEventPublishSubject = bSplineChangedEventPublishSubject;

        anchorPointsPublishSubject.subscribe(ap -> {
            if (ap != anchorPoints) {
                anchorPoints.clear();
                anchorPoints.addAll(ap);
                recountBSplinePoints();
            }
        });

        nPublishSubject.subscribe(this::setSegmentCount);
    }

    private void setSegmentCount(int segmentCount) {
        this.segmentCount = segmentCount;
        recountBSplinePoints();
    }

    public void addAnchorPoint(Point point) {
        anchorPoints.add(point);
        recountBSplinePoints();
    }

    public void removeLastAnchorPoint() {
        anchorPoints.remove(anchorPoints.size() - 1);
        recountBSplinePoints();
    }

    public int getPointIDByCoords(Point targetPoint, int r) {
        for (int i = anchorPoints.size() - 1; i >= 0; --i) {
            Point p = anchorPoints.get(i);
            if ((p.x - targetPoint.x) * (p.x - targetPoint.x) + (p.y - targetPoint.y) * (p.y - targetPoint.y) < r * r) {
                return i;
            }
        }
        return -1;
    }

    public void addToAnchorPoint(int index, int diffX, int diffY) {
        Point p = anchorPoints.get(index);
        p.x += diffX;
        p.y += diffY;
        recountBSplinePoints();
    }

    public void removeAnchorPoint(Point targetPoint, int r) {
        int i = getPointIDByCoords(targetPoint, r);
        if (i != -1) {
            anchorPoints.remove(i);
            recountBSplinePoints();
        }
    }

    public boolean canBuild() {
        return anchorPoints.size() >= 4;
    }

    public void recountBSplinePoints() {
        if (!canBuild()) {
            return;
        }

        bSplinePoints.clear();

        for (int i = 1; i < anchorPoints.size() - 2; ++i) {
            SimpleMatrix gx = new SimpleMatrix(new double[] {
                    anchorPoints.get(i - 1).x,
                    anchorPoints.get(i).x,
                    anchorPoints.get(i + 1).x,
                    anchorPoints.get(i + 2).x
            });

            SimpleMatrix gy = new SimpleMatrix(new double[] {
                    anchorPoints.get(i - 1).y,
                    anchorPoints.get(i).y,
                    anchorPoints.get(i + 1).y,
                    anchorPoints.get(i + 2).y
            });

            SimpleMatrix mgx = m.mult(gx);
            SimpleMatrix mgy = m.mult(gy);

            double segmentStep = 1. / segmentCount;
            for (int j = 0; j < segmentCount + 1; ++j) {
                double t = j * segmentStep;
                SimpleMatrix tVector = new SimpleMatrix(new double[][]{
                        { t * t * t, t * t, t, 1, },
                });
                double x = tVector.mult(mgx).get(0);
                double y = tVector.mult(mgy).get(0);

                bSplinePoints.add(new Point((int) x, (int) y));
            }
        }

        bSplineChangedEventPublishSubject.onNext(new BSplineChangedEvent());
    }
}
