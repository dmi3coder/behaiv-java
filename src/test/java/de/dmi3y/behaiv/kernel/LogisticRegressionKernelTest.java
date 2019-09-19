package de.dmi3y.behaiv.kernel;

import de.dmi3y.behaiv.storage.SimpleStorage;
import org.apache.commons.math3.util.Pair;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.util.ArrayList;

import static de.dmi3y.behaiv.kernel.KernelTest.HOME;
import static de.dmi3y.behaiv.kernel.KernelTest.WORK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LogisticRegressionKernelTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


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
    public void storeResults_saveDataAndThenTheta_expectNormalFlow() throws IOException, ClassNotFoundException {
        ArrayList<Pair<ArrayList<Double>, String>> data = KernelTest.getTrainingData();
        LogisticRegressionKernel kernel = new LogisticRegressionKernel("storeTest");
        kernel.data = data;
        //Omit fit
//        kernel.fit(data);
        ArrayList<Double> predictList = new ArrayList<>();
        predictList.add((10 * 60 + 10.0) / (24 * 60));
        predictList.add(WORK[0]);
        predictList.add(WORK[1]);
        predictList.add(1.0);


        final SimpleStorage storage = new SimpleStorage(testFolder.getRoot());
        kernel.save(storage); //Saving data this time

        kernel = new LogisticRegressionKernel("storeTest");
        kernel.restore(storage);

        assertFalse(kernel.data.isEmpty());
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