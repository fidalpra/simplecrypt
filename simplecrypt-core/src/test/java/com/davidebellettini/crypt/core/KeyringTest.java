package com.davidebellettini.crypt.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;

public class KeyringTest {

	private Keyring keyring;

	private EquatableSecretKey key = new EquatableSecretKey(
			"Pistola".getBytes());

	@Before
	public void setUp() {
		this.keyring = new Keyring();
	}

	@Test
	public void testPutGet() {
		String keyId = "FooBar";

		assertEquals(null, keyring.get(keyId));
		keyring.put(keyId, key);
		assertEquals(key, keyring.get(keyId));
	}

	@Test
	public void testRemove() {
		String keyId = "FooBar";

		keyring.put(keyId, key);
		EquatableSecretKey removed = keyring.remove(keyId);

		assertSame(key, removed);
		assertEquals(null, keyring.get(keyId));
	}

	@Test
	public void testKeyringIsSerializable() {
		assertTrue(keyring instanceof Serializable);
	}

	@Test
	public void testEquals() {
		String keyId = "FooBar";

		keyring.put(keyId, key);

		Keyring other = new Keyring();
		other.put(keyId, new EquatableSecretKey(key.getData().clone()));

		assertEquals(keyring, other);
	}
}
