package de.dmi3y.behaiv.kernel.decision_tree;

import com.fasterxml.jackson.core.type.TypeReference;
import de.dmi3y.behaiv.kernel.BaseKernel;
import de.dmi3y.behaiv.storage.BehaivStorage;
import de.dmi3y.behaiv.tools.DataMappingUtils;
import de.dmi3y.behaiv.tools.Pair;
import org.apache.commons.lang3.ArrayUtils;
import org.ejml.simple.SimpleMatrix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static de.dmi3y.behaiv.tools.DataMappingUtils.toDistinctListOfPairValues;

public class DecisionTreeKernel extends BaseKernel {
    protected HashMap<String, Object> node;
    protected List<String> labels;

    public DecisionTreeKernel(String id) {
        super(id);
    }

    @Override
    public boolean isEmpty() {
        return node.size() > 0;
    }

    @Override
    public void fit(List<Pair<List<Double>, String>> data) {
        labels = toDistinctListOfPairValues(data);
        final double[][] data1 = DataMappingUtils.toInputLabel2dArray(data, labels);
        final SimpleMatrix dataset = new SimpleMatrix(data1);
        node = DecisionTreeUtils.buildTree(dataset, 5, 1);
    }

    @Override
    public String predictOne(List<Double> features) {
        final double[] doubles = ArrayUtils.toPrimitive(features.toArray(new Double[0]));
        return labels.get(DecisionTreeUtils.predict(node, new SimpleMatrix(new double[][]{doubles})).intValue());
    }

    @Override
    public boolean isPartialFitAllowed() {
        return true;
    }

    @Override
    public void save(BehaivStorage storage) throws IOException {
        if ((node == null || node.isEmpty()) && (data == null || data.isEmpty())) {
            throw new IOException("Not enough data to save, network data is empty");
        }
        final File networkFile = storage.getNetworkFile(id);
        if (labels == null || labels.isEmpty()) {
            labels = toDistinctListOfPairValues(data);
        }
        if (labels.isEmpty()) {
            String message;
            message = "Kernel collected data but failed to get labels, couldn't save network.";
            throw new IOException(message);
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(networkFile))) {
            objectOutputStream.writeObject(node);
        }
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(storage.getNetworkMetadataFile(id)))) {
            writer.write(objectMapper.writeValueAsString(labels));
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.save(storage);
    }

    @Override
    public void restore(BehaivStorage storage) throws IOException {
        final File networkFile = storage.getNetworkFile(id);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(networkFile))) {
            node = (HashMap<String, Object>) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        super.restore(storage);
        try (final BufferedReader reader = new BufferedReader(new FileReader(storage.getNetworkMetadataFile(id)))) {
            final TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {
            };
            final String labelsData = reader.readLine();
            if (labelsData == null) {
                labels = new ArrayList<>();
            } else {
                labels = objectMapper.readValue(labelsData, typeReference);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
