package de.dmi3y.behaiv.kernel;

import de.dmi3y.behaiv.storage.SimpleStorage;
import de.dmi3y.behaiv.tools.Pair;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static de.dmi3y.behaiv.kernel.KernelTest.HOME;
import static de.dmi3y.behaiv.kernel.KernelTest.WORK;
import static de.dmi3y.behaiv.kernel.KernelTest.getTrainingData;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LogisticRegressionKernelTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private SimpleStorage storage;


    @Before
    public void setUp() throws Exception {
        storage = new SimpleStorage(testFolder.getRoot());
    }

    @Test
    public void predictOne() {
        ArrayList<Pair<ArrayList<Double>, String>> data = KernelTest.getTrainingData();
        Kernel testKernel = new LogisticRegressionKernel("testId");
        testKernel.fit(data);
        ArrayList<Double> predictList = new ArrayList<>();
        predictList.add((10 * 60 + 10.0) / (24 * 60));
        predictList.add(WORK[0]);
        predictList.add(WORK[1]);
        predictList.add(1.0);

        testKernel.update(null);
        String prediction = testKernel.predictOne(predictList);
        assertEquals("WORK_SCREEN", prediction);

        predictList = new ArrayList<>();
        predictList.add((5 * 60 + 10.0) / (24 * 60));
        predictList.add(HOME[0]);
        predictList.add(HOME[1]);
        predictList.add(0.0);
        prediction = testKernel.predictOne(predictList);
        assertEquals("SELFIMPROVEMENT_SCREEN", prediction);
    }

    @Test
    public void storeResults() throws IOException, ClassNotFoundException {
        ArrayList<Pair<ArrayList<Double>, String>> data = KernelTest.getTrainingData();
        Kernel kernel = new LogisticRegressionKernel("testId");
        kernel.setId("storeTest");
        kernel.fit(data);
        ArrayList<Double> predictList = new ArrayList<>();
        predictList.add((10 * 60 + 10.0) / (24 * 60));
        predictList.add(WORK[0]);
        predictList.add(WORK[1]);
        predictList.add(1.0);

        kernel.update(null);
        String prediction = kernel.predictOne(predictList);
        assertEquals("WORK_SCREEN", prediction);

        final SimpleStorage storage = new SimpleStorage(testFolder.getRoot());
        kernel.save(storage);

        kernel = new LogisticRegressionKernel("testId");
        kernel.setId("storeTest");
        kernel.restore(storage);
        prediction = kernel.predictOne(predictList);
        assertEquals("WORK_SCREEN", prediction);
    }

    @Test
    public void storeResults_saveWhenDataIsNull_expectException() throws IOException, ClassNotFoundException {
        LogisticRegressionKernel kernel = new LogisticRegressionKernel("storeTest");
        try {
            kernel.save(storage);
            fail();
        } catch (IOException e) {
            assertEquals(e.getMessage(), "Not enough data to save, network data is empty");
        }
    }

    @Test
    public void storeResults_saveWhenLabelsIsNull_expectException() throws IOException, ClassNotFoundException {
        LogisticRegressionKernel kernel = new LogisticRegressionKernel("storeTest");
        kernel.data = getTrainingData();
        try {
            kernel.save(storage);
            fail();
        } catch (IOException e) {
            assertEquals(e.getMessage(), "Kernel collected labels but failed to get data, couldn't save network.");
        }
    }


    @Test
    public void storeResults_saveDataAndThenTheta_expectNormalFlow() throws IOException, ClassNotFoundException {
        ArrayList<Pair<ArrayList<Double>, String>> data = KernelTest.getTrainingData();
        LogisticRegressionKernel kernel = new LogisticRegressionKernel("storeTest", new Random());
        kernel.data = data;
        kernel.labels = Arrays.asList("time", "lat", "lon", "headphones");
        //Omit fit
//        kernel.fit(data);
        ArrayList<Double> predictList = new ArrayList<>();
        predictList.add((10 * 60 + 10.0) / (24 * 60));
        predictList.add(WORK[0]);
        predictList.add(WORK[1]);
        predictList.add(1.0);


        kernel.save(storage); //Saving data this time

        kernel = new LogisticRegressionKernel("storeTest");
        kernel.restore(storage);

        assertFalse(kernel.data.isEmpty());
        kernel.data.get(0).getKey();
        kernel.fit(data);

        String prediction = kernel.predictOne(predictList);
        assertEquals("WORK_SCREEN", prediction);
        kernel.save(storage);

        kernel = new LogisticRegressionKernel("storeTest");
        kernel.restore(storage);

        assertTrue(kernel.theta != null);


        prediction = kernel.predictOne(predictList);
        assertEquals("WORK_SCREEN", prediction);


    }
}