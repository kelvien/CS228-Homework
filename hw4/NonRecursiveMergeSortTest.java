package edu.iastate.cs228.hw4;

import org.junit.Test;

import java.util.Comparator;
 
import static edu.iastate.cs228.hw4.NonRecursiveMergeSort.mergeSort;
import static org.junit.Assert.assertArrayEquals;
 
/**
 * @author Nick Gerleman
 *         A suite of tests for NonRecursiveMergeSort
 */
public class NonRecursiveMergeSortTest
    {
        Integer[] data;
        Integer[] expected;
        Comparator<Integer> comp = new intComparator<Integer>();
 
        @Test
        public void testComparableEmpty()
            {
                data = new Integer[]{};
                expected = new Integer[]{};
                mergeSort(data);
                assertArrayEquals("Nothing should Happen with a blank array", expected, data);
            }
 
        @Test
        public void testComparableLengthOfOne()
            {
                data = new Integer[]{42};
                expected = new Integer[]{42};
                mergeSort(data);
                assertArrayEquals("Array with a length of one should remain the same", expected, data);
            }
 
        @Test
        public void testComparableOddPresorted()
            {
                data = new Integer[]{1, 2, 3, 4, 5};
                expected = new Integer[]{1, 2, 3, 4, 5};
                mergeSort(data);
                assertArrayEquals("Presorted array should stay the same", expected, data);
            }
 
        @Test
        public void testComparableEvenPresorted()
            {
                data = new Integer[]{1, 2, 3, 4, 5, 6};
                expected = new Integer[]{1, 2, 3, 4, 5, 6};
                mergeSort(data);
                assertArrayEquals("Presorted array should stay the same", expected, data);
            }
 
        @Test
        public void testComparableOddUnsorted()
            {
                data = new Integer[]{2, 5, 1, 3, 4};
                expected = new Integer[]{1, 2, 3, 4, 5};
                mergeSort(data);
                assertArrayEquals(expected, data);
            }
 
        @Test
        public void testComparableEvenUnsorted()
            {
                data = new Integer[]{4, 3, 6, 1, 5, 2};
                expected = new Integer[]{1, 2, 3, 4, 5, 6};
                mergeSort(data);
                assertArrayEquals(expected, data);
            }
 
        @Test
        public void testComparableOddInverted()
            {
                data = new Integer[]{5, 4, 3, 2, 1};
                expected = new Integer[]{1, 2, 3, 4, 5};
                mergeSort(data);
                assertArrayEquals("Being inverted should not affect sorting", expected, data);
            }
 
        @Test
        public void testComparableEvenInverted()
            {
                data = new Integer[]{6, 5, 4, 3, 2, 1};
                expected = new Integer[]{1, 2, 3, 4, 5, 6};
                mergeSort(data);
                assertArrayEquals("Being inverted should not affect sorting", expected, data);
            }
 
        @Test
        public void testComparableOddWithDupes()
            {
                data = new Integer[]{5, 2, 3, 3, 1};
                expected = new Integer[]{1, 2, 3, 3, 5};
                mergeSort(data);
                assertArrayEquals("Should work correctly with duplicate values", expected, data);
            }
 
        @Test
        public void testComparableEvenWithDupes()
            {
                data = new Integer[]{6, 5, 3, 4, 4, 1};
                expected = new Integer[]{1, 3, 4, 4, 5, 6};
                mergeSort(data);
                assertArrayEquals("Should work correctly for duplicate values", expected, data);
            }
 
        @Test
        public void testComparatorEmpty()
            {
                data = new Integer[]{};
                expected = new Integer[]{};
                mergeSort(data, comp);
                assertArrayEquals("Nothing should Happen with a blank array", expected, data);
            }
 
        @Test
        public void testComparatorLengthOfOne()
            {
                data = new Integer[]{42};
                expected = new Integer[]{42};
                mergeSort(data, comp);
                assertArrayEquals("Array with a length of one should remain the same", expected, data);
            }
 
        @Test
        public void testComparatorOddPresorted()
            {
                data = new Integer[]{1, 2, 3, 4, 5};
                expected = new Integer[]{1, 2, 3, 4, 5};
                mergeSort(data, comp);
                assertArrayEquals("Presorted array should stay the same", expected, data);
            }
 
        @Test
        public void testComparatorEvenPresorted()
            {
                data = new Integer[]{1, 2, 3, 4, 5, 6};
                expected = new Integer[]{1, 2, 3, 4, 5, 6};
                mergeSort(data, comp);
                assertArrayEquals("Presorted array should stay the same", expected, data);
            }
 
        @Test
        public void testComparatorOddUnsorted()
            {
                data = new Integer[]{2, 5, 1, 3, 4};
                expected = new Integer[]{1, 2, 3, 4, 5};
                mergeSort(data, comp);
                assertArrayEquals(expected, data);
            }
 
        @Test
        public void testComparatorEvenUnsorted()
            {
                data = new Integer[]{4, 3, 6, 1, 5, 2};
                expected = new Integer[]{1, 2, 3, 4, 5, 6};
                mergeSort(data, comp);
                assertArrayEquals(expected, data);
            }
 
        @Test
        public void testComparatorOddInverted()
            {
                data = new Integer[]{5, 4, 3, 2, 1};
                expected = new Integer[]{1, 2, 3, 4, 5};
                mergeSort(data, comp);
                assertArrayEquals("Being inverted should not affect sorting", expected, data);
            }
 
        @Test
        public void testComparatorEvenInverted()
            {
                data = new Integer[]{6, 5, 4, 3, 2, 1};
                expected = new Integer[]{1, 2, 3, 4, 5, 6};
                mergeSort(data, comp);
                assertArrayEquals("Being inverted should not affect sorting", expected, data);
            }
 
        @Test
        public void testComparatorOddWithDupes()
            {
                data = new Integer[]{5, 2, 3, 3, 1};
                expected = new Integer[]{1, 2, 3, 3, 5};
                mergeSort(data, comp);
                assertArrayEquals("Should work correctly with duplicate values", expected, data);
            }
 
        @Test
        public void testComparatorEvenWithDupes()
            {
                data = new Integer[]{6, 5, 3, 4, 4, 1};
                expected = new Integer[]{1, 3, 4, 4, 5, 6};
                mergeSort(data, comp);
                assertArrayEquals("Should work correctly for duplicate values", expected, data);
            }
 
        /** Simple comparator for Integers */
        class intComparator<T extends Integer> implements Comparator<T>
            {
                @Override
                public int compare(T o1, T o2)
                    {
                        return o1.compareTo(o2);
                    }
            }
    }