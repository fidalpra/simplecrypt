package com.davidebellettini.crypt.core;

import java.io.Serializable;

public class EncryptedObject implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7547213685228981532L;
    private final Serializable keyId;
    private final String algorithm;
    private final Serializable data;

    public EncryptedObject(Serializable keyId, String algorithm,
            Serializable data) {
        this.keyId = keyId;
        this.algorithm = algorithm;
        this.data = data;
    }

    public Serializable getKeyId() {
        return keyId;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public Serializable getData() {
        return data;
    }

}
