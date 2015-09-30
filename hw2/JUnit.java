package edu.iastate.cs228.hw2;

import static org.junit.Assert.*;

import java.util.IllegalFormatException;

import org.junit.Test;

/**
 * 
 * @author Kelvien Hidayat
 * 
 */
public class JUnit {

	// Testing the first constructor with the n (length of the ranking)
	// parameter
	// Should throw an IllegalArgumentException because the length is below 1.
	@Test(expected = IllegalArgumentException.class)
	public void constructorWithIllegalArgumentException() {
		Ranking a = new Ranking(0);
	}

	// Testing the first constructor if it produces valid input
	public void constructorCheckValidRankings() {
		Ranking a = new Ranking(3);
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		for (int i = 0; i < a.getNumItems(); i++) {
			if (a.getRank(i) == 1) {
				count1++;
			}
			if (a.getRank(i) == 2) {
				count2++;
			}
			if (a.getRank(i) == 3) {
				count3++;
			}
		}
		assertEquals(count1, 1);
		assertEquals(count2, 1);
		assertEquals(count3, 1);
	}

	// Testing the second constructor with the array of integers parameter
	// Should throw an NullPointerException because the array is null.
	@Test(expected = NullPointerException.class)
	public void constructor2WithNullPointerException() {
		int[] rank = null;
		Ranking a = new Ranking(rank);
	}

	// Testing the second constructor with the array of integers parameter
	// Should throw an IllegalArgumentException because the rank does not
	// consist elements between 1 to the array length.
	@Test(expected = IllegalArgumentException.class)
	public void constructor2WithIllegalArgumentException() {
		int[] rank = { 1, 2, 5 };
		Ranking a = new Ranking(rank);
	}

	// Testing the second constructor if it produces valid input
	public void constructor2CheckValidRankings() {
		int[] input = { 1, 2, 3 };
		Ranking a = new Ranking(input);
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		for (int i = 0; i < a.getNumItems(); i++) {
			if (a.getRank(i) == 1) {
				count1++;
			}
			if (a.getRank(i) == 2) {
				count2++;
			}
			if (a.getRank(i) == 3) {
				count3++;
			}
		}
		assertEquals(count1, 1);
		assertEquals(count2, 1);
		assertEquals(count3, 1);
	}

	// Testing the third constructor with the array of floats parameter
	// Should throw an IllegalArgumentException because there is/are floats
	// numbers that is/are doubling.
	@Test(expected = IllegalArgumentException.class)
	public void constructor3WithIllegalArgumentException() {
		float[] rank = { (float) 0.99, (float) 1.5, 5, (float) 3.78,
				(float) 1.5, (float) 0.99 };
		Ranking a = new Ranking(rank);
	}

	// Testing the third constructor with the array of floats parameter
	// Should throw a NullPointerException because the parameter is null.
	@Test(expected = NullPointerException.class)
	public void constructor3WithNullArgumentException() {
		float[] rank = null;
		Ranking a = new Ranking(rank);
	}

	// Testing the third constructor if it produces valid input
	public void constructor3CheckValidRankings() {
		float[] input = { (float) 0.99, 2, (float) 10.5 };
		Ranking a = new Ranking(input);
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		for (int i = 0; i < a.getNumItems(); i++) {
			if (a.getRank(i) == 1) {
				count1++;
			}
			if (a.getRank(i) == 2) {
				count2++;
			}
			if (a.getRank(i) == 3) {
				count3++;
			}
		}
		assertEquals(count1, 1);
		assertEquals(count2, 1);
		assertEquals(count3, 1);
	}

	// Testing getNumItems method
	// Should return 5 because there are 5 rankings.
	@Test
	public void getNumItems() {
		Ranking a = new Ranking(5);
		assertEquals(a.getNumItems(), 5);
	}

	// Testing getNumItems with the second constructor and should return 8
	@Test
	public void getNumItems2() {
		int[] ranks = { 1, 2, 3, 4, 5, 6, 7, 8 };
		Ranking a = new Ranking(ranks);
		assertEquals(a.getNumItems(), 8);
	}

	// Testing getNumItems with the third constructor and should return 10
	@Test
	public void getNumItems3() {
		float[] ranks = { 3, 5, (float) 7.6, (float) 0.99, (float) 0.56, 2, 1,
				(float) 8.9, (float) 10.12, 12 };
		Ranking a = new Ranking(ranks);
		assertEquals(a.getNumItems(), 10);
	}

	// Testing getRank method with a parameter 0th index and should return the
	// first index in the ranking which is 0.
	@Test
	public void getRank() {
		int[] rank = { 1, 2, 3, 4, 5 };
		Ranking a = new Ranking(rank);
		assertEquals(a.getRank(1), 1);
	}

	// Testing getRank method with different Rankings.
	@Test
	public void getRankRev() {
		int[] rank = { 5, 4, 3, 2, 1 };
		Ranking a = new Ranking(rank);
		assertEquals(a.getRank(1), 5);
	}

	// Testing getRank on single-element Ranking
	@Test
	public void getRankSingle() {
		int[] rank = { 1 };
		Ranking a = new Ranking(rank);
		assertEquals(a.getRank(1), 1);
	}

	// Testing getRank with other index (3), and should return 4.
	@Test
	public void getRankOtherIndex() {
		int[] ranks = { 1, 2, 3, 4, 5 };
		Ranking a = new Ranking(ranks);
		assertEquals(a.getRank(4), 4);
	}

	// Testing getRank
	// Should throw an IllegalArgumentException because the index is not within
	// the Rankings boundary.
	@Test(expected = IllegalArgumentException.class)
	public void getRankException() {
		Ranking a = new Ranking(2);
		a.getRank(10);
	}

	// Second testing of getRank
	// Should throw an IllegalArgumentException because the index is not within
	// the Rankings boundary.
	@Test(expected = IllegalArgumentException.class)
	public void getRankException2() {
		int[] ranks = { 1, 2, 3, 4, 5 };
		Ranking a = new Ranking(ranks);
		a.getRank(6);
	}

	// Testing on footrule method
	// Should return 12 based on the footrule formula |a1 - a2|+|b1 -
	// b2|+........
	@Test
	public void footrule() {
		int[] ranks = { 1, 2, 3, 4, 5 };
		int[] ranks2 = { 5, 4, 3, 2, 1 };
		Ranking a = new Ranking(ranks);
		Ranking b = new Ranking(ranks2);
		Ranking c = new Ranking(5);
		assertEquals(c.footrule(a, b), 12);
	}

	// Testing on footrule method
	// Should throw a NullPointerException because either one of the parameter
	// is null.
	@Test(expected = NullPointerException.class)
	public void footruleNullPointerException() {
		int[] ranks = null;
		int[] ranks2 = { 5, 4, 3, 2, 1 };
		Ranking a = new Ranking(ranks);
		Ranking b = new Ranking(ranks2);
		Ranking c = new Ranking(5);
		c.footrule(a, b);
	}

	// Testing on footrule method
	// Should throw an IllegalArgumentException because either of the length of
	// the parameter is different.
	@Test(expected = IllegalArgumentException.class)
	public void footruleIllegalArgumentException() {
		int[] ranks = { 1, 2, 3, 4, 5, 6, 7, 8 };
		int[] ranks2 = { 5, 4, 3, 2, 1 };
		Ranking a = new Ranking(ranks);
		Ranking b = new Ranking(ranks2);
		Ranking c = new Ranking(5);
		c.footrule(a, b);
	}

	// Testing on kemeny method
	// To calculate the kemeny distance between 2 rankings based on the kemeny
	// formula.
	@Test
	public void kemeny() {
		int[] ranks = { 1, 2, 3, 4, 5 };
		int[] ranks2 = { 1, 2, 4, 5, 3 };
		Ranking a = new Ranking(ranks);
		Ranking b = new Ranking(ranks2);
		Ranking c = new Ranking(5);
		assertEquals(c.kemeny(a, b), 2);
	}

	// Testing on kemeny method
	// Should throw a NullPointerException because either one of the parameter
	// is null.
	@Test(expected = NullPointerException.class)
	public void kemenyNullPointerException() {
		int[] ranks = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		int[] ranks2 = null;
		Ranking a = new Ranking(ranks);
		Ranking b = new Ranking(ranks2);
		Ranking c = new Ranking(5);
		c.kemeny(a, b);
	}

	// Testing on fDist method (Comparing its own Rank with the other Rank)
	// Should return 5 based on the footrule formula |a1 - a2|+|b1 -
	// b2|+........
	@Test
	public void fDist() {
		int[] ranks = { 1, 2, 3, 4, 5, 6 };
		int[] other = { 1, 2, 4, 5, 3, 6 };
		Ranking a = new Ranking(ranks);
		Ranking b = new Ranking(other);
		assertEquals(a.fDist(b), 4);
	}

	// Testing on fDist method
	// Should throw NullPointerException because the parameter is null.
	@Test(expected = NullPointerException.class)
	public void fDistNullPointerException() {
		int[] ranks = { 1, 2, 3, 4, 5 };
		int[] other = null;
		Ranking a = new Ranking(ranks);
		Ranking b = new Ranking(other);
		a.fDist(b);
	}

	// Testing on fDist method
	// Should throw IllegalFormatException because the parameter's length is
	// different with its own Rank's length.
	@Test(expected = IllegalArgumentException.class)
	public void fDistIllegalArgumentException() {
		int[] ranks = { 1, 2, 3, 4, 5 };
		int[] other = { 1, 2, 3, 4, 5, 6, 7, 8 };
		Ranking a = new Ranking(ranks);
		Ranking b = new Ranking(other);
		a.fDist(b);
	}

	// Testing on kDist method (Comparing its own Rank with the other
	// parameter).
	// Should return 2 according to the kemeny distance formula.
	@Test
	public void kDist() {
		int[] ranks = { 1, 2, 3, 4, 5 };
		int[] other = { 1, 2, 4, 5, 3 };
		Ranking a = new Ranking(ranks);
		Ranking b = new Ranking(other);
		assertEquals(a.kDist(b), 2);
	}

	// Second testing of kDist method (Comparing its own Rank with the other
	// parameter).
	// Should return 2 according to the kemeny distance formula.
	@Test
	public void kDist2() {
		int[] ranks = { 1, 2, 3, 4, 5, 6, 7 };
		int[] other = { 1, 4, 3, 2, 7, 5, 6 };
		Ranking a = new Ranking(ranks);
		Ranking b = new Ranking(other);
		assertEquals(a.kDist(b), 5);
	}

	// Testing on kDist method
	// Should throw NullPointerException because the parameter is null.
	@Test(expected = NullPointerException.class)
	public void kDistNullPointerException() {
		int[] ranks = { 1, 2, 3, 4, 5 };
		int[] other = null;
		Ranking a = new Ranking(ranks);
		Ranking b = new Ranking(other);
		a.kDist(b);
	}

	// Testing on kDist method
	// Should throw IllegalFormatException because the parameter's length is
	// different with its own Rank's length.
	@Test(expected = IllegalArgumentException.class)
	public void kDistIllegalArgumentException() {
		int[] ranks = { 1, 2, 3, 4, 5 };
		int[] other = { 1, 2, 3, 4, 5, 6, 7, 8 };
		Ranking a = new Ranking(ranks);
		Ranking b = new Ranking(other);
		a.kDist(b);
	}

	// Testing on invCount method
	// Should return 10 based on the inversion formula
	@Test
	public void invCount() {
		int[] ranks = { 1, 5, 3, 8, 4, 2, 7, 6 };
		Ranking a = new Ranking(ranks);
		assertEquals(a.invCount(), 10);
	}

	// Second testing on invCount method
	// Should return 7 based on the inversion formula
	@Test
	public void invCount2() {
		int[] ranks = { 1, 7, 3, 4, 2, 5, 6 };
		Ranking a = new Ranking(ranks);
		assertEquals(a.invCount(), 7);
	}

	// Third testing on invCount method
	// Should return 7 based on the inversion formula
	@Test
	public void invCountDescending() {
		int[] ranks = { 6,5,2,4,3,7,1 };
		Ranking a = new Ranking(ranks);
		assertEquals(a.invCount(), 14);
	}

}
