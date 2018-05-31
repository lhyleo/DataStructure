package datastructures.concrete;

import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IDisjointSet;
import datastructures.interfaces.IList;


/**
 * See IDisjointSet for more details.
 */
public class ArrayDisjointSet<T> implements IDisjointSet<T> {
    // Note: do NOT rename or delete this field. We will be inspecting it
    // directly within our private tests.
    private int[] pointers;
    
    // However, feel free to add more methods and private helper methods.
    // You will probably need to add one or two more fields in order to
    // successfully implement this class.
    private static final int INITIAL_SIZE = 50;
    private IDictionary<T, Integer> items;
    private int representative;
    
    public ArrayDisjointSet() {
        this.pointers = new int[INITIAL_SIZE];
        this.items = new ChainedHashDictionary<>();
        this.representative = 0;
    }

    @Override
    public void makeSet(T item) {
        if (representative >= pointers.length) {
            this.pointers = makeNewPointer(this.pointers);
        }
        
        if (items.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        
        this.items.put(item, representative);
        this.pointers[representative++] = -1;
    }
    
    private int[] makeNewPointer(int[] oldPointers) {
        int[] newPointer = new int[oldPointers.length * 2];
        for (int i = 0; i < oldPointers.length; i++) {
            newPointer[i] = oldPointers[i];
        }
        return newPointer;
    }
    
    @Override
    public int findSet(T item) {
        if (!items.containsKey(item)) {
            throw new IllegalArgumentException(); 
        }
        int position = items.get(item);
        IList<Integer> positions = new DoubleLinkedList<>();
        while (this.pointers[position] > -1) {
            positions.add(position);
            position = this.pointers[position];
        }
        for (int i:positions) {
            this.pointers[i] = position;
        }
        return position;
    }

    @Override
    public void union(T item1, T item2) {
        if (!items.containsKey(item1) || !items.containsKey(item2)) {
            throw new IllegalArgumentException();
        }
        int position1 = this.findSet(item1);
        int position2 = this.findSet(item2);
        
        if (position1 == position2) {
            throw new IllegalArgumentException();
        }
        
        int r1 = -this.pointers[position1] - 1;
        int r2 = -this.pointers[position2] - 1;
        
        if (r1 >= r2) { 
            this.pointers[position2] = position1;
            if (r1 == r2) {
                this.pointers[position1] -= 1;
            }
        } else {
            this.pointers[position1] = position2;
        }
    }
}
