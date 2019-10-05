package de.dmi3y.behaiv.storage;

import java.io.File;
import java.io.IOException;

public interface BehaivStorage {

    boolean containsNetworkFile(String id);

    File getDataFile(String id) throws IOException;

    File getNetworkFile(String id) throws IOException;

    File getNetworkMetadataFile(String id) throws IOException;

}
