import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private int size;
	private Node headPtr;
	private Node lastPtr;

	private class Node {
		Item item;
		Node next;
		Node prev;

		public Node() {

		}
	}

	// construct an empty deque
	public Deque() {
		this.size = 0;
		this.headPtr = null;
		this.lastPtr = null;
	}

	// is the deque empty?
	public boolean isEmpty() {
		return size == 0;
	}

	// return the number of items on the deque
	public int size() {
		return this.size;
	}

	// add the item to the front
	public void addFirst(Item item) {
		if (item == null)
			throw new IllegalArgumentException();
		Node temp = new Node();
		temp.item = item;

		if (this.size == 0) {
			this.headPtr = temp;
			this.lastPtr = temp;
		} else {
			temp.next = this.headPtr;
			this.headPtr.prev = temp;
			this.headPtr = temp;
		}
		this.size += 1;
	}

	// add the item to the back
	public void addLast(Item item) {
		if (item == null)
			throw new IllegalArgumentException();
		Node temp = new Node();
		temp.item = item;
		if (this.size == 0) {
			this.headPtr = temp;
			this.lastPtr = temp;
		} else {
			temp.prev = this.lastPtr;
			this.lastPtr.next = temp;
			this.lastPtr = temp;
		}
		this.size += 1;
	}

	// remove and return the item from the front
	public Item removeFirst() {
		if (this.size == 0)
			throw new NoSuchElementException();
		Node temp = new Node();
		temp = this.headPtr;
		if (this.size == 1) {
			this.headPtr = null;
			this.lastPtr = null;
		} else {
			this.headPtr = this.headPtr.next;
			this.headPtr.prev = null;
			temp.next = null;
		}
		this.size -= 1;
		return temp.item;
	}

	// remove and return the item from the back
	public Item removeLast() {
		if (this.size == 0)
			throw new NoSuchElementException();
		Node temp = new Node();
		temp = this.lastPtr;
		if (this.size == 1) {
			this.headPtr = null;
			this.lastPtr = null;
		} else {
			this.lastPtr = this.lastPtr.prev;
			this.lastPtr.next = null;
			temp.prev = null;
		}
		this.size -= 1;
		return temp.item;
	}

	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new ListIterator();
	}

	private class ListIterator implements Iterator<Item> {

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

	public static void main(String[] args) {

	}
}
