package com.davidebellettini.crypt.gui;

import org.jdesktop.application.Action;

public class MainController {
	private final SimpleCryptApp application;

	public MainController(SimpleCryptApp application) {
		this.application = application;
	}

	public SimpleCryptApp getApplication() {
		return this.application;
	}

	@Action
	public void exit() {
		getApplication().exit();
	}
}
