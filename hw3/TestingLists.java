package edu.iastate.cs228.hw3;

import static org.junit.Assert.*;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import javax.swing.text.html.HTMLDocument.Iterator;

import junit.framework.Assert;

import org.junit.Test;

public class TestingLists {

	/**
	 * exceptions
	 * new list
	 * add to end of list when empty
	 * add to end of list when space available
	 * add to end of list when no space left
	 * add to pos when empty
	 * add to pos when space available and node not full
	 * add to pos when space available and node is full but predecessor has space
	 * add to pos when space available and node is full but predecessor has no space
	 * add to pos when pos == list size and last node is not full
	 * add to pos when pos == list size and last node is full but predecessor has space
	 * add to pos when pos == list size and last node is full and predecessor has no space
	 * add to pos when cap == list size and pos == list size
	 * add to pos when cap == list size and pos < list size
	 * remove pos so that list size == 0
	 * remove pos so that items must be shifted left
	 * remove pos so that list must be compacted
	 * listIterator at index 0
	 * listIterator at different index
	 * listIterator add tests
	 * listIterator remove tests
	 * listIterator hasNext
	 * listIterator next
	 * listIterator hasPrevious
	 * listIterator previous
	 * listIterator nextIndex
	 * listIterator previousIndex
	 * listIterator set NONE
	 * listIterator set AHEAD
	 * listITerator set BEHIND
	 * Node data test
	 * Node next test
	 * Node prev test
	 * 
	 */
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void removePosIndexException() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.remove(1);
		
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void addIndexException() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add(4, "A");
	}
	
	@Test(expected = NullPointerException.class)
	public void addPosNullException() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add(0, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void addNullException() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add(null);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void listIteratorIndexException() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.listIterator(4);
	}
	
	@Test(expected = NullPointerException.class)
	public void listIteratorAddNullException() {
		DoublingList<String> myList = new DoublingList<String>();
		ListIterator<String> iter = myList.listIterator();
		iter.add(null);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void listIteratorNextNoException() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("A");
		ListIterator<String> iter = myList.listIterator();
		iter.next();
		iter.next();
	}
	
	@Test(expected = NoSuchElementException.class)
	public void listIteratorPreviousNoException() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("A");
		ListIterator<String> iter = myList.listIterator();
		iter.previous();
	}
	
	@Test(expected = IllegalStateException.class)
	public void listIteratorSetIllegalException() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("A");
		ListIterator<String> iter = myList.listIterator();
		iter.set("A");
	}
	
	//TODO regular iterator exception test
	
	@Test
	public void newEmptyList() {
		DoublingList<String> myList = new DoublingList<String>();
		Assert.assertEquals("[]", myList.toStringInternal());
	}
	
	
	@Test
	public void addToEndEmpty() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("A");
		Assert.assertEquals("[(A)]", myList.toStringInternal());
	}
	
	@Test
	public void addToEndSpaceAvailable() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("A");
		myList.add("B");
		myList.add("C");
		Assert.assertEquals("[(A), (B, C)]", myList.toStringInternal());
	}
	
	@Test
	public void addToEndNoSpace() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("A");
		myList.add("B");
		Assert.assertEquals("[(A), (B, -)]", myList.toStringInternal());
	}
	
	@Test
	public void addToPosEmpty() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add(0, "A");
		Assert.assertEquals("[(A)]", myList.toStringInternal());
	}
	
	@Test
	public void addToPosNodeNotFull() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("A");
		myList.add("C");
		myList.add(1, "B");
		Assert.assertEquals("[(A), (B, C)]", myList.toStringInternal());
	}
	
	@Test
	public void addToPosNodeFullPreOpen() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("B");
		myList.add("A");
		myList.add("C");
		myList.remove(0);
		myList.add(0, "B");
		Assert.assertEquals("[(A), (B, C)]", myList.toStringInternal());
	}
	
	@Test
	public void addToPosNodeFullPreFull() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("A");
		myList.add("B");
		myList.add("D");
		myList.add(2, "C");
		Assert.assertEquals("[(A), (B, C), (D, -, -, -)]", myList.toStringInternal());
	}
	
	@Test
	public void addToPosListSizeNodeNotFull() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("A");
		myList.add("B");
		myList.add(2, "C");
		Assert.assertEquals("[(A), (B, C)]", myList.toStringInternal());
	}
	
	@Test
	public void addToPosListSizePreNotFull() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("A");
		myList.add("B");
		myList.add("C");
		myList.add("D");
		myList.add("E");
		myList.add("F");
		myList.add("G");
		myList.remove(0);
		myList.add(5, "H");
		Assert.assertEquals("[(B), (C, D), (E, F, G, H)]", myList.toStringInternal());
	}
	
	@Test
	public void addToPosListSizeListFull() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("A");
		myList.add("B");
		myList.add("C");
		myList.add("E");
		myList.remove(3);
		myList.add(3, "D");
		Assert.assertEquals("[(A), (B, C), (D, -, -, -)]", myList.toStringInternal());
	}
	
	@Test
	public void addToPosListSizeEqualsListCap() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("A");
		myList.add("B");
		myList.add("C");
		myList.add(3, "D");
		Assert.assertEquals("[(A), (B, C), (D, -, -, -)]", myList.toStringInternal());
	}
	
	@Test
	public void addToPosLessThanListSizeEqualsListCap() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("A");
		myList.add("B");
		myList.add("D");
		myList.add(2, "C");
		Assert.assertEquals("[(A), (B, C), (D, -, -, -)]", myList.toStringInternal());
	}
	
	@Test
	public void removeListZero() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("A");
		myList.remove(0);
		Assert.assertEquals("[]", myList.toStringInternal());
	}
	
	@Test
	public void removeShiftLeft() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("A");
		myList.add("B");
		myList.add("C");
		myList.remove(1);
		Assert.assertEquals("[(A), (C, -)]", myList.toStringInternal());
	}
	
	@Test
	public void removeAndCompact() {
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("A");
		myList.add("B");
		myList.add("C");
		myList.add("D");
		myList.add("E");
		myList.add("F");
		myList.add("G");
		myList.add("H");
		myList.remove(0);
		myList.remove(0);
		myList.remove(0);
		myList.remove(0);
		myList.remove(0);
		myList.remove(0);
		Assert.assertEquals("[(-), (G, H), (-, -, -, -)]", myList.toStringInternal());
	}
	
	

}