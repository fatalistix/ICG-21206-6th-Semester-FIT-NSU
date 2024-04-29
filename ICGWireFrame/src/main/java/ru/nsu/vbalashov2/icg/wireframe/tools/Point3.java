package ru.nsu.vbalashov2.icg.wireframe.tools;

public record Point3(double x, double y, double z) {

    public Point3 fromVector4(Vector4 vector4) {
        return new Point3(vector4.x(), vector4.y(), vector4.z());
    }
}
