package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A doubly linked list that stores data in Nodes with varying size arrays as
 * the backing store.
 * 
 * Important Note: Your index-based methods remove(int pos), add(int pos, E
 * item) and listIterator(int pos) must not traverse every element in order to
 * find the node and offset for a given index pos (see spec for more details)
 * 
 */
public class DoublingListBAK<E> extends AbstractSequentialList<E> {

	/**
	 * Node to keep track of the head (beginning of the list)
	 */
	public Node head;
	/**
	 * Node to keep track of the tail (end of the list)
	 */
	public Node tail;

	/**
	 * 
	 * 
	 */
	private int nodeSize = 0;
	private int itemSize = 0;

	/**
	 * Removes the element with the given logical position, following the rules
	 * for removing an element. 1. Find the node r and the offset j for element
	 * with logical index i. 2. Save the contents of entry j of the array at r
	 * in a variable x and set the entry to null. 3. If the total number of
	 * elements in L drops to zero, replace L with an empty list and return x 4.
	 * Shift all the elements in r with offset greater than j down by one. Make
	 * sure that the resulting empty slot in the array is set to null. See
	 * Figure 9. 5. If L.size() <= 2^(k-2) - 1, replace L by another doubling
	 * list L' such that - L' has k-1 nodes (numbered 0 through k-2), - L'
	 * contains the same elements as L, in the same order, and - all elements of
	 * L' are stored in nodes 0 through k-3. Thus, the last node is completely
	 * empty 6. Return x.
	 */
	@Override
	public E remove(int pos) {
		// TODO
		return null;
	}

	/**
	 * Adds the given item to have the given logical position. Adds a new Node
	 * if necessary. Follows the rules stated by leftward and rightward shift.
	 * 
	 * Leftward shift at i. Let t be the rightmost predecessor of r that has
	 * empty slots (we assume that such a node exists). Then, we shift by one
	 * position to the left all elements with logical index less than or equal
	 * to i stored in the nodes between the successor of t and r inclusive. See
	 * Figure 5.
	 * 
	 * Rightward shift at i. Let t be the leftmost successor of r that has empty
	 * slots (we assume that such a node exists). Then, we shift by one position
	 * to the right every element with logical
	 */
	@Override
	public void add(int pos, E item) {
		// TODO
	}

	/**
	 * Adds the given item to the end of the list. Creates a new Node if
	 * Necessary. Throws a NullPointerException if the item is null. Return true
	 * if the add was successful, false otherwise.
	 */
	@Override
	public boolean add(E item) {
		if (item == null)
			throw new NullPointerException("Item is null");
		else {
			if(head == null){ // List is empty
				E[] tempData = (E[]) new Object[1];
				tempData[0] = item;
				Node temp = new Node(null, null, tempData);
				temp.add();
				head = temp;
				tail = temp;
				nodeSize = 0;
				itemSize++;
				return true;
			}
			else{
				checkCapacity();
				tail.data[tail.getIndex()] = item;
				itemSize++;
				tail.add();
				return true;
			}
		}
	}

	private void checkCapacity() {
		if(tail.getSize()==0){ // Node is full
			updateNodeSize();
			E[] data = (E[]) new Object[getNodeSize(nodeSize)];
			Node temp = new Node(null, tail, data);
			tail.next = temp;
			tail = temp;
		}
	}

	private void updateNodeSize(){
		nodeSize++;
	}
	
	private int getNodeSize(int nodeSize){
		return (int) Math.pow(2, nodeSize);
	}
	
	/**
	 * Returns a ListIterator for this DoublingList at the given position (I.E.
	 * a call to next should return the element with the logical index equal to
	 * the index given)
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		// TODO
		return null;
	}

	/**
	 * Returns a ListIterator for this DoublingList starting from the beginning
	 */
	@Override
	public ListIterator<E> listIterator() {
		return new DoublingListIterator();
	}

	/**
	 * Returns an Iterator for this DoublingList starting from the beginning
	 */
	@Override
	public Iterator<E> iterator() {
		return new DoublingIterator();
	}

	/**
	 * Returns the size of the list. It is acceptable to create an instance
	 * variable and update it during add / remove so you can just return that
	 * variable here.
	 */
	@Override
	public int size() {
		return itemSize;
	}

	/**
	 * 
	 * ListIterator class. Please reference the ListIterator API to see how
	 * methods handle errors (no next element, null arguments, etc.)
	 * 
	 * API: http://docs.oracle.com/javase/6/docs/api/java/util/ListIterator.html
	 * 
	 * @param <E>
	 *            The type of elements the ListIterator will be iterating over
	 */
	private class DoublingListIterator<E> implements ListIterator<E> {

		/**
		 * Adds the given element to the DoublingList following the rules of
		 * add(). DO NOT call the add method you wrote for DoublingList above!
		 * This one needs to run in O(1) (constant time).
		 */
		@Override
		public void add(E arg0) {
			// TODO
			// DO NOT call DoublingList add methods here!
		}

		/**
		 * Returns true if this list iterator has more elements when traversing
		 * the list in the forward direction. (In other words, returns true if
		 * next would return an element rather than throwing an exception.)
		 */
		@Override
		public boolean hasNext() {
			// TODO
			return false;
		}

		/**
		 * Returns true if this list iterator has more elements when traversing
		 * the list in the reverse direction. (In other words, returns true if
		 * previous would return an element rather than throwing an exception.)
		 */
		@Override
		public boolean hasPrevious() {
			// TODO
			return false;
		}

		/**
		 * Returns the next element in the list. This method may be called
		 * repeatedly to iterate through the list, or intermixed with calls to
		 * previous to go back and forth. (Note that alternating calls to next
		 * and previous will return the same element repeatedly.)
		 */
		@Override
		public E next() {
			// TODO
			return null;
		}

		/**
		 * Returns the index of the element that would be returned by a
		 * subsequent call to next. (Returns list size if the list iterator is
		 * at the end of the list.)
		 */
		@Override
		public int nextIndex() {
			// TODO
			return 0;
		}

		/**
		 * Returns the previous element in the list. This method may be called
		 * repeatedly to iterate through the list backwards, or intermixed with
		 * calls to next to go back and forth. (Note that alternating calls to
		 * next and previous will return the same element repeatedly.)
		 */
		@Override
		public E previous() {
			// TODO
			return null;
		}

		/**
		 * Returns the index of the element that would be returned by a
		 * subsequent call to previous. (Returns -1 if the list iterator is at
		 * the beginning of the list.)
		 */
		@Override
		public int previousIndex() {
			// TODO
			return 0;
		}

		/**
		 * Removes from the list the last element that was returned by next or
		 * previous (optional operation). This call can only be made once per
		 * call to next or previous. It can be made only if ListIterator.add has
		 * not been called after the last call to next or previous. DO NOT call
		 * the remove method you wrote for DoublingList above! This one should
		 * run in O(1) (constant time)
		 */
		@Override
		public void remove() {
			// TODO
			// DO NOT call DoublingList remove methods here
		}

		/**
		 * Replaces the last element returned by next or previous with the
		 * specified element (optional operation). This call can be made only if
		 * neither ListIterator.remove nor ListIterator.add have been called
		 * after the last call to next or previous.
		 */
		@Override
		public void set(E arg0) {
			// TODO
		}

	}

	/**
	 * 
	 * Iterator to be used for traversing a DoublingList. This iterator is
	 * optional if you fully implement the ListIterator but is easier and
	 * partial point will be awarded if the one is correct and your ListIterator
	 * is wrong.
	 * 
	 * API: http://docs.oracle.com/javase/6/docs/api/java/util/Iterator.html
	 * 
	 * @param <E>
	 *            The type of element to be traversed
	 */
	private class DoublingIterator<E> implements Iterator<E> {

		private Node cursor = head;
		private int index = 0;
		private int node = 0;
		/**
		 * Returns true if the iteration has more elements. (In other words,
		 * returns true if next would return an element rather than throwing an
		 * exception.)
		 */
		@Override
		public boolean hasNext() {
			return index<getNodeSize(nodeSize);
		}

		/**
		 * Returns the next element in the iteration.
		 */
		@Override
		public E next() {
			if(!hasNext()){
				throw new NoSuchElementException("Nothing to return");
			}
			if(index==getNodeSize(node)){
				index = 0;
				cursor = cursor.next;
				node++;
			}
			E ret = (E) cursor.data[index++];
			cursor.addIndex();
			return ret;
		}

		/**
		 * You do not need to implement this method
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Node class that makes up a DoublingList. Feel free to add methods /
	 * constructors / variables you might find useful in here.
	 * 
	 */
	private class Node {
		public Node next;
		public Node prev;
		public E[] data;
		private int size;
		private int index=0;

		public Node(Node next, Node prev, E[] data) {
			this.next = next;
			this.prev = prev;
			this.data = data;
			this.size = data.length;
		}

		public Node(E[] data) {
			this.data = data;
			this.size = data.length;
		}
		
		public int getSize(){ // public is fine??
			return size;
		}
		
		public void add(){
			size--;
			index++;
		}
		
		public void addIndex(){
			index++;
		}
		
		public int getIndex(){
			return index;
		}
	}

	/**
	 * NodeInfo class that you may find useful to use. Again, feel free to add
	 * methods / constructors / variables that you find useful in here.
	 * 
	 */
	private class NodeInfo {
		public Node node;
		public int offset;

		public NodeInfo(Node node, int offset) {
			this.node = node;
			this.offset = offset;
		}
	}

	/**
	 * Returns a string representation of this list showing the internal
	 * structure of the nodes.
	 */
	public String toStringInternal() {
		return toStringInternal(null);
	}

	/**
	 * Returns a string representation of this list showing the internal
	 * structure of the nodes and the position of the iterator.
	 * 
	 * @param iter
	 *            an iterator for this list
	 */
	public String toStringInternal(ListIterator<E> iter) {
		int count = 0;
		int position = -1;
		if (iter != null) {
			position = iter.nextIndex();
		}

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		Node current = head.next;
		while (current != tail) {
			sb.append('(');
			E data = current.data[0];
			if (data == null) {
				sb.append("-");
			} else {
				if (position == count) {
					sb.append("| ");
					position = -1;
				}
				sb.append(data.toString());
				++count;
			}

			for (int i = 1; i < current.data.length; ++i) {
				sb.append(", ");
				data = current.data[i];
				if (data == null) {
					sb.append("-");
				} else {
					if (position == count) {
						sb.append("| ");
						position = -1;
					}
					sb.append(data.toString());
					++count;

					// iterator at end
					if (position == size() && count == size()) {
						sb.append(" |");
						position = -1;
					}
				}
			}
			sb.append(')');
			current = current.next;
			if (current != tail)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

}
