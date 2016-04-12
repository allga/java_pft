package template;

/**
 * Created by Olga on 07.04.2016.
 */
public class GenericMap<K, V> {

    private K[] keys;
    private V[] values;
    private int capacity;
    private int size;

    public GenericMap(int size, K[] keys, V[] values) {
        this.size = size;
        this.capacity = 0;
        this.keys = keys;
        this.values = values;
    }

    public int getSize() {
        return capacity;
    }

    public boolean put(K key, V value) {
        if ((capacity < size) && (searchKeyIndex(key) == -1)) {
            keys[capacity] = key;
            values[capacity++] = value;
            return true;
        }
        return false;
    }

    public V get(K key) {
        int i = searchKeyIndex(key);
        if (i >= 0) {
            return values[i];
        }
        return null;
    }

    private int searchKeyIndex(K key) {
        for (int i = 0; i < capacity; i++) {
            if (keys[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }
}
