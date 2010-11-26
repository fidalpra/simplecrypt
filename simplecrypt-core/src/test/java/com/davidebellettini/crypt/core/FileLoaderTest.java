package com.davidebellettini.crypt.core;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
public class FileLoaderTest {

	@Test
	public void testLoad() throws IOException {
		File file = new File("example.txt");
		
		FileData data = FileLoader.load(file);
		
		assertArrayEquals("Hello world!".getBytes(), data.getData());		
	}
}
