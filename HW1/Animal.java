package edu.iastate.cs228.hw1;

import java.util.Random;

/**
 * 
 * @author Kelvien Hidayat
 * 
 *         An abstract, Animal, class that is used to reduce code repetition
 *         between similar characteristics of different animals.
 */
public abstract class Animal {
	/**
	 * Type of genders.
	 */
	protected enum Gender {
		FEMALE, MALE
	}

	/**
	 * The gender of the animal.
	 */
	protected Gender gender;

	/**
	 * The age of the animal
	 */
	protected int age;

	/**
	 * Life expectancies of the animals.
	 */
	public static final int BEAR_MAX_AGE = 9;
	public static final int FISH_MAX_AGE = 4;

	/**
	 * Creates an animal of a random age and gender.
	 */
	public Animal() {
		if (this.getClass() == edu.iastate.cs228.hw1.Fish.class) {
			age = RandomSingleton.getInstance().nextInt(FISH_MAX_AGE + 1);
		} else {
			age = RandomSingleton.getInstance().nextInt(BEAR_MAX_AGE + 1);
		}
		gender = (Gender) getRandomGender();
	}

	/**
	 * This is a personal constructor to create an Animal with an age 0 (Baby)
	 * and a random gender. It's protected in order to be able to be used by
	 * subclasses and river.
	 * 
	 * @param age
	 */
	protected Animal(int age) {
		this.age = age;
		gender = (Gender) getRandomGender();
	}

	/**
	 * a helper method to generate a random gender using RandomSingleton.
	 * 
	 * @return gender
	 */
	public Gender getRandomGender() {
		int temp = RandomSingleton.getInstance().nextInt(2);
		if (temp == 0) {
			return gender.MALE;
		} else {
			return gender.FEMALE;
		}
	}

	/**
	 * Creates an animal of given age and gender
	 * 
	 * @param age
	 *            The age of the animal to be created.
	 * @param gender
	 *            The gender of the animal to be created.
	 */
	public Animal(int age, Gender gender) {
		this.age = age;
		this.gender = gender;
	}

	/**
	 * Returns the age of the animal.
	 * 
	 * @return The age of the animal.
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Returns true if the current age of the animal has reached the limit for
	 * the species; otherwise, it return false.
	 * 
	 * @return true if age limit has been reached, false otherwise.
	 */
	public abstract boolean maxAge();

	/**
	 * If the current age of the animal is less than the maximum for the
	 * species, increment the age of the animal by one and return true.
	 * Otherwise, it leaves the age as is and return false.
	 * 
	 * @return true if the age has been incremented, false otherwise.
	 */
	public abstract boolean incrAge();

	/**
	 * a helper method to generate a string according to the gender of the
	 * animal.
	 * 
	 * @return String; first character of gender.
	 */
	private String stringGender() {
		if (this.gender == gender.MALE) {
			return "M";
		} else {
			return "F";
		}
	}

	@Override
	/**
	 * First character is the gender's first character and the followed by animal's age. The actual first character will be handled by the subclass.
	 */
	public String toString() throws NullPointerException {
		return stringGender() + "" + this.getAge() + "";
	}

}