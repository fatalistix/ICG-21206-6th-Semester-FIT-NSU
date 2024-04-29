package ru.nsu.vbalashov2.icg.wireframe;

public class Main {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.enableAccelerated2D", "true");
        new WireframeFrame().start();
    }
}