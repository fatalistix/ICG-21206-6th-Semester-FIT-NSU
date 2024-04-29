package ru.nsu.vbalashov2.icg.wireframe.tools;

import org.ejml.simple.SimpleMatrix;

public class Vector4 {

    public double x;
    public double y;
    public double z;
    public double w;

    public Vector4(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public double w() {
        return w;
    }

    public SimpleMatrix toSimpleMatrix() {
        return new SimpleMatrix(new double[][] {
                { x },
                { y },
                { z },
                { w },
        });
    }

    public static Vector4 fromSimpleMatrix(SimpleMatrix matrix) {
        return new Vector4(matrix.get(0, 0), matrix.get(1, 0), matrix.get(2, 0), matrix.get(3, 0));
    }

    public Point3 toPoint3() {
        return new Point3(x, y, z);
    }
}
