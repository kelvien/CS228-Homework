package edu.iastate.cs228.hw4;

import java.util.Comparator;
import java.util.Stack;

/**
 * @author Kelvien Hidayat
 * 
 * Project 4, non-recursive merge sort.
 */
public class NonRecursiveMergeSort {

	/**
	 * Generic version of merge sort. The type T is arbitrary, but the caller
	 * must supply a comparator. The algorithm must be implemented
	 * non-recursively and must run in O(n log n) time.
	 * @param <T>
	 */

	/**
	 * Private class "element"
	 * To store beginning index, ending index of an array, and a flag to indicate whether the stack needs a merge or not.
	 *
	 * @param <T>
	 */
	private static class element<T> {
		boolean flag;
		int boundL;
		int boundR;

		public element(int left, int right) {
			this.boundL = left;
			this.boundR = right;
			this.flag = false;
		}
	}
	
	/**
	 * Method mergeSort with a parameter "Comparator"
	 * @param arr element needed to be sorted
	 * @param comp Comparator object that compare 2 objects.
	 */
	public static <T> void mergeSort(T[] arr, Comparator<? super T> comp) {

		if (arr == null || comp == null) throw new IllegalArgumentException("Array or comparator is null");
		
		if(arr.length == 0)	return;
		
		ArrayBasedStack<element> stack = new ArrayBasedStack<element>();
		
		int left = 0;
		int right = arr.length - 1;
		stack.push(new element(left, right)); // Beginning index, and ending index pushed to the stack.
		
		while (!stack.isEmpty()) {
			
			element elmt = stack.peek();
			int rightIn = elmt.boundR;
			int leftIn = elmt.boundL;
			int mid = (leftIn + rightIn) / 2;
			
			if (leftIn == rightIn) { // Reach the base case which only has 1 element in a subarray.
				elmt.flag = true;
				stack.pop();
			} 
			else if(elmt.flag){ // Stack is ready to be merged because the subproblems are already merged/have reached the base case.
				stack.pop();
				mergeComp(arr, leftIn, mid, rightIn, comp);
			}
			else { // Stack has a subproblems, therefore it creates 2 more subarrays
				elmt.flag = true;
				stack.push(new element(mid + 1, rightIn));
				stack.push(new element(leftIn, mid));
			}
			
		}

	}

	/**
	 * A helper method of mergeSort that takes object of Comparator as the parameter
	 * @param arr array of elements to be sorted
	 * @param left beginning index of array
	 * @param mid middle index of array
	 * @param right ending index of an array
	 * @param comp object Comparator that will compare 2 elements/objects
	 */
	private static<T> void mergeComp(T[] arr, int left, int mid, int right, Comparator<? super T> comp){
		int p = mid-left+1;
		int q = right-mid;
		T[] elmt1 = (T[]) new Object[p];
		T[] elmt2 = (T[]) new Object[q];
		
		for (int i = 0; i < p; i++) { // Creating left subarray from the initial array with an index referring to the stack its corresponding.
			elmt1[i] = arr[left+i];
		}
		for (int i = 0; i < q; i++) { // Creating right subarray from the initial array with an index referring to the stack its corresponding.
			elmt2[i] = arr[(mid+1)+i];
		}
		
		T[] newArr = (T[]) new Object[p+q];
		int j = 0;
		int k = 0;
		int l = 0;
		
		while(j<p&&k<q){ // Comparison
			if(comp.compare(elmt1[j],elmt2[k])<0){
				newArr[l++] = elmt1[j++];
			}
			else{
				newArr[l++] = elmt2[k++];
			}
		}
		
		while(j<p){ // Appending the left subarray if any
				newArr[l++] = elmt1[j++];
		}
		while(k<q){ // Appending the right subarray if any
				newArr[l++] = elmt2[k++];
		}
		int a = 0;
		for (int i = left; i <= right; i++) { // Replacing the initial array with the sorted array within the boundaries corresponded by the stack.
			arr[i] = newArr[a++];
		}
	}
	
	/**
	 * A helper method of mergeSort that takes elements with a characteristic of Comparable / implements Comparable.
	 * @param arr Array of elements to be sorted
	 * @param left beginning index of the subarray
	 * @param mid middle index of the subarray
	 * @param right ending index of the subarray
	 */
	private static <T extends Comparable<? super T>> void merge(T[] arr, int left, int mid, int right){
		int p = mid-left+1;
		int q = right-mid;
		T[] elmt1 = (T[]) new Comparable[p];
		T[] elmt2 = (T[]) new Comparable[q];
		
		for (int i = 0; i < p; i++) { // Creating left subarray from the initial array with an index referring to the stack its corresponding.
			elmt1[i] = arr[left+i];
		}
		for (int i = 0; i < q; i++) { // Creating right subarray from the initial array with an index referring to the stack its corresponding.
			elmt2[i] = arr[(mid+1)+i];
		}
		T[] newArr = (T[]) new Comparable[p+q];
		int j = 0;
		int k = 0;
		int l = 0;
		
		while(j<p&&k<q){ // Comparison
			if(elmt1[j].compareTo(elmt2[k])<0){
				newArr[l++] = elmt1[j++];
			}
			else{
				newArr[l++] = elmt2[k++];
			}
		}
		while(j<p){ // Appending the left subarray if any
				newArr[l++] = elmt1[j++];
		}
		while(k<q){ // Appending the right subarray if any
				newArr[l++] = elmt2[k++];
		}
		int a = 0;
		for (int i = left; i <= right; i++) { // Replacing the initial array with the sorted array within the boundaries corresponded by the stack.
			arr[i] = newArr[a++];
		}
	}

	/**
	 * Another generic version of merge sort. This one imposes bounds on T to
	 * guarantee that we can call the compareTo() method on objects of type T.
	 * The algorithm must be implemented non-recursively and must run in O(n log
	 * n) time.
	 */
	public static <T extends Comparable<? super T>> void mergeSort(T[] arr) {
		if (arr == null) {
			throw new IllegalArgumentException("Array is null");
		}

		if(arr.length == 0)	return;
		
		ArrayBasedStack<element> stack = new ArrayBasedStack<element>();
		
		int left = 0;
		int right = arr.length - 1;
		stack.push(new element(left, right)); // Initial stack with the beginning and ending index of the problem.
		
		while (!stack.isEmpty()) {
			element elmt = stack.peek();
			int rightIn = elmt.boundR;
			int leftIn = elmt.boundL;
			int mid = (leftIn + rightIn) / 2;
			if (leftIn == rightIn) { // Reach the base case
				elmt.flag = true;
				stack.pop();
			} 
			else if(elmt.flag){ // Reach the stack that is ready to be merged
				stack.pop();
				Comparator<? super T> comp = null;
				merge(arr, leftIn, mid, rightIn);
			}
			else { // Stack that needs to be divided into 2 more subarrays
				elmt.flag = true;
				stack.push(new element(mid + 1, rightIn));
				stack.push(new element(leftIn, mid));
			}
			
		}
	}

}