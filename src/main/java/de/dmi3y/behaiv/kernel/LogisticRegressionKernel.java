package de.dmi3y.behaiv.kernel;

import com.google.gson.Gson;
import de.dmi3y.behaiv.storage.BehaivStorage;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.util.Pair;
import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.cpu.nativecpu.NDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.Nesterovs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LogisticRegressionKernel extends Kernel {

    private List<String> labels = new ArrayList<>();
    private MultiLayerNetwork network;

    @Override
    public boolean isEmpty() {
        return network == null && data.size() == 0;
    }

    @Override
    public void fit(ArrayList<Pair<ArrayList<Double>, String>> data) {
        this.data = data;
        labels = this.data.stream().map(Pair::getSecond).distinct().collect(Collectors.toList());
        if (readyToPredict()) {
            //This part takes too long. Maybe use native libs?
            OutputLayer outputLayer = new OutputLayer.Builder()
                    .nIn(this.data.get(0).getFirst().size())
                    .nOut(labels.size())
                    .weightInit(WeightInit.XAVIER)
                    .activation(Activation.SOFTMAX)
                    .build();
            MultiLayerConfiguration config = new NeuralNetConfiguration.Builder()
                    .updater(new Adam(0.5))
                    .weightInit(WeightInit.XAVIER)
                    .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                    .updater(new Nesterovs(0.9))
                    .list()
                    .layer(0, outputLayer)
                    .build();

            network = new MultiLayerNetwork(config);
            network.init();

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

            NDArray inputResults = new NDArray(inputs);
            NDArray outputResults = new NDArray(labelArray);

            final DataSet dataSet = new DataSet(inputResults, outputResults);
            final List<DataSet> list = dataSet.asList();
            Collections.shuffle(list);
            final ListDataSetIterator<DataSet> iterator = new ListDataSetIterator<>(list);
            for (int i = 0; i < 100; i++) {
                iterator.reset();
                network.fit(iterator);
            }
        }

    }

    @Override
    public void updateSingle(ArrayList<Double> features, String label) {
        super.updateSingle(features, label);
    }

    @Override
    public String predictOne(ArrayList<Double> features) {
        NDArray testInput = new NDArray(new double[][]{ArrayUtils.toPrimitive(features.toArray(new Double[0]))});
        int[] predict = network.predict(testInput);
        return labels.get(predict[0]);
    }

    @Override
    public void save(BehaivStorage storage) throws IOException {
        ModelSerializer.writeModel(network, storage.getNetworkFile(id), true);
        final Gson gson = new Gson();

        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(storage.getNetworkMetadataFile(id)))) {
            writer.write(gson.toJson(labels));
        }
    }

    @Override
    public void restore(BehaivStorage storage) throws IOException {
        network = ModelSerializer.restoreMultiLayerNetwork(storage.getNetworkFile(id));
        final Gson gson = new Gson();

        try (final BufferedReader reader = new BufferedReader(new FileReader(storage.getNetworkMetadataFile(id)))) {
            labels = ((List<String>) gson.fromJson(reader.readLine(), labels.getClass()));
        }
    }


}
