package com.davidebellettini.crypt.core;

import static org.junit.Assert.assertEquals;

import java.security.Security;

import org.junit.Test;

public class InitializerTest {

	@Test
	public void testProvider() {
		int length = Security.getProviders().length;
		
		Initializer.loadProvider();
		
		assertEquals(length+1, Security.getProviders().length);
	}
}
