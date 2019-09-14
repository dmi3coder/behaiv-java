package de.dmi3y.behaiv.storage;

import java.io.File;
import java.io.IOException;

public class TemporaryStorage implements BehaivStorage {

    private File directory;
    private File networkFile;
    private File metadataFile;

    public TemporaryStorage(File directory) {
        this.directory = directory;
    }


    @Override
    public File getNetworkFile(String id) throws IOException {
        if (networkFile == null || !networkFile.exists()) {
            networkFile = File.createTempFile(id, ".nn", directory);
        }
        return networkFile;
    }

    @Override
    public File getNetworkMetadataFile(String id) throws IOException {
        if (metadataFile == null || !metadataFile.exists()) {
            metadataFile = File.createTempFile(id, ".mt", directory);
        }
        return metadataFile;
    }


    public void erase() {
        networkFile.delete();
        metadataFile.delete();
    }
}
