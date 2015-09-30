package edu.iastate.cs228.hw2;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Random;

/**
 * 
 * This is the main class where you can run your code. This will not be graded.
 * 
 * @author Kelvien Hidayat
 * 
 */
public class Launcher {

	/**
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		int[] ranks = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		int[] ranks2 = { 1, 2, 4, 5, 3 };
		Ranking a = new Ranking(ranks);
		Ranking b = new Ranking(ranks2);
		Ranking.kemeny(a, b);
	}
	
	private static int binarySearch(float[] data, float find, int left, int right){
		if(left > right) return -1;
		int middle = (left + right)/2;
		if(data[middle] == find) return middle;
		else if(data[middle] < find) return binarySearch(data, find, left, middle-1);
		else return binarySearch(data, find, middle+1, right);
	}
	
	private static int sortAndCount(float[] data) {
		if (data.length == 1)
			return 0;
		else {
			int mid = data.length / 2;
			float[] left = new float[mid];
			for (int i = 0; i < mid; i++) { // O(n)
				left[i] = data[i];
			}
			float[] right = new float[data.length - mid];
			for (int i = 0; i < right.length; i++) { // O(n)
				right[i] = data[mid + i];
			}
			int invLeft = sortAndCount(left); // O(log n)
			int invRight = sortAndCount(right);// O(log n)
			int invCross = mergeAndCount(data, left, right); // O(n)
			// for (int i = 0; i < data.length; i++) {
			// Ranks[i] = data[i];
			// }
			return invLeft + invRight + invCross;
		} // O(2n+n 2log n) = O(n log n)
	}

	/**
	 * A helper method for kemeny and invCount method.
	 * 
	 * @param scores
	 *            The data which is being sorted
	 * @param left
	 *            The left subarrays
	 * @param right
	 *            The right subarrays
	 * @return
	 */
	private static int mergeAndCount(float[] scores, float[] left, float[] right) {
		int i, j, k, count;
		i = j = k = count = 0;
		float[] temp = new float[left.length + right.length];
		while (i < left.length && j < right.length) { // O(n)
			if (left[i] > right[j]) {
				temp[k++] = left[i++];
			} else {
				temp[k++] = right[j++];
				count += (left.length - i);
			}
		}
		while (i < left.length) { // O(n)
			temp[k++] = left[i++];
		}
		while (j < right.length) { // O(n)
			temp[k++] = right[j++];
		}
		for (int l = 0; l < temp.length; l++) { // O(n)
			scores[l] = temp[l];
		}
		return count;
	} // Total time complexity: O(4n) = O(n)
	
}
