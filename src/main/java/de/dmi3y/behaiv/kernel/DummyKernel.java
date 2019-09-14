package de.dmi3y.behaiv.kernel;

import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;

public class DummyKernel extends Kernel {


    @Override
    public boolean isEmpty() {
        return data.size() == 0;
    }

    @Override
    public void fit(ArrayList<Pair<ArrayList<Double>, String>> data) {
        this.data = data;
    }

    @Override
    public String predictOne(ArrayList<Double> features) {
        return this.data.stream().map(featuresIter -> {
            Double result = 0.0;
            for (int i = 0; i < featuresIter.getFirst().size(); i++) {
                if (featuresIter.getFirst().get(i).equals(features.get(i))) {
                    result += (1.0 / features.size());
                }
            }
            featuresIter.getFirst().add(result);
            return featuresIter;
        }).max(Comparator.comparingDouble(one -> one.getFirst().get(one.getFirst().size() - 1))).map(Pair::getSecond).orElse(null);
    }

}
