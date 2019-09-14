package de.dmi3y.behaiv.kernel;

import de.dmi3y.behaiv.storage.BehaivStorage;
import org.apache.commons.math3.util.Pair;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public abstract class Kernel {

    protected String id;

    public void setId(String id) {
        this.id = id;
    }

    private Long treshold = 10L;

    //list<features>, label
    protected ArrayList<Pair<ArrayList<Double>, String>> data = new ArrayList<>();


    public abstract boolean isEmpty();

    public abstract void fit(ArrayList<Pair<ArrayList<Double>, String>> data);

    public void fit() {
        fit(this.data);
    }

    public void setTreshold(Long treshold) {
        this.treshold = treshold;
    }

    public boolean readyToPredict() {
        return data.size() > treshold;
    }

    public void update(ArrayList<Pair<ArrayList<Double>, String>> data) {
    }

    public void updateSingle(ArrayList<Double> features, String label) {
        data.add(Pair.create(features, label));
    }

    public abstract String predictOne(ArrayList<Double> features);

    public void save(BehaivStorage storage) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(storage.getNetworkFile(id)));
        objectOutputStream.writeObject(data);
        objectOutputStream.close();
    }

    public void restore(BehaivStorage storage) throws IOException {
        ObjectInputStream objectInputStream =
                new ObjectInputStream(new FileInputStream(storage.getNetworkMetadataFile(id)));
        try {
            data = (ArrayList<Pair<ArrayList<Double>, String>>) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        objectInputStream.close();
    }
}
