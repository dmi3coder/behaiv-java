package de.dmi3y.behaiv.kernel;

import org.apache.commons.math3.util.Pair;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DummyKernelTest {

    public static Double[] HOME = {1.1, 1.2};
    public static Double[] GYM = {2.1, 2.2};
    public static Double[] JOG = {3.1, 3.2};
    public static Double[] WORK = {5.1, 5.2};


    @Test
    public void predictOne() {
        ArrayList<Double> list = new ArrayList<>();
        ArrayList<Pair<ArrayList<Double>, String>> data = new ArrayList<>();


        list.add(5 * 60 + 00.0);
        list.add(HOME[0]);
        list.add(HOME[1]);
        list.add(0.0);
        data.add(new Pair<>(list,"SELFIMPROVEMENT_SCREEN"));
        list = new ArrayList<>();
        list.add(5 * 60 + 10.0);
        list.add(HOME[0]);
        list.add(HOME[1]);
        list.add(0.0);
        data.add(new Pair<>(list,"SELFIMPROVEMENT_SCREEN"));
        list = new ArrayList<>();
        list.add(6 * 60 + 10.0);
        list.add(GYM[0]);
        list.add(GYM[1]);
        list.add(1.0);
        data.add(new Pair<>(list,"SPORT_SCREEN"));
        list = new ArrayList<>();
        list.add(7 * 60 + 30.0);
        list.add(HOME[0]);
        list.add(HOME[1]);
        list.add(1.0);
        data.add(new Pair<>(list,"SELFIMPROVEMENT_SCREEN"));
        list = new ArrayList<>();
        list.add(8 * 60 + 30.0);
        list.add(WORK[0]);
        list.add(WORK[1]);
        list.add(0.0);
        data.add(new Pair<>(list,"WORK_SCREEN"));
        list = new ArrayList<>();
        list.add(10 * 60 + 30.0);
        list.add(WORK[0]);
        list.add(WORK[1]);
        list.add(1.0);
        data.add(new Pair<>(list,"WORK_SCREEN"));
        list = new ArrayList<>();
        list.add(11 * 60 + 30.0);
        list.add(WORK[0]);
        list.add(WORK[1]);
        list.add(1.0);
        data.add(new Pair<>(list,"WORK_SCREEN"));
        list = new ArrayList<>();
        list.add(16 * 60 + 30.0);
        list.add(WORK[0]);
        list.add(WORK[1]);
        list.add(0.0);
        data.add(new Pair<>(list,"WORK_SCREEN"));
        list = new ArrayList<>();
        list.add(17 * 60 + 10.0);
        list.add(WORK[0]);
        list.add(WORK[1]);
        list.add(0.0);
        data.add(new Pair<>(list,"WORK_SCREEN"));
        list = new ArrayList<>();
        list.add(18 * 60 + 50.0);
        list.add(WORK[0]);
        list.add(WORK[1]);
        list.add(0.0);
        data.add(new Pair<>(list,"WORK_SCREEN"));
        list = new ArrayList<>();
        list.add(19 * 60 + 5.0);
        list.add(JOG[0]);
        list.add(JOG[1]);
        list.add(1.0);
        data.add(new Pair<>(list,"SPORT_SCREEN"));
        list = new ArrayList<>();
        list.add(19 * 60 + 10.0);
        list.add(JOG[0]);
        list.add(JOG[1]);
        list.add(1.0);
        data.add(new Pair<>(list,"SPORT_SCREEN"));
        list = new ArrayList<>();
        list.add(19 * 60 + 25.0);
        list.add(JOG[0]);
        list.add(JOG[1]);
        list.add(1.0);
        data.add(new Pair<>(list,"SPORT_SCREEN"));
        list = new ArrayList<>();
        list.add(21 * 60 + 00.0);
        list.add(HOME[0]);
        list.add(HOME[1]);
        list.add(0.0);
        data.add(new Pair<>(list,"ADD_SCREEN"));
        list = new ArrayList<>();
        DummyKernel dummyKernel = new DummyKernel();
        dummyKernel.fit(data);
        ArrayList<Double> predictList = new ArrayList<>();
        predictList.add(10 * 60 + 30.0);
        predictList.add(WORK[0]);
        predictList.add(WORK[1]);
        predictList.add(1.0);

        dummyKernel.update(null);
        String prediction = dummyKernel.predictOne(predictList);
        assertEquals("WORK_SCREEN", prediction);
    }
}