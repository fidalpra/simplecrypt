/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davidebellettini.crypt.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.jdesktop.application.FrameView;

/**
 * 
 * @author davide
 */
public class MainView extends FrameView {

	private JMenuBar menuBar;
	private MainPanel panel;
	private JMenuItem fileMenu;

	public MainView(SimpleCryptApp application) {
		super(application);

		initComponents();
	}

	private void initComponents() {
		this.panel = new MainPanel();

		getFrame().setContentPane(this.panel);

		initMenuBar();
	}

	private void initMenuBar() {
		this.menuBar = new JMenuBar();
		this.fileMenu = new JMenu("File");
		this.menuBar.add(this.fileMenu);
		
		setMenuBar(this.menuBar);
	}
}
