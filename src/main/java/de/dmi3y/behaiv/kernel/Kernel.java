package de.dmi3y.behaiv.kernel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.dmi3y.behaiv.storage.BehaivStorage;
import de.dmi3y.behaiv.tools.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Kernel {

    protected String id;
    protected Long treshold = 10L;
    protected ObjectMapper objectMapper;

    public Kernel(String id) {
        this.id = id;
        objectMapper = new ObjectMapper();
    }


    public void setId(String id) {
        this.id = id;
    }


    //list<features>, label
    protected ArrayList<Pair<ArrayList<Double>, String>> data = new ArrayList<>();


    public abstract boolean isEmpty();

    public abstract void fit(ArrayList<Pair<ArrayList<Double>, String>> data);

    public void fit() {
        fit(this.data);
    }

    public void setTreshold(Long treshold) {
        this.treshold = treshold;
    }

    public boolean readyToPredict() {
        return data.size() > treshold;
    }

    public void update(ArrayList<Pair<ArrayList<Double>, String>> data) {
    }

    public void updateSingle(ArrayList<Double> features, String label) {
        data.add(new Pair<>(features, label));
    }

    public abstract String predictOne(ArrayList<Double> features);

    public void save(BehaivStorage storage) throws IOException {

        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(storage.getNetworkFile(id)))) {
            writer.write(objectMapper.writeValueAsString(data));
        }
    }

    public void restore(BehaivStorage storage) throws IOException {
        final TypeReference<ArrayList<Pair<ArrayList<Double>, String>>> typeReference = new TypeReference<ArrayList<Pair<ArrayList<Double>, String>>>() {
        };
        try (final BufferedReader reader = new BufferedReader(new FileReader(storage.getNetworkFile(id)))) {
            final String content = reader.readLine();
            if (content == null || content.isEmpty()) {
                data = new ArrayList<>();
            } else {
                data = objectMapper.readValue(content, typeReference);
            }
        }
    }
}
