package com.davidebellettini.crypt.core;

import static org.junit.Assert.assertEquals;

import java.security.Security;

import org.junit.Test;

public class ProviderInitializerTest {

	@Test
	public void testProvider() {
		int length = Security.getProviders().length;
		
		ProviderInitializer.loadProvider();
		
		assertEquals(length+1, Security.getProviders().length);
	}
}
