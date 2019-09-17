package de.dmi3y.behaiv.kernel.logistic;

import org.ejml.simple.SimpleMatrix;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class LogisticUtilsTest {


    @Test
    public void sigmoid_feed10_receive0_99995() {
        final SimpleMatrix zigmoid = LogisticUtils.sigmoid(new SimpleMatrix(new double[][]{new double[]{10.0}}));
        assertEquals(0.9999546021312976, zigmoid.get(0, 0));
    }
}