package de.dmi3y.behaiv.kernel;

import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;

public abstract class Kernel {

    private Long treshold = 10L;

    //list<features>, label
    protected ArrayList<Pair<ArrayList<Double>, String>> data = new ArrayList<>();

    public abstract void fit(ArrayList<Pair<ArrayList<Double>, String>> data);

    public void setTreshold(Long treshold) {
        this.treshold = treshold;
    }

    public boolean readyToPredict() {
        return data.size() > treshold;
    }

    public void update(ArrayList<Pair<ArrayList<Double>, String>> data) {
    }

    public void updateSingle(ArrayList<Double> features, String label) {
        data.add(Pair.create(features, label));
    }

    public abstract String predictOne(ArrayList<Double> features);
}
