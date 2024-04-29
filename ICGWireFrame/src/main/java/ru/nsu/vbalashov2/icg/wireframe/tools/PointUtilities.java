package ru.nsu.vbalashov2.icg.wireframe.tools;

import java.awt.*;
import java.util.List;

public class PointUtilities {

    public static boolean pointListsEqual(List<Point> a, List<Point> b) {
        if (a.size() == b.size()) {
            for (int i = 0; i < a.size(); ++i) {
                Point ap = a.get(i);
                Point bp = b.get(i);
                if (ap.x != bp.x || ap.y != bp.y) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
