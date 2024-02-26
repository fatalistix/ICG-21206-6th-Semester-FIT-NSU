package ru.nsu.vbalashov2.igc.paint.tools;

import java.awt.image.Raster;
import java.util.ArrayDeque;

public class History {

    private static final int ARRAY_DEQUE_SIZE = 10;

    private final ArrayDeque<Raster> deque = new ArrayDeque<>(ARRAY_DEQUE_SIZE + 1);

    public void save(Raster raster) {
        deque.addLast(raster);

        if (deque.size() == ARRAY_DEQUE_SIZE + 1) {
            deque.removeFirst();
        }
    }

    public Raster getPrevious() {
        if (deque.isEmpty()) {
            return null;
        }
        return deque.removeLast();
    }
}
