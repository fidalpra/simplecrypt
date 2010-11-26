package com.davidebellettini.crypt.core;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.SecureRandom;

import org.junit.BeforeClass;
import org.junit.Test;

public class CipherFacadeTest {
	@BeforeClass
	public static void setUpClass() {
		ProviderInitializer.loadProvider();
	}

	@Test
	public void test1() throws IOException, ClassNotFoundException {
		byte[] salt = new byte[8];
		new SecureRandom().nextBytes(salt);

		File keyringFile = new File("target/keyring.test");
		keyringFile.delete();

		CipherFacade facade = CipherFacade.factory(keyringFile,
				"99BottlesOfBeer".toCharArray(), salt);

		File encryptedFile = new File("target/example.sc");
		File originalFile = new File("example.txt");

		facade.encryptFile(originalFile, encryptedFile, "AES");

		File decryptedFile = new File("target/decripted.txt");
		facade.decryptFile(encryptedFile, decryptedFile);

		byte[] original = new byte[(int) originalFile.length()];
		byte[] decrypted = new byte[(int) decryptedFile.length()];

		FileInputStream fis = new FileInputStream(originalFile);
		fis.read(original);
		fis.close();

		fis = new FileInputStream(decryptedFile);
		fis.read(decrypted);
		fis.close();

		assertArrayEquals(original, decrypted);
	}

}
