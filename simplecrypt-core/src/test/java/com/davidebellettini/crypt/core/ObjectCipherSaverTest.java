package com.davidebellettini.crypt.core;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.junit.BeforeClass;
import org.junit.Test;

public class ObjectCipherSaverTest {

	@BeforeClass
	public static void setUpClass() {
		ProviderInitializer.loadProvider();
	}

	@Test
	public void testObjectSaver() throws NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException, IOException,
			InvalidKeyException, ClassNotFoundException {
		String data = "Goofy";
		File file = new File("target/object.test");
		SecretKeySpec key = getRandomKey();
		Cipher encCipher = getCipher(key, Cipher.ENCRYPT_MODE);

		ObjectCipherSaver.save(data, file, encCipher);
		
		Cipher decCipher = getCipher(key, Cipher.DECRYPT_MODE);
		
		assertEquals(data, ObjectCipherSaver.load(file, decCipher));
	}

	private SecretKeySpec getRandomKey() throws NoSuchAlgorithmException {
		byte[] key = new byte[256 / 8];

		SecureRandom.getInstance("SHA1PRNG").nextBytes(key);

		return new SecretKeySpec(key, "AES");
	}

	private Cipher getCipher(SecretKeySpec key, int opmode)
			throws NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, InvalidKeyException {
		Cipher cipher = Cipher.getInstance("AES", "BC");

		cipher.init(opmode, key);

		return cipher;
	}
}
