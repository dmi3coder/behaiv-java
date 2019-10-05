package de.dmi3y.behaiv.provider;

import de.dmi3y.behaiv.tools.Pair;

import java.util.List;

public interface ProviderCallback {

    void onFeaturesCaptured(List<Pair<Double, String>> features);
}
