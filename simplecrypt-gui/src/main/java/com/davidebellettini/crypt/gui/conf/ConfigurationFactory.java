package com.davidebellettini.crypt.gui.conf;

public class ConfigurationFactory {

    private static Configuration conf = new HardCodedConfiguration();
    
    public static Configuration getConfiguration() {
        return conf;
    }
}
