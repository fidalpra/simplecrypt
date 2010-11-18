package com.davidebellettini.crypt.gui;

import org.jdesktop.application.SingleFrameApplication;

public class SimpleCryptApp extends SingleFrameApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(SimpleCryptApp.class, args);
	}

	private MainView mainView;

	@Override
	protected void startup() {
		this.mainView = new MainView(this);
		
		show(this.mainView);
	}

}
