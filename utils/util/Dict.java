package utils.util;

import utils.exceptions.KeyError;

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

    public Dict(List<DictItem<K, V>> dictItems) {
        this();
        for (DictItem<K, V> dictItem = dictItems.initForLoop(); dictItems.hasNext(); dictItem=dictItems.next()) {
            this.items.add(new DictItem<K, V>(dictItem.key, dictItem.value));
        }
    }

    public Dict(Dict<K, V> dict) {
        this(dict.dictItems());
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
        for (DictItem<K, V> dictItem = this.items.initForLoop(); this.items.hasNext(); dictItem=this.items.next()) {
            if (dictItem.hasKey(key)) {
                return dictItem;
            }
        }
        return null;
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
        for (DictItem<K, V> dictItem = this.items.initForLoop(); this.items.hasNext(); dictItem=this.items.next()) {
            if (dictItem.key.equals(key)) {
                this.items.remove(dictItem);
            }
        }
        throw new IllegalArgumentException("Key not found");
    }

    /**
     * Get a {@code List} of {@code DictItem}s with Key, Value pairs.
     * @return  {@code List} of {@code DictItem}s with Key, Value pairs
     */
    public List<DictItem<K, V>> dictItems() {
        return this.items.clone();
    }

    public List<K> keys() {
        List<K> result = new List<>();
        for (DictItem<K, V> dictItem = this.items.initForLoop(); this.items.hasNext(); dictItem=this.items.next()) {
            result.add(dictItem.key);
        }
        return result;
    }

    public List<V> values() {
        List<V> result = new List<>();
        for (DictItem<K, V> dictItem = this.items.initForLoop(); this.items.hasNext(); dictItem=this.items.next()) {
            result.add(dictItem.value);
        }
        return result;
    }

    @Override
    public String toString() {
        List<String> result = new List<>();
        for (DictItem<K, V> dictItem= this.items.initForLoop(); this.items.hasNext();dictItem=this.items.next()) {
            result.add(String.format("\"%s\": \"%s\"", dictItem.key, dictItem.value));
        }
        String finalResult = "{";
        for (String string=result.initForLoop();result.hasNext();string=result.next()) {
            finalResult += string;
            if (result.hasNext()) {
                finalResult += ", ";
            }
        }
        return finalResult + "}";
    }
}
