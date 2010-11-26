package com.davidebellettini.crypt.core;

import java.io.Serializable;

public class FileData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1450747962682212006L;
	private byte[] data;

	public FileData(byte[] bs) {
		this.data = bs;
	}

	/**
	 * Gets the file data (unencrypted)
	 * 
	 * @return
	 */
	public byte[] getData() {
		return this.data;
	}
}
