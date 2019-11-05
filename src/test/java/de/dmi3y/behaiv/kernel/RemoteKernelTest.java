package de.dmi3y.behaiv.kernel;

import de.dmi3y.behaiv.tools.Pair;
import io.swagger.client.api.KernelApi;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.ArrayList;
import java.util.List;

import static de.dmi3y.behaiv.kernel.KernelTest.HOME;
import static de.dmi3y.behaiv.kernel.KernelTest.WORK;
import static org.junit.Assert.assertEquals;

public class RemoteKernelTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private Kernel testKernel;


    @Before
    public void setUp() throws Exception {
        testKernel = new RemoteKernel("testId", new KernelApi());
    }

    // Disabled test while we need to setup server or mock
//    @Test
    public void predictOne() {
        List<Pair<List<Double>, String>> data = KernelTest.getTrainingData();

        for (int i = 0; i < data.size(); i++) {
            final Pair<List<Double>, String> listStringPair = data.get(i);
            testKernel.updateSingle(listStringPair.getKey(), listStringPair.getValue());

        }

        ArrayList<Double> predictList = new ArrayList<>();
        predictList.add((10 * 60 + 10.0) / (24 * 60));
        predictList.add(WORK[0]);
        predictList.add(WORK[1]);
        predictList.add(1.0);

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

}