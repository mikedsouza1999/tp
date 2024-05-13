package edu.ncsu.csc316.dsa.priority_queue;

import java.util.Comparator;

import edu.ncsu.csc316.dsa.list.ArrayBasedList;

/*
 * Citing Help from the Course Textbook 
 * The code used in this class is based on the
 * HeapPriorityQueue code given on page 377-378 in the course textbook
 * "Data Structures and Algorithms" by Goodrich, Tamassia, Goldwasser (6th
 * Edition).
 */

/**
 * A HeapPriorityQueue is an array-based min-heap implementation of the
 * {@link PriorityQueue} abstract data type. HeapPriorityQueue ensures a O(logn)
 * worst-case runtime for {@link PriorityQueue.insert} and
 * {@link PriorityQueue.deleteMin}. HeapPriorityQueue ensures a O(1) worst-case
 * runtime for {@link PriorityQueue.min}, {@link PriorityQueue.size}, and
 * {@link PriorityQueue.isEmpty}.
 * 
 * The HeapPriorityQueue class is based on an implementation developed for use
 * with the textbook:
 *
 * Data Structures and Algorithms in Java, Sixth Edition Michael T. Goodrich,
 * Roberto Tamassia, and Michael H. Goldwasser John Wiley & Sons, 2014
 * 
 * @author Dr. King
 *
 * @param <K> the type of keys (priorities) stored in the priority queue
 * @param <V> the type of values that are associated with keys in the priority
 *            queue
 */
public class HeapPriorityQueue<K extends Comparable<K>, V> extends AbstractPriorityQueue<K, V> {

	/**
	 * The internal array-based list used to model the heap
	 */
	protected ArrayBasedList<Entry<K, V>> list;

	/**
	 * Constructs a new HeapPriorityQueue using a custom comparator
	 * 
	 * @param comparator custom comparator
	 * 
	 */
	public HeapPriorityQueue(Comparator<K> comparator) {
		super(comparator);
		list = new ArrayBasedList<Entry<K, V>>();
	}

	/**
	 * Constructs a new HeapPriorityQueue that compares keys (priorities) using the
	 * natural ordering of the key type
	 */
	public HeapPriorityQueue() {
		this(null);
	}

	//////////////////////////////////////////////////
	// Convenience methods to help abstract the math
	// involved in determining parent or children in
	// an array-based implementation of a min-heap
	//////////////////////////////////////////////////

	/**
	 * Returns the index of the parent of the entry at the given index
	 * 
	 * @param index the index of the entry for which to return a reference to its
	 *              parent
	 * @return the index of the parent of the entry at the given index
	 */
	protected int parent(int index) {
		return (index - 1) / 2;
	}

	/**
	 * Returns the index of the left child of the entry at the given index
	 * 
	 * @param index the index of the entry for which to return a reference to its
	 *              left child
	 * @return the index of the left child of the entry at the given index
	 */
	protected int left(int index) {
		return 2 * index + 1;
	}

	/**
	 * Returns the index of the right child of the entry at the given index
	 * 
	 * @param index the index of the entry for which to return a reference to its
	 *              right child
	 * @return the index of the right child of the entry at the given index
	 */
	protected int right(int index) {
		return 2 * index + 2;
	}

	/**
	 * Returns true if the entry at the given index has a left child
	 * 
	 * @param index the index of the entry for which to check for a left child
	 * @return true if the entry at the given index has a left child; otherwise,
	 *         return false
	 */
	protected boolean hasLeft(int index) {
		return left(index) < list.size();
	}

	/**
	 * Returns true if the entry at the given index has a right child
	 * 
	 * @param index the index of the entry for which to check for a right child
	 * @return true if the entry at the given index has a right child; otherwise,
	 *         return false
	 */
	protected boolean hasRight(int index) {
		return right(index) < list.size();
	}

	//////////////////////////////////////////
	// ADT Operations
	//////////////////////////////////////////

	@Override
	public Entry<K, V> insert(K key, V value) {
		Entry<K, V> temp = createEntry(key, value);
		list.addLast(temp);
		upHeap(list.size() - 1);
		return temp;
	}

	@Override
	public Entry<K, V> min() {
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public Entry<K, V> deleteMin() {
		if (list.isEmpty()) {
			return null;
		}
		Entry<K, V> ent = list.get(0);
		swap(0, list.size() - 1);
		list.remove(list.size() - 1);
		downHeap(0);
		return ent;
	}

	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Ensures the min-heap ordering property is maintained appropriately by
	 * comparing an entry's key (priority) with the key of its parent, swapping the
	 * entries if necessary
	 * 
	 * @param index the index of the entry at which to determine if up-heap is
	 *              necessary to preserve the min-heap ordering property
	 */
	protected void upHeap(int index) {
		int temp = index;
		while (temp > 0) {
			int parent = parent(temp);
			if (super.compare(list.get(temp).getKey(), list.get(parent).getKey()) >= 0)
				break;
			swap(temp, parent);
			temp = parent;
		}
	}

	/**
	 * Swaps the entry at index1 with the entry at index2
	 * 
	 * @param index1 the index of the first entry involved in the swap
	 * @param index2 the index of the second entry involved in the swap
	 */
	protected void swap(int index1, int index2) {
		Entry<K, V> temp = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, temp);
	}

	/**
	 * Ensures the min-heap ordering property is maintained appropriately by
	 * comparing an entry's key (priority) with the keys of its children, swapping
	 * the entry with its lowest-priority child if necessary
	 * 
	 * @param index the index of the entry at which to determine if down-heap is
	 *              necessary to preserve the min-heap ordering property
	 */
	protected void downHeap(int index) {
		int temp = index;
		while (hasLeft(temp)) {
			int left = left(temp);
			int small = left;
			if (hasRight(temp)) {
				int right = right(temp);
				if (super.compare(list.get(left).getKey(), list.get(right).getKey()) > 0)
					small = right;
			}
			if (super.compare(list.get(small).getKey(), list.get(temp).getKey()) >= 0)
				break;
			swap(temp, small);
			temp = small;

		}

	}
}
