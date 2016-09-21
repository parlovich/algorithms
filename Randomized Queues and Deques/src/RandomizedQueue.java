import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size = 0;
    private int head = 0;
    private int tail = 0;
    private Item[] items = (Item[]) new Object[1];

    private class RandomizedQueueIterator implements Iterator<Item> {
        int[] indexes = new int[size];

        public RandomizedQueueIterator() {
            for(int i = 0; i < size; i++) {
                indexes[i] = i;
            }
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Item next() {
            return null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removing is not supported");
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
    }

    // is the queue empty?
    public boolean isEmpty() {
        return true;
    }

    // return the number of items on the queue
    public int size() {
        return 0;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("item must not be null");
        }
        if (size == items.length) resize(2 * items.length);
        size++;
        items[tail++] = item;
        if (tail >= items.length) tail = tail % items.length;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        if (size > 0 && size == items.length / 4) resize(items.length / 2);
        return null;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        if (size > 0 && size == items.length / 4) resize(items.length / 2);
        return null;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size(); i++)
            copy[i] = items[i];
        items = copy;
    }

    // unit testing
    public static void main(String[] args) {

    }
}
