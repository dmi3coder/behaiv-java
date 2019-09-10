package de.dmi3y.behaiv.kernel;

import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;

public abstract class Kernel {

    private Long treshold = 10L;

    protected ArrayList<Pair<ArrayList<Double>, String>> data;

    public void fit(ArrayList<Pair<ArrayList<Double>, String>> data) {
        this.data = data;
    }

    public void setTreshold(Long treshold) {
        this.treshold = treshold;
    }

    public boolean readyToPredict() {
        return data.size() > treshold;
    }

    public void update(ArrayList<Pair<ArrayList<Double>, String>> data) {
    }

    public abstract String predictOne(ArrayList<Double> features);
}
