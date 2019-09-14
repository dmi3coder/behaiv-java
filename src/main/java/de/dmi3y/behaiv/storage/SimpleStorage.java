package de.dmi3y.behaiv.storage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpleStorage implements BehaivStorage {

    private File directory;
    private Map<String, File> networkFiles = new HashMap<>();
    private Map<String, File> metadataFiles = new HashMap<>();

    public SimpleStorage(File directory) {
        this.directory = directory;
    }


    @Override
    public File getNetworkFile(String id) throws IOException {
        if (networkFiles.get(id) == null) {
            networkFiles.put(id, new File(directory, id + ".nn"));
        }
        if (!networkFiles.get(id).exists()) {
            networkFiles.get(id).createNewFile();
        }
        return networkFiles.get(id);
    }

    @Override
    public File getNetworkMetadataFile(String id) throws IOException {
        if (metadataFiles.get(id) == null) {
            metadataFiles.put(id, new File(directory, id + ".mt"));
        }
        if (!metadataFiles.get(id).exists()) {
            metadataFiles.get(id).createNewFile();
        }
        return metadataFiles.get(id);
    }
}
