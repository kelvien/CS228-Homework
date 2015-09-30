package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
	public static void main(String[] args) throws FileNotFoundException,
			NullPointerException {

		int trial = 0;
while(true){
		System.out
				.println("River Ecosystem Simulator\nkeys: 1 (random river) 2 (file input) 3 (exit)\n");
		Scanner scan = new Scanner(System.in);
		int input = scan.nextInt();

		switch (input) {
		case 1:
			trial++;
			System.out.println("Trial " + trial + ":" + " 1\nRandom river\n"
					+ "Enter river length: ");
			River riverRandom = new River(scan.nextInt());
			while (true) {
				System.out.println("Enter the number of cycles:");
				int cyclesForRandom = scan.nextInt();
				if (cyclesForRandom > 0) {
					System.out.println("Initial river:\n"+riverRandom.toString()+"\n");
					Cycling(cyclesForRandom, riverRandom);
					break;
				}
			}
			break;
		case 2:
			trial++;
			while (true) {
				try {
					System.out.println("Trial " + trial + ":"
							+ " 2\nFile input" + "Enter your file name:(.txt)");
					String inputFile = scan.next();
					River riverFile = new River(inputFile);
					while (true) {
						System.out.println("Enter the number of cycles:");
						int cyclesForFile = scan.nextInt();
						if (cyclesForFile > 0) {
							System.out.println("Initial river:\n"+riverFile.toString()+"\n");
							Cycling(cyclesForFile, riverFile);
							break;
						}
					}
					break;
				} catch (FileNotFoundException e) {
					System.out.println(e);
				}
			}
			break;
		case 3:
			trial++;
			System.out.println("Trial " + trial + ":" + " 3\nExit");
			System.exit(0);
		}
}
		// River a = new River("pro.txt");
		// River a = new River(4);
		// System.out.println(a.toString());
		// a.updateRiver();	
		// System.out.println(a.toString());

	}

	public static void Cycling(int cy, River river) {
		for (int i = 0; i < cy; i++) {
		System.out.println("After cycle "+(i+1)+":\n");
		river.updateRiver();
		System.out.println(river.toString()+"\n");
		}
	}

}