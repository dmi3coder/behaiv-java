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
    public boolean containsNetworkFile(String id) {
        if (networkFiles.containsKey(id)) {
            return true;
        }
        final File networkFile = new File(directory, id + ".nn");
        if (networkFile.exists()) {
            networkFiles.put(id, networkFile);
        }
        return networkFile.exists();
    }

    @Override
    public File getNetworkFile(String id) throws IOException {
        if (networkFiles.get(id) == null) {
            networkFiles.put(id, new File(directory, id + ".nn"));
        }
        if (!networkFiles.get(id).exists()) {
            final boolean newFileCreated = networkFiles.get(id).createNewFile();
            if (!newFileCreated) {
                throw new IOException("Couldn't create network file " + id + ".nn in directory \"" + directory + "\"");
            }
        }
        return networkFiles.get(id);
    }

    @Override
    public File getNetworkMetadataFile(String id) throws IOException {
        if (metadataFiles.get(id) == null) {
            metadataFiles.put(id, new File(directory, id + ".mt"));
        }
        if (!metadataFiles.get(id).exists()) {
            final boolean newFileCreated = metadataFiles.get(id).createNewFile();
            if (!newFileCreated) {
                throw new IOException("Couldn't create network file " + id + ".nn in directory \"" + directory + "\"");
            }
        }
        return metadataFiles.get(id);
    }
}
