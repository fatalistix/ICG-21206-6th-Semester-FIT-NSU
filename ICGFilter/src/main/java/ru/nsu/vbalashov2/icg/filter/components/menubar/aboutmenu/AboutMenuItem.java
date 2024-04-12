package ru.nsu.vbalashov2.icg.filter.components.menubar.aboutmenu;

import javax.swing.*;

public class AboutMenuItem extends JMenuItem {

    public AboutMenuItem() {
        super("About");

        addActionListener(event ->
                JOptionPane.showMessageDialog(
                this,
                new JLabel("""
                        <html>
                            <h1>ICGFilter</h1>
                            <div>
                                Filter application that takes an original image and transforms it. There are some available filters from left to right according to toolbar:
                                <ul>
                                    <li>1. Black-white filter - converts an image into a grey shades.</li>
                                    <li>2. Inversion filter - inverts colors on whole image.</li>
                                    <li>3. Gaussian blur - blurs an image according to gauss distribution function.</li>
                                    <li>4. Median blur - blurs an image according to median value of pixels' colors inside specified window.</li>
                                    <li>5. Increasing sharpness - increases a difference between brightest and darkest color.</li>
                                    <li>6. Embossing - makes image filling "material".</li>
                                    <li>7. Gamma correction - changes image's colors intensity curve.</li>
                                    <li>8. Roberts operator - highlights contours of an image, making an image black and white.</li>
                                    <li>9. Sobel operator - same as Roberts operator, but has another matrix is applied.</li>
                                    <li>10. Floyd Steinberg dither - cuts image's palette using error diffusion algorithm.</li>
                                    <li>11. Ordered dither - cuts image's palette, but uses algorithm without error diffusion.</li>
                                    <li>12. Watercolorization - makes image feel like it was colored with water colors.</li>
                                    <li>13. Bilateral - suppresses noises without losing any contours.</li>
                                </ul>
                                There are also some options for manipulating with images:
                                <ul>
                                    <li><b>Rotate</b> - rotate an image for specified degree.</li>
                                    <li><b>Fit to size</b> - makes an image to fit your viewport size.</li>
                                    <li><b>Real size</b> - makes an image map pixel to pixel.</li>
                                </ul>
                                Also you can choose interpolation type and quick switch between images (original and modified).
                                Click the leftest toolbar button with directory icon to open image and watch what can be done with it!
                            </div>
                            <div>
                                Developed by Vyacheslav Balashov, 2024.
                            </div>
                        </html>
                        """)
        ));
    }
}
