package com.davidebellettini.crypt.core;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import org.junit.Test;

public class FileDataTest {

	@Test
	public void testIsSerializable() {
		FileData data = new FileData(new byte[0]);

		assertTrue(data instanceof Serializable);
	}

	@Test
	public void testGetters() {
		FileData data = new FileData(new byte[0]);
		
		assertArrayEquals(new byte[0], data.getData());
	}
}
