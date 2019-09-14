package de.dmi3y.behaiv.provider;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class DayTimeProvider implements Provider {


    private boolean compound;
    private boolean secondsEnabled;
    private Calendar calendar;

    public DayTimeProvider() {
        this.calendar = Calendar.getInstance();
        compound = true;
        secondsEnabled = false;

    }

    public DayTimeProvider(@Nullable Calendar calendar) {
        this.calendar = calendar;
        compound = true;
        secondsEnabled = false;
    }

    public DayTimeProvider(boolean compound, boolean secondsEnabled) {
        this(Calendar.getInstance(), compound, secondsEnabled);
    }

    public DayTimeProvider(@Nullable Calendar calendar, boolean compound, boolean secondsEnabled) {
        this.calendar = calendar;
        this.compound = compound;
        this.secondsEnabled = secondsEnabled;
    }


    @Override
    public List<String> availableFeatures() {
        if (compound) {
            return Collections.singletonList("day_time");
        }
        final List<String> strings = Arrays.asList("day_time_hours", "day_time_minutes");
        if (secondsEnabled) {
            strings.add("day_time_seconds");
        }
        return strings;
    }

    @Override
    public Observable<List<Double>> subscribeFeatures() {
        return null;
    }

    @Override
    public Single<List<Double>> getFeature() {
        final int hours = calendar.get(Calendar.HOUR_OF_DAY);
        final int minutes = calendar.get(Calendar.MINUTE);
        if (compound) {
            return Single.just(Collections.singletonList(hours * 60.0 + minutes));
        }
        final List<Double> listToSend = Arrays.asList(
                (double) hours,
                (double) minutes
        );
        if (secondsEnabled) {
            final int seconds = calendar.get(Calendar.SECOND);
            listToSend.add((double) seconds);
        }
        return Single.just(listToSend);
    }
}
