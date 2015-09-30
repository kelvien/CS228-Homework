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
		if(this.getClass() == edu.iastate.cs228.hw1.Fish.class ){
			age = RandomSingleton.getInstance().nextInt(FISH_MAX_AGE+1);
		}
		else{
			age = RandomSingleton.getInstance().nextInt(BEAR_MAX_AGE+1);
		}
		gender =  (Gender) getRandomGender();
	}
	
	public Animal(int age){
		this.age = age;
		gender =  (Gender) getRandomGender();
	}

	public Enum getRandomGender(){
		int temp = RandomSingleton.getInstance().nextInt(2);
		if(temp == 0){
			return gender.MALE;
		}
		else{
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
	
	public char stringGender(){
		if(gender == Gender.MALE){
			return 'M';
		}
		else{
			return 'F';
		}
	}
	
	public char stringAnimalType(){
		if(this.getClass() == edu.iastate.cs228.hw1.Fish.class){
			return 'F';
		}
		else{
			return 'B';
		}
	}

	@Override
	public String toString(){
		if(this.getClass() == null){
			return "---";
		}
		else{
			return "what";
		//return stringAnimalType()+""+stringGender()+""+this.getAge()+"";
		}
	}
}