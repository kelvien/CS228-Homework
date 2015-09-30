package edu.iastate.cs228.hw1;

import java.util.Random;

/**
 * 
 * @author Kelvien Hidayat
 * 
 */
public class Bear extends Animal {
	/**
	 * The strength of the Bear.
	 */
	public int strength;

	/**
	 * Creates a Bear of a random age and gender.
	 */
	public Bear() {
		super();
	}

	/**
	 * Creates a Bear of given age and gender
	 * 
	 * @param age
	 *            The age of the animal to be created.
	 * @param gender
	 *            The gender of the Bear to be created.
	 */
	public Bear(int age, Gender gender) {
		super(age,gender);
	}

	public Bear(int age){
	super(age);
	}
	/**
	 * Returns true if the current age of the animal has reached the limit for
	 * the species; otherwise, it return false.
	 * 
	 * @return true if age limit has been reached, false otherwise.
	 */
	public boolean maxAge() {
		if(BEAR_MAX_AGE == this.getAge()){
			return true;
		}
		else{
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
		if(!this.maxAge()){
			this.age++;
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Returns the current strength of the Bear.
	 * 
	 * @return The current strength.
	 */
	public int getStrength() {
		if(this.age<5){
			return this.age+1;
		}
		else{
			return 9-this.age;
		}
	}

	/**
	 * Produces a string representation of this Bear object as described in the
	 * Project Description. e.g. BF7 would be returned for a 7-year old female
	 * bear.
	 * 
	 * @return The string representation of this Bear.
	 */
	@Override
	public String toString() {
		return 'B'+""+this.stringGender()+""+this.getAge()+"";
	}
}