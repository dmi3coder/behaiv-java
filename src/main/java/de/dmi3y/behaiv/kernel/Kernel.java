package de.dmi3y.behaiv.kernel;

import com.google.gson.Gson;
import de.dmi3y.behaiv.storage.BehaivStorage;
import org.apache.commons.math3.util.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Kernel {

    protected String id;
    protected Long treshold = 10L;

    public Kernel(String id) {
        this.id = id;
    }


    public void setId(String id) {
        this.id = id;
    }


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
        final Gson gson = new Gson();

        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(storage.getNetworkFile(id)))) {
            writer.write(gson.toJson(data));
        }
    }

    public void restore(BehaivStorage storage) throws IOException {
        final Gson gson = new Gson();

        try (final BufferedReader reader = new BufferedReader(new FileReader(storage.getNetworkFile(id)))) {
            data = ((ArrayList<Pair<ArrayList<Double>, String>>) gson.fromJson(reader.readLine(), data.getClass()));
        }
    }
}
