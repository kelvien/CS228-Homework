package edu.iastate.cs228.hw5;

import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;

public class BST_JUnit_Kip {

// updated: 4/24/2012 3pm

	/*************************************************
	 ***************** EXCEPTIONS ********************
	 *************************************************/

	// constructor throws IllegalArgumentException if top / bottom is less than
	// 1/2
	@Test(expected = IllegalArgumentException.class)
	public void illegalTopAndBottom() {
		@SuppressWarnings("unused")
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>(true, 1, 3);
	}

	// rebalance throws IllegalArgumentException if the given node is not part
	// of this tree.
	@Test(expected = IllegalArgumentException.class)
	public void illegalRebalance() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		BalancedBSTSet<Integer>.Node fakeNode = bst.new Node(new Integer(1),
				null);
		bst.rebalance(fakeNode);
	}

	/*************************************************
	 ************* NON-BALANCING TESTS *************** TODO
	 *************************************************/

	@Test
	public void newEmptyList() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		Assert.assertEquals("-\n", bst.toString());
	}

	@Test
	public void addToEmpty() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(5);
		Assert.assertEquals("- 5 (1)\n", bst.toString());
	}

	@Test
	public void addToRoot() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(5);
		bst.add(4);
		Assert.assertEquals("+ 5 (2)\n  - 4 (1)\n  -\n", bst.toString());
	}

	@Test
	public void removeLonelyRoot() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(5);
		bst.remove(5);
		Assert.assertEquals("-\n", bst.toString());
	}

	@Test
	public void removeNonExistant() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(5);
		bst.remove(99);
		Assert.assertEquals("- 5 (1)\n", bst.toString());
	}

	@Test
	public void addDuplicate() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(5);
		bst.add(5);
		Assert.assertEquals("- 5 (1)\n", bst.toString());
	}

	@Test
	public void removeLeafFromRoot() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(5);
		bst.add(4);
		bst.remove(4);
		Assert.assertEquals("- 5 (1)\n", bst.toString());
	}

	@Test
	public void addToAndRemoveRoot() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(5);
		bst.add(4);
		bst.remove(5);
		Assert.assertEquals("- 4 (1)\n", bst.toString());
	}

	@Test
	public void removeRootWithTwoChildren() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(2);
		bst.add(1);
		bst.add(3);
		bst.remove(2);
		Assert.assertEquals("+ 3 (2)\n  - 1 (1)\n  -\n", bst.toString());
	}

	@Test
	public void removeRootB() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(5);
		bst.remove(5);
		Assert.assertEquals("-\n", bst.toString());
	}

	@Test
	public void addToAndRemoveFromRootB() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(5);
		bst.add(4);
		bst.remove(4);
		Assert.assertEquals("- 5 (1)\n", bst.toString());
	}

	@Test
	public void rootTest() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(3);

		Assert.assertEquals("3", bst.root().toString());
	}

	@Test
	public void noRoot_rootTest() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(3);
		bst.remove(3);
		Assert.assertEquals(null, bst.root());
	}

	@Test
	public void addSizeTest() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(3);
		bst.add(10);
		Assert.assertEquals(2, bst.size());
	}

	@Test
	public void removeSizeTest() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(3);
		bst.add(10);
		bst.remove(3);
		Assert.assertEquals(1, bst.size());
	}

	@Test
	public void containsTest() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(3);
		bst.add(5);
		Assert.assertEquals(true, bst.contains(5));
	}

	@Test
	public void doesNotContainTest() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(3);
		bst.add(5);
		bst.remove(5);
		Assert.assertEquals(false, bst.contains(5));
	}

	/*************************************************
	 *************** ITERATOR TESTS ****************** TODO
	 *************************************************/

	@Test
	public void iteratorNextTest() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(5);
		bst.add(3);
		Iterator<Integer> it = bst.iterator();
		Assert.assertEquals("3", it.next().toString());
	}

	@Test
	public void iteratorHasNextTest() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(5);
		bst.add(3);
		Iterator<Integer> it = bst.iterator();
		Assert.assertEquals(true, it.hasNext());
	}

	@Test
	public void iteratorNoHasNextTest() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(5);
		bst.add(3);
		Iterator<Integer> it = bst.iterator();
		it.next();
		it.next();
		Assert.assertEquals(false, it.hasNext());
	}

	@Test
	public void iteratorRemoveTest() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(5);
		bst.add(3);
		Iterator<Integer> it = bst.iterator();
		it.next();
		it.remove();
		Assert.assertEquals("- 5 (1)\n", bst.toString());
	}

	/*************************************************
	 *************** BALANCING TESTS ***************** TODO
	 *************************************************/

	// root balance on add
	@Test
	public void add321noBalance() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(3);
		bst.add(2);
		bst.add(1);
		Assert.assertEquals("+ 3 (3)\n  + 2 (2)\n    - 1 (1)\n    -\n  -\n",
				bst.toString());
	}

	@Test
	public void add321forceBalanceAtRoot() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(3);
		bst.add(2);
		bst.add(1);
		bst.rebalance(bst.findEntry(3));
		Assert.assertEquals("+ 2 (3)\n  - 1 (1)\n  - 3 (1)\n", bst.toString());
	}

	@Test
	public void add3210autoBalanceAtRoot() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>(true);
		bst.add(3);
		bst.add(2);
		bst.add(1);
		bst.add(0);
		Assert.assertEquals(
				"+ 1 (4)\n  - 0 (1)\n  + 2 (2)\n    -\n    - 3 (1)\n",
				bst.toString());
	}

	@Test
	public void add321forceBalanceAtRoot_rootTest() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(3);
		bst.add(2);
		bst.add(1);
		bst.rebalance(bst.findEntry(3));
		Assert.assertEquals("2", bst.root().toString());
	}

	// nonroot balance on add

	@Test
	public void add45321noBalance() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(4);
		bst.add(5);
		bst.add(3);
		bst.add(2);
		bst.add(1);
		bst.add(0);
		Assert.assertEquals(
				"+ 4 (6)\n  + 3 (4)\n    + 2 (3)\n      + 1 (2)\n        - 0 (1)\n        -\n      -\n    -\n  - 5 (1)\n",
				bst.toString());
	}

	@Test
	public void add45321forceBalanceNotAtRoot() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(4);
		bst.add(5);
		bst.add(3);
		bst.add(2);
		bst.add(1);
		bst.add(0);
		bst.rebalance(bst.findEntry(3));
		Assert.assertEquals(
				"+ 4 (6)\n  + 1 (4)\n    - 0 (1)\n    + 2 (2)\n      -\n      - 3 (1)\n  - 5 (1)\n",
				bst.toString());
	}

	@Test
	public void add453210autoBalanceNotAtRoot() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>(true);
		bst.add(4);
		bst.add(5);
		bst.add(3);
		bst.add(2);
		bst.add(1);
		bst.add(0);
		Assert.assertEquals(
				"+ 4 (6)\n  + 1 (4)\n    - 0 (1)\n    + 2 (2)\n      -\n      - 3 (1)\n  - 5 (1)\n",
				bst.toString());
	}

	// #3
	// root balance on remove

	@Test
	public void add32410r4noBalance() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(3);
		bst.add(2);
		bst.add(4);
		bst.add(1);
		bst.add(0);
		bst.remove(4);
		Assert.assertEquals(
				"+ 3 (4)\n  + 2 (3)\n    + 1 (2)\n      - 0 (1)\n      -\n    -\n  -\n",
				bst.toString());
	}

	@Test
	public void add32410r4autoBalanceAtRoot() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>(true);
		bst.add(3);
		bst.add(2);
		bst.add(4);
		bst.add(1);
		bst.add(0);
		bst.remove(4);
		Assert.assertEquals(
				"+ 1 (4)\n  - 0 (1)\n  + 2 (2)\n    -\n    - 3 (1)\n",
				bst.toString());
	}

	// ///////////////////////////////////////
	// #4
	// nonroot balance on remove

	@Test
	public void removeUnbalancedNotAtRoot() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(5);
		bst.add(3);
		bst.add(7);
		bst.add(2);
		bst.add(6);
		bst.add(4);
		bst.add(8);
		bst.add(1);
		bst.add(0);
		bst.remove(4);
		Assert.assertEquals(
				"+ 5 (8)\n  + 3 (4)\n    + 2 (3)\n      + 1 (2)\n        - 0 (1)\n        -\n      -\n    -\n  + 7 (3)\n    - 6 (1)\n    - 8 (1)\n",
				bst.toString());
	}

	@Test
	public void removeForceBalanceNotAtRoot() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(5);
		bst.add(3);
		bst.add(7);
		bst.add(2);
		bst.add(6);
		bst.add(4);
		bst.add(8);
		bst.add(1);
		bst.add(0);
		bst.remove(4);
		bst.rebalance(bst.findEntry(3));
		Assert.assertEquals(
				"+ 5 (8)\n  + 1 (4)\n    - 0 (1)\n    + 2 (2)\n      -\n      - 3 (1)\n  + 7 (3)\n    - 6 (1)\n    - 8 (1)\n",
				bst.toString());
	}

	@Test
	public void removeAutoBalanceNotAtRoot() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>(true);
		bst.add(5);
		bst.add(3);
		bst.add(7);
		bst.add(2);
		bst.add(6);
		bst.add(4);
		bst.add(8);
		bst.add(1);
		bst.add(0);
		bst.remove(4);
		Assert.assertEquals(
				"+ 5 (8)\n  + 1 (4)\n    - 0 (1)\n    + 2 (2)\n      -\n      - 3 (1)\n  + 7 (3)\n    - 6 (1)\n    - 8 (1)\n",
				bst.toString());
	}

	@Test
	public void forceBalanceNotAtRoot_SizeTest() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(5);
		bst.add(3);
		bst.add(7);
		bst.add(2);
		bst.add(6);
		bst.add(4);
		bst.add(8);
		bst.add(1);
		bst.remove(4);
		bst.rebalance(bst.findEntry(3));
		Assert.assertEquals(7, bst.size());
	}

	// ////////// Adding (0) triggers balance requirement in two non-consecutive
	// nodes, but not in-between them:
	// ////////// grandchild(3) & grandparent(7) > 2/3, but (4) == 4/7
	@Test
	public void grandchildGrandparentMutualUnbalanced() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(7);
		bst.add(4);
		bst.add(8);
		bst.add(3);
		bst.add(5);
		bst.add(9);
		bst.add(2);
		bst.add(6);
		bst.add(1);
		bst.add(0);
		Assert.assertEquals(
				"+ 7 (10)\n  + 4 (7)\n    + 3 (4)\n      + 2 (3)\n        + 1 (2)\n          - 0 (1)\n          -\n        -\n      -\n    + 5 (2)\n      -\n      - 6 (1)\n  + 8 (2)\n    -\n    - 9 (1)\n",
				bst.toString());
	}

	@Test
	public void grandchildGrandparentMutualForceBalance() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(7);
		bst.add(4);
		bst.add(8);
		bst.add(3);
		bst.add(5);
		bst.add(9);
		bst.add(2);
		bst.add(6);
		bst.add(1);
		bst.add(0);
		bst.rebalance(bst.findEntry(7));
		Assert.assertEquals(
				"+ 4 (10)\n  + 1 (4)\n    - 0 (1)\n    + 2 (2)\n      -\n      - 3 (1)\n  + 7 (5)\n    + 5 (2)\n      -\n      - 6 (1)\n    + 8 (2)\n      -\n      - 9 (1)\n",
				bst.toString());
	}

	@Test
	public void grandchildGrandparentMutualAutoBalance() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>(true);
		bst.add(7);
		bst.add(4);
		bst.add(8);
		bst.add(3);
		bst.add(5);
		bst.add(9);
		bst.add(2);
		bst.add(6);
		bst.add(1);
		bst.add(0);
		Assert.assertEquals(
				"+ 4 (10)\n  + 1 (4)\n    - 0 (1)\n    + 2 (2)\n      -\n      - 3 (1)\n  + 7 (5)\n    + 5 (2)\n      -\n      - 6 (1)\n    + 8 (2)\n      -\n      - 9 (1)\n",
				bst.toString());
	}

	// ////////// Adding (0) triggers balance requirement in two consecutive
	// nodes:
	// ////////// child(5) & parent(7) > 2/3
	@Test
	public void childParentMutualUnbalanced() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(7);
		bst.add(5);
		bst.add(8);
		bst.add(9);
		bst.add(3);
		bst.add(6);
		bst.add(2);
		bst.add(4);
		bst.add(1);
		bst.add(0);
		Assert.assertEquals(
				"+ 7 (10)\n  + 5 (7)\n    + 3 (5)\n      + 2 (3)\n        + 1 (2)\n          - 0 (1)\n          -\n        -\n      - 4 (1)\n    - 6 (1)\n  + 8 (2)\n    -\n    - 9 (1)\n",
				bst.toString());
	}

	@Test
	public void childParentMutualForceBalance() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
		bst.add(7);
		bst.add(5);
		bst.add(8);
		bst.add(9);
		bst.add(3);
		bst.add(6);
		bst.add(2);
		bst.add(4);
		bst.add(1);
		bst.add(0);
		bst.rebalance(bst.findEntry(7));
		Assert.assertEquals(
				"+ 4 (10)\n  + 1 (4)\n    - 0 (1)\n    + 2 (2)\n      -\n      - 3 (1)\n  + 7 (5)\n    + 5 (2)\n      -\n      - 6 (1)\n    + 8 (2)\n      -\n      - 9 (1)\n",
				bst.toString());
	}

	@Test
	public void childParentMutualAutoBalance() {
		BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>(true, 2, 3);
		bst.add(7);
		bst.add(5);
		bst.add(8);
		bst.add(9);
		bst.add(3);
		bst.add(6);
		bst.add(2);
		bst.add(4);
		bst.add(1);
		bst.add(0);
		Assert.assertEquals(
				"+ 4 (10)\n  + 1 (4)\n    - 0 (1)\n    + 2 (2)\n      -\n      - 3 (1)\n  + 7 (5)\n    + 5 (2)\n      -\n      - 6 (1)\n    + 8 (2)\n      -\n      - 9 (1)\n",
				bst.toString());
	}
	@Test
    public void emptySizeTest()
    {
        BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>();
        Assert.assertEquals(0, bst.size());
    }


    @Test
    public void noAutoBalanceAtExactAlpha()
    {
        BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>(true, 2, 3);
        bst.add(3);
        bst.add(2);
        bst.add(1);
        Assert.assertEquals("+ 3 (3)\n  + 2 (2)\n    - 1 (1)\n    -\n  -\n", bst.toString());
    }


//    @Test
//    public void autoBalanceAtLowAlpha()
//    {
//        BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>(true, 1, 2);
//        bst.add(3);
//        bst.add(2);
//        bst.add(1);
//        Assert.assertEquals("+ 2 (3)\n  - 1 (1)\n  - 3 (1)\n", bst.toString());
//    }

    @Test
    public void noAutoBalanceAtHighAlpha()
    {
        BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>(true, 8, 9);
        bst.add(3);
        bst.add(2);
        bst.add(1);
        bst.add(0);
        Assert.assertEquals("+ 3 (4)\n  + 2 (3)\n    + 1 (2)\n      - 0 (1)\n      -\n    -\n  -\n", bst.toString());
    }
    
    @Test
    public void grandchildGrandparentMutualAutoBalanceNotAtRoot()
    {
        BalancedBSTSet<Integer> bst = new BalancedBSTSet<Integer>(true);
        bst.add(10);
        bst.add(7);
        bst.add(12);
        bst.add(4);
        bst.add(13);
        bst.add(8);
        bst.add(11);
        bst.add(3);
        bst.add(14);
        bst.add(5);
        bst.add(9);
        bst.add(2);
        bst.add(6);
        bst.add(1);
        bst.add(0);
        Assert.assertEquals("+ 10 (15)\n  + 4 (10)\n    + 1 (4)\n      - 0 (1)\n      + 2 (2)\n        -\n        - 3 (1)\n    + 7 (5)\n      + 5 (2)\n        -\n        - 6 (1)\n      + 8 (2)\n        -\n        - 9 (1)\n  + 12 (4)\n    - 11 (1)\n    + 13 (2)\n      -\n      - 14 (1)\n",    bst.toString());
    }
	
}