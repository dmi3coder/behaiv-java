package de.dmi3y.behaiv.session;

import de.dmi3y.behaiv.provider.Provider;
import de.dmi3y.behaiv.provider.TestProvider;
import de.dmi3y.behaiv.provider.TestSleepProvider;
import de.dmi3y.behaiv.tools.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

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

    @Test
    public void captureSession_fetchProviderData_receiveInCorrectOrder() {
        final List<Provider> providers = Arrays.asList(
                new TestSleepProvider(new String[]{"ord1", "ord2"}, new Double[]{1.0, 2.0}, 200),
                new TestSleepProvider(new String[]{"ord3"}, new Double[]{3.0}, 500),
                new TestSleepProvider(new String[]{"ord4", "ord5"}, new Double[]{4.0, 5.0}, 1)
        );
        session = new CaptureSession(providers);
        session.startBlocking(null);
        final List<Pair<Double, String>> features = session.getFeatures();
        for (int i = 0; i < 5; i++) {
            assertEquals(features.get(i).getKey(), i + 1.0);
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(features.get(i).getValue(), "ord" + (i + 1));
        }

    }
}