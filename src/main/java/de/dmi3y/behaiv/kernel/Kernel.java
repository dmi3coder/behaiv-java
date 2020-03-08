package de.dmi3y.behaiv.kernel;

import de.dmi3y.behaiv.storage.BehaivStorage;
import de.dmi3y.behaiv.tools.Pair;
import tech.donau.behaiv.proto.PredictionSet;

import java.io.IOException;
import java.util.List;

public interface Kernel {
    void setId(String id);

    boolean isEmpty();

    @Deprecated
    void fit(List<Pair<List<Double>, String>> data);

    void fit(PredictionSet data);

    void fit();

    void setTreshold(Long treshold);

    boolean readyToPredict();

    void update(List<Pair<List<Double>, String>> data);

    boolean isPartialFitAllowed();

    void updateSingle(List<Double> features, String label);

    String predictOne(List<Double> features);

    boolean isAlwaysKeepData();

    void setAlwaysKeepData(boolean alwaysKeepData);

    void save(BehaivStorage storage) throws IOException;

    void restore(BehaivStorage storage) throws IOException;
}
