package edu.iastate.cs228.hw2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * 
 * @author Kelvien Hidayat
 * 
 */
public class Ranking implements IRanking {

	/**
	 * Your code must include implementations of algorithms SORTANDCOUNT and
	 * MERGEANDCOUNT and it must use these implementations to count inversions
	 * and compute Kemeny distance.
	 */

	/**
	 * The precise representation of Ranking objects is left up to you - you
	 * could, for instance, use arrays or ArrayLists
	 */
	/**
	 * An array of int containing the permutation of n (Ranks' length)
	 */
	private int[] Ranks;
	/**
	 * A copy of arrays of int which can be sorted
	 */
	private int[] RanksCopy;
	/**
	 * Ranks after the sorted array which will be used to count the inversion.
	 */
	private static int[] RanksForKemeny;
	/**
	 * A copy of arrays of float
	 */
	private float[] RanksCopyFloat;

	/**
	 * Constructs a random ranking of the numbers 1 through n. Throws an
	 * IllegalArgumentException if n < 1. Must run in O(n log n) time <br>
	 * <br>
	 * <strong>Note:</strong> For random number generation, use the
	 * RandomSingleton class from Project 1; this generator might be modified
	 * slightly for use in this project. To generate a random permutation of 1
	 * through n, use the <a href=
	 * "http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm"
	 * >"shufﬂe" algorithm</a>
	 * 
	 * @param n
	 *            Number of random rankings to create
	 * @throws IllegalArgumentException
	 *             if n < 1
	 */
	public Ranking(int n) throws IllegalArgumentException {
		// Grab our Random object (You must use this class for anything Random!)
		Random rand = RandomSingleton.getInstance();
		if (n < 1) { // If n is less than one, constructor will throw an
						// exception.
			throw new IllegalArgumentException(
					"n has to be bigger than or equal to 1");
		} else { // Otherwise it will make a shuffled permutation of n.
			Ranks = new int[n];
			for (int i = 0; i < n; i++) { // O(n)
				Ranks[i] = i + 1;
			}
			for (int i = n - 1; i >= 0; i--) { // O(n)
				int index = 1 + rand.nextInt(i + 1);
				int temp = Ranks[index - 1];
				Ranks[index - 1] = Ranks[i];
				Ranks[i] = temp;
			}
			// Total time complexity: O(2n) = O(n)
		}
	}

	/**
	 * Constructs a ranking o of the set U = {1,...,rank.length}, where o(i) =
	 * rank[i-1]. Throws a NullPointerException if rank is null. Throws an
	 * IllegalArgumentException if rank does not consist of distinct elements
	 * between 1 and rank.length. Must run in O(n log n) time or better, where n
	 * = rank.length.
	 * 
	 * @param rank
	 *            Ranking set
	 * @throws NullPointerException
	 *             if rank is null
	 * @throws IllegalArgumentException
	 *             if rank does not consist of distinct elements between 1 and
	 *             rank.length
	 */
	public Ranking(int[] rank) throws NullPointerException,
			IllegalArgumentException {

		if (rank == null) { // To check whether the parameter is null.
			throw new NullPointerException("Rank is null.");
		} else { // Otherwise it will check whether it contains each of the
					// permutation of rank's length
			int[] check = new int[rank.length];
			for (int i = 0; i < rank.length; i++) { // O(n)
				int add = 0;
				add = rank[i];
				if (add <= 0 || add > rank.length) { // If it is not or there's
														// a double element, it
														// throws an exception.
					throw new IllegalArgumentException(
							"There is a double rank or rank does not consist of distinct elements between 1 and rank.length");
				}
				check[add - 1]++;
			}
			for (int i = 0; i < rank.length; i++) { // O(n)
				if (check[i] != 1) { // To check whether it contains only 1
										// element in each permutation
					throw new IllegalArgumentException(
							"There is a double rank or rank does not consist of distinct elements between 1 and rank.length");
				}
			}
			Ranks = rank; // If all of the above are passed, it assigns the rank
							// to the Rank in this class.
		}
	} // Total time complexity : O(2n) = O(n).

	/**
	 * Constructs a ranking of the set U = {1,...,scores.length}, where element
	 * i gets rank k if and only if scores[i-1] is the kth largest element in
	 * the array scores. Throws a NullPointerException if scores is null. Throws
	 * an IllegalArgumentException if scores contains duplicate values. Must run
	 * in O(n log n) time, where n = scores.length. <br>
	 * <br>
	 * Example: <br>
	 * Suppose scores = (0,75, 0.36, 0.65, 1.5, 0.85). Then, the corresponding
	 * ranking is o = (2, 4, 3, 5, 1).
	 * 
	 * @param scores
	 *            Scores set
	 * @throws NullPointerException
	 *             if scores is null
	 * @throws IllegalArgumentException
	 *             if scores contains duplicate values
	 */
	public Ranking(float[] scores) {
		if (scores == null) { // To check whether scores is null.
			throw new NullPointerException("scores is null");
		} else {
			Ranks = new int[scores.length];
			float[] copy = new float[scores.length];
			for (int i = 0; i < copy.length; i++) { // Making a copy of scores;
													// O(n)
				copy[i] = scores[i];
			}
			sortFloat(copy); // O(n log n)
			int[] check = new int[copy.length];
			for (int j = 0; j < copy.length; j++) { // For each j, do a binary
													// search // O(n)
				Ranks[j] = binarySearch(copy, scores[j], 0, copy.length) + 1; // O(log
																				// n)
				check[Ranks[j] - 1]++;
			}// Total: O(n log n)
			for (int i = 0; i < copy.length; i++) { // O(n)
				if (check[i] != 1) { // To check whether there is no double
										// ranking.
					throw new IllegalArgumentException( // Otherwise throw an
														// exception.
							"There is a double rank or rank does not consist of distinct elements between 1 and rank.length");
				}
			}

		}
	} // Total time complexity: O(n log n + n log n + n) = O(n+ 2n log n) = O(n
		// log n).

	/**
	 * Binary search used to search a value in a sorted array. In this case,
	 * it's to find a ranking of a sorted array from the float constructor
	 * 
	 * @param data
	 *            Data which is already sorted
	 * @param find
	 *            value to be searched
	 * @param left
	 *            left subarrays
	 * @param right
	 *            right subarrays
	 * @return index of the value to be searched
	 */
	private static int binarySearch(float[] data, float find, int left,
			int right) {
		if (left > right) // Base case
			return -1; // if not found, return -1
		int middle = (left + right) / 2;
		if (data[middle] == find) // If value is at the middle partition of
									// array, we can return the value right away
			return middle;
		else if (data[middle] < find) // If value is bigger than the middle
										// part, then the value must be on the
										// left side. Call binary Search
										// recursively on the left subarray
			return binarySearch(data, find, left, middle - 1);
		else
			// If value is smaller than the middle part, then the value must be
			// on the right side.
			return binarySearch(data, find, middle + 1, right);
	}

	/**
	 * A helper method to sort arrays of float.
	 * 
	 * @param data
	 *            data with arrays of float
	 */
	private static void sortFloat(float[] data) {
		if (data.length == 1) {
		} else {
			int mid = data.length / 2;
			float[] left = new float[mid];
			for (int i = 0; i < mid; i++) { // O(n)
				left[i] = data[i];
			}
			float[] right = new float[data.length - mid];
			for (int i = 0; i < right.length; i++) { // O(n)
				right[i] = data[mid + i];
			}
			sortFloat(left); // O(log n)
			sortFloat(right);// O(log n)
			mergeFloat(data, left, right); // O(n)
		} // O(2n+n 2log n) = O(n log n)
	}

	/**
	 * A helper method for constructor with float[] parameter to merge 2
	 * subarrays
	 * 
	 * @param scores
	 *            The data which is being sorted
	 * @param left
	 *            The left subarrays
	 * @param right
	 *            The right subarrays
	 */
	private static void mergeFloat(float[] scores, float[] left, float[] right) {
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
	} // Total time complexity: O(4n) = O(n)

	/**
	 * To return the length of the Ranking
	 * 
	 * @return the number of items in the ranking. Must run in O(1) time
	 */
	public int getNumItems() {
		return Ranks.length; // O(1);
	}

	/**
	 * Returns the rank of item i. Throws an IllegalArgumentException if item i
	 * is not present in the ranking. Must run in O(1) time.
	 * 
	 * @param i
	 *            Item to get rank for
	 * @return the rank of item i
	 * @throws IllegalArgumentException
	 *             if item i is not present in the ranking
	 */
	public int getRank(int i) throws IllegalArgumentException {
		if (i <= Ranks.length && i >= 1) {
			return Ranks[i - 1]; // O(1)
		} else {
			throw new IllegalArgumentException(
					"Item i is not present in the ranking"); // O(1)
		}
	}

	/**
	 * Returns the footrule distance between r1 and r2. Throws a
	 * NullPointerException if either r1 or r2 is null. Throws an
	 * IllegalArgumentException if r1 and r2 have different lengths. Must run in
	 * O(n) time, where n is the number of elements in r1 (or r2).
	 * 
	 * @param r1
	 *            Ranking object
	 * @param r2
	 *            Ranking object
	 * @return the footrule distance between r1 and r2
	 * @throws NullPointerException
	 *             if either r1 or r2 is null
	 * @throws IllegalArgumentException
	 *             if r1 and r2 have different lengths
	 */
	public static int footrule(Ranking r1, Ranking r2) {
		int result = 0;

		if ((r1 == null || r2 == null) || (r1 == null && r2 == null)) {
			throw new NullPointerException(
					"Either r1 or r2 is null or both are null");
		} else if (r1.getNumItems() != r2.getNumItems()) { // O(1)
			throw new IllegalArgumentException(
					"r1 and r2 have different lengths");
		} else { // O(1)
			for (int i = 1; i <= r1.getNumItems(); i++) { // O(n)
				result += Math.abs(r1.getRank(i) - r2.getRank(i));
			}
			return result;
		}
	} // Total time complexity : O(n)

	/**
	 * Returns the Kemeny distance between r1 and r2. Throws a
	 * NullPointerException if either r1 or r2 is null. Throws an
	 * IllegalArgumentException if r1 and r2 have different lengths. Must run in
	 * O(n log n) time, where n is the number of elements in r1 (or r2).
	 * 
	 * @param r1
	 *            Ranking object
	 * @param r2
	 *            Ranking object
	 * @return the Kemeny distance between r1 and r2
	 * @throws NullPointerException
	 *             if either r1 or r2 is null
	 * @throws IllegalArgumentException
	 *             if r1 and r2 have different lengths
	 */
	public static int kemeny(Ranking r1, Ranking r2) {
		if ((r1 == null || r2 == null) || (r1 == null && r2 == null)) { // O(1)
			throw new NullPointerException("Either r1 or r2 is null"); // To
																		// check
																		// whether
																		// either
																		// r1 or
																		// r2 is
																		// null,
																		// throws
																		// an
																		// exception
																		// if it
																		// does.
		} else if (r1.getNumItems() != r2.getNumItems()) { // O(1)
			throw new IllegalArgumentException( // To check whether r1 and r2
												// have different lengths,
												// throws an exception if it
												// does.
					"r1 and r2 have different lengths");
		} else { // O(1)
			int[][] kemenyRank = new int[r1.getNumItems()][2];
			for (int i = 1; i <= r1.getNumItems(); i++) { // O(n)
				kemenyRank[i - 1][0] = r1.getRank(i);
				kemenyRank[i - 1][1] = r2.getRank(i);
			}
			sortAndCountKemeny(kemenyRank); // O(n log n)
			RanksForKemeny = new int[r1.getNumItems()];
			for (int i = 0; i < kemenyRank.length; i++) { // O(n)
				RanksForKemeny[i] = kemenyRank[i][1];
			}
			return sortAndCount(RanksForKemeny); // O(n log n)
		}
	} // Total time complexity: O(n + 2n log n) = O(n log n).

	/**
	 * A helper method for kemeny method. It sort and count the data, total
	 * numbers of inversion in the data. The sorted r1 will arrange r2 such as
	 * that we can count the inversion and it tells us that it's the kemeny
	 * distance of r1 and r2. It takes a 2 dimensional array as a parameter.
	 * "First column" is to store each r1's rank, "Second column" is to store
	 * each r2's rank When it sorts r1, r2 then will also be sorted such as that
	 * it arranged and made eligible for us to count the inversions in r2.
	 * 
	 * @param data
	 *            A 2 dimensional array which "First column" stores each ranks
	 *            in r1, and "Second column" stores each rank in r2
	 */
	private static void sortAndCountKemeny(int[][] data) {
		if (data.length == 1)
			return;// O(1)
		else { // O(1)
			int mid = data.length / 2;
			int[][] left = new int[mid][2];
			for (int i = 0; i < mid; i++) { // O(n)
				left[i][0] = data[i][0];
				left[i][1] = data[i][1];
			}
			int[][] right = new int[data.length - mid][2];
			for (int i = 0; i < right.length; i++) { // O(n)
				right[i][0] = data[mid + i][0];
				right[i][1] = data[mid + i][1];
			}
			sortAndCountKemeny(left); // Recursively call sortAndCountKemeny
										// O(log n)
			sortAndCountKemeny(right); // Recursively call sortAndCountKemeny
										// O(log n)
			mergeAndCountKemeny(data, left, right); // Recursively call
													// mergeAndCountKemeny O(n)
		} // Total time complexity: O(n log n)
	}

	/**
	 * A helper method for kemeny method It combines 2 sub arrays and sort them
	 * into 1. When it sorts scores, it sorts the first column and second
	 * column, therefore you can get the sorted r1, and the arranged r2 such
	 * that we can count the inversion of r2 and tell that it is the kemeny
	 * distance of r1 and r2
	 * 
	 * @param scores
	 *            The data which is being sorted
	 * @param left
	 *            The left subarrays
	 * @param right
	 *            The right subarrays
	 */

	private static void mergeAndCountKemeny(int[][] scores, int[][] left,
			int[][] right) {
		int i, j, k;
		i = j = k = 0;
		int[][] temp = new int[left.length + right.length][2];
		while (i < left.length && j < right.length) { // O(n)
			if (left[i][0] < right[j][0]) {
				temp[k][0] = left[i][0];
				temp[k++][1] = left[i++][1];
			} else {
				temp[k][0] = right[j][0];
				temp[k++][1] = right[j++][1];
			}
		}
		while (i < left.length) { // O(n)
			temp[k][0] = left[i][0];
			temp[k++][1] = left[i++][1];
		}
		while (j < right.length) { // O(n)
			temp[k][0] = right[j][0];
			temp[k++][1] = right[j++][1];
		}
		for (int l = 0; l < temp.length; l++) { // O(n)
			scores[l][0] = temp[l][0];
			scores[l][1] = temp[l][1];
		}
	} // Total time complexity : O (4n) = O(n)

	/**
	 * A helper method for kemeny distance and invCount method.
	 * 
	 * @param data
	 *            the Ranks that is being sorted
	 * @return total number of inversion.
	 */
	private static int sortAndCount(int[] data) {
		if (data.length == 1)
			return 0;
		else {
			int mid = data.length / 2;
			int[] left = new int[mid];
			for (int i = 0; i < mid; i++) { // O(n)
				left[i] = data[i];
			}
			int[] right = new int[data.length - mid];
			for (int i = 0; i < right.length; i++) { // O(n)
				right[i] = data[mid + i];
			}
			int invLeft = sortAndCount(left); // O(log n)
			int invRight = sortAndCount(right);// O(log n)
			int invCross = mergeAndCount(data, left, right); // O(n)
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
	private static int mergeAndCount(int[] scores, int[] left, int[] right) {
		int i, j, k, count;
		i = j = k = count = 0;
		int[] temp = new int[left.length + right.length];
		while (i < left.length && j < right.length) { // O(n)
			if (left[i] < right[j]) {
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

	/**
	 * Returns the footrule distance between this and other. Throws a
	 * NullPointerException if other is null. Throws an IllegalArgumentException
	 * if this and other have different lengths. Must run in O(n) time, where n
	 * is the number of elements in this (or other).
	 * 
	 * @param other
	 *            Ranking object
	 * @return the footrule distance between this and other
	 * @throws NullPointerException
	 *             if other is null
	 * @throws IllegalArgumentException
	 *             if this and other have different lengths
	 */
	public int fDist(Ranking other) {
		int result = 0;
		if (other == null) {
			throw new NullPointerException("Other is null");
		} else if (this.getNumItems() != other.getNumItems()) {
			throw new IllegalArgumentException(
					"this ranks and other have different lengths");
		} else {
			for (int i = 1; i <= this.getNumItems(); i++) { // O(n)
				result += Math.abs(this.getRank(i) - other.getRank(i));
			}
			return result;
		}
	} // Total time complexity: O(n)

	/**
	 * Returns the Kemeny distance between this and other. Throws a
	 * NullPointerException if other is null. Throws an IllegalArgumentException
	 * if this and other have different lengths. Must run in O(n log n) time,
	 * where n is the number of elements in this (or other).
	 * 
	 * @param other
	 *            Ranking object
	 * @return the Kemeny distance between this and other
	 * @throws NullPointerException
	 *             if other is null
	 * @throws IllegalArgumentException
	 *             if this and other have different lengths
	 */
	public int kDist(Ranking other) {
		if (other == null) { // O(1)
			throw new NullPointerException("Other is null");
		} else if (this.getNumItems() != other.getNumItems()) {
			throw new IllegalArgumentException( // O(1)
					"This and other have different lengths");
		} else { // O(1)
			int[][] kemenyRank = new int[other.getNumItems()][2];
			for (int i = 1; i <= other.getNumItems(); i++) { // O(n)
				kemenyRank[i - 1][0] = this.getRank(i);
				kemenyRank[i - 1][1] = other.getRank(i);
			}
			sortAndCountKemeny(kemenyRank); // O(n log n)
			RanksForKemeny = new int[other.getNumItems()];
			for (int i = 0; i < kemenyRank.length; i++) { // O(n)
				RanksForKemeny[i] = kemenyRank[i][1];
			}
			return sortAndCount(RanksForKemeny); // O(n log n)
		} // Total time complexity: O(n + 2n log n) = O(n log n)
	}

	/**
	 * Returns the number of inversions in this ranking. Should run in O(n log
	 * n) time, where n is the number of elements in this. <br>
	 * <br>
	 * <strong>Note:</strong> Since Ranking objects are immutable, you could, in
	 * fact, compute the number of inversions in a ranking just once, at the
	 * time of creation, and store it for later access. With this
	 * implementation, invCount would take O(1) time. You are free to implement
	 * this version or the one that computes inversions every time the method is
	 * called; your documentation should indicate clearly which approach your
	 * method uses.
	 * 
	 * @return the number of inversions in t
	 */
	public int invCount() {
		RanksCopy = new int[Ranks.length];
		for (int i = 0; i < RanksCopy.length; i++) { // O(n)
			RanksCopy[i] = Ranks[i];
		}
		return sortAndCount(RanksCopy); // O(n log n)
	} // Total time complexity: O(n + n log n) = O(n log n)

}
