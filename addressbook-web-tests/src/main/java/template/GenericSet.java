package template;

/**
 * Created by Olga on 07.04.2016.
 */
public class GenericSet<V> {

    private int[] keys;
    private V[] values;
    private int capacity;
    private int size;

    public GenericSet(int size, V[] values) {
        this.size = size;
        this.capacity = 0;
        this.keys = new int[size];
        this.values = values;
    }

    public int getSize() {
        return capacity;
    }

    public boolean put(V value) {
        if ((capacity < size) && (searchKeyIndex(value) == -1)) {
            keys[capacity] = value.hashCode();
            values[capacity++] = value;
            return true;
        }
        return false;
    }

    public boolean exists(V value) {
        return searchKeyIndex(value) >= 0;
    }

    private int searchKeyIndex(V value) {
        for (int i = 0; i < capacity; i++) {
            if (keys[i] == value.hashCode()) {
                return i;
            }
        }
        return -1;
    }
}
