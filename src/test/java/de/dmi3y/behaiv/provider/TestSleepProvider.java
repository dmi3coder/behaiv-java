package de.dmi3y.behaiv.provider;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;

import java.util.Arrays;
import java.util.List;

public class TestSleepProvider implements Provider {

    private List<String> names;
    private List<Double> features;
    private long latency;

    public TestSleepProvider(String[] names, Double[] features, long latency) {
        this.names = Arrays.asList(names);
        this.features = Arrays.asList(features);
        this.latency = latency;
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
        return Single.create(new SingleOnSubscribe<List<Double>>()
        {
            @Override
            public void subscribe(SingleEmitter<List<Double>> emitter) throws Throwable
            {
                System.out.println("Current thread: " + Thread.currentThread().getName());
                Thread.sleep(latency);
                emitter.onSuccess(features);
            }
        });
    }

    public void next(Double[] features) {
        this.features = Arrays.asList(features);
    }
}
