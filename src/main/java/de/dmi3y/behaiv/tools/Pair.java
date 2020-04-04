package de.dmi3y.behaiv.tools;

public class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K k, V v) {
        this.key = k;
        this.value = v;
    }


    public Pair(de.dmi3y.behaiv.tools.Pair<? extends K, ? extends V> entry) {
        this(entry.getKey(), entry.getValue());
    }

    public Pair() {
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public int hashCode() {
        int result = this.key == null ? 0 : this.key.hashCode();
        int h = this.value == null ? 0 : this.value.hashCode();
        result = 37 * result + h ^ h >>> 16;
        return result;
    }

    public String toString() {
        return "[" + this.getKey() + ", " + this.getValue() + "]";
    }

    public static <K, V> Pair<K, V> create(K k, V v) {
        return new Pair<K, V>(k, v);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return key.equals(pair.key) &&
                value.equals(pair.value);
    }
}