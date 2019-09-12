package de.dmi3y.behaiv.session;

import de.dmi3y.behaiv.Behaiv;
import de.dmi3y.behaiv.provider.Provider;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

public class CaptureSession {

    private List<Provider> providers;
    private List<Pair<Double, String>> features;
    private String label;

    public CaptureSession(List<Provider> providers) {
        this.providers = providers;
    }

    public void start(Behaiv behaiv) {
        new Thread(() -> {
            startBlocking(behaiv);
        }).start();
    }

    public void startBlocking(Behaiv behaiv) {
        List<Double> capturedFeatures = providers.stream().flatMap(provider -> provider.getFeature().blockingGet().stream()).collect(Collectors.toList());
        List<String> capturedNames = providers.stream().flatMap((Provider provider) -> provider.availableFeatures().stream()).collect(Collectors.toList());
        if (capturedFeatures.size() != capturedNames.size()) {
            throw new InputMismatchException("Features size should match it's names");
        }

        features = new ArrayList<>();
        for (int i = 0; i < capturedFeatures.size(); i++) {
            features.add(new Pair<>(capturedFeatures.get(i), capturedNames.get(i)));

        }
        if (behaiv != null) {
            behaiv.onFeaturesCaptured(features);
        }
    }

    public void captureLabel(String name) {
        label = name;
    }

    public List<Pair<Double, String>> getFeatures() {
        return features;
    }

    public String getLabel() {
        return label;
    }

}
