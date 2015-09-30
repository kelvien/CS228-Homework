package edu.iastate.cs228.hw3;

import java.util.ListIterator;
import java.util.Scanner;

/**
 * @author Kip Hoelscher
 * 
 *         Simple program which allows you to test various list and listIterator functions while watching their effects in real
 *         time. Obviously there are many ways to produce exceptions which will crash the program. You may need to manually
 *         terminate the program from the Debug tab if the program continues to run but the normal terminate button is greyed
 *         out.
 * 
 *         Note: The list function remove(Object obj) is not testable in this implementation because the list is populated with
 *         ints, making the function call identical to remove(int index). Feel free to fix this if you have the time.
 */
public class CommandLineTester
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

		Scanner scan = new Scanner(System.in);

		// populate the new list
		System.out.println("Initial size?: ");
		int initialSize = scan.nextInt();
		DoublingList dl = new DoublingList();
		for (int i = 0; i < initialSize; i++)
		{
			dl.add(i);
		}
		System.out.println(dl.toStringInternal() + " <-- Newly populated list.");

		while (true)
		{
			// implemented list functions
			System.out.println("Choose a list function:");
			System.out.println("1: listIterator(int index)  (choose this to quit list functions)");
			System.out.println("2: add(int index, E item)");
			System.out.println("3: add(E item)");
			System.out.println("4: remove(int index)");

			// inherited functions
			System.out.println("5: get(int index)");
			System.out.println("6: indexOf(Object obj)");
			System.out.println("7: set(int index, E element)");

			// TODO not working 
			//System.out.println("8: remove(Object obj)  (note: will call same method as in 4 above because of ints)");

			System.out.println("9: clear()");
			System.out.println("0: quit");

			int input = scan.nextInt();

			if (input == 1)
			{
				// if listIterator wanted, break to next loop
				break;
			}
			if (input == 2)
			{
				System.out.println("Add at what index?");
				int index = scan.nextInt();
				System.out.println("Add what int?");
				int item = scan.nextInt();
				dl.add(index, item);
				System.out.println(dl.toStringInternal() + " After: add(" + index + ", " + item + ")");
			}
			if (input == 3)
			{
				System.out.println("Add what int?");
				int item = scan.nextInt();
				dl.add(item);
				System.out.println(dl.toStringInternal() + " After: add(" + item + ")");
			}
			if (input == 4)
			{
				System.out.println("Remove at what index?");
				int pos = scan.nextInt();
				dl.remove(pos);
				System.out.println(dl.toStringInternal() + " After: remove(" + pos + ")");
			}
			if (input == 5)
			{
				System.out.println("Get what index?");
				int index = scan.nextInt();
				System.out.println(dl.toStringInternal() + " After: get(" + index + ")");
				System.out.println("The element at index " + index + " is: " + dl.get(index));
			}
			if (input == 6)
			{
				System.out.println("IndexOf what object?");
				int o = scan.nextInt();
				System.out.println(dl.toStringInternal() + " After: indexOf(" + o + ")");
				System.out.println("The indexOf object " + o + " is: " + dl.indexOf(o));
			}

			if (input == 7)
			{
				System.out.println("Set at what index?");
				int index = scan.nextInt();
				System.out.println("Set what int?");
				int element = scan.nextInt();
				dl.set(index, element);
				System.out.println(dl.toStringInternal() + " After: set(" + index + ", " + element + ")");
			}

			// TODO this doesn't work with a list of ints
			//			if (input == 8)
			//			{
			//				System.out.println("Remove what object?");
			//				int obj = scan.nextInt();
			//				dl.remove(obj);
			//				System.out.println(dl.toStringInternal() + " After: remove(" + obj + ")");
			//			}

			if (input == 9)
				dl.clear();

			if (input == 0)
				break;
		}

		// create listIterator
		System.out.println("ListIterator starting position?: ");
		int initialPosition = scan.nextInt();
		ListIterator listIter = dl.listIterator(initialPosition);
		System.out.println(dl.toStringInternal(listIter) + " List with new cursor at " + initialPosition);

		// ListIterator functions loop
		while (true)
		{

			System.out.println("Choose a listIterator function:");
			System.out.println("1: next()");
			System.out.println("2: previous()");
			System.out.println("3: add(E arg0)");
			System.out.println("4: remove()");
			System.out.println("5: set(E arg0)");
			System.out.println("9: quit");
			int input = scan.nextInt();

			if (input == 1)
			{
				listIter.next();
				System.out.println(dl.toStringInternal(listIter) + " After: next()");
			}
			if (input == 2)
			{
				listIter.previous();
				System.out.println(dl.toStringInternal(listIter) + " After: previous()");
			}
			if (input == 3)
			{
				System.out.println("Add what int?");
				int e = scan.nextInt();
				listIter.add(e);
				System.out.println(dl.toStringInternal(listIter) + " After: add(" + e + ")");
			}
			if (input == 4)
			{
				listIter.remove();
				System.out.println(dl.toStringInternal(listIter) + " After: remove()");
			}
			if (input == 5)
			{
				System.out.println("Set what int?");
				int e = scan.nextInt();
				listIter.set(e);
				System.out.println(dl.toStringInternal(listIter) + " After: set(" + e + ")");
			}

			if (input == 9)
				break;
		}
	}
}