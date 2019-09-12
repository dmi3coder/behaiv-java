package de.dmi3y.behaiv.kernel;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.util.Pair;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.cpu.nativecpu.NDArray;
import org.nd4j.linalg.learning.config.Nesterovs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogisticRegressionKernel extends Kernel {

    private List<String> labels = new ArrayList<>();
    private OutputLayer outputLayer;
    private MultiLayerNetwork network;

    @Override
    public void fit(ArrayList<Pair<ArrayList<Double>, String>> data) {
        this.data = data;
        labels = this.data.stream().map(Pair::getSecond).distinct().collect(Collectors.toList());
        if (readyToPredict()) {
            outputLayer = new OutputLayer.Builder()
                    .nIn(this.data.get(0).getFirst().size())
                    .nOut(labels.size())
                    .weightInit(WeightInit.DISTRIBUTION)
                    .activation(Activation.SOFTMAX)
                    .build();
            MultiLayerConfiguration config = new NeuralNetConfiguration.Builder().seed(123).learningRate(0.1).iterations(100).optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).updater(new Nesterovs(0.9)) //High Level Configuration
                    .list() //For configuring MultiLayerNetwork we call the list method
                    .layer(0, outputLayer) //    <----- output layer fed here
                    .pretrain(true).backprop(true) //Pretraining and Backprop Configuration
                    .build();//Building Configuration

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

            network.fit(inputResults, outputResults);
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
}
