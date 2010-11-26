package com.davidebellettini.crypt.core;

import static org.junit.Assert.*;

import java.io.Serializable;

import org.junit.Test;

public class FileDataTest {

	@Test
	public void testIsSerializable() {
		FileData data = new FileData("Goofy", new byte[0]);

		assertTrue(data instanceof Serializable);
	}

	@Test
	public void testGetters() {
		FileData data = new FileData("Goofy", new byte[0]);
		
		assertArrayEquals(new byte[0], data.getData());
		assertEquals("Goofy", data.getKeyId());
	}
}
