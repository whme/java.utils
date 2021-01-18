package utils.util;

public class DictItem<K, V> {

    public K key;
    public V value;

    public DictItem(K key, V value){
        this.key = key;
        this.value = value;
    }

    public boolean hasKey(K key) {
        return this.key.equals(key);
    }
}