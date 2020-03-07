package de.dmi3y.behaiv.kernel;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dmi3y.behaiv.storage.BehaivStorage;
import de.dmi3y.behaiv.tools.Pair;
import tech.donau.behaiv.proto.Data;
import tech.donau.behaiv.proto.Prediction;
import tech.donau.behaiv.proto.PredictionSet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    protected PredictionSet data = PredictionSet.newBuilder().addAllPrediction(new ArrayList<Prediction>()).build();


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
        return data.getPredictionList().size() > treshold;
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
        final Prediction.Builder predictionBuilder = Prediction.newBuilder();
        for (int i = 0; i < features.size(); i++) {
            predictionBuilder.addData(Data.newBuilder().setKey("key" + i).setValue(features.get(i)).build());
        }
        predictionBuilder.setLabel(label);
        data = data.toBuilder().addPrediction(predictionBuilder.build()).build();
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
        try (final FileOutputStream writer = new FileOutputStream(storage.getDataFile(id))) {
            data.writeTo(writer);
        }
    }

    @Override
    public void restore(BehaivStorage storage) throws IOException {
        try (final FileInputStream reader = new FileInputStream(storage.getDataFile(id))) {
            data = PredictionSet.parseFrom(reader);
        }
    }
}
