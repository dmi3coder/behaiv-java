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
import java.util.List;

public abstract class BaseKernel implements Kernel {

    protected String id;
    protected Long treshold = 10L;
    protected ObjectMapper objectMapper;
    protected boolean partialFitAllowed = false;
    protected boolean alwaysKeepData = true;

    public BaseKernel(String id) {
        this.id = id;
        objectMapper = new ObjectMapper();
    }


    @Override
    public void setId(String id) {
        this.id = id;
    }


    //list<features>, label
    protected List<Pair<List<Double>, String>> data = new ArrayList<>();


    @Override
    public void fit() {
        fit(this.data);
    }

    @Override
    public void setTreshold(Long treshold) {
        this.treshold = treshold;
    }

    @Override
    public boolean readyToPredict() {
        return data.size() > treshold;
    }

    @Override
    public void update(List<Pair<List<Double>, String>> data) {
    }

    @Override
    public boolean isPartialFitAllowed() {
        return partialFitAllowed;
    }

    @Override
    public void updateSingle(List<Double> features, String label) {
        data.add(new Pair<>(features, label));
    }

    @Override
    public boolean isAlwaysKeepData() {
        return alwaysKeepData;
    }

    @Override
    public void setAlwaysKeepData(boolean alwaysKeepData) {
        this.alwaysKeepData = alwaysKeepData;
    }

    @Override
    public void save(BehaivStorage storage) throws IOException {

        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(storage.getDataFile(id)))) {
            writer.write(objectMapper.writeValueAsString(data));
        }
    }

    @Override
    public void restore(BehaivStorage storage) throws IOException {
        final TypeReference<List<Pair<List<Double>, String>>> typeReference = new TypeReference<List<Pair<List<Double>, String>>>() {
        };
        try (final BufferedReader reader = new BufferedReader(new FileReader(storage.getDataFile(id)))) {
            final String content = reader.readLine();
            if (content == null || content.isEmpty()) {
                data = new ArrayList<>();
            } else {
                data = objectMapper.readValue(content, typeReference);
            }
        }
    }
}
