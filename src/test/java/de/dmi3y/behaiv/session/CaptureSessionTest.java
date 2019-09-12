package de.dmi3y.behaiv.session;

import de.dmi3y.behaiv.provider.TestProvider;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.InputMismatchException;

public class CaptureSessionTest {

    private CaptureSession session;

    @Before
    public void setUp() throws Exception {
        session = new CaptureSession(Arrays.asList(new TestProvider(new String[]{"name1", "name2"}, new Double[]{1.0})));
    }

    @Test(expected = InputMismatchException.class)
    public void startBlocking() {
        session.startBlocking(null);
    }
}