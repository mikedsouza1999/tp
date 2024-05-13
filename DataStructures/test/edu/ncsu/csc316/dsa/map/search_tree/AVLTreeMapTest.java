package edu.ncsu.csc316.dsa.map.search_tree;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for AVLTreeMap Checks the expected outputs of the Map abstract
 * data type behaviors when using an AVL tree data structure
 *
 * @author Dr. King
 *
 */
public class AVLTreeMapTest {

	private BinarySearchTreeMap<Integer, String> tree;

	/**
	 * Create a new instance of an AVL tree-based map before each test case executes
	 */
	@Before
	public void setUp() {
		tree = new AVLTreeMap<Integer, String>();
	}

	/**
	 * Test the output of the put(k,v) behavior
	 */
	@Test
	public void testPut() {
		assertEquals(0, tree.size());
		assertTrue(tree.isEmpty());
		tree.put(4, "four");
		tree.put(2, "two");
		tree.put(5, "five");
		tree.put(7, "seven");
		tree.put(9, "nine");
		assertEquals(5, tree.size());

		assertEquals(4, (int) tree.root().getElement().getKey());
		assertEquals(2, (int) tree.left(tree.root()).getElement().getKey());
		assertEquals(7, (int) tree.right(tree.root()).getElement().getKey());
		assertEquals(9, (int) tree.right(tree.right(tree.root())).getElement().getKey());
		assertEquals(5, (int) tree.left(tree.right(tree.root())).getElement().getKey());
		
		tree.put(10, "ten");
		assertEquals(7, (int) tree.root().getElement().getKey());
		assertEquals(4, (int) tree.left(tree.root()).getElement().getKey());
		assertEquals(9, (int) tree.right(tree.root()).getElement().getKey());
		assertEquals(10, (int) tree.right(tree.right(tree.root())).getElement().getKey());


		
	}

	/**
	 * Test the output of the get(k) behavior
	 */
	@Test
	public void testGet() {
		tree.put(1, "one");
		tree.put(2, "two");
		tree.put(3, "three");
		tree.put(4, "four");
		assertEquals(4, tree.size());
		assertEquals("one", tree.get(1));
		assertEquals("two", tree.get(2));
		assertEquals("three", tree.get(3));
		assertEquals("four", tree.get(4));
		assertNull(tree.get(5));
		
	}

	/**
	 * Test the output of the remove(k) behavior
	 */
	@Test
	public void testRemove() {
		assertTrue(tree.isEmpty());
		tree.put(4, "four");
		tree.put(2, "two");
		tree.put(5, "five");
		tree.put(7, "seven");
		tree.put(9, "nine");
		tree.put(10, "ten");
		
		assertEquals(7, (int) tree.root().getElement().getKey());
		assertEquals(4, (int) tree.left(tree.root()).getElement().getKey());
		assertEquals(9, (int) tree.right(tree.root()).getElement().getKey());
		assertEquals(10, (int) tree.right(tree.right(tree.root())).getElement().getKey());
                                                                                                   
		assertEquals("nine", tree.remove(9));
		assertEquals(7, (int) tree.root().getElement().getKey());
		assertEquals(4, (int) tree.left(tree.root()).getElement().getKey());
		assertEquals(10, (int) tree.right(tree.root()).getElement().getKey());
	}
}
