package de.dmi3y.behaiv.kernel;

import org.apache.commons.math3.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class KernelTest {

    public static Double[] HOME = {1.1, 1.2};
    public static Double[] GYM = {2.1, 2.2};
    public static Double[] JOG = {3.1, 3.2};
    public static Double[] WORK = {5.1, 5.2};

    private DummyKernel dummyKernel;

    @Before
    public void setUp() throws Exception {
        dummyKernel = new DummyKernel();
    }

    @Test
    public void setTreshold() {
        dummyKernel.setTreshold(1L);
        dummyKernel.data.add(Pair.create(null, null));
        dummyKernel.data.add(Pair.create(null, null));
        boolean readyToPredict = dummyKernel.readyToPredict();
        assertTrue(readyToPredict);
        dummyKernel.setTreshold(10L);
        readyToPredict = dummyKernel.readyToPredict();
        assertFalse(readyToPredict);
    }

    public static ArrayList<Pair<ArrayList<Double>, String>> getTrainingData() {
        ArrayList<Double> list = new ArrayList<>();
        ArrayList<Pair<ArrayList<Double>, String>> data = new ArrayList<>();


        list.add((5 * 60 + 00.0) / (24 * 60));
        list.add(HOME[0]);
        list.add(HOME[1]);
        list.add(0.0);
        data.add(new Pair<>(list, "SELFIMPROVEMENT_SCREEN"));
        list = new ArrayList<>();
        list.add((5 * 60 + 10.0) / (24 * 60));
        list.add(HOME[0]);
        list.add(HOME[1]);
        list.add(0.0);
        data.add(new Pair<>(list, "SELFIMPROVEMENT_SCREEN"));
        list = new ArrayList<>();
        list.add((6 * 60 + 10.0) / (24 * 60));
        list.add(GYM[0]);
        list.add(GYM[1]);
        list.add(1.0);
        data.add(new Pair<>(list, "SPORT_SCREEN"));
        list = new ArrayList<>();
        list.add((7 * 60 + 30.0) / (24 * 60));
        list.add(HOME[0]);
        list.add(HOME[1]);
        list.add(1.0);
        data.add(new Pair<>(list, "SELFIMPROVEMENT_SCREEN"));
        list = new ArrayList<>();
        list.add((8 * 60 + 30.0) / (24 * 60));
        list.add(WORK[0]);
        list.add(WORK[1]);
        list.add(0.0);
        data.add(new Pair<>(list, "WORK_SCREEN"));
        list = new ArrayList<>();
        list.add((10 * 60 + 30.0) / (24 * 60));
        list.add(WORK[0]);
        list.add(WORK[1]);
        list.add(1.0);
        data.add(new Pair<>(list, "WORK_SCREEN"));
        list = new ArrayList<>();
        list.add((11 * 60 + 30.0) / (24 * 60));
        list.add(WORK[0]);
        list.add(WORK[1]);
        list.add(1.0);
        data.add(new Pair<>(list, "WORK_SCREEN"));
        list = new ArrayList<>();
        list.add((16 * 60 + 30.0) / (24 * 60));
        list.add(WORK[0]);
        list.add(WORK[1]);
        list.add(0.0);
        data.add(new Pair<>(list, "WORK_SCREEN"));
        list = new ArrayList<>();
        list.add((17 * 60 + 10.0) / (24 * 60));
        list.add(WORK[0]);
        list.add(WORK[1]);
        list.add(0.0);
        data.add(new Pair<>(list, "WORK_SCREEN"));
        list = new ArrayList<>();
        list.add((18 * 60 + 50.0) / (24 * 60));
        list.add(WORK[0]);
        list.add(WORK[1]);
        list.add(0.0);
        data.add(new Pair<>(list, "WORK_SCREEN"));
        list = new ArrayList<>();
        list.add((19 * 60 + 5.0) / (24 * 60));
        list.add(JOG[0]);
        list.add(JOG[1]);
        list.add(1.0);
        data.add(new Pair<>(list, "SPORT_SCREEN"));
        list = new ArrayList<>();
        list.add((19 * 60 + 10.0) / (24 * 60));
        list.add(JOG[0]);
        list.add(JOG[1]);
        list.add(1.0);
        data.add(new Pair<>(list, "SPORT_SCREEN"));
        list = new ArrayList<>();
        list.add((19 * 60 + 25.0) / (24 * 60));
        list.add(JOG[0]);
        list.add(JOG[1]);
        list.add(1.0);
        data.add(new Pair<>(list, "SPORT_SCREEN"));
        list = new ArrayList<>();
        list.add((21 * 60 + 00.0) / (24 * 60));
        list.add(HOME[0]);
        list.add(HOME[1]);
        list.add(0.0);
        data.add(new Pair<>(list, "ADD_SCREEN"));
        return data;
    }

}