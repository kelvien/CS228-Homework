package edu.iastate.cs228.hw4;

import java.util.Arrays;
import java.util.Comparator;

public class test {
public static void main(String[] args){
	String[] data = {"C","B","E","D","A"};
	String[] data1 = new String[1];
	String[] dataNull = null;
	Comparator com = new Comparator(){
		@Override
		public int compare(Object arg0, Object arg1) {
			// TODO Auto-generated method stub
			return 0;
		}};
	NonRecursiveMergeSort.mergeSort(data);
	System.out.println(Arrays.toString(data));
	
	
}
}
