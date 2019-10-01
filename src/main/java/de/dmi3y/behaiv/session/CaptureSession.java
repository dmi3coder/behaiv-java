package de.dmi3y.behaiv.session;

import de.dmi3y.behaiv.Behaiv;
import de.dmi3y.behaiv.provider.Provider;
import de.dmi3y.behaiv.tools.Pair;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;

public class CaptureSession {

    private List<Provider> providers;
    private List<Pair<Double, String>> features;
    private String label;

    public CaptureSession(List<Provider> providers) {
        this.providers = providers;
    }

    public void start(Behaiv behaiv) {
        new Thread(() -> startBlocking(behaiv)).start();
    }

    public void startBlocking(Behaiv behaiv) {
        List<Single<List<Double>>> featureList = new ArrayList<>();
        List<String> capturedNames = new LinkedList<>();
        for (int i = 0; i < providers.size(); i++) {
            final Provider provider = providers.get(i);
            capturedNames.addAll(provider.availableFeatures());
            final Single<List<Double>> listSingle = provider.getFeature();
            featureList.add(i, listSingle.subscribeOn(Schedulers.io()));
        }

        if (featureList.size() == 0) {
            throw new InputMismatchException("Feature size shouldn't be zero");
        }
        final List<Double> capturedFeatures = Single.zip(featureList, objects -> {
            List<Double> mapDoubleList = new ArrayList<>();
            for (Object obj : objects) {
                mapDoubleList.addAll((List<Double>) obj);
            }
            return mapDoubleList;
        }).blockingGet();

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
