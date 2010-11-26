package com.davidebellettini.crypt.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.Provider;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

public class ProviderInitializerTest {

	@Test
	public void testProvider() {
		Security.removeProvider("BC");

		assertFalse(findBCProvider());
		ProviderInitializer.loadProvider();
		assertTrue(findBCProvider());
	}

	private boolean findBCProvider() {
		for (Provider p : Security.getProviders()) {
			if (BouncyCastleProvider.class.equals(p.getClass())) {
				return true;
			}
		}

		return false;
	}
}
