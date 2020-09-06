import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private int size;
	private Node headPtr;

	private class Node {
		Item item;
		Node next;
	}

	// construct an empty randomized queue
	public RandomizedQueue() {
		this.size = 0;
		this.headPtr = null;
	}

	// is the randomized queue empty?
	public boolean isEmpty() {
		return this.size == 0;
	}

	// return the number of items on the randomized queue
	public int size() {
		return this.size;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null)
			throw new IllegalArgumentException();
		Node temp = new Node();
		temp.item = item;
		temp.next = this.headPtr;
		this.headPtr = temp;
		this.size += 1;
	}

	// remove and return a random item
	public Item dequeue() {
		if (this.size == 0)
			throw new NoSuchElementException();
		int idx = StdRandom.uniform(this.size);
		Node temp = new Node();
		temp = headPtr;
		for (int i = 0; i < idx - 1; i++) {
			temp = temp.next;
		}
		temp.next = temp.next.next;
		this.size -= 1;
		return temp.item;
	}

	// return a random item (but do not remove it)
	public Item sample() {
		if (this.size == 0)
			throw new NoSuchElementException();
		int idx = StdRandom.uniform(this.size);
		Node temp = new Node();
		temp = headPtr;
		for (int i = 0; i < idx; i++) {
			temp = temp.next;
		}
		return temp.item;
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandomIterator();
	}

	private class RandomIterator implements Iterator<Item> {
		
		private Node current = headPtr;

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return current != null;
		}

		@Override
		public Item next() {
			// TODO Auto-generated method stub
			if (current == null) {
				throw new NoSuchElementException();
			}
			Item item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	// unit testing (required)
	public static void main(String[] args) {

	}

}