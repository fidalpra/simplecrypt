package com.davidebellettini.crypt.core;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Provider Initializer
 * 
 * @author Davide Bellettini <davide.bellettini@studio.unibo.it>
 *
 */
public class ProviderInitializer {

	public static void loadProvider() {
		Security.addProvider(new BouncyCastleProvider());
	}

}
