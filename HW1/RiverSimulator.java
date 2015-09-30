package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;

import edu.iastate.cs228.hw1.Animal.Gender;

/**
 * 
 * @author Kelvien Hidayat 
 * 
 */
public class RiverSimulator {
	/**
	 * Repeatedly simulates evolutions of river ecosystems. Each iteration
	 * should execute as the Project Description describes.
	 * 
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		River a = new River("pro1.txt");

		
		System.out.println(a.river[1]);
		
		
	}
}