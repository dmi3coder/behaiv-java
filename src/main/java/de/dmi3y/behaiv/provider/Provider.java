package de.dmi3y.behaiv.provider;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

/**
 * Doesn't support generics in first version
 */
public interface Provider {

    List<String> availableFeatures();

    Observable<List<Double>> subscribeFeatures();

    Single<List<Double>> getFeature();

}
