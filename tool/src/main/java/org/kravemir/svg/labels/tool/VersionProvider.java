package org.kravemir.svg.labels.tool;

import picocli.CommandLine;

import java.io.InputStream;
import java.util.Properties;

public class VersionProvider implements CommandLine.IVersionProvider {
    @Override
    public String[] getVersion() throws Exception {
        Properties versionProperties = new Properties();
        try(InputStream stream = getClass().getResourceAsStream("/version.properties")) {
            versionProperties.load(stream);
        }

        return new String[] {
                versionProperties.getProperty("org.kravemir.svg.labels.tool.version")
        };
    }
}
