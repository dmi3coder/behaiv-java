package de.dmi3y.behaiv;

import de.dmi3y.behaiv.kernel.DummyKernel;
import de.dmi3y.behaiv.kernel.Kernel;
import de.dmi3y.behaiv.session.CaptureSession;
import de.dmi3y.behaiv.storage.BehaivStorage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class BehaivTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

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
        assertFalse(testKernel.equals(newKernel));

    }

    @Test
    public void stopCapturing_whenDiscard_sessionShouldBeNull() {
        behaiv.startCapturing(false);
        CaptureSession currentSession = behaiv.getCurrentSession();
        assertNotNull(currentSession);
        behaiv.stopCapturing(true);
        assertNull(behaiv.getCurrentSession());
    }


    @Test
    public void startCapturing_tryToRestore_restoreNetwork() throws IOException {
        final Kernel mockKernel = mock(Kernel.class);
        final BehaivStorage storage = mock(BehaivStorage.class);
        final Behaiv behaiv = Behaiv.with("testId")
                .setKernel(mockKernel)
                .setStorage(storage);

        when(mockKernel.isEmpty()).thenReturn(true);

        behaiv.startCapturing(false);
        verify(mockKernel, times(1)).restore(storage);

    }

    @Test
    public void startCapturing_withIOException_continueWithoutSaving() throws IOException {
        final Kernel testKernel = mock(Kernel.class);
        final BehaivStorage storage = mock(BehaivStorage.class);
        final Behaiv behaiv = Behaiv.with("testId")
                .setKernel(testKernel)
                .setStorage(storage);

        when(testKernel.isEmpty()).thenReturn(true);
        doThrow(IOException.class).when(testKernel).restore(storage);
        behaiv.startCapturing(false);
    }
}