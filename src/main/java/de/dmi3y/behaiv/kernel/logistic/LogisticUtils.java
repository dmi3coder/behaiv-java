package de.dmi3y.behaiv.kernel.logistic;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.simple.SimpleMatrix;

public final class LogisticUtils {
    private LogisticUtils() {
    }

    public static SimpleMatrix sigmoid(SimpleMatrix x) {
        final SimpleMatrix bottom = x.negative()
                .elementExp()
                .plus(1);
        final DMatrixRMaj result = bottom.getDDRM();
        CommonOps_DDRM.divide(1.0, result);
        return new SimpleMatrix(result);
    }

    public static SimpleMatrix costFunction(SimpleMatrix x, SimpleMatrix theta, SimpleMatrix y) {
        SimpleMatrix h_x = sigmoid(x.mult(theta));

        final SimpleMatrix log_h = h_x.elementLog();
        final int m = y.numCols();
        return y.transpose().negative()
                .mult(log_h)
                .minus(
                        y.negative().plus(1).transpose()
                                .mult(
                                        h_x.negative().plus(1).elementLog() // (1 - y)T
                                )
                )
                .divide(m);
    }

    public static SimpleMatrix gradient(SimpleMatrix x, SimpleMatrix theta, SimpleMatrix y) {
        final int m = y.numCols();
        final SimpleMatrix h_x = sigmoid(x.mult(theta));
        final SimpleMatrix loss = h_x.minus(y);
        final SimpleMatrix gradient = x.transpose().mult(loss).divide(m);
        return gradient;

    }

    public static SimpleMatrix gradientDescent(SimpleMatrix x, SimpleMatrix theta, SimpleMatrix y, double alpha) {
        return theta.minus(gradient(x, theta, y).scale(alpha));
    }


}
