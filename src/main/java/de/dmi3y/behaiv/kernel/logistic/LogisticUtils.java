package de.dmi3y.behaiv.kernel.logistic;

import org.ejml.simple.SimpleMatrix;

public final class LogisticUtils {
    private LogisticUtils() {
    }

    public static SimpleMatrix sigmoid(SimpleMatrix x) {
        return x.negative().elementExp().plus(1).pseudoInverse();
    }


}
