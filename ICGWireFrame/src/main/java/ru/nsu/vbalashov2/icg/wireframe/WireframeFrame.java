package ru.nsu.vbalashov2.icg.wireframe;

import ru.nsu.vbalashov2.icg.wireframe.components.dialogs.Dialogs;
import ru.nsu.vbalashov2.icg.wireframe.components.menubar.WireframeMenuBar;
import ru.nsu.vbalashov2.icg.wireframe.components.wireframe.WireframePanel;
import ru.nsu.vbalashov2.icg.wireframe.tools.FileSaverLoader;
import ru.nsu.vbalashov2.icg.wireframe.tools.events.EventPublishSubjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

public class WireframeFrame extends JFrame {

    private List<Point> anchorPoints = List.of();

    public WireframeFrame() {
        super("ICGWireFrame");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(640, 480));
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Dialogs.showExitDialog();
            }
        });

        // Creating all main publishSubjects (like event bus) in one class with getters
        EventPublishSubjects eventPublishSubjects = new EventPublishSubjects();

        // Frame listens that event for saving b-spline anchor points when needed
        eventPublishSubjects.getBSplineAnchorPointsEventPublishSubject().subscribe(points -> anchorPoints = points);

        setJMenuBar(new WireframeMenuBar(eventPublishSubjects));

        WireframePanel wireframePanel = new WireframePanel(
                eventPublishSubjects.getBSplineAnchorPointsEventPublishSubject(),
                eventPublishSubjects.getBSplineNPublishSubject(),
                eventPublishSubjects.getBSplineMPublishSubject(),
                eventPublishSubjects.getBSplineM1PublishSubject(),
                eventPublishSubjects.getResetWireframeRotationEventPublishSubject()
        );

        getContentPane().add(wireframePanel);

        FileSaverLoader fileSaverLoader = new FileSaverLoader();

        eventPublishSubjects.getSaveFilePublishSubject().subscribe(file -> {
            try {
                fileSaverLoader.saveFile(file, anchorPoints);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error while saving file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        eventPublishSubjects.getLoadFilePublishSubject().subscribe(file -> {
            try {
                List<Point> anchorPoints = fileSaverLoader.loadFile(file);
                // after loading file, frame notifies all listeners on changing anchor points that now they need to
                // display that points
                eventPublishSubjects.getBSplineAnchorPointsEventPublishSubject().onNext(anchorPoints);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error while loading file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void start() {
        setVisible(true);
    }
}
