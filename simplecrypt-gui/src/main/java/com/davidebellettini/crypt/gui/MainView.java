/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davidebellettini.crypt.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.FrameView;

/**
 * 
 * @author davide
 */
public class MainView extends FrameView {

    private JMenuBar menuBar;
    private MainPanel panel;
    private JMenuItem fileMenu;
    private JMenuItem fileExitMenuItem;
    private ApplicationActionMap actionMap;
    private JMenuItem fileCryptMenuItem;
    private JMenuItem fileDecryptMenuItem;

    public MainView(SimpleCryptApp application, ApplicationActionMap actionMap) {
        super(application);

        this.actionMap = actionMap;

        initComponents();
    }

    private void initComponents() {
        this.panel = new MainPanel();

        getFrame().setContentPane(this.panel);

        initMenuBar();
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");

        fileCryptMenuItem = new JMenuItem();
        fileCryptMenuItem.setAction(actionMap.get("encrypt"));
        fileCryptMenuItem.setText("Cifra...");

        fileMenu.add(fileCryptMenuItem);

        fileDecryptMenuItem = new JMenuItem();
        fileDecryptMenuItem.setAction(actionMap.get("decrypt"));
        fileDecryptMenuItem.setText("Decifra...");

        fileMenu.add(fileDecryptMenuItem);

        fileMenu.add(new JSeparator());

        fileExitMenuItem = new JMenuItem();
        fileExitMenuItem.setAction(actionMap.get("exit"));
        fileExitMenuItem.setText("Esci");

        fileMenu.add(fileExitMenuItem);

        this.menuBar.add(this.fileMenu);

        setMenuBar(this.menuBar);
    }
}
