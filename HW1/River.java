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

	public Animal scanStringToAnimal(String scan) {
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
		river = new Animal[length];
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

	public int[] emptySpace() {
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
				Fish use = (Fish) animal;
				river[emptySpace()[randomPlace]] = use;
				return true;
			} else {
				Bear use = (Bear) animal;
				river[emptySpace()[randomPlace]] = use;
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
		} else {
//			if (river[i].stringAnimalType() == 'B'
//					&& river[i].getAge() == river[i].BEAR_MAX_AGE) {
//				river[i] = null;
//			} else if (river[i].stringAnimalType() == 'F'
//					&& river[i].getAge() == river[i].FISH_MAX_AGE) {
//				river[i] = null;
//			} else {
				int move = RandomSingleton.getInstance().nextInt(3);
				switch (move) {
				case 0:
				case 1:
					if (i == 0) {
						rule(this.getLength(),i);
						break;
					} else {
						rule(i - 1,i);
						break;
					}
				case 2:
					if (i == this.getLength()-1) {
						rule(0,i);
						break;
					} else {
						rule(i + 1,i);
						break;
					}
				}
			}
		}
//	}

	public void rule(int destination, int initial) {
		if (river[destination] == null) {
			river[destination] = river[initial];
			river[initial] = null;
			// Bear and fish, Fish moves and dies at destination place
		} else if (river[destination].stringAnimalType() != river[initial]
				.stringAnimalType() && river[initial].stringAnimalType() == 'F') {
			river[initial] = null;
			// Bear and fish, Bear moves and fish dies at current place
		} else if (river[destination].stringAnimalType() != river[initial]
				.stringAnimalType()
				&& river[destination].stringAnimalType() == 'F') {
			river[destination] = river[initial];
			river[initial] = null;
		}
		// Bear with same Gender
		else if (river[destination].stringAnimalType() == river[initial]
				.stringAnimalType()
				&& river[initial].stringAnimalType() == 'B'
				&& river[initial].stringGender() == river[destination].stringGender()) {
			Bear initialBear = (Bear) river[initial];
			Bear dest = (Bear) river[destination];
			// Bear with bigger strength win and move
			if (initialBear.getStrength() > dest.getStrength()) {
				river[destination] = river[initial];
				river[initial] = null;
			}
			// Bear lost and killed
			else {
				river[initial] = null;
			}
		}
		else if(river[destination].stringAnimalType() == river[initial].stringAnimalType() && river[destination].stringGender() != river[initial].stringGender()){
			if(this.numEmpty() != 0 && river[initial].stringAnimalType() == 'F'){
				this.addRandom(new Fish(0));
			}
			else if(this.numEmpty() != 0 && river[initial].stringAnimalType() == 'B'){
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
			updateCell(i);
		}
		for (int i = 0; i < this.getLength(); i++) {
			river[i].incrAge();
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
		// TODO
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
			result += river[i] + " ";
		}
		return result;
	}

}