package de.dmi3y.behaiv.provider;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DayTimeProviderTest {

    private DayTimeProvider timeProvider;
    private Calendar calendar;

    @Before
    public void setUp() throws Exception {
        calendar = mock(Calendar.class);
        timeProvider = new DayTimeProvider(calendar, true, false);
    }

    @Test
    public void getFeature() {
        assertEquals(1, timeProvider.availableFeatures().size());
        assertEquals("day_time", timeProvider.availableFeatures().get(0));
        when(calendar.get(Calendar.HOUR_OF_DAY)).thenReturn(10);
        when(calendar.get(Calendar.MINUTE)).thenReturn(30);
        final List<Double> result = timeProvider.getFeature().blockingGet();

        assertEquals(1, result.size());
        assertEquals(10 * 60.0 + 30, result.get(0));


    }
}