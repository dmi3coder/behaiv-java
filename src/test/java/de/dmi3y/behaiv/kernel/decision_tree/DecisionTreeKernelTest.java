package de.dmi3y.behaiv.kernel.decision_tree;

import de.dmi3y.behaiv.kernel.Kernel;
import de.dmi3y.behaiv.kernel.KernelTest;
import de.dmi3y.behaiv.storage.SimpleStorage;
import de.dmi3y.behaiv.tools.Pair;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.dmi3y.behaiv.kernel.KernelTest.HOME;
import static de.dmi3y.behaiv.kernel.KernelTest.WORK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DecisionTreeKernelTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private SimpleStorage storage;


    @Before
    public void setUp() throws Exception {
        storage = new SimpleStorage(testFolder.getRoot());
    }

    @Test
    public void predictOne() {
        List<Pair<List<Double>, String>> data = KernelTest.getTrainingData();
        Kernel testKernel = new DecisionTreeKernel("testId");
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
    public void isPartialFitAllowed_inLogisticRegression_shouldReturnFalse() {
        final Kernel kernel = new DecisionTreeKernel("testId");
        assertTrue(kernel.isPartialFitAllowed());
    }

    @Test
    public void storeResults() throws IOException, ClassNotFoundException {
        List<Pair<List<Double>, String>> data = KernelTest.getTrainingData();
        Kernel kernel = new DecisionTreeKernel("testId");
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

        kernel = new DecisionTreeKernel("testId");
        kernel.setId("storeTest");
        kernel.restore(storage);
        prediction = kernel.predictOne(predictList);
        assertEquals("WORK_SCREEN", prediction);
    }

    @Test
    public void storeResults_addAdditionalLabel_shouldFail() throws IOException, ClassNotFoundException {
        List<Pair<List<Double>, String>> data = KernelTest.getTrainingData();
        Kernel kernel = new DecisionTreeKernel("testId");
        kernel.setAlwaysKeepData(false);
        kernel.setId("storeTest");
        kernel.fit(data);
        ArrayList<Double> predictList = new ArrayList<>();
        predictList.add((10 * 60 + 10.0) / (24 * 60));
        predictList.add(WORK[0]);
        predictList.add(WORK[1]);
        predictList.add(1.0);

        String prediction = kernel.predictOne(predictList);
        assertEquals("WORK_SCREEN", prediction);
        final SimpleStorage storage = new SimpleStorage(testFolder.getRoot());
        kernel.save(storage);

        kernel = new DecisionTreeKernel("testId");
        kernel.setId("storeTest");
        kernel.setAlwaysKeepData(false);
        kernel.restore(storage);
        prediction = kernel.predictOne(predictList);
        assertEquals("WORK_SCREEN", prediction);

        ArrayList<Double> randomFeatures = new ArrayList<>();
        randomFeatures.add((2 * 60 + 10.0) / (24 * 60));
        randomFeatures.add(HOME[0]);
        randomFeatures.add(HOME[1]);
        randomFeatures.add(1.0);
        kernel.updateSingle(randomFeatures, "NEW_LABEL");
        try {
            kernel.fit();
        } catch (UnsupportedOperationException npe) {
            assertEquals(
                    "Partial fit of DecisionTreeKernel() is not supported. " +
                            "Number of labels differs from trained model. " +
                            "Consider setting alwaysKeepData to true or changing Kernel that supports partial fit.",
                    npe.getMessage()
            );
        }


    }

    @Test
    public void storeResults_saveWhenDataIsNull_expectException() throws IOException, ClassNotFoundException {
        DecisionTreeKernel kernel = new DecisionTreeKernel("storeTest");
        try {
            kernel.save(storage);
            fail();
        } catch (IOException e) {
            assertEquals(e.getMessage(), "Not enough data to save, network data is empty");
        }
    }


    // TODO
//    @Test
//    public void storeResults_saveDataAndThenTheta_expectNormalFlow() throws IOException, ClassNotFoundException {
//        List<Pair<List<Double>, String>> data = KernelTest.getTrainingData();
//        DecisionTreeKernel kernel = new DecisionTreeKernel("storeTest", new Random());
//        kernel.data = data;
//        kernel.labels = Arrays.asList("time", "lat", "lon", "headphones");
//        //Omit fit
////        kernel.fit(data);
//        ArrayList<Double> predictList = new ArrayList<>();
//        predictList.add((10 * 60 + 10.0) / (24 * 60));
//        predictList.add(WORK[0]);
//        predictList.add(WORK[1]);
//        predictList.add(1.0);
//
//
//        kernel.save(storage); //Saving data this time
//
//        kernel = new DecisionTreeKernel("storeTest");
//        kernel.restore(storage);
//
//        assertFalse(kernel.node.isEmpty());
//        kernel.node.get(0).getKey();
//        kernel.fit(data);
//
//        String prediction = kernel.predictOne(predictList);
//        assertEquals("WORK_SCREEN", prediction);
//        kernel.save(storage);
//
//        kernel = new DecisionTreeKernel("storeTest");
//        kernel.restore(storage);
//
//        assertTrue(kernel.theta != null);
//
//
//        prediction = kernel.predictOne(predictList);
//        assertEquals("WORK_SCREEN", prediction);
//
//
//    }
}