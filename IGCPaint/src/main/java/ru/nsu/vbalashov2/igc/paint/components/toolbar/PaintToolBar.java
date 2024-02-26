package ru.nsu.vbalashov2.igc.paint.components.toolbar;

import com.google.common.eventbus.EventBus;
import ru.nsu.vbalashov2.igc.paint.tools.PaintTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PaintToolBar extends JToolBar {
    private static final String clearIconLocation = "icons/tools/clear.png";
    private static final String colorChooserIconLocation = "icons/tools/color_palette.png";
    private static final String fillIconLocation = "icons/tools/fill.png";
    private static final String lineIconLocation = "icons/tools/line.png";
    private static final String polygonIconLocation = "icons/tools/polygon.png";
    private static final String settingsIconLocation = "icons/tools/settings.png";
    private static final String squareIconLocation = "icons/tools/square.png";
    private static final String starIconLocation = "icons/tools/star.png";
    private static final String undoIconLocation = "icons/tools/undo.png";

    private static final String blueColorIconLocation = "icons/colors/blue.png";
    private static final String deepBlueColorIconLocation = "icons/colors/deep_blue.png";
    private static final String greenColorIconLocation = "icons/colors/green.png";
    private static final String redColorIconLocation = "icons/colors/red.png";
    private static final String violetColorIconLocation = "icons/colors/violet.png";
    private static final String whiteColorIconLocation = "icons/colors/white.png";
    private static final String yellowColorIconLocation = "icons/colors/yellow.png";

    private static final int iconSize = 16;

    public PaintToolBar(EventBus eventBus) throws IOException {
        ImageIcon clearImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(clearIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon colorChooserImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(colorChooserIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon fillImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(fillIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon lineImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(lineIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon polygonImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(polygonIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon settingsImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(settingsIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon squareImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(squareIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon starImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(starIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon undoImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(undoIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));

        ImageIcon blueColorIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(blueColorIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon deepBlueColorIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(deepBlueColorIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon greenColorIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(greenColorIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon redColorIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(redColorIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon violetColorIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(violetColorIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon whiteColorIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(whiteColorIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon yellowColorIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(yellowColorIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));

        this.setFloatable(false);

        this.add(new SettingsButton(settingsImageIcon, eventBus));

        this.addSeparator();

        this.add(new ToolToggleButton(fillImageIcon, PaintTool.FILL, eventBus, "Fill area"));
        this.add(new ToolToggleButton(lineImageIcon, PaintTool.LINE, eventBus, "Draw a line using 2 clicks"));
        this.add(new ToolToggleButton(polygonImageIcon, PaintTool.POLYGON, eventBus, "Draw a polygon"));
        this.add(new ToolToggleButton(starImageIcon, PaintTool.STAR, eventBus, "Draw a star"));

        this.addSeparator();

        this.add(new ClearButton(clearImageIcon, eventBus));

        this.addSeparator();

        this.add(new UndoButton(undoImageIcon, eventBus));

        this.addSeparator();

        this.add(new ColorChooserButton(colorChooserImageIcon, eventBus));

        this.addSeparator();

        this.add(new ColorButton(redColorIcon, Color.RED, eventBus));
        this.add(new ColorButton(yellowColorIcon, Color.YELLOW, eventBus));
        this.add(new ColorButton(greenColorIcon, Color.GREEN, eventBus));
        this.add(new ColorButton(blueColorIcon, Color.BLUE, eventBus));
        this.add(new ColorButton(deepBlueColorIcon, new Color(0, 66, 110), eventBus));
        this.add(new ColorButton(violetColorIcon, new Color(139, 0, 255), eventBus));
        this.add(new ColorButton(whiteColorIcon, Color.WHITE, eventBus));
    }
}
