package com.davidebellettini.crypt.core;

import static org.junit.Assert.*;

import java.io.File;
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
		facade.encryptFile(new File("example.txt"),
				encryptedFile, "AES");
		
		facade.decryptFile(new File("target/example.sc"), new File("target/decripted.txt"));
	}

}
