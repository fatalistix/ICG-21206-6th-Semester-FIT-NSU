package ru.nsu.vbalashov2.icg.wireframe.components.menubar.aboutmenu;

import javax.swing.*;

public class AboutMenuItem extends JMenuItem {

    public AboutMenuItem() {
        super("About");

        addActionListener(event -> JOptionPane.showMessageDialog(
                this,
                new JLabel("""
                        <html>
                            <h1>ICGWireFrame</h1>
                            <div>
                                Wireframe application that forms b-spline curve from anchor points and then translates it to wireframe model.
                            </div>
                            <h2>B-spline editing</h2>
                            <div>
                                B-spline editing is done in dynamic mode: all changed in b-spline editor are shown in wireframe window at the same time.
                            </div>
                            <div>
                                In b-spline editor you can add new anchor points and edit them:
                                <ul>
                                    <li>Move center of axes</li>
                                    <li>Zoom b-spline</li>
                                    <li>Delete anchor point in any place</li>
                                </ul>
                            </div>
                            <div>
                                Also you can affect on wireframe model by changing parameters in options panel below: <i>n, m, m1</i>.
                            </div>
                            <div>
                                To apply or revert changes there are 3 buttons below options panel: Ok, Apply and Cancel.
                                <ul>
                                    <li><b>Ok</b> - save changes and close editing window</li>
                                    <li><b>Apply</b> - save changes without closing editing window</li>
                                    <li><b>Cancel</b> - undo all changes in editor</li>
                                </ul>
                            </div>
                            <h2>Wireframe editing</h2>
                            <div>
                            In wireframe window you can rotate wireframe model, save model to file or open file with model.
                            </div>
                            <h2>Author</h2>
                            <div>
                                <i>Developed by Vyacheslav Balashov, 2024.</i>
                            </div>
                        </html>
                        """)
        ));
    }
}
