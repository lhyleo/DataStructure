package datastructures.concrete.dictionaries;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Replace this class with your ArrayDictionary implementation from project 1.
 */

public class ArrayDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private Pair<K, V>[] pairs;
    // You're encouraged to add extra fields (and helper methods) though!
    private static final int SIZE = 5;
    // Number of pairs in the array
    private int pairSize;
    
    public ArrayDictionary() {
        this.pairs = makeArrayOfPairs(SIZE);
        this.pairSize = 0;
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain Pair<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
        // It turns out that creating arrays of generic objects in Java
        // is complicated due to something known as 'type erasure'.
        //
        // We've given you this helper method to help simplify this part of
        // your assignment. Use this helper method as appropriate when
        // implementing the rest of this class.
        //
        // You are not required to understand how this method works, what
        // type erasure is, or how arrays and generics interact. Do not
        // modify this method in any way.
        return (Pair<K, V>[]) (new Pair[arraySize]);

    }

    @Override
    public V get(K key) {
        int keyIndex = indexOfKey(key);
        if (keyIndex < 0) {
                throw new NoSuchKeyException();
        }
        return this.pairs[keyIndex].value;
    }

    @Override
    public void put(K key, V value) {
        int keyIndex = indexOfKey(key);
        if (keyIndex < 0) {
                Pair<K, V> newPair = new Pair<K, V>(key, value);
                if (this.pairSize == this.pairs.length) {
                    Pair<K, V>[] newPairs = copyArray(this.pairs);
                    this.pairs = newPairs;
                }
                this.pairs[this.pairSize] = newPair;
                this.pairSize += 1;
        } else {
                this.pairs[keyIndex].value = value;
        }
    }
    
    @Override
    public V remove(K key) {
            int keyIndex = indexOfKey(key);
        if (keyIndex < 0) {
                throw new NoSuchKeyException();
        }
        
        V value = this.pairs[keyIndex].value;
        this.pairs[keyIndex].key = this.pairs[this.pairSize - 1].key;
        this.pairs[keyIndex].value = this.pairs[this.pairSize - 1].value;
        pairSize -= 1;
        return value;
    }

    @Override
    public boolean containsKey(K key) {
        return indexOfKey(key) >= 0;
    }

    @Override
    public int size() {
        return this.pairSize;
    }

    /**
     * @param key
     * @return index of key in the dictionary. -1 if not exist.
     */
    private int indexOfKey(K key) {
            for (int i = 0; i < this.pairSize; i++) {
            if (key == null && this.pairs[i].key == null || key != null && key.equals(this.pairs[i].key)) {
                    return i;
            }
        }
            return -1;
    }

    /**
     * @param arrayFrom
     * @return a double sized Pair<K, V> of arrayFrom,
     *         The first half is copy of arrayFrom and the second half is empty.
     */
    private Pair<K, V>[] copyArray(Pair<K, V>[] arrayFrom) {
        Pair<K, V>[] arrayTo = makeArrayOfPairs(arrayFrom.length * 2);
        for (int i = 0; i < arrayFrom.length; i++) {
            arrayTo[i] = arrayFrom[i];
        }
        return arrayTo;
    }

    private static class Pair<K, V> {
        public K key;
        public V value;

        // You may add constructors and methods to this class as necessary.        
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        return new ArrayDictionaryIterator<K, V>(this.pairs, this.pairSize);
    }
    
    private static class ArrayDictionaryIterator<K, V> implements Iterator<KVPair<K, V>> {
        private Pair<K, V>[] pairs;
        int pairSize;
        int index;
        
        public ArrayDictionaryIterator(Pair<K, V>[] pairs, int pairSize) {
            // You do not need to make any changes to this constructor.
            this.pairs = pairs;
            this.pairSize = pairSize;
            this.index = 0;
        }

        /**
         * Returns 'true' if the iterator still has elements to look at;
         * returns 'false' otherwise.
         */
        public boolean hasNext() {
            return this.index < pairSize;
        }

        /**
         * Returns the next item in the iteration and internally updates the
         * iterator to advance one element forward.
         *
         * @throws NoSuchElementException if we have reached the end of the iteration and
         *         there are no more elements to look at.
         */
        public KVPair<K, V> next() {
            if (this.hasNext()) {
                    KVPair<K, V> tempPair = new KVPair<K, V>(pairs[this.index].key, pairs[this.index++].value);
                return tempPair;
            } else {
                    throw new NoSuchElementException();
            }
        }
    }
}