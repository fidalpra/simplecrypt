package com.davidebellettini.crypt.gui.conf;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class HardCodedConfigurationTest {

    HardCodedConfiguration conf;

    @Before
    public void setUp() {
        conf = new HardCodedConfiguration();
    }

    @Test
    public void testKeyringFile() {
        assertEquals("keyring.sc", conf.get("keyringFile"));
    }

}
