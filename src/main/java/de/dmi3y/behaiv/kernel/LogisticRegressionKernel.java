package de.dmi3y.behaiv.kernel;

import com.fasterxml.jackson.core.type.TypeReference;
import de.dmi3y.behaiv.kernel.logistic.LogisticUtils;
import de.dmi3y.behaiv.storage.BehaivStorage;
import de.dmi3y.behaiv.tools.DataMappingUtils;
import de.dmi3y.behaiv.tools.Pair;
import org.apache.commons.lang3.ArrayUtils;
import org.ejml.simple.SimpleMatrix;
import tech.donau.behaiv.proto.PredictionSet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static de.dmi3y.behaiv.tools.DataMappingUtils.toDistinctListOfPairValues;
import static de.dmi3y.behaiv.tools.DataMappingUtils.toInput2dArray;

public class LogisticRegressionKernel extends BaseKernel {

    protected List<String> cachedLables = new ArrayList<>();
    private Random rand;
    protected SimpleMatrix theta;

    public LogisticRegressionKernel(String id, Random rand) {
        super(id);
        this.rand = rand;
        this.partialFitAllowed = false;
    }

    public LogisticRegressionKernel(String id) {
        this(id, new Random());
    }

    @Override
    public boolean isEmpty() {
        return theta == null && data.getPredictionCount() == 0;
    }

    @Override
    public void fit(PredictionSet data) {
        this.data = data;
        if (data.getDynamicColumns()) {
            throw new UnsupportedOperationException("LogisticRegressionKernel doesn't support dynamic fields");
        }
        for (int i = 0; i < data.getPredictionList().size(); i++) {
            cachedLables.add(data.getPredictionList().get(i).getLabel());
        }
        if (readyToPredict()) {
            //features
            double[][] inputs = toInput2dArray(data);

            //labels
            double[][] labelArray = new double[data.getPredictionCount()][cachedLables.size()];
            for (int i = 0; i < data.getPredictionCount(); i++) {
                int dummyPos = cachedLables.indexOf(data.getPrediction(i).getLabel());
                labelArray[i][dummyPos] = 1.0;
            }

            //output layer
            final SimpleMatrix inputMatrix = new SimpleMatrix(inputs);
            final SimpleMatrix outputMatrix = new SimpleMatrix(labelArray);
            //3x4?

            //TODO dilemma on if we need to re-do theta or keep it as-is, if new features arrising we'll have a problem
            if (theta == null || (theta.numCols() != cachedLables.size() && alwaysKeepData)) {
                theta = SimpleMatrix.random_DDRM(inputMatrix.numCols(), outputMatrix.numCols(), 0, 1, rand);
            } else if (theta.numCols() != cachedLables.size() && !alwaysKeepData) {
                throw new UnsupportedOperationException(
                        "Partial fit of LogisticRegressionKernel is not supported. " +
                                "Number of labels differs from trained model." +
                                " Consider setting alwaysKeepData to true or changing Kernel that supports partial fit."
                );
            }

            for (int i = 0; i < 10000; i++) {
                theta = LogisticUtils.gradientDescent(inputMatrix, theta, outputMatrix, 0.1);
            }

        }
    }

    @Override
    public void fit(List<Pair<List<Double>, String>> data) {
        this.data = DataMappingUtils.createPredictionSet(data);
        this.cachedLables = toDistinctListOfPairValues(data);
        if (readyToPredict()) {
            //features
            double[][] inputs = toInput2dArray(data);

            //labels
            double[][] labelArray = new double[data.size()][cachedLables.size()];
            for (int i = 0; i < data.size(); i++) {
                int dummyPos = cachedLables.indexOf(data.get(i).getValue());
                labelArray[i][dummyPos] = 1.0;
            }

            //output layer
            final SimpleMatrix inputMatrix = new SimpleMatrix(inputs);
            final SimpleMatrix outputMatrix = new SimpleMatrix(labelArray);
            //3x4?

            //TODO dilemma on if we need to re-do theta or keep it as-is, if new features arrising we'll have a problem
            if (theta == null || (theta.numCols() != cachedLables.size() && alwaysKeepData)) {
                theta = SimpleMatrix.random_DDRM(inputMatrix.numCols(), outputMatrix.numCols(), 0, 1, rand);
            } else if (theta.numCols() != cachedLables.size() && !alwaysKeepData) {
                throw new UnsupportedOperationException(
                        "Partial fit of LogisticRegressionKernel is not supported. " +
                                "Number of labels differs from trained model." +
                                " Consider setting alwaysKeepData to true or changing Kernel that supports partial fit."
                );
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
    public void updateSingle(List<Double> features, String label) {
        super.updateSingle(features, label);
    }

    @Override
    public String predictOne(List<Double> features) {
        final double[] doubles = ArrayUtils.toPrimitive(features.toArray(new Double[0]));

        final SimpleMatrix inputs = new SimpleMatrix(new double[][]{doubles});

        final SimpleMatrix output = LogisticUtils.sigmoid(inputs.mult(theta));

        int maxPosition = 0;
        for (int i = 0; i < output.numCols(); i++) {
            if (output.get(0, maxPosition) < output.get(0, i)) {
                maxPosition = i;
            }
        }
        return cachedLables.get(maxPosition);
    }

    @Override
    public void save(BehaivStorage storage) throws IOException {
        if (theta == null && (data == null || data.getPredictionList().isEmpty())) {
            throw new IOException("Not enough data to save, network data is empty");
        }
        if (cachedLables == null || cachedLables.isEmpty()) {
            cachedLables = new ArrayList<>();
            for (int i = 0; i < data.getPredictionList().size(); i++) {
                cachedLables.add(data.getPredictionList().get(i).getLabel());
            }
        }
        if (cachedLables.isEmpty()) {
            String message;
            message = "Kernel collected data but failed to get labels, couldn't save network.";
            throw new IOException(message);
        }
        if (theta == null) {
            super.save(storage);
        } else {
            if (alwaysKeepData) {
                super.save(storage);
            }
            theta.saveToFileBinary(storage.getNetworkFile(id).toString());
            try (final BufferedWriter writer = new BufferedWriter(new FileWriter(storage.getNetworkMetadataFile(id)))) {

                writer.write(objectMapper.writeValueAsString(cachedLables));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void restore(BehaivStorage storage) throws IOException {
        boolean failedToGetTheta = false;
        try {
            theta = SimpleMatrix.loadBinary(storage.getNetworkFile(id).toString());
        } catch (IOException exception) {
            failedToGetTheta = true;
        }

        if (failedToGetTheta || alwaysKeepData) {
            super.restore(storage);
        }

        try (final BufferedReader reader = new BufferedReader(new FileReader(storage.getNetworkMetadataFile(id)))) {
            final TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {
            };
            final String labelsData = reader.readLine();
            if (labelsData == null) {
                cachedLables = new ArrayList<>();
            } else {
                cachedLables = objectMapper.readValue(labelsData, typeReference);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
