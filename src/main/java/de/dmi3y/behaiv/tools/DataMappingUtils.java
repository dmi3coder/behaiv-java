package de.dmi3y.behaiv.tools;

import org.apache.commons.lang3.ArrayUtils;
import tech.donau.behaiv.proto.Data;
import tech.donau.behaiv.proto.Prediction;
import tech.donau.behaiv.proto.PredictionSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class DataMappingUtils
{

    private DataMappingUtils() {
        // Unused utility class
    }

    public static PredictionSet createPredictionSet(List<Pair<List<Double>, String>> data) {
        final PredictionSet.Builder predictionSetBuilder = PredictionSet.newBuilder();
        for (int i = 0; i < data.size(); i++) {
            final List<Double> weights = data.get(i).getKey();
            final ArrayList<Data> row = new ArrayList<>();
            for (int j = 0; j < weights.size(); j++) {
                row.add(Data.newBuilder().setKey("key"+j).setValue(weights.get(j)).build());
            }
            predictionSetBuilder.addPrediction(
                    Prediction.newBuilder()
                            .addAllData(row)
                            .setLabel(data.get(i).getValue())
                            .build()
            );
        }
        return predictionSetBuilder.build();
    }

    public static List<Pair<List<Double>, String>> dataFromPredictionSet(PredictionSet predictionSet) {
        final ArrayList<Pair<List<Double>, String>> data = new ArrayList<>();
        for (int i = 0; i < predictionSet.getPredictionCount(); i++) {
            final Prediction prediction = predictionSet.getPrediction(i);
            final List<Data> dataList = prediction.getDataList();
            final String label = prediction.getLabel();
            final List<Double> entries = new ArrayList<>();
            for (Data entry : dataList) {
                entries.add(entry.getValue());
            }
            data.add(Pair.create(entries, label));
        }
        return data;
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

    public static double[][] toInputLabel2dArray(List<Pair<List<Double>, String>> data, List<String> labels) {
        double[][] input2dArray = new double[data.size()][];
        int i = 0;
        for(Pair<List<Double>, String> dataPair : data) {
            double[] doubleArray = ArrayUtils.toPrimitive(dataPair.getKey().toArray(new Double[0]));
            doubleArray = Arrays.copyOf(doubleArray, doubleArray.length + 1);
            doubleArray[doubleArray.length - 1] = labels.indexOf(dataPair.getValue());
            input2dArray[i] = doubleArray;
            i++;
        }
        return input2dArray;
    }

}
