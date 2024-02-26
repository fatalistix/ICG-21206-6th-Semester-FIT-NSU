package ru.nsu.vbalashov2.igc.paint.components.menubar.aboutmenu;

import javax.swing.*;

public class AboutMenuItem extends JMenuItem {

    public AboutMenuItem() {
        super("About");

        addActionListener((event) ->
                JOptionPane.showMessageDialog(
                        this,
                        new JLabel("""
                                <html>
                                    <h1>IGCPaint<h1>
                                    <div>
                                        Paint utility that has 4 instruments for modification image:
                                        <ul>
                                            <li>1. Fill area (Bucket icon) - fills area of same color pixels, surrounded by other color pixels.</li>
                                            <li>2. Line - draws a line with specified thickness. To draw line pick instrument, than click 2 times to area. Your click will specify line's ends.</li>
                                            <li>3. Polygon - draws a regular polygon with specified thickness, rotation angle, radius and number of angles.</li>
                                            <li>4. Star - draws a regular star with specified thickness, rotation angle, radius and number of angles.</li>
                                        </ul>
                                        There are also some addition options:
                                        <ul>
                                            <li>Clear (eraser icon) - clears whole area.</li>
                                            <li>Undo (rollback arrow) - undo last changes.</li>
                                            <li>Fast colors - select one of colors placed on a toolbar.</li>
                                            <li>Color picker (palette) - more detailed color chooser.</li>
                                        </ul>
                                    </div>
                                    <div>
                                        Developed by Vyacheslav Balashov, 2024
                                    </div>
                                </html>
                                """)
                )
        );
    }
}
