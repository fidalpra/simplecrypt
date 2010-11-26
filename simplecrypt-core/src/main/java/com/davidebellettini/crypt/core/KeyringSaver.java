package com.davidebellettini.crypt.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class KeyringSaver {

	public static void save(Keyring keyring, File keyringFile,
			char[] passphrase, byte[] salt) throws IOException {
		ObjectOutputStream os = null;

		try {
			Cipher cipher = getCipher(passphrase, salt, Cipher.ENCRYPT_MODE);

			os = new ObjectOutputStream(new CipherOutputStream(
					new FileOutputStream(keyringFile), cipher));

			os.writeObject(keyring);
		} finally {
			if (os != null)
				os.close();
		}
	}

	public static Keyring load(File keyringFile, char[] passphrase, byte[] salt)
			throws IOException {

		ObjectInputStream is = null;

		try {
			Cipher cipher = getCipher(passphrase, salt, Cipher.DECRYPT_MODE);

			is = new ObjectInputStream(new CipherInputStream(
					new FileInputStream(keyringFile), cipher));

			try {
				Object obj = is.readObject();
				return (Keyring) obj;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		finally {
			if (is != null)
				is.close();
		}

		return null;
	}

	private static Cipher getCipher(char[] password, byte[] salt, int opmode) {
		try {
			Cipher cipher = Cipher
					.getInstance("PBEWithSHA256And256BitAES-CBC-BC");

			PBEKeySpec pbeKeySpec = new PBEKeySpec(password);
			SecretKeyFactory keyFac = SecretKeyFactory
					.getInstance("PBEWithSHA256And256BitAES-CBC-BC");
			SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

			PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 20);

			cipher.init(opmode, pbeKey, pbeParamSpec);

			return cipher;
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

}
