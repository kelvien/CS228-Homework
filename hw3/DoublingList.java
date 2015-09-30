package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author Kelvien Hidayat
 * 
 *         A doubly linked list that stores data in Nodes with varying size
 *         arrays as the backing store.
 * 
 *         Important Note: Your index-based methods remove(int pos), add(int
 *         pos, E item) and listIterator(int pos) must not traverse every
 *         element in order to find the node and offset for a given index pos
 *         (see spec for more details)
 * 
 * @param <E>
 *            the type of elements in this list
 */
public class DoublingList<E> extends AbstractSequentialList<E> {

	/**
	 * Node to keep track of the head (beginning of the list)
	 */
	private Node head;
	/**
	 * Node to keep track of the tail (end of the list)
	 */
	private Node tail;
	/**
	 * Size of nodes. NOT elements in each node.
	 */
	private int size;
	/**
	 * Total of elements from each nodes.
	 */
	private int elementSize;

	/**
	 * Constructor of DoublingList Setting a head dummy node and a tail dummy
	 * node and connect them together.
	 */
	public DoublingList() {
		head = new Node(null);
		tail = new Node(null);
		head.prev = null;
		head.next = tail;
		tail.prev = head;
		tail.next = null;
		size = 0;
		elementSize = 0;
	}

	/**
	 * HELPER method: To get the size of the cap.
	 * 
	 * @return total Size of the cap
	 */
	private int getSizeOfNode() {
		int total = 0;
		for (int i = 0; i < size; i++) {
			total += Math.pow(2, i);
		}
		return total;
	}

	/**
	 * HELPER method : Link nodes, happens when we want to insert a new
	 * node/item.
	 */
	private void link(Node curr, Node news) {
		if (curr == null || news == null) {
			throw new IllegalStateException(
					"The current or the new node you want to link is null");
		} else {
			news.next = curr.next;
			curr.next.prev = news;
			news.prev = curr;
			curr.next = news;
			size++;
		}
	}

	/**
	 * HELPER method : Unlink nodes, happens when we remove the node.
	 */
	private void unlink(Node target) {
		if (target == null) {
			throw new IllegalStateException(
					"The node you want to remove is null");
		} else {
			target.prev.next = target.next;
			target.next.prev = target.prev;
			size--;
		}
	}

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
		if (pos < 0 || pos >= elementSize) {
			throw new IndexOutOfBoundsException(
					"The position you want to remove is not within boundary");
		}

		NodeInfo prog = findNodeByIndex(pos);
		int progOffset = prog.offset;
		Node progNode = prog.node;
		E ret = (E) progNode.data[progOffset];
		elementSize--;
		if (elementSize == 0) {
			head = new Node(null);
			tail = new Node(null);
			head.prev = null;
			head.next = tail;
			tail.prev = head;
			tail.next = null;
			size = 0;
			elementSize = 0;
		}

		for (int a = progOffset; a < progNode.available - 1; a++) {
			progNode.data[a] = progNode.data[a + 1];
		}
		progNode.data[progNode.available - 1] = null;
		progNode.deleteElement();

		if (elementSize <= (int) (Math.pow(2, size - 2)) - 1) { // The case when
																// compacting
																// list is
																// needed.
			removeAndCompact();
		}

		return ret;
	}

	/**
	 * HELPER method: To compact the list that has elements less than (2^k-2)-1
	 * This method create a new list with a compacted elements where in the end
	 * it's pointed back to the head and tail (Making it as the current list)
	 */
	private void removeAndCompact() {
		DoublingList<E> newList = new DoublingList<E>();
		Iterator<E> move = this.iterator();

		E[] data = (E[]) new Object[this.getSizeOfNode()];
		int index = 0;
		while (move.hasNext()) { // To take every elements on the current list.
			data[index++] = move.next();
		}

		E[] newData = (E[]) new Object[this.getSizeOfNode()];
		int newIndex = 0;
		int nullElement = 0;
		for (int i = 0; i < this.getSizeOfNode(); i++) { // Filling up a new array of elements with the elements from old list.
			if (data[i] != null) {
				newData[newIndex++] = data[i];
			} else {
				nullElement++;
			}
		}

		for (int i = 0; i < newIndex; i++) { // Constructing the new list with ordered elements.
			newList.add(newData[i]);
		}
		for (int i = 0; i < nullElement; i++) { // Adding null elements so that the list can get empty nodes if necessary.
			newList.addNull(null);
		}

		unlink(newList.tail.prev);

		this.head = newList.head;
		this.tail = newList.tail;

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
		if (pos < 0 || pos > elementSize)
			throw new IndexOutOfBoundsException(
					"The position you want to add is out of boundary");

		if (this.getSizeOfNode() == 0) {
			add(item);
		}

		else if (elementSize < this.getSizeOfNode()) { // at least one element
														// is
														// empty
			NodeInfo info = findNodeByIndex(pos);
			int offset = info.offset;

			if (pos <= elementSize - 1) { // (C)
				if (info.node.available < info.node.data.length) { // If the
																	// current
																	// node is
																	// not full.
					for (int i = info.node.available - 1; i >= offset; i--) {
						info.node.data[i + 1] = info.node.data[i];
					}
					info.node.data[offset] = item;
					info.node.addElement();
				} else if (this.checkPredecessor(info.node)) { // If there is an
																// empty space
																// on the
																// predecessor
																// node. Do a
																// leftward
																// shift
					leftwardShifting(info.node, offset);
					info.node.data[offset] = item;
				} else {
					rightwardShifting(info.node, offset);
					info.node.data[offset] = item;
				}
			} else if (pos == elementSize) {
				if (info.node.available < info.node.data.length
						&& info.node.available != 0) { // If the current node is
														// not full.
					info.node.data[offset] = item;
					info.node.addElement();
				} else if (this.checkPredecessor(info.node)) { // If there is an
																// empty space
																// on the
																// predecessor
																// node. Do a
																// leftward
																// shift
					leftwardShifting(info.node, offset);
					info.node.data[offset] = item;
				} else {
					info.node.data[0] = item;
					info.node.addElement();
				}
			}
		} else if (elementSize == this.getSizeOfNode()) { // needs more space
			NodeInfo info = findNodeByIndex(pos);
			int offset = info.offset;

			E[] use = (E[]) new Object[1];
			use[0] = item;
			checkCapacity();
			if (pos == elementSize) {
				tail.prev.data[0] = item;
				tail.prev.addElement();
			} else {
				rightwardShifting(info.node, info.offset);
				info.node.data[info.offset] = item;
			}
		}
		elementSize++;
	}

	/**
	 * HELPER method: To check whether the predecessor nodes has an empty space
	 * 
	 * @param cur
	 *            The node where it starts checking the empty space
	 * @return True if it has empty element on the rest of the predecessors,
	 *         False if it doesn't have any.
	 */
	private boolean checkPredecessor(Node cur) {
		Node current = cur;
		while (current.prev != head) {
			current = current.prev;
			if (current.available < current.data.length) {
				return true;
			}
		}
		return false;
	}

	/**
	 * HELPER method: To shift all elements to the left starting from the offset
	 * and node where it starts.
	 * 
	 * @param info
	 *            the Node where it starts shifting all elements to the left.
	 * @param offset
	 *            the offset of the element where it starts shifting all
	 *            elements to the left.
	 */
	private void leftwardShifting(Node info, int offset) {
		Node curNode = info;
		int index = 0;

		while (curNode.available == curNode.data.length) { // To get to the most left predecessor that has empty slot
			curNode = curNode.prev;
		}
		while (curNode != info) {
			if (curNode != head.next
					&& curNode.data[curNode.data.length - 1] != null) { // Process of pulling elements to the right
				for (int i = 0; i < curNode.available; i++) {
					curNode.data[i] = curNode.data[i + 1];
				}
			}
			while (curNode.available < curNode.data.length) {
				curNode.data[curNode.available] = curNode.next.data[index]; // Taking element from next node if necessary.
				curNode.addElement();
				curNode.next.deleteElement();
			}
			index = 0;
			curNode = curNode.next;
		}
		for (int i = 0; i < offset; i++) { // Final alignment on the current node where an item is added.
			curNode.data[i] = curNode.data[i + 1];
		}
	}

	/**
	 * HELPER method: To shift all elements to the right starting from the
	 * offset and node where it starts.
	 * 
	 * @param info
	 *            the Node where it starts shifting all elements to the right.
	 * @param offset
	 *            the offset of the element where it starts shifting all
	 *            elements to the right.
	 */
	private void rightwardShifting(Node info, int offset) {
		Node curNode = info;
		int index;

		while (curNode.available == curNode.data.length) { // Process of going to the most right node with an empty spot.
			curNode = curNode.next;
		}

		while (curNode != info) {
			for (int i = curNode.available; i > 0; i--) { // process of aligning elements to the most left after the first element is taken from the previous node.
				curNode.data[i] = curNode.data[i - 1];
			}

			if (curNode.prev.available == 0)
				index = 0;
			else
				index = curNode.prev.available - 1;
			curNode.data[0] = curNode.prev.data[index];
			curNode.prev.deleteElement();
			curNode.addElement();
			curNode = curNode.prev;
		}

		for (int i = curNode.data.length - 1; i > offset; i--) { // final alignment of the node where the item is added.
			curNode.data[i] = curNode.data[i - 1];
		}
		curNode.addElement();

	}

	/**
	 * HELPER Method: Allow add to add null element. This is used to add null
	 * element during compacting List.
	 * 
	 * @param item
	 *            It will be null and allowed by this add method.
	 * @return True if adding is successful, false otherwise.
	 */
	private boolean addNull(E item) {
		if (size == 0) { // If there isn't any node yet, we create our first
			// node with the size of 1.
			E[] use = (E[]) new Object[1];
			use[0] = item;
			Node firstNode = new Node(use);
			head.next = firstNode;
			tail.prev = firstNode;
			firstNode.next = tail;
			firstNode.prev = head;
			size++;
			elementSize++;
		} else { // Add the item to the end of the list.
			E[] use = (E[]) new Object[1];
			use[0] = item;
			checkCapacity();
			tail.prev.data[tail.prev.available] = use[0];
			tail.prev.addElement();
			elementSize++;
		}
		return false;
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
			if (size == 0) { // If there isn't any node yet, we create our first
								// node with the size of 1.
				E[] use = (E[]) new Object[1];
				use[0] = item;
				Node firstNode = new Node(use);
				head.next = firstNode;
				tail.prev = firstNode;
				firstNode.next = tail;
				firstNode.prev = head;
				size++;
				elementSize++;
			} 
			else if(checkPredecessor(tail.prev)){
				Node current = tail.prev;
				while(tail.prev.available == 0){
					current = current.prev;
				}
				current.addElement();
				leftwardShifting(current,current.available-1);
				current.data[current.available-1] = item;
				elementSize++;
			}
			else { // Add the item to the end of the list.
				E[] use = (E[]) new Object[1];
				use[0] = item;
				checkCapacity();
				tail.prev.data[tail.prev.available] = use[0];
				tail.prev.addElement();
				elementSize++;
			}
		}
		return true;
	}

	/**
	 * HELPER method : Check whether the last node has an empty space for an
	 * item to be added. Used when we need to create another node with doubling size.
	 */
	private void checkCapacity() {
		if (tail.prev.available == tail.prev.data.length) { // The last node is
															// full
			E[] data = (E[]) new Object[(int) Math.pow(2, size)];
			Node biggerNode = new Node(data);
			link(tail.prev, biggerNode);
		}
	}

	/**
	 * Returns a ListIterator for this DoublingList at the given position (I.E.
	 * a call to next should return the element with the logical index equal to
	 * the index given)
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		return new DoublingListIterator(index);
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
		return elementSize;
	}

	/**
	 * A method to set the size for this DoublingList. In real life this method
	 * would not exist, but in order to test your code we need it.
	 */
	public void setSize(int size) {
		this.elementSize = elementSize;
	}

	/**
	 * 
	 * ListIterator class. Please reference the ListIterator API to see how
	 * methods handle errors (no next element, null arguments, etc.)
	 * 
	 * Note that is inner class is public. Normally this isn't done because then
	 * outside users can mess with the internal structure of the list, however
	 * we need this to test your code.
	 * 
	 * API: http://docs.oracle.com/javase/6/docs/api/java/util/ListIterator.html
	 */
	public class DoublingListIterator implements ListIterator<E> {
		/**
		 * Integer value that represents a value to be returned is behind the
		 * cursor.
		 */
		private static final int BEHIND = -1;
		/**
		 * Integer value that represents a value to be returned is ahead of the
		 * cursor.
		 */
		private static final int AHEAD = 1;
		/**
		 * Integer value that represents no value to be returned.
		 */
		private static final int NONE = 0;
		/**
		 * Integer value that represents where the cursor has just moved.
		 */
		private int direction;

		/**
		 * Head representation as a cursor.
		 */
		private Node cursorHead = head;
		/**
		 * An integer value to represent cursor.
		 */
		private int dataCursor = 0;
		/**
		 * An integer value to represent cursor inside a node.
		 */
		private int dataOffset = 0;

		/**
		 * ListIterator constructor that takes an integer value as a starting
		 * point of the cursor.
		 * 
		 * @param pos
		 *            desired position for the cursor to start.
		 */
		public DoublingListIterator(int pos) {
			if (pos < 0 || pos > elementSize)
				throw new IndexOutOfBoundsException(
						"Position of desired cursor is out of boundary");
			dataCursor = pos;
			NodeInfo info = findNodeByIndex(pos);
			dataOffset = info.offset;
			cursorHead = info.node;
			direction = NONE;
		}

		/**
		 * Default constructor that starts the cursor from 0.
		 */
		public DoublingListIterator() {
			this(0);
		}

		/**
		 * Adds the given element to the DoublingList following the rules of
		 * add(). DO NOT call the add method you wrote for DoublingList above!
		 */
		@Override
		public void add(E item) {
			if (item == null)
				throw new NullPointerException("Item to be added is null");

			if (elementSize == 0) {
				E[] use = (E[]) new Object[1];
				use[0] = item;
				Node firstNode = new Node(use);
				head.next = firstNode;
				tail.prev = firstNode;
				firstNode.next = tail;
				firstNode.prev = head;
				size++;
				cursorHead = cursorHead.next;
				dataOffset++;
			}

			else if (elementSize < getSizeOfNode()) {
				if (cursorHead.available < cursorHead.data.length) {
					rightwardShifting(cursorHead, dataOffset);
					if (dataOffset == cursorHead.available) { // normal adding at an empty spot on the node.
						dataOffset = 0;
						cursorHead = cursorHead.next;
					}
					cursorHead.data[dataOffset++] = item;
				} else if (checkPredecessor(cursorHead)) { // If predecessor has an empty spot, leftward shifting is performed.
					leftwardShifting(cursorHead, dataOffset - 1);
					if (dataOffset == cursorHead.available) {
						dataOffset = 0;
						cursorHead.data[dataOffset] = item;
					} else
						cursorHead.data[--dataOffset] = item;
				} else {
					rightwardShifting(cursorHead, dataOffset);
					if (dataOffset == cursorHead.available) {
						dataOffset = 0;
						cursorHead = cursorHead.next;
					}
					cursorHead.data[dataOffset++] = item;
				}
			} else { // The nodes are full, therefore it needs to create a new node with doubling size.
				checkCapacity();
				if (dataCursor == cursorHead.data.length - 1) {
					cursorHead.data[0] = item;
					cursorHead.addElement();
					dataCursor++;
					cursorHead = cursorHead.next;
				} else {
					rightwardShifting(cursorHead, dataOffset);
					if (dataOffset == cursorHead.available) {
						dataOffset = 0;
						cursorHead = cursorHead.next;
					}
					cursorHead.data[dataOffset++] = item;
				}
			}
			elementSize++;
			dataCursor++;
			direction = NONE;
		}

		/**
		 * Returns true if this list iterator has more elements when traversing
		 * the list in the forward direction. (In other words, returns true if
		 * next would return an element rather than throwing an exception.)
		 */
		@Override
		public boolean hasNext() {
			return dataCursor < elementSize;
		}

		/**
		 * Returns true if this list iterator has more elements when traversing
		 * the list in the reverse direction. (In other words, returns true if
		 * previous would return an element rather than throwing an exception.)
		 */
		@Override
		public boolean hasPrevious() {
			return dataCursor != 0;
		}

		/**
		 * Returns the next element in the list. This method may be called
		 * repeatedly to iterate through the list, or intermixed with calls to
		 * previous to go back and forth. (Note that alternating calls to next
		 * and previous will return the same element repeatedly.)
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException("Nothing to return");
			} else {
				if (dataOffset == cursorHead.data.length) {
					dataOffset = 0;
					direction = BEHIND;
					cursorHead = cursorHead.next;
				}
				E ret = (E) cursorHead.data[dataOffset];
				dataCursor++;
				direction = BEHIND;
				if (ret == null) {
					if (dataOffset + 1 == cursorHead.data.length) {
						cursorHead = cursorHead.next;
						dataOffset = 0;
					}
					E rets = (E) cursorHead.data[dataOffset++];
					return rets;
				}
				dataOffset++;
				return ret;
			}
		}

		/**
		 * Returns the index of the element that would be returned by a
		 * subsequent call to next. (Returns list size if the list iterator is
		 * at the end of the list.)
		 */
		@Override
		public int nextIndex() {
			return dataCursor;
		}

		/**
		 * Returns the previous element in the list. This method may be called
		 * repeatedly to iterate through the list backwards, or intermixed with
		 * calls to next to go back and forth. (Note that alternating calls to
		 * next and previous will return the same element repeatedly.)
		 */
		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException("Nothing to return");
			} else {
				if (dataOffset == 0) {
					cursorHead = cursorHead.prev;
					dataOffset = cursorHead.data.length - 1;
					E ret = (E) cursorHead.data[dataOffset];
					dataCursor--;
					if (ret == null) {
						E rets = (E) cursorHead.data[--dataOffset];
						direction = AHEAD;
						return rets;
					}
					direction = AHEAD;
					return ret;
				}
				E ret = (E) cursorHead.data[--dataOffset];
				dataCursor--;
				if (ret == null) {
					cursorHead = cursorHead.prev;
					dataOffset = cursorHead.data.length - 1;
					E rets = (E) cursorHead.data[dataOffset];
					direction = AHEAD;
					return rets;
				}
				direction = AHEAD;
				return ret;
			}
		}

		/**
		 * Returns the index of the element that would be returned by a
		 * subsequent call to previous. (Returns -1 if the list iterator is at
		 * the beginning of the list.)
		 */
		@Override
		public int previousIndex() {
			if (dataCursor == 0)
				return -1;
			else {
				return dataCursor - 1;
			}
		}

		/**
		 * Removes from the list the last element that was returned by next or
		 * previous (optional operation). This call can only be made once per
		 * call to next or previous. It can be made only if ListIterator.add has
		 * not been called after the last call to next or previous. DO NOT call
		 * the remove method you wrote for DoublingList above!
		 */
		@Override
		public void remove() {
			if (direction == NONE)
				throw new IllegalStateException(
						"You can't remove anything until you call next() or previous() method");
			else if (direction == AHEAD) {
				cursorHead.data[dataOffset] = null;
			} else {
				cursorHead.data[dataOffset - 1] = null;
				dataCursor--;
			}

			if (direction == AHEAD) {
				for (int a = dataOffset; a < cursorHead.available - 1; a++) {
					cursorHead.data[a] = cursorHead.data[a + 1];
				}
				cursorHead.data[cursorHead.available - 1] = null;
			} else {
				for (int a = dataOffset - 1; a < cursorHead.available - 1; a++) {
					cursorHead.data[a] = cursorHead.data[a + 1];
				}
				cursorHead.data[cursorHead.available - 1] = null;
				cursorHead.deleteElement();
			}
			direction = NONE;
			elementSize--;

			if (elementSize <= (int) (Math.pow(2, size - 2)) - 1) {
				removeAndCompact();
			}
		}

		/**
		 * Replaces the last element returned by next or previous with the
		 * specified element (optional operation). This call can be made only if
		 * neither ListIterator.remove nor ListIterator.add have been called
		 * after the last call to next or previous.
		 */
		@Override
		public void set(E item) {
			if (direction == NONE)
				throw new IllegalStateException(
						"You can't set anything until you call next() or previous() method");
			else if (direction == BEHIND) {
				cursorHead.data[dataOffset - 1] = item;
			} else {
				cursorHead.data[dataOffset] = item;
			}
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
	 */
	private class DoublingIterator implements Iterator<E> {
		/**
		 * Head representation as a cursor.
		 */
		private Node cursorHead = head;
		/**
		 * An integer value to represent cursor.
		 */
		private int dataCursor = 0;
		/**
		 * An integer value to represent cursor inside a node.
		 */
		private int dataOffset = 0;

		/**
		 * Returns true if the iteration has more elements. (In other words,
		 * returns true if next would return an element rather than throwing an
		 * exception.)
		 */
		@Override
		public boolean hasNext() {
			return dataCursor < getSizeOfNode();
		}

		/**
		 * Returns the next element in the iteration.
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException("Nothing to return");
			} else {
				if (cursorHead.data == null) {
					cursorHead = cursorHead.next;
					E ret = (E) cursorHead.data[dataOffset++];
					dataCursor++;
					return ret;
				}
				if (dataOffset == cursorHead.data.length) {
					dataOffset = 0;
					cursorHead = cursorHead.next;
				}
				E ret = (E) cursorHead.data[dataOffset++];
				dataCursor++;
				return ret;
			}
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
	 * HELPER method : To find the node that contains an element in logical
	 * index 'pos'.
	 * 
	 * @param pos
	 *            Logical index of element to be found.
	 * @return An inner class named NodeInfo that can contain Node and offset.
	 */
	private NodeInfo findNodeByIndex(int pos) {
		if (pos < 0 || pos > elementSize)
			throw new IllegalStateException(
					"Index that you want to find is out of boundary");
		Node current = head.next;
		int initial = 0;
		int count = current.available;

		if (pos == elementSize) {
			return new NodeInfo(tail.prev, tail.prev.available); 
		}

		while (true) {
			if ((initial <= pos && count > pos)) { 
				break;
			} else {
				current = current.next;
				initial = count;
				count += current.available;
			}
		}
		return new NodeInfo(current, pos - initial);
	}

	/**
	 * Node class that makes up a DoublingList. Feel free to add methods /
	 * constructors / variables you might find useful in here.
	 */
	public class Node {
		public Node next;
		public Node prev;
		public E[] data;
		/**
		 * number of elements in the node currently.
		 */
		private int available = 0;

		public Node(Node next, Node prev, E[] data) {
			this.next = next;
			this.prev = prev;
			this.data = data;
			if (data == null)
				available = 0;
			else {
				for (int i = 0; i < data.length; i++) {
					if (data[i] != null)
						available++;
				}
			}
		}

		public Node(E[] data) {
			this.data = data;
			if (data == null)
				available = 0;
			else {
				for (int i = 0; i < data.length; i++) {
					if (data[i] != null)
						available++;
				}
			}
		}

		/**
		 * HELPER method of node to add the element in the node.
		 */
		private void addElement() {
			available++;
		}

		/**
		 * HELPER method of node to delete the number of element in the node.
		 */
		private void deleteElement() {
			available--;
		}

	}

	/**
	 * NodeInfo class that you may find useful to use. Again, feel free to add
	 * methods / constructors / variables that you find useful in here.
	 */
	public class NodeInfo {
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
