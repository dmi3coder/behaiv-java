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
        timeProvider = new DayTimeProvider(calendar);
    }

    @Test
    public void constructors_shouldInit() {
        timeProvider = new DayTimeProvider();
        timeProvider = new DayTimeProvider(Calendar.getInstance());
        timeProvider = new DayTimeProvider(true, true);
        timeProvider = new DayTimeProvider(Calendar.getInstance(), true, true);
    }

    @Test
    public void getFeatureCompound() {
        assertEquals(1, timeProvider.availableFeatures().size());
        assertEquals("day_time", timeProvider.availableFeatures().get(0));
        when(calendar.get(Calendar.HOUR_OF_DAY)).thenReturn(10);
        when(calendar.get(Calendar.MINUTE)).thenReturn(30);
        final List<Double> result = timeProvider.getFeature().blockingGet();

        assertEquals(1, result.size());
        assertEquals(10 * 60.0 + 30, result.get(0));
    }

    @Test
    public void getFeatureAll() {
        timeProvider = new DayTimeProvider(calendar, false, true);
        assertEquals(3, timeProvider.availableFeatures().size());
        assertEquals("day_time_hours", timeProvider.availableFeatures().get(0));
        assertEquals("day_time_minutes", timeProvider.availableFeatures().get(1));
        assertEquals("day_time_seconds", timeProvider.availableFeatures().get(2));
        when(calendar.get(Calendar.HOUR_OF_DAY)).thenReturn(10);
        when(calendar.get(Calendar.MINUTE)).thenReturn(30);
        when(calendar.get(Calendar.SECOND)).thenReturn(25);
        final List<Double> result = timeProvider.getFeature().blockingGet();

        assertEquals(3, result.size());
        assertEquals(10.0, result.get(0));
        assertEquals(30.0, result.get(1));
        assertEquals(25.0, result.get(2));
    }

}