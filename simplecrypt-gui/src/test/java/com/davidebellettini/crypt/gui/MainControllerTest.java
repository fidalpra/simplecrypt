package com.davidebellettini.crypt.gui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {

	@Mock
	private SimpleCryptApp application;
	private MainController controller;

	@Before
	public void setUp() {
		this.controller = new MainController(application);
	}

	@Test
	public void testGetApplication() {
		assertEquals(application, controller.getApplication());
	}
}
