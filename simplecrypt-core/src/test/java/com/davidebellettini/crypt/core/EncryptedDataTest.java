package com.davidebellettini.crypt.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class EncryptedDataTest {

    private Serializable keyId = UUID.randomUUID();
    private String algorithm = "AES";
    private Serializable data = "data";
    private EncryptedObject obj;

    @Before
    public void setUp() {
        obj = new EncryptedObject(keyId, algorithm, data);
    }

    @Test
    public void testSerializable() {
        assertTrue(obj instanceof Serializable);
    }

    @Test
    public void testKeyIdGetter() {
        assertEquals(keyId, obj.getKeyId());
    }

    @Test
    public void testAlgorithmGetter() {
        assertEquals(algorithm, obj.getAlgorithm());
    }

    @Test
    public void testDataGetter() {
        assertEquals(data, obj.getData());
    }
}
