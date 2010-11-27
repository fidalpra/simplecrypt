package com.davidebellettini.crypt.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
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

            os = new ObjectOutputStream(new FileOutputStream(keyringFile));

            os.writeObject(salt);
            os.writeObject(new SealedObject(keyring, cipher));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } finally {
            if (os != null)
                os.close();
        }
    }

    public static Keyring load(File keyringFile, char[] passphrase)
            throws IOException {

        ObjectInputStream is = null;

        try {
            is = new ObjectInputStream(new FileInputStream(keyringFile));

            try {
                byte[] salt = (byte[])is.readObject();
                
                Cipher cipher = getCipher(passphrase, salt, Cipher.DECRYPT_MODE);
                
                SealedObject skr = (SealedObject)is.readObject();
                return (Keyring)skr.getObject(cipher);
            }
            catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
            catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        finally {
            if (is != null)
                is.close();
        }
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
