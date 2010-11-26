package com.davidebellettini.crypt.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EquatableSecretKeyTest {

	@Test
	public void testEquals() {
		byte[] data = "hello".getBytes();

		assertEquals(new EquatableSecretKey(data),
				new EquatableSecretKey(data.clone()));
	}
}
