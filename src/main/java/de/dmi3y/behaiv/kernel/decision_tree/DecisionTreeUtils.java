package de.dmi3y.behaiv.kernel.decision_tree;

import de.dmi3y.behaiv.tools.Pair;
import org.ejml.data.DMatrixRMaj;
import org.ejml.simple.SimpleMatrix;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public final class DecisionTreeUtils {
    private static final String KEY_GROUPS = "groups";
    private static final String KEY_LEFT = "left";
    private static final String KEY_RIGHT = "right";

    private DecisionTreeUtils() {
    }

    public static Double predict(HashMap<String, Object> node, SimpleMatrix row) {
        final Double index = (Double) node.get("index");
        final Double value = (Double) node.get("value");
        if (row.get(index.intValue()) < value) {
            if (node.get(KEY_LEFT) instanceof HashMap) {
                return predict((HashMap<String, Object>) node.get(KEY_LEFT), row);
            } else {
                return (Double) node.get(KEY_LEFT);
            }
        } else {
            if (node.get(KEY_RIGHT) instanceof HashMap) {
                return predict((HashMap<String, Object>) node.get(KEY_RIGHT), row);
            } else {
                return (Double) node.get(KEY_RIGHT);
            }
        }
    }


    public static HashMap<String, Object> buildTree(SimpleMatrix dataset, int max_depth, int min_size) {
        HashMap<String, Object> rootNode = splitDataset(dataset);
        split(rootNode, max_depth, min_size, 1);
        return rootNode;
    }

    private static void split(HashMap<String, Object> node, int max_depth, int min_size, int depth) {
        final Groups group = (Groups) node.get(KEY_GROUPS);
        final ArrayList<SimpleMatrix> left = group.getKey();
        final ArrayList<SimpleMatrix> right = group.getValue();
        node.remove(KEY_GROUPS);
        if (left.isEmpty() || right.isEmpty()) {
            final ArrayList<SimpleMatrix> both = new ArrayList<>();
            both.addAll(left);
            both.addAll(right);
            node.put(KEY_LEFT, toTerminalNode(both));
            node.put(KEY_RIGHT, toTerminalNode(both));
            return;
        }

        if (depth >= max_depth) {
            node.put(KEY_LEFT, toTerminalNode(left));
            node.put(KEY_RIGHT, toTerminalNode(right));
            return;
        }
        splitSide(node, max_depth, min_size, depth, left, KEY_LEFT);

        splitSide(node, max_depth, min_size, depth, right, KEY_RIGHT);
    }

    private static void splitSide(HashMap<String, Object> node, int max_depth, int min_size,
                                  int depth, ArrayList<SimpleMatrix> side, String sideKey) {
        if (side.size() <= min_size) {
            node.put(sideKey, toTerminalNode(side));
        } else {
            double[][] leftSet = new double[side.size()][side.get(0).numCols()];

            for (int i = 0; i < side.size(); i++) {
                leftSet[i] = ((DMatrixRMaj) side.get(i).getMatrix()).data;
            }

            node.put(sideKey, splitDataset(new SimpleMatrix(leftSet)));
            split((HashMap<String, Object>) node.get(sideKey), max_depth, min_size, depth + 1);
        }
    }


    private static Double toTerminalNode(ArrayList<SimpleMatrix> group) {
        final ArrayList<Double> outcomes = new ArrayList<>();
        for (SimpleMatrix row : group) {
            outcomes.add(row.get(row.numCols() - 1));
        }

        return Collections.max(outcomes, new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                final Integer o1Freq = Collections.frequency(outcomes, o1);
                return o1Freq.compareTo(Collections.frequency(outcomes, o2));
            }
        });
    }

    private static HashMap<String, Object> splitDataset(SimpleMatrix dataset) {
        final HashSet<Double> classes = new HashSet<>();
        final SimpleMatrix y = dataset.cols(dataset.numCols() - 1, dataset.numCols());
        for (int i = 0; i < y.numRows(); i++) {
            classes.add(y.get(i, 0));
        }
        double bIndex = 999.0, bValue = 999.0, bScore = 999.0;
        Groups bGroups = null;
        for (int index = 0; index < dataset.numCols(); index++) {
            for (int rowi = 0; rowi < dataset.numRows(); rowi++) {
                final SimpleMatrix row = dataset.rows(rowi, rowi + 1);
                final Groups groups = testSplit(index, row.get(index), dataset);
                final double gini = giniIndex(groups, classes);
                if (gini < bScore) {
                    bIndex = index;
                    bValue = row.get(index);
                    bScore = gini;
                    bGroups = groups;
                }
            }
        }
        final double finalBIndex = bIndex;
        final double finalBValue = bValue;
        final Groups finalBGroups = bGroups;
        return new HashMap<String, Object>() {{
            put("index", finalBIndex);
            put("value", finalBValue);
            put(KEY_GROUPS, finalBGroups);
        }};
    }

    private static double giniIndex(Groups groups, Collection<Double> classes) {
        double instanceNumber = groups.total();
        double gini = 0.0d;
        for (int i = 0; i < 2; i++) {
            ArrayList<SimpleMatrix> group = i == 0 ? groups.getKey() : groups.getValue();
            final int size = group.size();

            if (size == 0) {
                continue;
            }
            double score = 0.0;
            for (Double classValue : classes) {
                int classValueCount = 0;
                for (SimpleMatrix value : group) {
                    final double v = value.get(value.numCols() - 1);
                    if (v == classValue) {
                        classValueCount += 1;
                    }
                }
                int p = classValueCount / size;
                score += p * p;
            }
            gini += (1.0 - score) * (size / instanceNumber);
        }
        return gini;
    }

    private static Groups testSplit(int col, double value, SimpleMatrix dataset) {
        final ArrayList<SimpleMatrix> left = new ArrayList<>(), right = new ArrayList<>();
        for (int rowi = 0; rowi < dataset.numRows(); rowi++) {
            final SimpleMatrix row = dataset.rows(rowi, rowi + 1);
            if (row.get(col) < value) {
                left.add(row);
            } else {
                right.add(row);
            }
        }
        return new Groups(left, right);
    }


    public static class Groups extends Pair<ArrayList<SimpleMatrix>, ArrayList<SimpleMatrix>> implements Serializable {
        public Groups(ArrayList<SimpleMatrix> simpleMatrices, ArrayList<SimpleMatrix> simpleMatrices2) {
            super(simpleMatrices, simpleMatrices2);
        }

        public int total() {
            return getKey().size() + getValue().size();
        }
    }
}
