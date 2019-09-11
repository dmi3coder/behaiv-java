package de.dmi3y.behaiv.provider;

import org.apache.commons.math3.util.Pair;

import java.util.List;

public interface ProviderCallback {

    public void onFeaturesCaptured(List<Pair<Double, String>> features);
}
