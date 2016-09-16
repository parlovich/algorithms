import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size = 0;
    private int head = 0;
    private int tail = 0;
    private Item[] items = (Item[]) new Object[2];

    // construct an empty randomized queue
    public RandomizedQueue() {
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("item must not be null");
        }
        if (size == items.length)
            resize(2 * items.length);
        items[tail++] = item;
        if (tail == items.length)
            tail = 0;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        int i = (head + StdRandom.uniform(size)) % items.length;
        Item item = items[i++];

        // Shift items
        for (; i % items.length != tail; i++) {
            items[(i - 1) % items.length] = items[i % items.length ];
        }
        tail = tail == 0 ? tail = items.length - 1 : tail - 1;
        items[tail] = null;
        size--;

        // Shrink if needed
        if (size > 0 && size == items.length / 4)
            resize(items.length / 2);
        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        return items[(head + StdRandom.uniform(size)) % items.length];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size(); i++)
            copy[i] = items[(head + i) % items.length];
        items = copy;
        head = 0;
        tail = size;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        int[] indexes = new int[size];
        int cur = 0;

        public RandomizedQueueIterator() {
            for(int i = 0; i < size; i++) indexes[i] = i;
            StdRandom.shuffle(indexes);
        }

        @Override
        public boolean hasNext() {
            return cur < size;
        }

        @Override
        public Item next() {
            return items[(head + indexes[cur++]) % items.length];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removing is not supported");
        }
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<>();
        q.enqueue("AA");
        q.enqueue("BB");
        q.enqueue("CC");
        q.enqueue("DD");
        q.enqueue("EE");
        q.enqueue("FF");
        q.enqueue("GG");
        for (String s : q) StdOut.println(s);
        for(int i = 0; i < 5; i++)
            StdOut.println("Dequeue " + q.dequeue());
        for (String s : q) StdOut.println(s);
        q.enqueue("AA1");
        q.enqueue("BB1");
        q.enqueue("CC1");
        q.enqueue("DD1");
        q.enqueue("EE1");
        q.enqueue("FF1");
        q.enqueue("GG1");
        for (String s : q) StdOut.println(s);
    }
}
