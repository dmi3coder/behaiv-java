package de.dmi3y.behaiv.provider;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.util.Arrays;
import java.util.List;

public class TestProvider implements Provider {

    private List<String> names;
    private List<Double> features;

    public TestProvider(String[] names, Double[] features) {
        this.names = Arrays.asList(names);
        this.features = Arrays.asList(features);
    }

    @Override
    public List<String> availableFeatures() {
        return names;
    }

    @Override
    public Observable<List<Double>> subscribeFeatures() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Single<List<Double>> getFeature() {
        return Single.just(features);
    }

    public void next(Double[] features) {
        this.features = Arrays.asList(features);
    }
}
