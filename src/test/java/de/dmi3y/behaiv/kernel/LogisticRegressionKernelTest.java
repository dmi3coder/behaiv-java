package de.dmi3y.behaiv.kernel;

import org.apache.commons.math3.util.Pair;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class LogisticRegressionKernelTest {

    public static Double[] HOME = {1.1, 1.2};
    public static Double[] GYM = {2.1, 2.2};
    public static Double[] JOG = {3.1, 3.2};
    public static Double[] WORK = {5.1, 5.2};


    @Test
    public void predictOne() {
        ArrayList<Pair<ArrayList<Double>, String>> data = KernelTest.getTrainingData();
        Kernel dummyKernel = new LogisticRegressionKernel();
        dummyKernel.fit(data);
        ArrayList<Double> predictList = new ArrayList<>();
        predictList.add((10 * 60 + 30.0) / (24 * 60));
        predictList.add(WORK[0]);
        predictList.add(WORK[1]);
        predictList.add(1.0);

        dummyKernel.update(null);
        String prediction = dummyKernel.predictOne(predictList);
        assertEquals("WORK_SCREEN", prediction);

        predictList = new ArrayList<>();
        predictList.add((5 * 60 + 10.0) / (24 * 60));
        predictList.add(HOME[0]);
        predictList.add(HOME[1]);
        predictList.add(0.0);
        prediction = dummyKernel.predictOne(predictList);
        assertEquals("SELFIMPROVEMENT_SCREEN", prediction);
    }
}