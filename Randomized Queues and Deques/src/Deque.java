import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int size = 0;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removing is not supported");
        }
    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("item must not be null");
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        if (size == 0)
            last = first;
        else {
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("item must not be null");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (size == 0)
            first = last;
        else {
            last.prev = oldLast;
            oldLast.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException("Deque is empty");
        }
        Node item = first;
        first = first.next;
        if (first == null) {
            last = null;
            size = 0;
        } else {
            first.prev = null;
            size--;
        }
        return item.item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException("Deque is empty");
        }
        Node item = last;
        last = last.prev;
        if (last == null) {
            first = null;
            size = 0;
        } else {
            last.next = null;
            size--;
        }
        return item.item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing
    public static void main(String[] args) {
        Deque<Double> q = new Deque<>();
        double[] val = new double[] {0.0, 0.8, 0.0, 0.1, 0.1, 0.0};

        for (int i = 0; i < 1000; i++)
            q.addLast(val[StdRandom.uniform(val.length)]);
        for (int i = 0; i < 1000; i++)
            q.removeLast();
        assert q.isEmpty();

        for (int i = 0; i < 1000; i++)
            q.addFirst(val[StdRandom.uniform(val.length)]);
        for (int i = 0; i < 1000; i++)
            q.removeFirst();
        assert q.isEmpty();

        for (int i = 0; i < 1000; i++)
            q.addFirst(val[StdRandom.uniform(val.length)]);
        for (int i = 0; i < 1000; i++)
            q.removeLast();
        assert q.isEmpty();

        for (int i = 0; i < 1000; i++)
            q.addLast(val[StdRandom.uniform(val.length)]);
        for (int i = 0; i < 1000; i++)
            q.removeFirst();
        assert q.isEmpty();
    }
}