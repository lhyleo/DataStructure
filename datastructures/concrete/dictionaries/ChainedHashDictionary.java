package datastructures.concrete.dictionaries;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See IDictionary for details on what each method must do.
 */
public class ChainedHashDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private static final int INITIALSIZE =29;
    private int arrayLength;
    private int sizeOfDictionary;
    private IDictionary<K, V>[] chains;

    // You're encouraged to add extra fields (and helper methods) though!
    
    public ChainedHashDictionary() {
        this.chains = makeArrayOfChains(INITIALSIZE);
        this.arrayLength = INITIALSIZE;
        this.sizeOfDictionary = 0;
        for (int i = 0; i < INITIALSIZE; i++) {
            this.chains[i] = new ArrayDictionary<K, V>();
        }
        
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain IDictionary<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private IDictionary<K, V>[] makeArrayOfChains(int size) {
        // Note: You do not need to modify this method.
        // See ArrayDictionary's makeArrayOfPairs(...) method for
        // more background on why we need this method.
        return (IDictionary<K, V>[]) new IDictionary[size];
    }

    @Override
    public V get(K key) {
            if (!containsKey(key)) {
                throw new NoSuchKeyException();
            }
        int index = getIndex(key);
        return getBucket(index).get(key);
    }
    
    public IDictionary<K, V> getBucket(int index) {
        return this.chains[index];
    }
    
    @Override
    public void put(K key, V value) {
        // resize
        if (this.sizeOfDictionary / this.arrayLength == 2) { 
            int newLength = this.arrayLength * 2 - 1;
            IDictionary<K, V>[]  newChain = makeArrayOfChains(newLength);
            for (int i = 0; i < newLength; i++) {
                newChain[i] = new ArrayDictionary<K, V>();
            }
            for (int i = 0; i < this.arrayLength; i++) {
                IDictionary<K, V> bucket = this.chains[i];
                for (KVPair<K, V> element : bucket) {
                    int hash = element.getKey().hashCode();
                    newChain[Math.abs(hash % newLength)].put(element.getKey(), element.getValue());
                }
            }
            this.chains = newChain;
            this.arrayLength = newLength;
        }
        // put elements
        int index = getIndex(key); //hashCode(key)
        IDictionary<K, V> bucket = getBucket(index);
        if (!bucket.containsKey(key)) {
            this.sizeOfDictionary++;
        }
        this.chains[index].put(key, value);
    }
    
    private int getIndex(K key) {
        int hash = 0;
        if (key != null) {
            hash = key.hashCode();
        }
        return Math.abs(hash % this.arrayLength);
    }

    @Override
    public V remove(K key) {
            if (!containsKey(key)) {
                throw new NoSuchKeyException();
            }
        V value = this.chains[getIndex(key)].remove(key);
        this.sizeOfDictionary--;
        return value;
    }

    @Override
    public boolean containsKey(K key) {
        return getBucket(getIndex(key)).containsKey(key);
    }

    @Override
    public int size() {
        return this.sizeOfDictionary;
    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        // Note: you do not need to change this method
        return new ChainedIterator<>(this.chains);
    }

    /**
     * Hints:
     *
     * 1. You should add extra fields to keep track of your iteration
     *    state. You can add as many fields as you want. If it helps,
     *    our reference implementation uses three (including the one we
     *    gave you).
     *    
     * 2. Before you try and write code, try designing an algorithm
     *    using pencil and paper and run through a few examples by hand.
     *
     * 3. Think about what exactly your *invariants* are. An *invariant*
     *    is something that must *always* be true once the constructor is
     *    done setting up the class AND must *always* be true both before and
     *    after you call any method in your class.
     *
     *    Once you've decided, write them down in a comment somewhere to
     *    help you remember.
     *
     *    You may also find it useful to write a helper method that checks
     *    your invariants and throws an exception if they're violated.
     *    You can then call this helper method at the start and end of each
     *    method if you're running into issues while debugging.
     *
     *    (Be sure to delete this method once your iterator is fully working.)
     *
     * Implementation restrictions:
     *
     * 1. You **MAY NOT** create any new data structures. Iterators
     *    are meant to be lightweight and so should not be copying
     *    the data contained in your dictionary to some other data
     *    structure.
     *
     * 2. You **MAY** call the `.iterator()` method on each IDictionary
     *    instance inside your 'chains' array, however.
     */
    private static class ChainedIterator<K, V> implements Iterator<KVPair<K, V>> {
        private IDictionary<K, V>[] chains;
        private int currentBucket;
        private Iterator<KVPair<K, V>> dictIt;
        private boolean nullIterator;

        public ChainedIterator(IDictionary<K, V>[] chains) {
            this.chains = chains;
            this.currentBucket = 0;
            while (this.currentBucket < this.chains.length && this.chains[this.currentBucket].size() == 0) {
                this.currentBucket++;
            }
            if (this.currentBucket < this.chains.length) {
                this.dictIt = this.chains[currentBucket].iterator();
                nullIterator = false;
            } else {
                nullIterator = true;
            }
        }

        @Override
        public boolean hasNext() {
            if (nullIterator) {
                return false;
            }
            return currentBucket < chains.length && dictIt.hasNext(); 
        }

        @Override
        public KVPair<K, V> next() {
            if (this.hasNext()) {
                KVPair<K, V> tempPair = dictIt.next();
                if (!dictIt.hasNext()) {
                    this.currentBucket++;
                    while (this.currentBucket < this.chains.length && this.chains[this.currentBucket].size() == 0) {
                        this.currentBucket++;
                    }
                    if (this.currentBucket < this.chains.length) {
                        this.dictIt = this.chains[currentBucket].iterator();
                    }
                }
                return tempPair;
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}