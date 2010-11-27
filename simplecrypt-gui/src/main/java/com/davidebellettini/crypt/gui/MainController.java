package com.davidebellettini.crypt.gui;

import java.awt.Component;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.jdesktop.application.Action;

public class MainController {
    private final SimpleCryptApp application;
    private MainView mainView;

    public MainController(SimpleCryptApp application) {
        this.application = application;
        application.show(this.mainView = new MainView(application, application
                .getContext().getActionMap(this)));
    }

    public SimpleCryptApp getApplication() {
        return this.application;
    }

    @Action
    public void exit() {
        getApplication().exit();
    }

    @Action
    public void encrypt() {
        File[] files = selectSourceAndDest(mainView.getFrame());
        
        com.davidebellettini.crypt.core.CipherFacade x;
    }

    @Action
    public void decrypt() {
        File[] files = selectSourceAndDest(mainView.getFrame());

    }

    private File[] selectSourceAndDest(Component component) {
        File[] files = new File[2];

        JFileChooser chooser = new JFileChooser();
        int option = chooser.showOpenDialog(component);
        if (option == JFileChooser.APPROVE_OPTION) {
            files[0] = chooser.getSelectedFile();

            option = chooser.showSaveDialog(component);

            if (option == JFileChooser.APPROVE_OPTION) {
                files[1] = chooser.getSelectedFile();
            }

        } else {
            return null;
        }

        return files;
    }
}
