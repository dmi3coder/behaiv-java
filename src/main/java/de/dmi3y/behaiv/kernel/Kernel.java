package de.dmi3y.behaiv.kernel;

import org.apache.commons.math3.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public abstract class Kernel {

    private Long treshold = 10L;

    //list<features>, label
    protected ArrayList<Pair<ArrayList<Double>, String>> data = new ArrayList<>();

    public abstract void fit(ArrayList<Pair<ArrayList<Double>, String>> data);

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

    public void save(File file, File metadata) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        objectOutputStream.writeObject(data);
        objectOutputStream.close();
    }

    public void restore(File file, File metadata) throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
        try {
            data = (ArrayList<Pair<ArrayList<Double>, String>>) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        objectInputStream.close();
    }
}
