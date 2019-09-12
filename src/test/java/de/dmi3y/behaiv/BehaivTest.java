package de.dmi3y.behaiv;

import de.dmi3y.behaiv.kernel.DummyKernel;
import de.dmi3y.behaiv.session.CaptureSession;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BehaivTest {

    private Behaiv behaiv;
    private DummyKernel testKernel;

    @Before
    public void setUp() throws Exception {
        testKernel = new DummyKernel();
        behaiv = Behaiv.with(testKernel);
    }

    @Test
    public void setKernel() {
        DummyKernel newKernel = new DummyKernel();
        behaiv.setKernel(newKernel);
        assertTrue(!testKernel.equals(newKernel));

    }

    @Test
    public void stopCapturing_whenDiscard_sessionShouldBeNull() {
        behaiv.startCapturing(false);
        CaptureSession currentSession = behaiv.getCurrentSession();
        assertNotNull(currentSession);
        behaiv.stopCapturing(true);
        assertNull(behaiv.getCurrentSession());
    }
}