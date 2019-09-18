package de.dmi3y.behaiv.kernel;

import com.google.gson.Gson;
import de.dmi3y.behaiv.kernel.logistic.LogisticUtils;
import de.dmi3y.behaiv.storage.BehaivStorage;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.util.Pair;
import org.ejml.simple.SimpleMatrix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LogisticRegressionKernel extends Kernel {

    private List<String> labels = new ArrayList<>();
    private Random rand;
    private SimpleMatrix theta;

    public LogisticRegressionKernel(Random rand) {
        this.rand = rand;
    }

    public LogisticRegressionKernel() {
        rand = new Random();
    }

    @Override
    public boolean isEmpty() {
        return theta == null && data.size() == 0;
    }

    @Override
    public void fit(ArrayList<Pair<ArrayList<Double>, String>> data) {
        this.data = data;
        labels = this.data.stream().map(Pair::getSecond).distinct().collect(Collectors.toList());
        if (readyToPredict()) {


            //features
            double[][] inputs = this.data.stream().map(Pair::getFirst).map(l -> l.toArray(new Double[0]))
                    .map(ArrayUtils::toPrimitive)
                    .toArray(double[][]::new);

            //labels
            double[][] labelArray = new double[data.size()][labels.size()];
            for (int i = 0; i < data.size(); i++) {
                int dummyPos = labels.indexOf(data.get(i).getSecond());
                labelArray[i][dummyPos] = 1.0;
            }

            //output layer
            final SimpleMatrix inputMatrix = new SimpleMatrix(inputs);
            final SimpleMatrix outputMatrix = new SimpleMatrix(labelArray);
            //3x4?

            //TODO dilemma on if we need to re-do theta or keep it as-is, if new features arrising we'll have a problem
            if (theta == null || theta.numCols() != labels.size()) {
                theta = SimpleMatrix.random_DDRM(inputMatrix.numCols(), outputMatrix.numCols(), 0, 1, rand);

            }

            for (int i = 0; i < 10000; i++) {
                theta = LogisticUtils.gradientDescent(inputMatrix, theta, outputMatrix, 0.1);
            }

        }

    }

    @Override
    public boolean readyToPredict() {
        return theta != null || super.readyToPredict();
    }

    @Override
    public void updateSingle(ArrayList<Double> features, String label) {
        super.updateSingle(features, label);
    }

    @Override
    public String predictOne(ArrayList<Double> features) {


        final double[] doubles = ArrayUtils.toPrimitive(features.toArray(new Double[0]));

        final SimpleMatrix inputs = new SimpleMatrix(new double[][]{doubles});

        final SimpleMatrix output = LogisticUtils.sigmoid(inputs.mult(theta));

        int maxPosition = 0;
        for (int i = 0; i < output.numCols(); i++) {
            if (output.get(0, maxPosition) < output.get(0, i)) {
                maxPosition = i;
            }
        }
        return labels.get(maxPosition);
    }

    @Override
    public void save(BehaivStorage storage) throws IOException {

        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(storage.getNetworkMetadataFile(id)))) {
            theta.saveToFileBinary(storage.getNetworkFile(id).toString());
            final Gson gson = new Gson();
            writer.write(gson.toJson(labels));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restore(BehaivStorage storage) throws IOException {

        try (final BufferedReader reader = new BufferedReader(new FileReader(storage.getNetworkMetadataFile(id)))) {
            theta = SimpleMatrix.loadBinary(storage.getNetworkFile(id).toString());
            final Gson gson = new Gson();
            labels = ((List<String>) gson.fromJson(reader.readLine(), labels.getClass()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
