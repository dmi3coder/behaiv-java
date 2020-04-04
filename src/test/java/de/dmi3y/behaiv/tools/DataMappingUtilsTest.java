package de.dmi3y.behaiv.tools;

import org.junit.Test;
import org.mockito.internal.matchers.Null;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class DataMappingUtilsTest {

    @Test
    public void toDistinctListOfPairValues_withInputData_returnsExpectedDistinctListOfPairValues() {
        List<Pair<List<Double>, String>> mockData = Arrays.asList(
                getMockPairListDoubleStringData(), getMockPairListDoubleStringData()
        );
        List<String> actual = DataMappingUtils.toDistinctListOfPairValues(mockData);
        assertThat(actual, hasItem("Potato"));
        assertThat(actual.size(), is(1));
    }

    @Test
    public void toDistinctListOfPairValues_withEmptyListOfInputData_returnsEmptyList() {
        List<String> actual = DataMappingUtils.toDistinctListOfPairValues(Collections.<Pair<List<Double>, String>>emptyList());
        assertThat(actual, notNullValue());
        assertTrue(actual.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void toDistinctListOfPairValues_withNull_throwsNullPointerException() {
        DataMappingUtils.toDistinctListOfPairValues(null);
    }

    @Test
    public void toListOfPairKeys_withInputData_returnsExpectedListOfPairKeys() {
        List<Pair<Double, String>> mockData = Arrays.asList(
                getMockPairDoubleStringData(), getMockPairDoubleStringData()
        );
        ArrayList<Double> actual = DataMappingUtils.toListOfPairKeys(mockData);
        assertThat(actual, hasItem(20d));
        assertThat(actual.size(), is(2));
    }

    @Test
    public void toListOfPairKeys_withEmptyListOfInputData_returnsEmptyList() {
        ArrayList<Double> actual = DataMappingUtils.toListOfPairKeys(Collections.<Pair<Double, String>>emptyList());
        assertThat(actual, notNullValue());
        assertTrue(actual.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void toListOfPairKeys__withNull_throwsNullPointerException() {
        DataMappingUtils.toListOfPairKeys(null);
    }

    @Test
    public void toInput2dArray_withInputData_returnsExpected2dArray() {
        double[][] actual = DataMappingUtils.toInput2dArray(Collections.singletonList(getMockPairListDoubleStringData()));
        assertThat(actual.length, is(1));
        assertThat(actual[0].length, is(1));
        assertEquals(20d, actual[0][0], 0.0);
    }

    @Test
    public void toInput2dArray_withEmptyListOfInputData_returnsEmpty2dArray() {
        double[][] actual = DataMappingUtils.toInput2dArray(Collections.<Pair<List<Double>, String>>emptyList());
        assertThat(actual.length, is(0));
    }

    @Test(expected = NullPointerException.class)
    public void toInput2dArray_withNull_throwsNullPointerException() {
        DataMappingUtils.toInput2dArray(null);
    }


    private Pair<Double, String> getMockPairDoubleStringData() {
        return new Pair<>(20d, "Fish");
    }

    private Pair<List<Double>, String> getMockPairListDoubleStringData() {
        List<Double> myDoubleList = new ArrayList<>();
        myDoubleList.add(20d);
        return new Pair<>(myDoubleList, "Potato");
    }
}