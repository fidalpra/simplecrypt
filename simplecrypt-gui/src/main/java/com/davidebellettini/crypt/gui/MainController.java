package com.davidebellettini.crypt.gui;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jdesktop.application.Action;

import com.davidebellettini.crypt.core.CipherFacade;
import com.davidebellettini.crypt.gui.conf.ConfigurationFactory;

public class MainController {
    private final SimpleCryptApp application;
    private MainView mainView;
    private CipherFacade facade;

    public MainController(SimpleCryptApp application) {
        this.application = application;
        application.show(this.mainView = new MainView(application, application
                .getContext().getActionMap(this)));
        
        //getCipherFacade();
    }

    public SimpleCryptApp getApplication() {
        return this.application;
    }

    @Action
    public void exit() {
        getApplication().exit();
    }

    @Action
    public void encrypt() throws IOException {
        File[] files = selectSourceAndDest(mainView.getFrame());

        boolean ok = false;

        while (!ok) {
            try {
                getCipherFacade().encryptFile(files[0], files[1], "AES");
                ok = true;
            } catch (RuntimeException e) {
                this.facade = null;
            }
        }
    }

    @Action
    public void decrypt() throws IOException, ClassNotFoundException {
        File[] files = selectSourceAndDest(mainView.getFrame());

        boolean ok = false;

        while (!ok) {
            try {
                getCipherFacade().decryptFile(files[0], files[1]);
                ok = true;
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(getApplication().getMainFrame(),
                        "Chiave non presente nel portachiavi!");
                ok = true;
            } catch (RuntimeException e) {
                this.facade = null;
            }
        }
    }

    private File[] selectSourceAndDest(Component component) {
        File[] files = new File[2];

        JFileChooser chooser = new JFileChooser();
        int option = chooser.showOpenDialog(component);
        if (option == JFileChooser.APPROVE_OPTION) {
            files[0] = chooser.getSelectedFile();

            chooser = new JFileChooser();
            option = chooser.showSaveDialog(component);

            if (option == JFileChooser.APPROVE_OPTION) {
                files[1] = chooser.getSelectedFile();
            }

        } else {
            return null;
        }

        return files;
    }

    private CipherFacade getCipherFacade() {
        while (this.facade == null) {
            char[] passphrase;

            this.facade = CipherFacade.factory(getKeyringFile(),
                    passphrase = askForPassphrase());

            if (this.facade.isNewFile()) {
                if (!Arrays.equals(askForPassphrase(true), passphrase)) {
                    this.facade = null;
                }
            }
        }

        return this.facade;
    }

    private File getKeyringFile() {
        File file;
        file = new File(ConfigurationFactory.getConfiguration().get(
                "keyringFile"));
        return file;
    }

    private char[] askForPassphrase(boolean secondTime) {
        String response = JOptionPane
                .showInputDialog(getApplication().getMainFrame(), "Passphrase"
                        + (secondTime ? " (ripeti)" : ""));

        if (response == null)
            getApplication().exit();

        return response.toCharArray();
    }

    private char[] askForPassphrase() {
        return askForPassphrase(false);
    }
}
