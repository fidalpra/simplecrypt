package com.davidebellettini.crypt.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

public class ObjectCipherSaver {

	public static void save(Serializable data, File file, Cipher cipher)
			throws IOException {

		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new CipherOutputStream(
					new FileOutputStream(file), cipher));
			os.writeObject(data);
		} finally {
			if (os != null)
				os.close();
		}
	}

	public static Serializable load(File file, Cipher cipher)
			throws IOException, ClassNotFoundException {

		ObjectInputStream is = null;

		try {
			is = new ObjectInputStream(new CipherInputStream(
					new FileInputStream(file), cipher));
			return (Serializable) is.readObject();
		} finally {
			if (is != null)
				is.close();
		}
	}

}
