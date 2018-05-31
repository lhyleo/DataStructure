package datastructures.concrete;

import datastructures.interfaces.IList;
import misc.exceptions.EmptyContainerException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Replace this class with your DoubleLinkedList implementation from
 * project 1.
 */

public class DoubleLinkedList<T> implements IList<T> {
    // You may not rename these fields or change their types.
    // We will be inspecting these in our private tests.
    // You also may not add any additional fields.
    private Node<T> front;
    private Node<T> back;
    private int size;

    public DoubleLinkedList() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    @Override
    public void add(T item) {
        this.insert(size, item);
    }

    @Override
    public T remove() {
            if (this.size == 0) {
                throw new EmptyContainerException();
            }
            return this.delete(this.size - 1);
    }
    
    private Node<T> goToFromFront(Node<T> node, int index) {
            for (int i = 0; i < index; i++) {
                node = node.next;
         }
            return node;    
    }
    
    private Node<T> goToFromBack(Node<T> node, int index) {
            for (int i = this.size - 1; i > index; i--) {
                node = node.prev; 
            }
            return node;
    }
    
    @Override
    public T get(int index) {
            if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> temp;
        if (index < size / 2) {
            temp = goToFromFront(this.front, index);
        } else {
            temp = goToFromBack(this.back, index);
        }
        return temp.data;
    }

    @Override
    public void set(int index, T item) {
            if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> setfront;
        Node<T> setback;
        Node<T> temp;
        if (size == 1) {
            temp = new Node<T>(item);
            this.front = temp;
            this.back = temp;
        } else {
            setfront = this.front;
            if (index == 0) {
                temp = new Node<T>(null, item, this.front.next);
                this.front = temp;
                this.front.next.prev = temp;
            } else {
                setfront = goToFromFront(setfront, index - 1);
                setback = setfront.next.next;
                temp = new Node<T>(setfront, item, setback);
                if (index != this.size - 1) {
                    setback.prev = temp;
                } else {
                    this.back = temp;
                }
                setfront.next = temp;
            }
        }
    }

    @Override
    public void insert(int index, T item) {
        if (index >= (this.size + 1) || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> temp;
        if (this.size == 0) {
            temp = new Node<T>(item);
            this.front = temp;
            this.back = temp;
        } else if (index == this.size) {
            temp = new Node<T>(this.back, item, null);
            this.back.next = temp;
            this.back = temp;
        } else if (index == 0) {
            temp = new Node<T>(null, item, this.front);
            this.front = temp;
            this.front.next.prev = temp;
        } else {
            Node<T> setfront;
            Node<T> setback;
            if (index < (this.size/2)) {
                setfront = goToFromFront(this.front, index - 1);
                setback = setfront.next;
                temp = new Node<T>(setfront, item, setback);
                setfront.next = temp;
                setback.prev = temp;
            } else {
                setback = goToFromBack(this.back, index);
                setfront = setback.prev;
                temp = new Node<T>(setfront, item, setback);
                setfront.next = temp;
                setback.prev = temp;
            }
        }
        this.size += 1;
    }

    @Override
    public T delete(int index) {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        
        Node<T> temp;
        Node<T> setfront;
        Node<T> setback;
        if (index == 0) {
           temp = this.front;
           if (this.size == 1) {
               this.front = null;
               this.back = null;
           } else if (this.size == 2) {
               this.front = this.back;
               this.back.prev = null;
           } else {
               this.front = this.front.next;
               this.front.prev = null;
             }
        } else if (index == this.size - 1) {
            temp = this.back;
            if (this.size == 2) {
                this.back = this.front;
                this.front.next = null;
            } else {
                this.back = this.back.prev;
                this.back.next = null;
            }              
        } else {
            if (index < (this.size/2)) {
                setfront = goToFromFront(this.front, index - 1);
                temp = setfront.next;
                setback = setfront.next.next;
            } else {
                setback = goToFromBack(this.back, index + 1);
                temp = setback.prev;
                setfront = setback.prev.prev;
            }
            setfront.next = setback;
            setback.prev = setfront;  
        }
        this.size -= 1;
        return temp.data;
    }

    @Override
    public int indexOf(T item) {
        Node<T> current = this.front;
        if (item != null) {
            for (int i = 0; i < this.size; i++) {
                if (current.data.equals(item)) {
                    return i;
                }
                current = current.next;
            }
        } else {
            for (int i = 0; i < this.size; i++) {
                if (current.data == item) {
                    return i;
                }
                current = current.next;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean contains(T other) {
        int i = indexOf(other);
        return i > -1;
    }

    @Override
    public Iterator<T> iterator() {
        // Note: we have provided a part of the implementation of
        // an iterator for you. You should complete the methods stubs
        // in the DoubleLinkedListIterator inner class at the bottom
        // of this file. You do not need to change this method.
        return new DoubleLinkedListIterator<>(this.front);
    }

    private static class Node<E> {
        // You may not change the fields in this node or add any new fields.
        public final E data;
        public Node<E> prev;
        public Node<E> next;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public Node(E data) {
            this(null, data, null);
        }
        // Feel free to add additional constructors or methods to this class.
    }

    private static class DoubleLinkedListIterator<T> implements Iterator<T> {
        // You should not need to change this field, or add any new fields.
        private Node<T> current;
        
        public DoubleLinkedListIterator(Node<T> current) {
            // You do not need to make any changes to this constructor.
            this.current = current;
        }

        /**
         * Returns 'true' if the iterator still has elements to look at;
         * returns 'false' otherwise.
         */
        public boolean hasNext() {
            return this.current != null;

        }

        /**
         * Returns the next item in the iteration and internally updates the
         * iterator to advance one element forward.
         *
         * @throws NoSuchElementException if we have reached the end of the iteration and
         *         there are no more elements to look at.
         */
        public T next() {
            Node<T> temp;
            if (this.hasNext()) {
                temp = this.current;
                this.current = this.current.next;
            } else {
                throw new NoSuchElementException();
            }
            return temp.data;
        }
    }
}
