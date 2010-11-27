package com.davidebellettini.crypt.core;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;

import org.junit.BeforeClass;
import org.junit.Test;

public class ObjectCipherSaverTest {

	@BeforeClass
	public static void setUpClass() {
		ProviderInitializer.loadProvider();
	}

	@Test
	public void testObjectSaver() throws Exception {
		String data = "Goofy";
		File file = new File("target/object.test");
		SecretKeySpec key = getRandomKey();
		Cipher encCipher = getCipher(key, Cipher.ENCRYPT_MODE,"AES");

		ObjectCipherSaver.save(data, file, "DonaldDuck", encCipher);
		
		Cipher decCipher = getCipher(key, Cipher.DECRYPT_MODE, "AES");
		
		EncryptedObject loadedData = ObjectCipherSaver.load(file);
		assertEquals("DonaldDuck", loadedData.getKeyId());
		assertEquals(data, ((SealedObject)loadedData.getData()).getObject(decCipher));
	}

	private SecretKeySpec getRandomKey() throws NoSuchAlgorithmException {
		byte[] key = new byte[256 / 8];

		SecureRandom.getInstance("SHA1PRNG").nextBytes(key);

		return new SecretKeySpec(key, "AES");
	}

	private Cipher getCipher(SecretKeySpec key, int opmode, String algorithm)
			throws NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, InvalidKeyException {
		Cipher cipher = Cipher.getInstance(algorithm, "BC");

		cipher.init(opmode, key);

		return cipher;
	}
}
