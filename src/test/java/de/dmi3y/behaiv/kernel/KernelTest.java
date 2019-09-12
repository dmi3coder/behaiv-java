package de.dmi3y.behaiv.kernel;

import org.apache.commons.math3.util.Pair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KernelTest {

    private DummyKernel dummyKernel;

    @Before
    public void setUp() throws Exception {
        dummyKernel = new DummyKernel();
    }

    @Test
    public void setTreshold() {
        dummyKernel.setTreshold(1L);
        dummyKernel.data.add(Pair.create(null,null));
        dummyKernel.data.add(Pair.create(null,null));
        boolean readyToPredict = dummyKernel.readyToPredict();
        assertTrue(readyToPredict);
        dummyKernel.setTreshold(10L);
        readyToPredict = dummyKernel.readyToPredict();
        assertFalse(readyToPredict);
    }
}