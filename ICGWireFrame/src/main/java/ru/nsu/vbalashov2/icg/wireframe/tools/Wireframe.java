package ru.nsu.vbalashov2.icg.wireframe.tools;

import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.Getter;
import org.ejml.simple.SimpleMatrix;
import ru.nsu.vbalashov2.icg.wireframe.tools.events.BSplineChangedEvent;
import ru.nsu.vbalashov2.icg.wireframe.tools.events.WireframeChangedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Wireframe {

    private int m = 2;
    private int m1 = 1;
//    private double zn = 10.;
//    private double zoom = 1.;
    private double d = 10.;

    @Getter
    private final List<Line3> formingLines = new ArrayList<>();
    @Getter
    private final List<Line1> projectionLines = new ArrayList<>();

    private final BSpline bSpline;

    @Getter
    private SimpleMatrix rotationMatrix = createRotationMatrix(0, 0, 0);
    private SimpleMatrix perspectiveProjectionMatrix = createPerspectiveProjectionMatrix(d);
//    private SimpleMatrix zoomMatrix = createZoomMatrix(zoom);

    private final PublishSubject<WireframeChangedEvent> wireframeChangedEventPublishSubject;

    public Wireframe(
            PublishSubject<List<Point>> anchorPointsPublishSubject,
            PublishSubject<Integer> nPublishSubject,
            PublishSubject<Integer> mPublishSubject,
            PublishSubject<Integer> m1PublishSubject,
            PublishSubject<Double> znPublishSubject,
            PublishSubject<WireframeChangedEvent> wireframeChangedEventPublishSubject
    ) {
        this.wireframeChangedEventPublishSubject = wireframeChangedEventPublishSubject;

        PublishSubject<BSplineChangedEvent> bSplineChangedEventPublishSubject = PublishSubject.create();
        bSplineChangedEventPublishSubject.subscribe(bSpline -> recountFormingLines());

        bSpline = new BSpline(
                anchorPointsPublishSubject,
                nPublishSubject,
                bSplineChangedEventPublishSubject
        );

        mPublishSubject.subscribe(m -> {
            this.m = m;
            recountFormingLines();
        });
        m1PublishSubject.subscribe(m1 -> {
            this.m1 = m1;
            recountFormingLines();
        });
        znPublishSubject.subscribe(zn -> {
            perspectiveProjectionMatrix = createPerspectiveProjectionMatrix(zn);
            recountFormingLines();
        });

        bSpline.addAnchorPoint(new Point(-100, 0));
        bSpline.addAnchorPoint(new Point(-50, 100));
        bSpline.addAnchorPoint(new Point(50, 100));
        bSpline.addAnchorPoint(new Point(100, 0));
    }

    private static SimpleMatrix createPerspectiveProjectionMatrix(double d) {
        return new SimpleMatrix(new double[][]{
                {  1., 0., 0., 0., },
                {  0., 1., 0., 0., },
                {  0., 0., 1., 0., },
                {  0., 0.,  d, 0., },
        });
    }

    private static SimpleMatrix createZoomMatrix(double zn) {
        return new SimpleMatrix(new double[][]{
                {  zn, 0., 0.,  0., },
                {  0., zn, 0.,  0., },
                {  0., 0., 1.,  0., },
                {  0., 0., 0.,  1., },
        });
    }

    private static SimpleMatrix createRotationMatrix(double angleX, double angleY, double angleZ) {
        // A == alpha, B == beta, G == gamma
        double cosA = Math.cos(angleX);
        double cosB = Math.cos(angleY);
        double cosG = Math.cos(angleZ);

        double sinA = Math.sin(angleX);
        double sinB = Math.sin(angleY);
        double sinG = Math.sin(angleZ);

        return new SimpleMatrix(new double[][]{
                {                      cosB * cosG,                      -sinG * cosB,         sinB, 0., },
                { sinA * sinB * cosG + sinG * cosA, -sinA * sinB * sinG + cosA * cosG, -sinA * cosB, 0., },
                { sinA * sinG - sinB * cosA * cosG,  sinA * cosG + sinB * sinG * cosA,  cosA * cosB, 0., },
                {                               0.,                                0.,           0., 1., },
        });
    }

    public void resetRotationAngles() {
        rotationMatrix = createRotationMatrix(0, 0, 0);
        recountFormingLines();
    }

    public void addToRotationMatrix(int diffX, int diffY) {
        if (diffX == 0 && diffY == 0) {
            return;
        }

        // A == alpha, B == beta, G == gamma
        double angleX = Math.toRadians(diffY);
        double angleY = Math.toRadians(-diffX);
        double angleZ = 0;

        rotationMatrix = rotationMatrix.mult(createRotationMatrix(angleX, angleY, angleZ));
        recountFormingLines();
    }

    public void zoomIn() {
        d *= 1.1;
        perspectiveProjectionMatrix = createPerspectiveProjectionMatrix(d);
        recountFormingLines();
    }

    public void zoomOut() {
        d /= 1.1;
        perspectiveProjectionMatrix = createPerspectiveProjectionMatrix(d);
        recountFormingLines();
    }

    public boolean canBuild() {
        return bSpline.canBuild();
    }

    private void recountFormingLines() {
        formingLines.clear();
        projectionLines.clear();

        if (!canBuild()) {
            wireframeChangedEventPublishSubject.onNext(new WireframeChangedEvent());
            return;
        }

        double angle = 2 * Math.PI / m / m1;

        List<Vector4> wireframePoints = new ArrayList<>(bSpline.getBSplinePoints().size());
        List<Double> projectionPoints = new ArrayList<>(bSpline.getBSplinePoints().size());

        for (int j = 0; j < m * m1; ++j) {
            double currentAngle = j * angle;
            double currentAngleCos = Math.cos(currentAngle);
            double currentAngleSin = Math.sin(currentAngle);


            for (Point p : bSpline.getBSplinePoints()) {
                Vector4 pointVector4 = new Vector4(p.y * currentAngleCos, p.y * currentAngleSin, p.x, 1);

                wireframePoints.add(pointVector4);
            }
        }

        int bSplinePointsCount = bSpline.getBSplinePoints().size();

        scalePoints(wireframePoints);

        wireframePoints.replaceAll(vector4 -> Vector4.fromSimpleMatrix(rotationMatrix.mult(vector4.toSimpleMatrix())));

        projectionPoints.addAll(wireframePoints.stream().map(Vector4::z).toList());

        shiftPoints(wireframePoints, 0, 0, d);

        wireframePoints.replaceAll(vector4 -> {
            Vector4 result = Vector4.fromSimpleMatrix(
                    perspectiveProjectionMatrix.mult(vector4.toSimpleMatrix())
            );

            return new Vector4(result.x / result.w, result.y / result.w, result.z / result.w, 1);
        });

        // over all "main" forming b-splines
        for (int j = 0; j < m; ++j) {
            // over all b-splines points
            for (int i = 0; i < bSplinePointsCount - 1; ++i) {
                // j - current "main" b-spline
                // m1 - number of circle approximation steps
                int startIndex = j * bSplinePointsCount * m1 + i;
                int endIndex = j * bSplinePointsCount * m1 + i + 1;
                Vector4 start = wireframePoints.get(startIndex);
                Vector4 end = wireframePoints.get(endIndex);
                formingLines.add(new Line3(
                        start.toPoint3(),
                        end.toPoint3()
                ));
                projectionLines.add(new Line1(
                        projectionPoints.get(startIndex),
                        projectionPoints.get(endIndex)
                ));
            }
        }

        int n = bSpline.getSegmentCount();
        for (int j = 0; j < m * m1; ++j) {
            for (int i = 0; i < bSpline.getAnchorPoints().size() - 2; ++i) {
                int startIndex = j * bSplinePointsCount + i * n;
                int endIndex = ((j + 1) % (m * m1)) * bSplinePointsCount + i * n;
                Vector4 start = wireframePoints.get(startIndex);
                Vector4 end = wireframePoints.get(endIndex);
                formingLines.add(new Line3(
                        start.toPoint3(),
                        end.toPoint3()
                ));
                projectionLines.add(new Line1(
                        projectionPoints.get(startIndex),
                        projectionPoints.get(endIndex)
                ));
            }
        }

        wireframeChangedEventPublishSubject.onNext(new WireframeChangedEvent());
    }

    private static void scalePoints(List<Vector4> points) {
        double maxX = points.get(0).x();
        double maxY = points.get(0).y();
        double maxZ = points.get(0).z();

        double minX = points.get(0).x();
        double minY = points.get(0).y();
        double minZ = points.get(0).z();

        for (Vector4 c : points) {
            maxX = Math.max(maxX, c.x());
            maxY = Math.max(maxY, c.y());
            maxZ = Math.max(maxZ, c.z());

            minX = Math.min(minX, c.x());
            minY = Math.min(minY, c.y());
            minZ = Math.min(minZ, c.z());
        }

        double dx = maxX - minX;
        double dy = maxY - minY;
        double dz = maxZ - minZ;

        double shiftX = (minX + maxX) / 2;
        double shiftY = (minY + maxY) / 2;
        double shiftZ = (minZ + maxZ) / 2;

        final double res;
        // for making res effectively final
        double temp = Math.max(dx, Math.max(dy, dz));
        if (temp == 0) {
            res = 1.;
        } else {
            res = temp;
        }

        points.forEach(p -> {
            p.x -= shiftX;
            p.y -= shiftY;
            p.z -= shiftZ;
            p.x /= res;
            p.y /= res;
            p.z /= res;
        });
    }

    private static void shiftPoints(List<Vector4> points, double dx, double dy, double dz) {
        points.forEach(p -> {
                    p.x += dx;
                    p.y += dy;
                    p.z += dz;
                });
    }
}
