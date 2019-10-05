package de.dmi3y.behaiv.kernel;

import de.dmi3y.behaiv.storage.SimpleStorage;
import de.dmi3y.behaiv.tools.Pair;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class KernelTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    public static Double[] HOME = {1.1, 1.2};
    public static Double[] GYM = {2.1, 2.2};
    public static Double[] JOG = {3.1, 3.2};
    public static Double[] WORK = {5.1, 5.2};

    public static double[] SELFIMPROVEMENT_SCREEN = new double[]{1, 0, 0, 0};
    public static double[] SPORT_SCREEN = new double[]{0, 1, 0, 0};
    public static double[] WORK_SCREEN = new double[]{0, 0, 1, 0};
    public static double[] ADD_SCREEN = new double[]{0, 0, 0, 1};

    private LogisticRegressionKernel dummyKernel;

    @Before
    public void setUp() throws Exception {
        dummyKernel = new LogisticRegressionKernel("testId");
    }

    @Test
    public void setTreshold() {
        dummyKernel.setTreshold(1L);
        dummyKernel.data.add(new Pair<List<Double>, String>(null, null));
        dummyKernel.data.add(new Pair<List<Double>, String>(null, null));
        boolean readyToPredict = dummyKernel.readyToPredict();
        assertTrue(readyToPredict);
        dummyKernel.setTreshold(10L);
        readyToPredict = dummyKernel.readyToPredict();
        assertFalse(readyToPredict);
    }

    @Test
    public void kernel_saveAndRestore_expectNormalFlow() throws IOException {
        dummyKernel.setId("testId");
        dummyKernel.fit(getTrainingData());
        final SimpleStorage storage = new SimpleStorage(temporaryFolder.getRoot());
        dummyKernel.save(storage);
        assertTrue(new File(temporaryFolder.getRoot(), "testId.nn").exists());
        //for general kernel we don't serialise metadata, it's included into network
        //NO labels
        assertTrue(new File(temporaryFolder.getRoot(), "testId.mt").exists());

        dummyKernel = new LogisticRegressionKernel("testId");
        dummyKernel.restore(storage);

        assertFalse(dummyKernel.isEmpty());

    }

    public static List<Pair<List<Double>, String>> getTrainingData() {
        List<Double> list = new ArrayList<>();
        List<Pair<List<Double>, String>> data = new ArrayList<>();


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


    public static double[][] getTrainingArray() {

        return new double[][]{
                new double[]{
                        (5 * 60 + 00.0) / (24 * 60),
                        HOME[0],
                        HOME[1]
                }, new double[]{
                (5 * 60 + 10.0) / (24 * 60),
                HOME[0],
                HOME[1]
        }, new double[]{
                (6 * 60 + 10.0) / (24 * 60),
                GYM[0],
                GYM[1]
        }, new double[]{
                (7 * 60 + 30.0) / (24 * 60),
                HOME[0],
                HOME[1]
        }, new double[]{
                (8 * 60 + 30.0) / (24 * 60),
                WORK[0],
                WORK[1]
        }, new double[]{
                (10 * 60 + 30.0) / (24 * 60),
                WORK[0],
                WORK[1]
        }, new double[]{
                (11 * 60 + 30.0) / (24 * 60),
                WORK[0],
                WORK[1]
        }, new double[]{
                (16 * 60 + 30.0) / (24 * 60),
                WORK[0],
                WORK[1]
        }, new double[]{
                (17 * 60 + 10.0) / (24 * 60),
                WORK[0],
                WORK[1]
        }, new double[]{
                (18 * 60 + 50.0) / (24 * 60),
                WORK[0],
                WORK[1]
        }, new double[]{
                (19 * 60 + 5.0) / (24 * 60),
                JOG[0],
                JOG[1]
        }, new double[]{
                (19 * 60 + 10.0) / (24 * 60),
                JOG[0],
                JOG[1]
        }, new double[]{
                (19 * 60 + 25.0) / (24 * 60),
                JOG[0],
                JOG[1]
        }, new double[]{
                (21 * 60 + 00.0) / (24 * 60),
                HOME[0],
                HOME[1]
        }
        };
    }

    public static double[][] getLabelsArray() {
        return new double[][]{
                SELFIMPROVEMENT_SCREEN,
                SELFIMPROVEMENT_SCREEN,
                SPORT_SCREEN,
                SELFIMPROVEMENT_SCREEN,
                WORK_SCREEN,
                WORK_SCREEN,
                WORK_SCREEN,
                WORK_SCREEN,
                WORK_SCREEN,
                WORK_SCREEN,
                SPORT_SCREEN,
                SPORT_SCREEN,
                SPORT_SCREEN,
                ADD_SCREEN
        };
    }

}