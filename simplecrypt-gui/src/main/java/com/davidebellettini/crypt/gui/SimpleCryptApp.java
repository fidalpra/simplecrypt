package com.davidebellettini.crypt.gui;

import java.util.EventObject;

import javax.swing.JOptionPane;

import org.jdesktop.application.SingleFrameApplication;

public class SimpleCryptApp extends SingleFrameApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(SimpleCryptApp.class, args);
	}

	public SimpleCryptApp() {
		this.addExitListener(new ExitListener() {

			@Override
			public void willExit(EventObject arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean canExit(EventObject arg0) {
				int option = JOptionPane.showConfirmDialog(null,
						"Vuoi davvero uscire?", "Esci da SimpleCrypt", JOptionPane.YES_NO_OPTION);
				return option == JOptionPane.YES_OPTION;

			}
		});

	}

	@Override
	protected void startup() {
		new MainController(this);
	}

}
