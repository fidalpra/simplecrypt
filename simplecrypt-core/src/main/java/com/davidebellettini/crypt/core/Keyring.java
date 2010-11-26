package com.davidebellettini.crypt.core;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Simple Keyring
 */
public class Keyring implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3633358150630280931L;

	private HashMap<Serializable, EquatableSecretKey> cipherKeys = new HashMap<Serializable, EquatableSecretKey>();

	public void put(Serializable keyId, EquatableSecretKey key) {
		cipherKeys.put(keyId, key);
	}

	public EquatableSecretKey get(Serializable key) {
		return cipherKeys.get(key);
	}

	public EquatableSecretKey remove(Serializable key) {
		return cipherKeys.remove(key);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cipherKeys == null) ? 0 : cipherKeys.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Keyring other = (Keyring) obj;
		if (cipherKeys == null) {
			if (other.cipherKeys != null)
				return false;
		} else if (!cipherKeys.equals(other.cipherKeys))
			return false;
		return true;
	}
}
