package com.davidebellettini.crypt.core;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Davide Bellettini <davide.bellettini@studio.unibo.it>
 * 
 */
public class KeyringSaverTest {

	@BeforeClass
	public static void setUpClass() {
		ProviderInitializer.loadProvider();
	}

	@Test
	public void testSaveLoad() throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		Keyring keyring = new Keyring();

		keyring.put("hello", new EquatableSecretKey("pirla".getBytes()));

		char[] passphrase = "99BottlesOfBeer".toCharArray();

		byte salt[] = generateSalt();
		File keyringFile = new File("target/simplecrypt.kr");

		KeyringSaver.save(keyring, keyringFile, passphrase, salt);

		Keyring loaded = KeyringSaver.load(keyringFile, passphrase);

		assertEquals(keyring, loaded);
	}

	private static byte[] generateSalt() throws NoSuchAlgorithmException {
		byte salt[] = new byte[8];
		SecureRandom saltGen = SecureRandom.getInstance("SHA1PRNG");
		saltGen.nextBytes(salt);
		return salt;
	}

}
