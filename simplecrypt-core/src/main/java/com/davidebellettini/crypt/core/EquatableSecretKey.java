package com.davidebellettini.crypt.core;

import java.io.Serializable;
import java.util.Arrays;

public class EquatableSecretKey implements Serializable {
	private static final long serialVersionUID = 6274690709244897093L;

	private final byte[] data;

	public EquatableSecretKey(byte[] data) {
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
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
		EquatableSecretKey other = (EquatableSecretKey) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		return true;
	}
}
