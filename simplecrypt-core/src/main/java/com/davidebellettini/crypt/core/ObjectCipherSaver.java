package com.davidebellettini.crypt.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SealedObject;

public class ObjectCipherSaver {

	public static void save(Serializable data, File file, Serializable keyId,
			Cipher cipher) throws IOException, IllegalBlockSizeException {

		SealedObject sealed = new SealedObject(data, cipher);

		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream(file));

			os.writeObject(keyId);
			os.writeObject(sealed);
		} finally {
			if (os != null)
				os.close();
		}
	}

	public static Serializable[] load(File file) throws IOException,
			ClassNotFoundException, IllegalBlockSizeException,
			BadPaddingException {

		ObjectInputStream is = null;
		Serializable[] data = new Serializable[2];

		try {
			is = new ObjectInputStream(new FileInputStream(file));
			data[0] = (Serializable) is.readObject();
			data[1] = (Serializable) is.readObject();

			return data;
		} finally {
			if (is != null)
				is.close();
		}
	}

}
