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

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;

public class CipherFacade {

    private final File keyringFile;
    private final char[] passphrase;
    public CipherFacade(File keyringFile, char[] passPhrase) {
        this.keyringFile = keyringFile;
        this.passphrase = passPhrase;
    }

    public static CipherFacade factory(File keyringFile, char[] passPhrase) {

        return new CipherFacade(keyringFile, passPhrase);
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

        SecretKeySpec key = new SecretKeySpec(rawKey, algorithm);

        Cipher cipher = null;

        try {
            cipher = getCipher(key, Cipher.ENCRYPT_MODE, algorithm);
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

        KeyringSaver.save(keyring, keyringFile, passphrase, generateSalt());
    }

    private byte[] generateSalt() {
        return getSecureRandomBytes(8);
    }

    private byte[] getSecureRandomBytes(int length) {
        byte[] bytes = new byte[length];

        SecureRandom random = null;

        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        random.nextBytes(bytes);

        return bytes;
    }

    private Keyring loadKeyring() throws IOException {
        if (isNewFile()) {
            return new Keyring();
        } else {
            return KeyringSaver.load(keyringFile, passphrase);
        }
    }

    private Cipher getCipher(SecretKeySpec key, int opmode, String algorithm)
            throws InvalidKeyException {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(algorithm, "BC");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        cipher.init(opmode, key);

        return cipher;
    }

    public void decryptFile(File source, File dest) throws IOException,
            ClassNotFoundException {
        EncryptedObject data = null;
        try {
            data = ObjectCipherSaver.load(source);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        Serializable keyId = data.getKeyId();
        SealedObject object = (SealedObject) data.getData();

        EquatableSecretKey skey = loadKeyring().get(keyId);
        SecretKeySpec key = new SecretKeySpec(skey.getData(),
                data.getAlgorithm());

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

    public boolean isNewFile() {
        return !keyringFile.exists();
    }
}
