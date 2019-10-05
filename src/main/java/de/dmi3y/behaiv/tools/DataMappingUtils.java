package de.dmi3y.behaiv.tools;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class DataMappingUtils
{

    private DataMappingUtils() {
        // Unused utility class
    }


    public static List<String> toDistinctListOfPairValues(List<Pair<List<Double>, String>> data) {
        Set<String> setOfValues = new HashSet<>();
        for(Pair<List<Double>, String> arrayListStringPair : data ) {
            setOfValues.add(arrayListStringPair.getValue());
        }
        return new ArrayList<>(setOfValues);
    }

    public static ArrayList<Double> toListOfPairKeys(List<Pair<Double, String>> featurePairList) {
        ArrayList<Double> doubleArrayList = new ArrayList<>();
        for (Pair<Double, String> doubleStringPair : featurePairList) {
            doubleArrayList.add(doubleStringPair.getKey());
        }
        return doubleArrayList;
    }

    public static double[][] toInput2dArray(List<Pair<List<Double>, String>> data) {
        double[][] input2dArray = new double[data.size()][];
        int i = 0;
        for(Pair<List<Double>, String> dataPair : data) {
            double[] doubleArray = ArrayUtils.toPrimitive(dataPair.getKey().toArray(new Double[0]));
            input2dArray[i] = doubleArray;
            i++;
        }
        return input2dArray;
    }

}
