package com.davidebellettini.crypt.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;

public class CipherFacade {

	private final File keyringFile;
	private final char[] passphrase;
	private final byte[] salt;

	public CipherFacade(File keyringFile, char[] passPhrase, byte[] salt) {
		this.keyringFile = keyringFile;
		this.passphrase = passPhrase;
		this.salt = salt;
	}

	public static CipherFacade factory(File keyringFile, char[] passPhrase,
			byte[] salt) {

		return new CipherFacade(keyringFile, passPhrase, salt);
	}

	public void encryptFile(File source, File dest, String algorithm)
			throws IOException {
		FileData fileData = FileLoader.load(source);

		Keyring keyring = loadKeyring();

		SecureRandom random = null;

		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		byte[] rawKey = new byte[256 / 8];
		random.nextBytes(rawKey);

		SecretKeySpec key = new SecretKeySpec(rawKey, "AES");

		Cipher cipher = null;

		try {
			cipher = getCipher(key, Cipher.ENCRYPT_MODE);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}

		UUID keyId = UUID.randomUUID();
		keyring.put(keyId, new EquatableSecretKey(rawKey));

		try {
			ObjectCipherSaver.save(fileData, dest, keyId, cipher);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		}

		KeyringSaver.save(keyring, keyringFile, passphrase, salt);
	}

	private Keyring loadKeyring() throws IOException {
		if (keyringFile.canRead()) {
			return KeyringSaver.load(keyringFile, passphrase, salt);
		} else {
			return new Keyring();
		}
	}

	private Cipher getCipher(SecretKeySpec key, int opmode)
			throws InvalidKeyException {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES", "BC");
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}

		cipher.init(opmode, key);

		return cipher;
	}

	public void decryptFile(File source, File dest) throws IOException,
			ClassNotFoundException {
		Serializable[] data = null;
		try {
			data = ObjectCipherSaver.load(source);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}

		Serializable keyId = data[0];
		SealedObject object = (SealedObject) data[1];

		EquatableSecretKey skey = loadKeyring().get(keyId);
		SecretKeySpec key = new SecretKeySpec(skey.getData(), "AES");

		FileData fileData = null;
		try {
			fileData = (FileData) object.getObject(key);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
		
		FileOutputStream fos = new FileOutputStream(dest);
		fos.write(fileData.getData());
		fos.close();
	}
}
