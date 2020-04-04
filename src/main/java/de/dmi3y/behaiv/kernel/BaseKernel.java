package de.dmi3y.behaiv.kernel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.CodedOutputStream;
import de.dmi3y.behaiv.storage.BehaivStorage;
import de.dmi3y.behaiv.tools.DataMappingUtils;
import de.dmi3y.behaiv.tools.Pair;
import tech.donau.behaiv.proto.PredictionSet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
        final File dataFile = storage.getDataFile(id);
        final PredictionSet predictionSet = DataMappingUtils.createPredictionSet(data);
        try(FileOutputStream fileOutputStream = new FileOutputStream(dataFile)) {
            predictionSet.writeTo(fileOutputStream);
        }
    }

    @Override
    public void restore(BehaivStorage storage) throws IOException {
        final File dataFile = storage.getDataFile(id);
        try (FileInputStream fileInputStream = new FileInputStream(dataFile)) {
            final PredictionSet predictionSet = PredictionSet.parseFrom(fileInputStream);
            data = DataMappingUtils.dataFromPredictionSet(predictionSet);
        }
    }
}
