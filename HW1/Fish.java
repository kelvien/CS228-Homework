package edu.iastate.cs228.hw1;

import java.util.Random;

import edu.iastate.cs228.hw1.Animal.Gender;

/**
 * 
 * @author Kelvien Hidayat
 * 
 */
public class Fish extends Animal {
	/**
	 * Creates a Fish of a random age and gender.
	 */
	public Fish() {
		super();
	}

	/**
	 * Creates a Fish of given age and gender
	 * 
	 * @param age
	 *            The age of the animal to be created.
	 * @param gender
	 *            The gender of the Fish to be created.
	 */
	public Fish(int age, Gender gender) {
		super(age, gender);
	}

	/**
	 * This is a personal constructor to create an Animal with an age 0 (Baby)
	 * and a random gender. It's protected in order to be able to be used by
	 * subclasses and river.
	 * 
	 * @param age
	 */
	protected Fish(int age) {
		super(age);
	}

	/**
	 * Returns true if the current age of the animal has reached the limit for
	 * the species; otherwise, it return false.
	 * 
	 * @return true if age limit has been reached, false otherwise.
	 */
	public boolean maxAge() {
		if (FISH_MAX_AGE == this.getAge()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * If the current age of the animal is less than the maximum for the
	 * species, increment the age of the animal by one and return true.
	 * Otherwise, it leaves the age as is and return false.
	 * 
	 * @return true if the age has been incremented, false otherwise.
	 */
	public boolean incrAge() {
		if (!this.maxAge()) {
			this.age++;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Produces a string representation of this Fish object as described in the
	 * Project Description. e.g. FM3 would be returned for a 3-year old male
	 * fish.
	 * 
	 * @return The string representation of this Fish.
	 */
	@Override
	public String toString() {
		return 'F' + super.toString();
	}
}