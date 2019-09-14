package de.dmi3y.behaiv.storage;

import java.io.File;
import java.io.IOException;

public interface BehaivStorage {

    public File getNetworkFile(String id) throws IOException;

    public File getNetworkMetadataFile(String id) throws IOException;

}
