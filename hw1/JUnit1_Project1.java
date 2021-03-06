package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.iastate.cs228.hw1.Animal.Gender;
/*
 * 
 * @author Kelvien Hidayat
 * 
 */
public class JUnit1_Project1 {

	
	
	@Test
	public void testFishAge(){
		Animal a = new Fish(3, Gender.MALE);
		assertEquals(a.getAge(), 3);
	}
	
	@Test
	public void testBearAge(){
		Animal a = new Bear(4, Gender.MALE);
		assertEquals(a.getAge(),4);
	}
	
	@Test
	public void testFishMaxAge(){
		Animal a = new Fish(4, Gender.MALE);
		assertEquals(a.maxAge(), true);
	}
	
	@Test
	public void testFishMaxAgeFALSE(){
		Animal a = new Fish(3, Gender.MALE);
		assertEquals(a.maxAge(), false);
	}
	
	@Test
	public void testBearMaxAge(){
		Animal a = new Bear(9, Gender.MALE);
		assertEquals(a.maxAge(), true);
	}
	
	@Test
	public void testBearMaxAgeFALSE(){
		Animal a = new Bear(8, Gender.MALE);
		assertEquals(a.maxAge(), false);
	}
	
	@Test
	public void testFishIncrAgeANDAgeAfterThat(){
		Animal a = new Fish(2, Gender.MALE);
		assertEquals(a.incrAge(), true);
		assertEquals(a.getAge(), 3);
	}

	@Test
	public void testFishIncrAgeFALSEANDAgeAfterThat(){
		Animal a = new Fish(4, Gender.MALE);
		assertEquals(a.incrAge(), false);
		assertEquals(a.getAge(), 4);
	}
	
	@Test
	public void testBearIncrAgeANDAgeAfterThat(){
		Animal a = new Bear(7, Gender.MALE);
		assertEquals(a.incrAge(), true);
		assertEquals(a.getAge(), 8);
	}

	@Test
	public void testBearIncrAgeFALSEANDAgeAfterThat(){
		Animal a = new Bear(9, Gender.MALE);
		assertEquals(a.incrAge(), false);
		assertEquals(a.getAge(), 9);
	}
	
	@Test
	public void testBearGetStrength0(){
		Bear a = new Bear(0, Gender.MALE);
		assertEquals(a.getStrength(),1);
	}
	
	@Test
	public void testBearGetStrength5(){
		Bear a = new Bear(5, Gender.MALE);
		assertEquals(a.getStrength(),4);
	}
	
	@Test
	public void testBearGetStrength8(){
		Bear a = new Bear(8, Gender.MALE);
		assertEquals(a.getStrength(),1);
	}
	
	@Test
	public void testToStringForMaleBear(){
		Bear a = new Bear(4, Gender.MALE);
		assertEquals(a.toString(),"BM4");
	}
	
	@Test
	public void testToStringForFemaleBear(){
		Bear a = new Bear(9, Gender.FEMALE);
		assertEquals(a.toString(),"BF9");
	}
	
	@Test
	public void testToStringForMaleFish(){
		Fish a = new Fish(4, Gender.MALE);
		assertEquals(a.toString(),"FM4");
	}
	
	@Test
	public void testToStringForFemaleFish(){
		Fish a = new Fish(2, Gender.FEMALE);
		assertEquals(a.toString(),"FF2");
	}
	
	@Test
	public void testToStringForAnimal(){
		Animal a = new Fish(2, Gender.FEMALE);
		assertEquals(a.toString(),"FF2");
	}	
	
	@Test
	public void testToStringForNull(){
		Animal a = null;
		assertEquals(a.toString(),null);
	}
	
	@Test
	public void newRiverWithLength5(){
		River a = new River(5);
		assertEquals(a.getLength(), 5);
	}
	
	@Test
	public void RiverLength5With3AnimalsCheckNumEmpty(){
		River a = new River(5);
		a.addRandom(new Fish());
		a.addRandom(new Fish());
		a.addRandom(new Fish());
		assertEquals(a.numEmpty(), 2);
	}
	
	@Test
	public void addAnimalAfterFull(){
		River a = new River(5);
		a.addRandom(new Fish());
		a.addRandom(new Fish());
		a.addRandom(new Fish());
		a.addRandom(new Fish());
		a.addRandom(new Fish());
		assertEquals(a.addRandom(new Fish()), false);
	}
	
	@Test
	public void addAnimalBeforeFull(){
		River a = new River(5);
		a.addRandom(new Fish());
		a.addRandom(new Fish());
		a.addRandom(new Fish());
		a.addRandom(new Fish());
		assertEquals(a.addRandom(new Fish()), true);
	}
	
	@Test
	public void seeding(){
		River a = new River(5,30);
		a.addRandom(new Fish());
		a.addRandom(new Bear());
		a.addRandom(new Fish());
		a.addRandom(new Bear());
		a.addRandom(new Fish());
		assertEquals(a.river[0].toString(), "FM1");
	}
	
	
	//Male Fish moves left (Male Bear). Fish dies, becomes null, Bear still exist.
//	@Test
//	public void seeding300TestFishMoveLeft(){
//		River a = new River(5,300);
//		a.addRandom(new Fish(0, Gender.MALE));
//		a.addRandom(new Bear(1, Gender.MALE));
//		a.addRandom(new Fish(4, Gender.FEMALE));
//		a.addRandom(new Bear(2, Gender.MALE));
//		a.addRandom(new Fish(0, Gender.FEMALE));
//		assertEquals(a.river[0].toString(), "---");
//	}
//	
//	// Male Bear moves to right (Male Fish). Bear should kill the fish and left the previous space to null.
//	@Test
//	public void seeding30TestFishMoveRight(){
//		River a = new River(5,30);
//		a.addRandom(new Fish(0, Gender.MALE));
//		a.addRandom(new Bear(1, Gender.MALE));
//		a.addRandom(new Fish(4, Gender.FEMALE));
//		a.addRandom(new Bear(2, Gender.MALE));
//		a.addRandom(new Fish(0, Gender.FEMALE));
//		assertEquals(a.river[0].toString(), "null");
//		assertEquals(a.river[1].toString(), "BM1");
//	}
	
	
	
	
	
	
	

}
