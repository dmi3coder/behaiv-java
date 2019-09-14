package de.dmi3y.behaiv.storage;

import java.io.File;
import java.io.IOException;

public class SimpleStorage implements BehaivStorage {

    private File directory;
    private File networkFile;
    private File metadataFile;

    public SimpleStorage(File directory) {
        this.directory = directory;
    }


    @Override
    public File getNetworkFile(String id) throws IOException {
        if (networkFile == null) {
            networkFile = new File(directory, id + ".nn");
        }
        if (!networkFile.exists()) {
            networkFile.createNewFile();
        }
        return networkFile;
    }

    @Override
    public File getNetworkMetadataFile(String id) throws IOException {
        if (metadataFile == null) {
            metadataFile = new File(directory, id + ".mt");
        }
        if (!metadataFile.exists()) {
            metadataFile.createNewFile();
        }
        return metadataFile;
    }


    public void erase() {
        networkFile.delete();
        metadataFile.delete();
    }
}
