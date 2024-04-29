package ru.nsu.vbalashov2.icg.wireframe.tools;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.io.File;

public class FileSaverLoader {

    ObjectMapper objectMapper = new ObjectMapper();

    private record AnchorPointsHolder(List<Point> anchorPoints) {
    }

    public void saveFile(File file, List<Point> anchorPoints) throws IOException {
        objectMapper.writeValue(file, new AnchorPointsHolder(anchorPoints));
    }

    public List<Point> loadFile(File file) throws IOException {
        AnchorPointsHolder anchorPointsHolder = objectMapper.readValue(file, AnchorPointsHolder.class);
        return anchorPointsHolder.anchorPoints;
    }
}
