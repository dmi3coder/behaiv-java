package de.dmi3y.behaiv.kernel;

import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;

public abstract class Kernel {

    public abstract void fit(ArrayList<Pair<ArrayList<Double>, String>> data);

    public abstract void update(ArrayList<Pair<ArrayList<Double>, String>> data);

    public abstract String predictOne(ArrayList<Double> features);
}
