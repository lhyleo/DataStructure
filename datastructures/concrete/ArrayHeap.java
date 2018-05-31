package datastructures.concrete;

import java.util.NoSuchElementException;

import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;
//testing
/**
 * See IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;
    private static final int INITIAL_SIZE = 21;
    // You MUST use this field to store the contents of your heap.
    // You may NOT rename this field: we will be inspecting it within
    // our private tests.
    private T[] heap;
    // Feel free to add more fields and constants.
    private int size;

    public ArrayHeap() {
        this.heap = makeArrayOfT(INITIAL_SIZE);
        this.size = 0;
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain elements of type T.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int sizeMake) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[sizeMake]);
    }

    @Override
    public T removeMin() {
        if (this.size == 0) {
            throw new EmptyContainerException();
        } 
        T temp = this.heap[0];
        this.heap[0] = this.heap[this.size - 1];
        this.size--;
        perlocateDown(0);
        return temp;
    }

    @Override
    public T peekMin() {
       if (this.size == 0) {
           throw new EmptyContainerException();
       }
       return heap[0];
    }
    
    @Override
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (this.size == this.heap.length) {
            T[] newArray = makeArrayOfT(this.size * NUM_CHILDREN);
            System.arraycopy(this.heap, 0, newArray, 0, this.size);
            this.heap = newArray;
        }
        this.heap[this.size] = item;
        this.size++;
        perlocateUp(this.size - 1);
    }
    
    public void exchangeElements(int first, int second) {
        T temp = this.heap[first];
        this.heap[first] = this.heap[second];
        this.heap[second] = temp;
    }
    
    private void perlocateUp(int item) {
        int max = (item - 1) / NUM_CHILDREN;
        while (item > 0 && this.heap[item].compareTo(this.heap[max]) < 0) {
            exchangeElements(max, item);
            item = max;
            max = (item - 1) / NUM_CHILDREN;
        }
    }
    
    private void perlocateDown(int item) {
        int min = findMinChildren(NUM_CHILDREN * item + 1, NUM_CHILDREN * item + NUM_CHILDREN);
        while (min >= 0 && this.heap[item].compareTo(this.heap[min]) > 0) {
            exchangeElements(min, item);
            item = min;
            min = findMinChildren(NUM_CHILDREN * item + 1, NUM_CHILDREN * item + NUM_CHILDREN);
        }
    }
    
    private int findMinChildren(int startIndex, int endIndex) {
        if (startIndex >= this.size) {
            return -1;
        } else if (endIndex >= this.size) {
            endIndex = this.size - 1;
        }
        int min = startIndex;
        for (int i = startIndex + 1; i <= endIndex; i++) {
            if (this.heap[min].compareTo(heap[i]) > 0) {
                min = i;
            }
        }
        return min;
    }
    
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void remove(T item) {
        int index = -1;
        for (int i = 0; i < this.size; i++) {
            if (item.equals(this.heap[i])) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new NoSuchElementException();
        }
        // swap with the last
        this.heap[index] = this.heap[this.size - 1];
        this.size--;
        perlocateDown(index);
    }
    
    public boolean contains(T item) {
        int index = -1;
        for (int i = 0; i < this.size; i++) {
            if (item.equals(this.heap[i])) {
                index = i;
                break;
            }
        }
        return index > -1;
    }
}
