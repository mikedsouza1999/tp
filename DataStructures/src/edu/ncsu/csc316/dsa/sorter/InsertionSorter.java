package edu.ncsu.csc316.dsa.sorter;

import java.util.Comparator;

/**
 * InsertionSorter uses the insertion sort algorithm to sort data.
 * 
 * @author Dr. King
 * @param <E> Java generic type
 */
public class InsertionSorter<E extends Comparable<E>> extends AbstractComparisonSorter<E> implements Sorter<E> {

	/**
     * Default constructor
     */
	public InsertionSorter() {
		super(null);
	}

	/**
	 * Constructor for custom comparator
	 * @param comparator a custom comparator
	 */
	public InsertionSorter(Comparator<E> comparator) {
		super(comparator);
	}

	

	@Override
	public void sort(E[] data) {
		for (int i = 1; i < data.length; i++) {
			E x = data[i];
			int j = i - 1;
			while (j >= 0 && super.compare(data[j], x) > 0) {
				data[j + 1] = data[j];
				j = j - 1;
			}
			data[j + 1] = x;
		}

	}


}
