import java.util.function.Function;

public class Dict<K, V> {

    private List<DictItem<K, V>> items;

    public Dict() {
        this.items = new List<DictItem<K, V>>();
    }

    public Dict(K key, V value) {
        this();
        this.items.add(new DictItem<K, V>(key, value));
    }

    public Dict(K[] keys, V[] values) {
        this();
        if (keys.length != values.length) throw new IllegalArgumentException("Number of keys must be equal to number of values.");
        for (int i=0; i<keys.length;i++) {
            this.items.add(new DictItem<K, V>(keys[i], values[i]));
        }
    }

    public boolean hasKey(K key) {
        return this._get(key) != null;
    }

    public V get(K key) {
        DictItem<K, V> result = this._get(key);
        if (result == null) throw new KeyError(String.format("Key %s not found", key.toString()));
        return result.value;
    }


    private DictItem<K, V> _get(K key) {
        return this.items.get(this.getKeyCompareFunction(key));
    }

    public void set(K key, V value) {
        DictItem<K, V> oldItem = this._get(key);
        if (oldItem == null) {
            this.items.add(new  DictItem<K, V>(key, value));
        } else {
            oldItem.value = value;
        }
    }

    public void remove(K key) {
        this.items.remove(this.getKeyCompareFunction(key));
    }

    public List<DictItem<K, V>> dictItems() throws CloneNotSupportedException {
        return this.items.clone();
    }

    public List<K> keys() {
        List<K> result = new List<>();
        for (DictItem<K, V> dictItem : this.items) {
            result.add(dictItem.key);
        }
        return result;
    }

    public List<V> values() {
        List<V> result = new List<>();
        for (DictItem<K, V> dictItem : this.items) {
            result.add(dictItem.value);
        }
        return result;
    }

    private Function<DictItem<K,V>, Boolean> getKeyCompareFunction(K key) {
        return new Function<DictItem<K,V>, Boolean>(){

			@Override
			public Boolean apply(DictItem<K, V> dictItem) {
				return dictItem.hasKey(key);
			}
        };
    }

    @Override
    public String toString() {
        List<String> result = new List<>();
        for (DictItem<K, V> dictItem : this.items) {
            result.add(String.format("\"%s\": \"%s\"", dictItem.key, dictItem.value));
        }
        return "{" + String.join(", ", result) + "}";
    }

    @SuppressWarnings("hiding")
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
}
