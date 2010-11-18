/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davidebellettini.crypt.gui;

import javax.swing.JMenuBar;
import org.jdesktop.application.FrameView;

/**
 *
 * @author davide
 */
public class MainView extends FrameView {

    private MainFrame mainFrame;

    public MainView(SimpleCryptApp application) {
        super(application);

        initComponents();
    }

    private void initComponents() {
        this.mainFrame = new MainFrame();

        setFrame(mainFrame);
    }
}
