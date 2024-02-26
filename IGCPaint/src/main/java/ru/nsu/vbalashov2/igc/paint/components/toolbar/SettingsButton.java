package ru.nsu.vbalashov2.igc.paint.components.toolbar;

import com.google.common.eventbus.EventBus;
import ru.nsu.vbalashov2.igc.paint.components.options.OptionsPanel;

import javax.swing.*;

public class SettingsButton extends JButton {
    public SettingsButton(ImageIcon settingsImageIcon, EventBus eventBus) {
        OptionsPanel optionsPanel = new OptionsPanel(eventBus);

        setIcon(settingsImageIcon);
        setToolTipText("Open settings");
        addActionListener((event) -> JOptionPane.showMessageDialog(
                this,
                optionsPanel,
                "Options",
                JOptionPane.PLAIN_MESSAGE
        ));
    }
}
