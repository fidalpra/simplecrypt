package com.davidebellettini.crypt.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileLoader {

	public static FileData load(File file) throws IOException {
		if (file.length() > Integer.MAX_VALUE)
			throw new IllegalArgumentException("Huge file!");

		byte[] data = new byte[(int) file.length()];

		FileInputStream fis = new FileInputStream(file);
		fis.read(data);

		return new FileData(data);
	}

}
