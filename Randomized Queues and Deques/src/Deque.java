import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    int size = 0;

    private class Node {
        Item item;
        Node next;
        Node prev;
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
                throw new NoSuchElementException("Deque is empty");
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
    public Deque(){
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
        first.next = oldFirst;
        if (size == 0)
            last = first;
        else
            oldFirst.prev = first;
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
        else
            oldLast.next = last;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new UnsupportedOperationException("Deque is empty");
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
            throw new UnsupportedOperationException("Deque is empty");
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
        Deque<String> deque = new Deque<>();
        deque.addFirst("3");
        deque.addFirst("2");
        deque.addFirst("1");

        deque.size();
        String s = deque.removeFirst();
        s = deque.removeLast();
        s = deque.removeFirst();
        //assert (deque.size() == 0);
    }
}