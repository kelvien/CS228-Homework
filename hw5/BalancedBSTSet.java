package edu.iastate.cs228.hw5;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Binary search tree implementation of the Set interface. The contains() and
 * remove() methods of AbstractSet are overridden to search the tree without
 * using the iterator. Instances of this class always maintain node counts; that
 * is, the count() method of the BSTNode interface is implemented to be O(1). If
 * constructed with the isSelfBalancing flag true, instances of this tree are
 * self-balancing: whenever an add(), remove(), or Iterator.remove() operation
 * causes any node to become unbalanced, a rebalance operation is automatically
 * performed at the highest unbalanced node.
 * 
 * @author Kelvien Hidayat
 */
public class BalancedBSTSet<E extends Comparable<? super E>> extends
		AbstractSet<E> {
	/**
	 * Root of this tree.
	 */
	protected Node root;
	/**
	 * Number of elements in this tree.
	 */
	protected int size;
	/**
	 * Status whether the tree is self balancing or not
	 */
	private boolean isSelfBalancing = false;
	/**
	 * Index for converting BST to an array
	 */
	private int index;
	/**
	 * An array for conversion of BST into an array
	 */
	private BSTNode[] array;
	/**
	 * Top; Numerator of the binary search tree
	 */
	private int top;
	/**
	 * Bottom; Denominator of the binary search tree
	 */
	private int bottom;

	/**
	 * Node type for this implementation.
	 */
	protected class Node implements BSTNode<E> {
		public Node left;
		public Node right;
		public Node parent;
		public E data;

		public Node(E key, Node parent) {
			this.data = key;
			this.parent = parent;
		}

		@Override
		public BSTNode<E> left() {
			return left;
		}

		@Override
		public BSTNode<E> right() {
			return right;
		}

		@Override
		public int count() {
			if (this.left() == null && this.right() == null) // If node doesn't
																// have any left
																// or right
																// nodes, it
																// only has one
																// node itself
				return 1;
			else if (this.left() == null && this.right() != null) // If node has
																	// a right
																	// node, it
																	// recursively
																	// go
																	// further
																	// to the
																	// right
																	// node,
																	// otherwise
																	// go the
																	// left one.
				return 1 + this.right().count();
			else if (this.right() == null && this.left() != null)
				return 1 + this.left().count();
			else
				// Node has both left and right nodes, it will recursively
				// adding the sub problems from both nodes.
				return 1 + this.left().count() + this.right().count();
		}

		@Override
		public E data() {
			return data;
		}

		@Override
		public BSTNode<E> parent() {
			return parent;
		}

		@Override
		public String toString() {
			return data.toString();
		}

	}

	/**
	 * Constructs an empty binary search tree. This tree will maintain node
	 * counts but will not automatically perform rebalance operations.
	 */
	public BalancedBSTSet() {
		top = 2;
		bottom = 3;
		isSelfBalancing = false;
	}

	/**
	 * Constructs an empty binary search tree. If the isSelfBalancing flag is
	 * true, the tree will be self-balancing: if so, whenever an add(),
	 * remove(), or Iterator.remove() operation causes any node to become
	 * unbalanced, a rebalance operation is automatically performed at the
	 * highest unbalanced node. The default value alpha = 2/3 is used for the
	 * balance condition. Maintains node counts whether or not isSelfBalancing
	 * is true.
	 * 
	 * @param isSelfBalancing
	 *            true if this binary search tree is to be self-balancing, false
	 *            otherwise
	 */
	public BalancedBSTSet(boolean isSelfBalancing) {
		top = 2;
		bottom = 3;
		if (isSelfBalancing)
			this.isSelfBalancing = true;
		else
			this.isSelfBalancing = false;
	}

	/**
	 * Constructs an empty binary search tree. If the isSelfBalancing flag is
	 * true, the tree will be self-balancing: if so, whenever an add(),
	 * remove(), or Iterator.remove() operation causes any node to become
	 * unbalanced, a rebalance operation is automatically performed at the
	 * highest unbalanced node. The given alpha = top/bottom is used for the
	 * balance condition. Maintains node counts whether or not isSelfBalancing
	 * is true.
	 * 
	 * @param isSelfBalancing
	 *            true if this binary search tree is to be self-balancing, false
	 *            otherwise
	 * @param top
	 *            numerator of the fraction alpha
	 * @param bottom
	 *            denominator of the fraction alpha
	 * @throws IllegalArgumentException
	 *             if top / bottom is less than 1/2
	 */
	public BalancedBSTSet(boolean isSelfBalancing, int top, int bottom) {
		if ((float) top / bottom <= (float) 1 / 2 || (float) top / bottom >= 1) // Alpha
																				// bounds
																				// to
																				// be
																				// more
																				// than
																				// 1/2
																				// and
																				// less
																				// than
																				// 1
			throw new IllegalArgumentException(
					"Alpha is less than or equal to 1/2 or more than equal to 1");
		else {
			this.top = top;
			this.bottom = bottom;
			if (isSelfBalancing)
				this.isSelfBalancing = true;
			else
				this.isSelfBalancing = false;
		}
	}

	/**
	 * Returns a read-only view of the root node of this tree.
	 * 
	 * @return root node of this tree
	 */
	public BSTNode<E> root() {
		return root;
	}

	/**
	 * Helper method that iterate through the tree and return true if the node
	 * as an actual object(not the data) given is in the tree, false otherwise
	 * 
	 * @param node
	 *            node to be inspected
	 * @return true if the actual object is in the tree, false otherwise
	 */
	private boolean findIt(BSTNode<E> node) {
		BSTIterator iter = (BSTIterator) this.iterator();
		while (iter.hasNext()) {
			if (iter.current == node)
				return true;
			else
				iter.next();
		}
		return false;
	}

	/**
	 * Performs a rebalance operation on the given subtree. This operation does
	 * not create or destroy any nodes and does not change the size of this
	 * tree.
	 * 
	 * @param bstNode
	 *            root of the subtree to be rebalanced.
	 * @throws IllegalArgumentException
	 *             if the given node is not part of this tree.
	 * @throws ClassCastException
	 *             if the given node cannot be cast to the correct concrete node
	 *             type for this tree.
	 */
	public void rebalance(BSTNode<E> bstNode) {
		if (bstNode == null)
			throw new IllegalArgumentException("The node given is null");
		if (!this.findIt(bstNode)) // Throwing an exception if
			// the given node is not in
			// the tree
			throw new IllegalArgumentException(
					"The given node is not part of this tree");

		if (bstNode.getClass() != edu.iastate.cs228.hw5.BalancedBSTSet.Node.class) // Throwing
																					// an
																					// exception
																					// if
																					// the
																					// given
																					// node
																					// is
																					// not
																					// the
																					// type
																					// of
																					// BalancedBSTSet.Node
			throw new ClassCastException(
					"The given node cannot be cast to the correct concrete node type for this tree");

		if (bstNode.count() <= 1)
			return; // This is for a node that is a leaf

		index = 0;
		array = new BSTNode[bstNode.count()];
		intoArray(bstNode); // Calling a helper method that creates an array
							// from a tree

		int mid = (array.length - 1) / 2;
		BSTNode<E>[] arrayLeft;
		BSTNode<E>[] arrayRight;
		// Creating arrays of subproblems
		boolean check = true;
		if (array.length == 2) { // This check is only for a subproblem that
									// only has 2 elements left.
			mid = 0;
			check = false;
			arrayLeft = new BSTNode[1];
			arrayRight = new BSTNode[1];
			arrayLeft[0] = null; // The first subproblem would be null because
									// it has to be assigned to something else
			arrayRight[0] = array[1]; // The second subproblem would have the
										// only one left
		} else if (array.length % 2 == 0) { // This check is only for a
											// subproblem that is even
			arrayLeft = new BSTNode[mid];
			arrayRight = new BSTNode[bstNode.count() - mid - 1];
			for (int i = 0; i < arrayLeft.length; i++) {
				arrayLeft[i] = array[i];
			}
			for (int i = 0; i < arrayRight.length; i++) {
				arrayRight[i] = array[i + mid + 1];
			}
		} else { // This check is only for a subproblem that is odd
			arrayLeft = new BSTNode[mid];
			arrayRight = new BSTNode[bstNode.count() - mid - 1];
			for (int i = 0; i < arrayLeft.length; i++) {
				arrayLeft[i] = array[i];
			}
			for (int i = 0; i < arrayRight.length; i++) {
				arrayRight[i] = array[i + mid + 1];
			}
		}

		if (bstNode == root) { // Replacing the root if the one that is being
								// rebalanced is the root
			root = (Node) array[(bstNode.count() - 1) / 2]; //
			Node node = root;
			// Helper method by putting both subproblems created earlier
			recursiveDecompose(true, node, arrayLeft);
			recursiveDecompose(false, node, arrayRight);
		} else {
			Node counter = root;
			int LR = 0;
			while (true) { // To direct the counter to the node that is desired
							// to be rebalanced
				int comp = counter.data.compareTo(bstNode.data());
				if (comp == 0) {
					break;
				}
				if (comp > 0) {
					counter = counter.left;
					LR = 1;
				} else {
					counter = counter.right;
					LR = 2;
				}
			}

			if (LR == 1) { // If the node is on the left tree
				Node temp = counter.parent;
				counter.parent.left = (Node) array[mid];
				counter.parent.left.parent = temp;
				// Helper method that gives both subproblems
				recursiveDecompose(true, counter.parent.left, arrayLeft);
				recursiveDecompose(false, counter.parent.left, arrayRight);
			} else { // If the node is on the right tree
				Node temp = counter.parent;
				counter.parent.right = (Node) array[mid];
				counter.parent.right.parent = temp;
				// Helper method that gives both subproblems
				recursiveDecompose(true, counter.parent.right, arrayLeft);
				recursiveDecompose(false, counter.parent.right, arrayRight);
			}

		}
	}

	/**
	 * A Helper method that is being called recursively to decompose an array
	 * into a tree
	 * 
	 * @param flag
	 *            To indicate that the middle array to be put on the left/right
	 *            tree. True = left, False = right
	 * @param node
	 *            Node whose left and right tree to be updated
	 * @param array
	 *            Array of the subproblems
	 */
	private void recursiveDecompose(boolean flag, Node node, BSTNode[] array) {
		if (array[0] == null) // If the subarray/subproblem is null, it
								// straightly returns nothing.
			return;
		if (array.length <= 1) { // If only one element left in the
									// subarray/subproblem, creating the leaf
									// node.
			if (flag) { // To indicate if the problem is to be added on the left
						// tree
				node.left = (Node) array[0];
				node.left.left = null;
				node.left.right = null;
				if (node.left == null)
					node.left = (Node) array[0];
				node.left.parent = node;
			} else { // To indicate if the problem is to be added on the right
						// tree
				node.right = (Node) array[0];
				node.right.left = null;
				node.right.right = null;
				if (node.right == null)
					node.right = (Node) array[0];
				node.right.parent = node;
			}
			return;
		}

		boolean check = true; // to indicate whether the subproblem only have 2
								// elements left
		BSTNode<E>[] arrayLeft;
		BSTNode<E>[] arrayRight;
		int mid;

		if (array.length == 2) { // condition if subproblem only have 2 elements
									// left, it's going to make the first
									// subproblem to be null
			mid = 0;
			check = false;
			arrayLeft = new BSTNode[1];
			arrayRight = new BSTNode[1];
		} else if (array.length % 2 == 0) {
			mid = (array.length / 2) - 1;
			arrayLeft = new BSTNode[mid];
			arrayRight = new BSTNode[array.length - mid - 1];
		} else {
			mid = (array.length / 2);
			arrayLeft = new BSTNode[mid];
			arrayRight = new BSTNode[array.length - mid - 1];
		}

		if (flag) { // If the subproblem is on the left
			node.left = (Node) array[mid];
			node.left.left = null;
			node.left.right = null;
			// check if node.left.parent is null
			if (node.left == null)
				node.left = (Node) array[mid];
			node.left.parent = node;
		} else { // If the subproblem is on the right
			node.right = (Node) array[mid];
			node.right.left = null;
			node.right.right = null;
			// check if node.right.parent is null
			if (node.right == null)
				node.right = (Node) array[mid];
			node.right.parent = node;
		}

		if (check) { // If there are more than 2 elements left in the array
			for (int i = 0; i < arrayLeft.length; i++) {
				arrayLeft[i] = array[i];
			}
			for (int i = 0; i < arrayRight.length; i++) {
				arrayRight[i] = array[i + mid + 1];
			}
		} else { // Otherwise it sets the first subproblem to be null, so that
					// it can be returned right away by this method
			arrayLeft[0] = null;
			arrayRight[0] = array[1]; // The right subproblem only has 1 element
										// left which is the one next to the
										// middle array
		}
		if (flag) { // If we need to go to the left node
			recursiveDecompose(true, node.left, arrayLeft);
			recursiveDecompose(false, node.left, arrayRight);
		} else {
			recursiveDecompose(true, node.right, arrayLeft);
			recursiveDecompose(false, node.right, arrayRight);
		}
	}

	/**
	 * A Helper method that creates an array from a binary search tree
	 * 
	 * @param bstNode
	 *            The node that is going to be recursively put into the array in
	 *            order (Infix)
	 */
	private void intoArray(BSTNode<E> bstNode) {
		if (bstNode != null) {
			intoArray(bstNode.left());
			array[index++] = bstNode;
			intoArray(bstNode.right());
		}
	}

	@Override
	public boolean contains(Object obj) {
		// This cast may cause comparator to throw ClassCastException at
		// runtime,
		// which is the expected behavior
		E key = (E) obj;
		return findEntry(key) != null;
	}

	@Override
	public boolean add(E key) {
		if (root == null) {
			root = new Node(key, null);
			root.parent = null;
			++size;
			return true;
		}

		Node current = root;
		while (true) {
			int comp = current.data.compareTo(key);
			if (comp == 0) {
				// key is already in the tree
				return false;
			} else if (comp > 0) {
				if (current.left != null) {
					current = current.left;
				} else {
					current.left = new Node(key, current);
					current.left.parent = current;
					++size;
					if (isSelfBalancing) // To check whether the BST is
											// isSelfBalancing, if so it's going
											// to break right away
						break;
					else
						return true;
				}
			} else {
				if (current.right != null) {
					current = current.right;
				} else {
					current.right = new Node(key, current);
					current.right.parent = current;
					++size;
					if (isSelfBalancing) // To check whether the BST is
											// isSelfBalancing, if so it's going
											// to break right away
						break;
					else
						return true;
				}
			}
		}

		Node counter = root;
		boolean balanceIt = false;
		while (true) { // To traverse from the root to the nearest unbalanced
						// subtree, heading towards the node that has just been
						// added
			int comp = counter.data.compareTo(key);
			if (comp > 0) {
				counter = counter.left;
				if ((float) counter.count() / counter.parent.count() > (float) top
						/ bottom) {
					balanceIt = true;
					break;
				}
			} else {
				counter = counter.right;
				if ((float) counter.count() / counter.parent.count() > (float) top
						/ bottom) {
					balanceIt = true;
					break;
				}
			}
			if (counter.left == null && counter.right == null) { // Reach the
																	// leaf node
				break;
			}
		}

		if (balanceIt) {
			rebalance(counter.parent);
		}

		return true;
	}

	@Override
	public boolean remove(Object obj) {
		// This cast may cause comparator to throw ClassCastException at
		// runtime,
		// which is the expected behavior
		E key = (E) obj;
		Node n = findEntry(key);
		if (n == null) {
			return false;
		}
		unlinkNode(n);
		if (isSelfBalancing) { // If the BST is isSelfBalancing, it's going to
								// call a helper method checkRemove that
								// traverses every subtree
			Node counter = root;
			checkRemove(root);
		}
		return true;
	}

	/**
	 * A Helper method that is going traverse each node from the root, it breaks
	 * right away once it finds a node that is unbalanced on each left and right
	 * nodes
	 * 
	 * @param node
	 *            Node to be check if its left and right node are unbalanced
	 */
	private void checkRemove(Node node) {
		if (node.left == null && node.right == null) { // It reaches the leaf
														// node
			return;
		} else if (node.left != null && node.right != null) { // the node has
																// both left and
																// right nodes
																// that has to
																// be checked
																// both to
																// qualify the
																// rebalance
																// method
			if ((float) node.left.count() / node.count() > (float) top / bottom) {
				rebalance(node);
				return;
			} else
				checkRemove(node.left);
			if ((float) node.right.count() / node.count() > (float) top
					/ bottom) {
				rebalance(node);
				return;
			} else
				checkRemove(node.right);
		} else if (node.left != null && node.right == null) {
			if ((float) node.left.count() / node.count() > (float) top / bottom) {
				rebalance(node);
				return;
			} else
				checkRemove(node.left);
		} else {
			if ((float) node.right.count() / node.count() > (float) top
					/ bottom) {
				rebalance(node);
				return;
			} else
				checkRemove(node.right);
		}
	}

	/**
	 * Returns the node containing key, or null if the key is not found in the
	 * tree.
	 * 
	 * @param key
	 * @return the node containing key, or null if not found
	 */
	protected Node findEntry(E key) {
		Node current = root;
		while (current != null) {
			int comp = current.data.compareTo(key);
			if (comp == 0) {
				return current;
			} else if (comp > 0) {
				current = current.left;
			} else {
				current = current.right;
			}
		}
		return null;
	}

	/**
	 * Returns the successor of the given node.
	 * 
	 * @param n
	 * @return the successor of the given node in this tree, or null if there is
	 *         no successor
	 */
	protected Node successor(Node n) {
		if (n == null) {
			return null;
		} else if (n.right != null) {
			// leftmost entry in right subtree
			Node current = n.right;
			while (current.left != null) {
				current = current.left;
			}
			return current;
		} else {
			// we need to go up the tree to the closest ancestor that is
			// a left child; its parent must be the successor
			Node current = n.parent;
			Node child = n;
			while (current != null && current.right == child) {
				child = current;
				current = current.parent;
			}
			// either current is null, or child is left child of current
			return current;
		}
	}

	/**
	 * Removes the given node, preserving the binary search tree property of the
	 * tree.
	 * 
	 * @param n
	 *            node to be removed
	 */
	protected void unlinkNode(Node n) {
		// first deal with the two-child case; copy
		// data from successor up to n, and then delete successor
		// node instead of given node n
		if (n.left != null && n.right != null) {
			Node s = successor(n);
			n.data = s.data;
			n = s; // causes s to be deleted in code below
		}

		// n has at most one child
		Node replacement = null;
		if (n.left != null) {
			replacement = n.left;
		} else if (n.right != null) {
			replacement = n.right;
		}

		// link replacement into tree in place of node n
		// (replacement may be null)
		if (n.parent == null) {
			root = replacement;
		} else {
			if (n == n.parent.left) {
				n.parent.left = replacement;
			} else {
				n.parent.right = replacement;
			}
		}

		if (replacement != null) {
			replacement.parent = n.parent;
		}

		--size;
	}

	@Override
	public Iterator<E> iterator() {
		return new BSTIterator();
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns a representation of this tree as a multi-line string. The tree is
	 * drawn with the root at the left and children are shown top-to-bottom.
	 * Leaves are marked with a "-" and non-leaves are marked with a "+".
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toStringRec(root, sb, 0);
		return sb.toString();
	}

	/**
	 * Preorder traversal of the tree that builds a string representation in the
	 * given StringBuilder.
	 * 
	 * @param n
	 *            root of subtree to be traversed
	 * @param sb
	 *            StringBuilder in which to create a string representation
	 * @param depth
	 *            depth of the given node in the tree
	 */
	private void toStringRec(Node n, StringBuilder sb, int depth) {
		for (int i = 0; i < depth; ++i) {
			sb.append("  ");
		}

		if (n == null) {
			sb.append("-\n");
			return;
		}

		if (n.left != null || n.right != null) {
			sb.append("+ ");
		} else {
			sb.append("- ");
		}
		sb.append(n.data.toString() + " (" + n.count() + ")");
		sb.append("\n");
		if (n.left != null || n.right != null) {
			toStringRec(n.left, sb, depth + 1);
			toStringRec(n.right, sb, depth + 1);
		}
	}

	/**
	 * Iterator implementation for this binary search tree. The elements are
	 * returned in ascending order according to their natural ordering.
	 */
	private class BSTIterator implements Iterator<E> {
		/**
		 * Node to be returned by next call to next().
		 */
		private Node current;

		/**
		 * Node returned by last call to next() and available for removal. This
		 * field is null when no node is available to be removed.
		 */
		private Node pending;

		/**
		 * Constructs an iterator starting at the smallest element in the tree.
		 */
		public BSTIterator() {
			// start out at smallest value
			current = root;
			if (current != null) {
				while (current.left != null) {
					current = current.left;
				}
			}
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			pending = current;
			current = successor(current);
			return pending.data;
		}

		@Override
		public void remove() {
			if (pending == null)
				throw new IllegalStateException();

			// Remember, current points to the successor of
			// pending, but if pending has two children, then
			// unlinkNode(pending) will copy the successor's data
			// into pending and delete the successor node.
			// So in this case we want to end up with current
			// pointing to the pending node.
			if (pending.left != null && pending.right != null) {
				current = pending;
			}
			unlinkNode(pending);
			pending = null;

			if (isSelfBalancing) {
				Node counter = root;
				checkRemove(root); // helper method that will check if there's
									// an unbalanced node after removing.
			}
		}
	}
}
