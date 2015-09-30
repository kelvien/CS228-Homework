package edu.iastate.cs228.hw5;

import java.util.Iterator;

import edu.iastate.cs228.hw5.BalancedBSTSet.Node;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		BalancedBSTSet<String> bst = new BalancedBSTSet<String>();
		bst.add("G");
		bst.add("A");
		bst.add("C");
		bst.add("D");
		bst.add("H");
		bst.add("Z");
		bst.add("V");
		bst.add("B");
		bst.add("P");
		bst.add("E");
		bst.add("L");
		for (int i = 0; i < 20; i++) {
			System.out.println("Hash "+i+": "+(i+33)%6);
		}
//		BalancedBSTSet<Integer>.Node fakeNode = bst.new Node(new Integer(3), null);
//		bst.rebalance(bst.findEntry(3));
//		System.out.println(bst.toString());
//		System.out.println(bst.recPreOrderTraversal(bst.root));
//		BalancedBSTSet<Integer>.Node fakeNode = bst.new Node(new Integer(2), null);
//		bst.rebalance(fakeNode);
//		Iterator<Integer> iter = bst.iterator();
//		while(iter.hasNext()){
//		System.out.println(iter.next());
//		}
//		
//		BalancedBSTSet<Integer>.Node fakeNode = bst.new Node(new Integer(3), null);
//		bst.rebalance(fakeNode);
//		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>(true);
//		bst.add(7);
//		bst.add(2);
//		bst.add(4);
//		bst.add(10);
//		bst.add(20);
//		bst.add(15);
//		bst.add(9);
//		bst.add(1);
//		bst.add(100);
		
//		BalancedBSTSet<Integer>.Node fakeNode = bst.new Node(new Integer(20), null);
//		bst.rebalance(bst.findEntry(20));
//		System.out.println(bst.successor(bst.findEntry(20)));
		
//		bst.remove(15);
//		bst.remove(18);
//		bst.remove(19);
//		bst.rebalance(bst.root.left);
//		tree.add(7);
//		tree.add(8);
//		tree.add(9);
//		System.out.println(a.root.left);
//		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
//		bst.add(4);
//		bst.add(5);
//		bst.add(3);
//		bst.add(2);
//		bst.add(1);
//		bst.add(0);
//		bst.rebalance(bst.findEntry(3));
//		System.out.println(bst.toString());
//		System.out.println(a.findEntry(5).parent);
//		System.out.println(a.findEntry(16).count());
		
	}

}
