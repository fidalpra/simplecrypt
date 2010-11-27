package com.davidebellettini.crypt.gui.conf;

import java.util.HashMap;

public class HardCodedConfiguration implements Configuration {

    private HashMap<String, String> configuration = new HashMap<String, String>();

    protected HardCodedConfiguration() {
        configuration.put("keyringFile", "keyring.sc");
    }

    @Override
    public String get(String configurationKey) {
        return configuration.get(configurationKey);
    }

}
