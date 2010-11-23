package com.davidebellettini.crypt.gui;

import org.jdesktop.application.Action;

public class MainController {
	private final SimpleCryptApp application;

	public MainController(SimpleCryptApp application) {
		this.application = application;
		application.show(new MainView(application, application.getContext()
				.getActionMap(this)));
	}

	public SimpleCryptApp getApplication() {
		return this.application;
	}

	@Action
	public void exit() {
		getApplication().exit();
	}
}
