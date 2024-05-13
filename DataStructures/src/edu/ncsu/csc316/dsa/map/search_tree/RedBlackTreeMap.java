package edu.ncsu.csc316.dsa.map.search_tree;

import java.util.Comparator;

import edu.ncsu.csc316.dsa.Position;

/*
 * Citing Help from the Course Textbook 
 * The code used in this class is based on the
 * RedBlackTreeMap code given on page 523-524 in the course textbook
 * "Data Structures and Algorithms" by Goodrich, Tamassia, Goldwasser (6th
 * Edition).
 */
/**
 * The RedBlackTreeMap is implemented as a linked data structure to support
 * efficient Tree and Map abstract data type behaviors.
 * 
 * A RedBlack tree must satisfy four properties: (1) the root must be black (2)
 * the children of a red node are black (3) all leaves (sentinels) are black (3)
 * the black-depth is the same for all leaves (sentinels) (i.e., every sentinel
 * has the same number of black ancestors)
 * 
 * The properties of a RedBlack tree ensure O(logn) height, and O(logn)
 * worst-case performance for {@see Map#put}, {@see Map#get}, and
 * {@see Map#remove}.
 * 
 * RedBlackTreeMap uses sentinel leaves. Every leaf node should have 2 sentinel
 * children.
 * 
 * The RedBlackTreeMap class is based on the implementation developed for use
 * with the textbook:
 *
 * Data Structures and Algorithms in Java, Sixth Edition Michael T. Goodrich,
 * Roberto Tamassia, and Michael H. Goldwasser John Wiley & Sons, 2014
 * 
 * @author Dr. King
 *
 * @param <K> the type of keys stored in the RedBlack tree
 * @param <V> the type of values associated with keys in the RedBlack tree
 */
public class RedBlackTreeMap<K extends Comparable<K>, V> extends BinarySearchTreeMap<K, V> {

	/**
	 * Constructs a new red-black tree map that uses natural ordering of keys when
	 * performing comparisons
	 */
	public RedBlackTreeMap() {
		this(null);
	}

	/**
	 * Constructs a new red-black tree map that uses a provided {@link Comparator}
	 * when performing comparisons of keys within the tree
	 * 
	 * @param compare a custom comparator
	 */
	public RedBlackTreeMap(Comparator<K> compare) {
		super(compare);
	}

	/**
	 * Returns true if the given position is black (it's property = 0)
	 * 
	 * @param p the position for which to determine if the color is black
	 * @return true if the position's property/color is black
	 */
	private boolean isBlack(Position<Entry<K, V>> p) {
		return getProperty(p) == 0;
	}

	/**
	 * Returns true if the given position is red (it's property = 1)
	 * 
	 * @param p the position for which to determine if the color is red
	 * @return true if the position's property/color is red
	 */
	private boolean isRed(Position<Entry<K, V>> p) {
		return getProperty(p) == 1;
	}

	/**
	 * Set the color of the given position to be black (property = 0)
	 * 
	 * @param p the position for which to make black
	 */
	private void makeBlack(Position<Entry<K, V>> p) {
		setProperty(p, 0);
	}

	/**
	 * Set the color of the given position to be red (property = 1)
	 * 
	 * @param p the position for which to make red
	 */
	private void makeRed(Position<Entry<K, V>> p) {
		setProperty(p, 1);
	}

	/**
	 * Resolves a double-red condition in a red-black tree where a red position has
	 * a red child
	 * 
	 * @param p the position that may have a red parent
	 */
	private void resolveRed(Position<Entry<K, V>> p) {
		Position<Entry<K, V>> parent, uncle, middle, grand;
		parent = parent(p);
		if (isRed(parent)) {
			uncle = sibling(parent);
			if (isBlack(uncle)) {
				middle = restructure(p);
				makeBlack(middle);
				makeRed(left(middle));
				makeRed(right(middle));
			} else {
				makeBlack(parent);
				makeBlack(uncle);
				grand = parent(parent);
				if (!isRoot(grand)) {
					makeRed(grand);
					resolveRed(grand);
				}
			}
		}
	}

	/**
	 * Resolves the double-black condition where the black-depths of the sentinel
	 * leaves are no longer equal.
	 * 
	 * @param p the position at which the double-black condition is located
	 */
	private void remedyDoubleBlack(Position<Entry<K, V>> p) {	
		Position<Entry<K, V>> parent = parent(p);
		Position<Entry<K, V>> sibling = sibling(p);
		if (isBlack(sibling)) {
			if (isRed(left(sibling)) || isRed(right(sibling))) { // Case 1: trinode restructuring
				Position<Entry<K, V>> x = (isRed(left(sibling)) ? left(sibling) : right(sibling));
				Position<Entry<K, V>> middle = restructure(x);
				if (isRed(parent))
					makeRed(middle);
				else
					makeBlack(middle);

				makeBlack(left(middle));
				makeBlack(right(middle));
			} else { 
				makeRed(sibling);
				if (isRed(parent))
					makeBlack(parent);
				else if (!isRoot(parent))
					remedyDoubleBlack(parent); 
			}
		} else { 
			rotate(sibling);
			makeBlack(sibling);
			makeRed(parent);
			remedyDoubleBlack(p); 
		}
	}

	/**
	 * {@inheritDoc} For a RedBlack tree, we must check that the newly inserted
	 * position has not created a double-red condition (i.e., the newly created
	 * position is red and has a red parent)
	 */
	protected void actionOnInsert(Position<Entry<K, V>> p) {
		if (!isRoot(p)) {
			makeRed(p);
			resolveRed(p);
		}
	}

	/**
	 * {@inheritDoc} For a RedBlack tree, we must check that the removed position
	 * has not created a double-black condition (i.e., a situation in which the
	 * black-depth property of the tree is violated)
	 */
	protected void actionOnDelete(Position<Entry<K, V>> p) {
		if (isRed(p)) {
			makeBlack(p);
		} else if (!isRoot(p)) {
			Position<Entry<K, V>> sib = sibling(p);
			if (isInternal(sib) && (isBlack(sib) || isInternal(left(sib)))) {
				remedyDoubleBlack(p);
			}
		}
	}
}
