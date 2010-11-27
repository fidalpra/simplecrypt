package com.davidebellettini.crypt.core;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

public class CipherFacadeTest {
	@BeforeClass
	public static void setUpClass() {
		ProviderInitializer.loadProvider();
	}

	@Test
	public void test1() throws IOException, ClassNotFoundException {
		File keyringFile = new File("target/keyring.test");
		keyringFile.delete();

		CipherFacade facade = CipherFacade.factory(keyringFile,
				"99BottlesOfBeer".toCharArray());
		
		assertTrue(facade.isNewFile());

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
