package edu.iastate.cs228.hw3;

import java.awt.List;
import java.util.Iterator;
import java.util.ListIterator;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
       
		DoublingList<String> myList = new DoublingList<String>();
		myList.add("A");
		myList.add("B");
		myList.add("C");
		myList.add("D");
		myList.add("E");
		myList.add("F");
//		myList.add("G");
		myList.remove(0);
		System.out.println(myList.toStringInternal());
		myList.add("X");
		System.out.println(myList.toStringInternal());
		myList.remove(1);
		System.out.println(myList.toStringInternal());
		myList.add("Z");
		System.out.println(myList.toStringInternal());
		
		
		
		
	}

}
