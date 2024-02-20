package ru.nsu.vbalashov2.igc.paint.components.toolbar;

import com.google.common.eventbus.EventBus;
import ru.nsu.vbalashov2.igc.paint.components.toolbar.*;
import ru.nsu.vbalashov2.igc.paint.tools.PaintTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PaintToolBar extends JToolBar {
    private static final String lineIconLocation = "icons/line.png";
    private static final String squareIconLocation = "icons/square.png";
    private static final String fillIconLocation = "icons/fill.png";
    private static final String clearIconLocation = "icons/clear.png";
    private static final String settingsIconLocation = "icons/settings.png";
    private static final String colorChooserIconLocation = "icons/color_palette.png";

    private static final String blueColorIconLocation = "icons/colors/blue.png";
    private static final String deepBlueColorIconLocation = "icons/colors/deep_blue.png";
    private static final String greenColorIconLocation = "icons/colors/green.png";
    private static final String redColorIconLocation = "icons/colors/red.png";
    private static final String violetColorIconLocation = "icons/colors/violet.png";
    private static final String whiteColorIconLocation = "icons/colors/white.png";
    private static final String yellowColorIconLocation = "icons/colors/yellow.png";

    private static final int iconSize = 16;

    public PaintToolBar(Color initialColor, EventBus eventBus) throws IOException {
        ImageIcon lineImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(lineIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon squareImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(squareIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon fillImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(fillIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon clearImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(clearIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon settingsImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(settingsIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        ImageIcon colorChooserImageIcon = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource(colorChooserIconLocation)).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));

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

        this.add(new ToolButton(lineImageIcon, PaintTool.LINE, eventBus));

        JButton squareButton = new JButton();
        squareButton.setIcon(squareImageIcon);
        this.add(squareButton);

        this.add(new ToolButton(fillImageIcon, PaintTool.FILL, eventBus));

        this.addSeparator();

        this.add(new ClearButton(clearImageIcon, eventBus));

        this.addSeparator();

        this.add(new ColorChooserButton(colorChooserImageIcon, initialColor, eventBus));

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
