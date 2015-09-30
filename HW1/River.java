package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import edu.iastate.cs228.hw1.Animal.Gender;

/**
 * 
 * @author Kelvien Hidayat
 * 
 */
public class River {
	/**
	 * The river in which the animals reside in.
	 */
	public Animal[] river;

	/**
	 * The length of the river.
	 */
	public int length;

	/**
	 * The seed value for the random number generator used in updating the
	 * river.
	 */
	public long seed;

	/**
	 * Constructs a river from a file.
	 * 
	 * @param inputFileName
	 *            The name of the file to be read from.
	 * @throws FileNotFoundException
	 */
	public River(String inputFileName) throws FileNotFoundException {
		File file = new File(inputFileName);
		Scanner scan = new Scanner(file);
		int length = 0;
		while (scan.hasNext()) {
			length++;
			scan.next();
		}
		scan.close();
		river = new Animal[length];
		this.length = length;
		Scanner scan2 = new Scanner(file);
		for (int a = 0; a < river.length; a++) {
			river[a] = scanStringToAnimal(scan2.next());
		}
		scan2.close();
	}

	/**
	 * A helper method to convert string from file into animals used for the
	 * River.
	 * 
	 * @param scan
	 *            - String from the file. E.g: --- / BM3/ FF4
	 * @return new object of Animal according to the string representing the
	 *         variables.
	 */
	private Animal scanStringToAnimal(String scan) {
		if (scan.equals("---")) {
			return null;
		} else if (scan.charAt(0) == 'B') {
			if (scan.charAt(1) == 'M') {
				return new Bear(Integer.parseInt(scan.charAt(2) + ""),
						Gender.MALE);
			} else {
				return new Bear(Integer.parseInt(scan.charAt(2) + ""),
						Gender.FEMALE);
			}
		} else {
			if (scan.charAt(1) == 'M') {
				return new Fish(Integer.parseInt(scan.charAt(2) + ""),
						Gender.MALE);
			} else {
				return new Fish(Integer.parseInt(scan.charAt(2) + ""),
						Gender.FEMALE);
			}
		}
	}

	/**
	 * Generates a random river ecosystem of the given length.
	 * 
	 * @param length
	 *            The length of the river.
	 */
	public River(int length) {
		this.length = length;
		river = new Animal[length];
		for (int i = 0; i < length; i++) {
			river[i] = generateRandomAnimal();
		}
		
	}
/**
 * a helper method to generate a random animal.
 * @return a new animal or null.
 */
	private Animal generateRandomAnimal(){
		int use = RandomSingleton.getInstance().nextInt(3);
		switch(use){
		case 0:
			return null;
		case 1:
			return new Bear();
		case 2:
			return new Fish();
		default:
			return null;
		}
	}
	
	/**
	 * Generates a random river ecosystem of the given length, where the seed of
	 * the random number is as given.
	 * 
	 * @param length
	 *            The length of the river.
	 * @param seed
	 *            The seed value used for the random number generator.
	 */
	public River(int length, long seed) {
		this.length = length;
		this.seed = seed;
		RandomSingleton.setSeed(this.seed);
		river = new Animal[length];
		for (int i = 0; i < length; i++) {
			river[i] = generateRandomAnimal();
		}
	}

	/**
	 * Returns the length of the river.
	 * 
	 * @return The length of the river.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Sets the seed of the random number generator used in updating the river.
	 * 
	 * @param seed
	 *            The seed value used for the random number generator.
	 */
	public void setSeed(long seed) {
		this.seed = seed;
		RandomSingleton.setSeed(this.seed);
	}

	/**
	 * Returns the number of empty (null) cells in the river.
	 * 
	 * @return The number of empty (null) cells.
	 */
	public int numEmpty() {
		int count = 0;
		for (int a = 0; a < river.length; a++) {
			if (river[a] == null) {
				count++;
			}
		}
		return count;
	}

	/**
	 * To find the position of the empty cell in the river
	 * 
	 * @return an array of empty cells
	 */
	private int[] emptySpace() {
		int count = 0;
		int[] emptySpace = new int[this.numEmpty()];
		for (int a = 0; a < river.length; a++) {
			if (river[a] == null) {
				emptySpace[count] = a;
				count++;
			}
		}
		return emptySpace;
	}

	/**
	 * If the river has no empty (null) cells, then do nothing and return false.
	 * Otherwise, add an animal of age 0 of randomly chosen gender and of the
	 * same type as animal to a cell chosen uniformly at random from among the
	 * currently empty cells and return true.
	 * 
	 * @param animal
	 *            The type of animal that should be added to the river.
	 * @return True if an animal was added, false otherwise.
	 */
	public boolean addRandom(Animal animal) {
		if (numEmpty() == 0) {
			return false;
		} else {
			int randomPlace = RandomSingleton.getInstance().nextInt(numEmpty());
			if (animal.getClass() == edu.iastate.cs228.hw1.Fish.class) {
				river[emptySpace()[randomPlace]] = new Fish(0);
				return true;
			} else {
				river[emptySpace()[randomPlace]] = new Bear(0);
				return true;
			}
		}
	}

	/**
	 * Process the object at cell i, following the rules given in the Project
	 * Description. If it is null, do nothing. If it is an animal and it has
	 * reached the end of its lifespan, the animal dies. Otherwise, decides
	 * which direction, if any, the animal should move, and what other actions
	 * to take (including the creation of a child), following the rules given in
	 * the Project Description.
	 * 
	 * @param i
	 *            The index of the cell to be updated.
	 */
	public void updateCell(int i) {
		if (river[i] == null) {
			//System.out.println(river[i]+"null");
		} 
		else if(river[i].maxAge()){
			river[i] = null;
		}
		else {
			int move = RandomSingleton.getInstance().nextInt(3);
			switch (move) {
			case 0:
				//System.out.print(river[i]+" ");
				rule(i, i);
				//System.out.println("not move");
				break;
			case 1:
				if (i == 0) {
					//System.out.print(river[i]+" ");
					//System.out.println("left");
					rule(this.getLength() - 1, i);
					break;
				} else {
					//System.out.print(river[i]+" ");
					rule(i - 1, i);
					//System.out.println("left");
					break;
				}
			case 2:
				if (i == this.getLength() - 1) {
					//System.out.print(river[i]+" ");
					rule(0, i);
					//System.out.println("right");
					break;
				} else {
					//System.out.print(river[i]+" ");
					rule(i + 1, i);
					//System.out.println("right");
					break;
				}
			}
		}
	}

	/**
	 * a helper method that has all of the rules held in this project and
	 * execute the cause and effect of a movement.
	 * 
	 * @param destination
	 *            - a cell that the animal is going to. Left cell/ Right cell/
	 *            not move.
	 * @param initial
	 */
	private void rule(int destination, int initial) {
		if (destination == initial) { // if animal doesn't move.
		} else if (river[destination] == null) { // if destination cell is
													// empty, animal can just
													// move there.
			river[destination] = river[initial];
			river[initial] = null;
		} else if (!river[destination].getClass().equals(
				river[initial].getClass())
				&& river[initial].getClass().equals(
						edu.iastate.cs228.hw1.Fish.class)) { // Bear and fish
																// meets, Fish
																// moves and
																// dies at
																// destination
																// place
			river[initial] = null;
		} else if (!river[destination].getClass().equals(
				river[initial].getClass())
				&& river[destination].getClass().equals(
						edu.iastate.cs228.hw1.Fish.class)) { // Bear and fish
																// meets, Bear
																// moves and
																// fish dies at
																// current place
			river[destination] = river[initial];
			river[initial] = null;
		} else if (river[destination].getClass().equals(
				river[initial].getClass())
				&& river[initial].getClass().equals(
						edu.iastate.cs228.hw1.Bear.class)
				&& river[initial].gender == river[destination].gender) { // Bear
																			// with
																			// same
																			// Gender
			Bear initialBear = (Bear) river[initial];
			Bear dest = (Bear) river[destination];
			if (initialBear.getStrength() > dest.getStrength()) { // Bear with
																	// bigger
																	// strength
																	// win and
																	// move
				river[destination] = river[initial];
				river[initial] = null;
			} else { // Bear lost and killed
				river[initial] = null;
			}
		} else if (river[destination].getClass().equals(
				river[initial].getClass())
				&& river[destination].gender != river[initial].gender) { // Animals
																			// with
																			// different
																			// gender
																			// (Mating)
			if (this.numEmpty() != 0
					&& river[initial].getClass().equals(
							edu.iastate.cs228.hw1.Fish.class)) { // Check if
																	// there's
																	// an empty
																	// cell, if
																	// there is
																	// create a
																	// new Fish
																	// with age
																	// 0 and
																	// random
																	// gender.
				this.addRandom(new Fish(0));
			} else if (this.numEmpty() != 0
					&& river[initial].getClass().equals(
							edu.iastate.cs228.hw1.Bear.class)) { // Check if
																	// there's
																	// an empty
																	// cell, if
																	// there is
																	// create a
																	// new Bear
																	// with age
																	// 0 and
																	// random
																	// gender.
				this.addRandom(new Bear(0));
			}
		}
	}

	/**
	 * Perform one cycle of the simulation, going through the cells of the
	 * river, updating ages, moving animals, creating animals, and killing
	 * animals, as explained in the Project Description.
	 */
	public void updateRiver() {
		for (int i = 0; i < this.getLength(); i++) {
			this.updateCell(i);
		}
		for (int i = 0; i < this.getLength(); i++) {
			if(river[i] == null){
			}
			else{ 
				river[i].incrAge();	
			}
		}
	}

	/**
	 * Writes the river to an output file.
	 * 
	 * @param outputFileName
	 *            The name of the output file.
	 * @throws FileNotFoundException
	 */
	public void write(String outputFileName) throws FileNotFoundException {
		PrintWriter print = new PrintWriter(outputFileName);
		print.println(this.toString());
		print.close();
	}

	/**
	 * Produces a string representation of the river following the format
	 * described in the Project Description.
	 * 
	 * @return The string representation of the river.
	 */
	public String toString() {
		String result = "";
		for (int i = 0; i < this.getLength(); i++) {
			if (river[i] == null) {
				result += "---" + " ";
			} else {
				result += river[i].toString() + " ";
			}
		}
		return result;
	}

}