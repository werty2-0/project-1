// -== CS400 Spring 2024 File Header Information ==-
// Name: Parin Gouraram
// Email: pgouraram@wisc.edu
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;  

/**
 * This class models a red black tree in the way that you are able to insert nodes into it.
 * It follows the properties that a red black tree can only have either red or black nodes, a red node must have a black child,
 * and that every path from the root to the leaf nodes must have the same number of black nodes in it.
 */
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

	/**
	 * This class models what a node would look like in a red black tree. It models a node that has a data value and color value that is either red or black.
	 * In addition, these nodes are doubly linked which means that every node has a reference to its children and its parent.
	 */
	protected static class RBTNode<T> extends Node<T> {
   		 public boolean isBlack = false;
   		 public RBTNode(T data) { super(data); }
   		 public RBTNode<T> getUp() { return (RBTNode<T>)this.up; }
   		 public RBTNode<T> getDownLeft() { return (RBTNode<T>)this.down[0]; }
   		 public RBTNode<T> getDownRight() { return (RBTNode<T>)this.down[1]; }
	}
	
	/**
	 * This method makes sure that a red black tree maintains all of its properties after an insertion into the tree takes place.
	 * It does this by abstracting each insertion into three cases: when the new node has a red parent and its aunt is red,
	 * when the new node forms a line with its red parent and its aunt is black or null, and when the new node forms a zig shape with its red parent and its aunt is black or null. 
	 *
	 * @param node - node that was just inserted in the red black tree
	 */
	protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> node){		
    		// First check if node does not violate tree
    		if (node.getUp() == null || node.getUp().isBlack == true)
    			return;

   		// create reference to grandparent and aunt
		RBTNode<T> grandparent = node.getUp().getUp();

		RBTNode<T> aunt = null;	
		if(grandparent.getDownLeft() == node.getUp()){
			aunt = grandparent.getDownRight();
		}
		else {
	    		aunt = grandparent.getDownLeft();
		}

    		// if inserted node violates tree, check if aunt is null or black
    		if (aunt == null || aunt.isBlack == true) {

     			// if zig formation
     			 if ((grandparent.getDownLeft() != null && grandparent.getDownLeft().getDownRight() == node) || (grandparent.getDownRight() != null && grandparent.getDownRight().getDownLeft() == node)) {

        			// rotate child and parent and recurse
				// keep reference to node's parent to recurse on later
				RBTNode<T> newChild = node.getUp();

        			this.rotate(node, node.getUp());
        			enforceRBTreePropertiesAfterInsert(newChild);
				return;
      			}

      			else {
        			// if line formation, swap parent and grandparent's colors and then rotate them
        			grandparent.isBlack = !grandparent.isBlack;
        			node.getUp().isBlack = !node.getUp().isBlack;
        			this.rotate(node.getUp(), grandparent);
        			return;
  	    		}
		}

		// else if the aunt is red, swap colors of aunt, grandparent, and parent and recurse with grandparent being the child
		aunt.isBlack = !aunt.isBlack;
		grandparent.isBlack = !grandparent.isBlack;
		node.getUp().isBlack = !node.getUp().isBlack;
		enforceRBTreePropertiesAfterInsert(grandparent);
	}
	
	/**
  	 * Inserts a new data value into the tree. This tree will not hold null references nor duplicate
  	 * data values.
  	 * 
  	 * @param data to be added into this binary search tree
 	 * @return true if the value was inserted, false if is was in the tree already
	 * @throws NullPointerException when the provided data argument is null
	 */ 
	@Override
	public boolean insert(T data) throws NullPointerException {
		RBTNode<T> node = new RBTNode<T>(data);
		boolean inserted = this.insertHelper(node);
		enforceRBTreePropertiesAfterInsert(node);
		RBTNode<T> rootRB = (RBTNode<T>) this.root;
		rootRB.isBlack = true;
		if(inserted) return true;
		return false;
	}
	
	/**
	 * This testor method tests the insertion case in which a node is added into a empty red black tree.
	 */
	@Test
        public void test1(){

		// create red black tree
                RedBlackTree<Integer> tree = new RedBlackTree<>();
		
		// add the 1 node to the red black tree
                tree.insert(1);

		// get red black verison of root to see its color
                RBTNode<Integer> rootRB = (RBTNode<Integer>) tree.root;

		// make sure node/root has the right value and is the right color
                Assertions.assertTrue(rootRB.data == 1 && rootRB.isBlack == true);

        }


	
	/**
	 * This testor method tests the insertion case in which the newly added red node is violating the red black tree because its the child of a red node, and its aunt is black. 
	 * The added node also forms a zig with its parent node.
 	 */
	@Test
        public void test2(){

		// create red black tree
                RedBlackTree<Integer> tree = new RedBlackTree<>();
		
		// add nodes to the tree
                tree.insert(10);
                tree.insert(5);
                tree.insert(11);
                tree.insert(3);
		
		// insertion which asserts case where aunt is black and child shares zig relationship with parent. Should cause the red black tree to change.
                tree.insert(4);

		// get red black version of root to see its color
                RBTNode<Integer> rootRB = (RBTNode<Integer>) tree.root;

		// Go through every node to make sure they are in the right spot in the tree and have the right color
                Assertions.assertTrue(rootRB.data == 10 && rootRB.isBlack == true);
                Assertions.assertTrue(rootRB.getDownRight().data == 11 && rootRB.getDownRight().isBlack == true);
                Assertions.assertTrue(rootRB.getDownLeft().data == 4 && rootRB.getDownLeft().isBlack == true);
                Assertions.assertTrue(rootRB.getDownLeft().getDownRight().data == 5 && rootRB.getDownLeft().getDownRight().isBlack == false);
                Assertions.assertTrue(rootRB.getDownLeft().getDownLeft().data == 3 && rootRB.getDownLeft().getDownLeft().isBlack == false);
        }

	/**
	 * This testor method tests the insertion case in which the newly added red node is violating the red black tree because its the child of a red node and its aunt is black.
         * The added node also forms a line with its parent node.
	 */
        @Test
        public void test3(){

                // create red black tree
                RedBlackTree<Integer> tree = new RedBlackTree<>();

                // add nodes to red black tree
                tree.insert(10);
                tree.insert(5);
                tree.insert(15);
                tree.insert(16);

                // insertion which asserts case where aunt is black and child forms a line formation with parent
                tree.insert(17);

                // get red black version of root to see its color
                RBTNode<Integer> rootRB = (RBTNode<Integer>) tree.root;

                // go through every node to make sure they are in the right spot in the tree and have the right color
                Assertions.assertTrue(rootRB.data == 10 && rootRB.isBlack == true);
                Assertions.assertTrue(rootRB.getDownRight().data == 16 && rootRB.getDownRight().isBlack == true);
                Assertions.assertTrue(rootRB.getDownLeft().data == 5 && rootRB.getDownLeft().isBlack == true);
                Assertions.assertTrue(rootRB.getDownRight().getDownRight().data == 17 && rootRB.getDownRight().getDownRight().isBlack == false);
                Assertions.assertTrue(rootRB.getDownRight().getDownLeft().data == 15 && rootRB.getDownRight().getDownLeft().isBlack == false);

        }

	/**
	 * This testor method tests the insertion case in which the newly added red node is violating the red black tree because its the child of a red node, and its aunt is black.
	 * The newly added node forms a line with its parent node and should change the root node of the tree.
	 */
	@Test
	public void test4(){

		// create red black tree
	    	RedBlackTree<Integer> tree = new RedBlackTree<>();

   	 	// add nodes to red black tree
   	 	tree.insert(10);
    		tree.insert(11);

    		// insertion which asserts case where aunt is null/black
   	 	tree.insert(12);

    		// get red black version of root to see its color
    		RBTNode<Integer> rootRB = (RBTNode<Integer>) tree.root;

		// Go through every node to make sure they are in the right spot in the tree and have the right color
    		Assertions.assertTrue(rootRB.data == 11 && rootRB.isBlack == true);
    		Assertions.assertTrue(rootRB.getDownLeft().data == 10 && rootRB.getDownLeft().isBlack == false);
    		Assertions.assertTrue(rootRB.getDownRight().data == 12 && rootRB.getDownRight().isBlack == false);
	}
	
	/**
	 * This testor method tests the insertion case in which the newly added red node is violating the red black tree because its the child of a red node, and its aunt is black.
	 * The newly added node forms a zig shape with its parent and should change the root of the tree.
	 */
	@Test
	public void test5(){

		// create red black tree
                RedBlackTree<Integer> tree = new RedBlackTree<>();

		// add nodes to red black tree
		tree.insert(10);
		tree.insert(15);

		// insertion which asserts case where aunt is null/black
		tree.insert(12);

		// get red black version of root to see its color
                RBTNode<Integer> rootRB = (RBTNode<Integer>) tree.root;

		// Go through every node to make sure they are in the right spot in the tree and have the right color
                Assertions.assertTrue(rootRB.data == 12 && rootRB.isBlack == true);
                Assertions.assertTrue(rootRB.getDownLeft().data == 10 && rootRB.getDownLeft().isBlack == false);
                Assertions.assertTrue(rootRB.getDownRight().data == 15 && rootRB.getDownRight().isBlack == false);


	}

	/**
	 * This testor method tests the insertion case in which the newly added red node is violating the red black tree because its the child of a red node, and its aunt is a red node.
	 * The newly added node also forms a line with its parent.
	 */
	@Test
	public void test6(){
	    	// create red black tree
    		RedBlackTree<Integer> tree = new RedBlackTree<>();

    		// add nodes to red black tree
    		tree.insert(10);
    		tree.insert(5);
    		tree.insert(11);

    		// insertion which asserts case where aunt is red
    		tree.insert(3);

    		// get red black version of root to see its color
    		RBTNode<Integer> rootRB = (RBTNode<Integer>) tree.root;

     		// Go through every node to make sure they are in the right spot in the tree and have the right color
    		Assertions.assertTrue(rootRB.data == 10 && rootRB.isBlack == true);
    		Assertions.assertTrue(rootRB.getDownRight().data == 11 && rootRB.getDownRight().isBlack == true);
    		Assertions.assertTrue(rootRB.getDownLeft().data == 5 && rootRB.getDownLeft().isBlack == true);
 		Assertions.assertTrue(rootRB.getDownLeft().getDownLeft().data == 3 && rootRB.getDownLeft().getDownLeft().isBlack == false);
	}

	/**
	 * This testor method tests the insertion case in which the newly added red node is violating the red black tree because its the child of a red node, and its aunt is a red node. 
	 * The newly added node also forms a zig shape with its parent
	 */
	@Test
	public void test7(){
	   	// create red black tree
                RedBlackTree<Integer> tree = new RedBlackTree<>();

                // add nodes to red black tree
                tree.insert(10);
                tree.insert(5);
                tree.insert(12);

                // insertion which asserts case where aunt is red
                tree.insert(11);

                // get red black version of root to see its color
                RBTNode<Integer> rootRB = (RBTNode<Integer>) tree.root;

		// Go through every node to make sure they are in the right spot in the tree and have the right color
		Assertions.assertTrue(rootRB.data == 10 && rootRB.isBlack == true);
                Assertions.assertTrue(rootRB.getDownLeft().data == 5 && rootRB.getDownLeft().isBlack == true);
                Assertions.assertTrue(rootRB.getDownRight().data == 12 && rootRB.getDownRight().isBlack == true);
                Assertions.assertTrue(rootRB.getDownRight().getDownLeft().data == 11 && rootRB.getDownRight().getDownLeft().isBlack == false);


	}
	
	/**
	 * This testor method tests the insertion case in which the newly added red node is violating the red black tree because its the child of a red node, and its aunt is a red node. 
         * This specific case will cause the enforce properties method to enter a recursive case in which the red black tree violation will move up the tree until it is eventually fixed.
	 */
	@Test
	public void test8(){

		// create red black tree
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		
		// add nodes to the tree
		tree.insert(10);
		tree.insert(15);
		tree.insert(5);
		tree.insert(4);
		tree.insert(6);
		tree.insert(2);
		tree.insert(1);

		// insertion which asserts case where aunt is red. Will cause enforce RBT properties to enter a recursive case
		tree.insert(3);

		// get red black verison of root to see its color
		RBTNode<Integer> rootRB = (RBTNode<Integer>) tree.root;

		// go through every node to make sure they are in the right spot and have the right color 
		Assertions.assertTrue(rootRB.data == 5 && rootRB.isBlack == true);
		Assertions.assertTrue(rootRB.getDownRight().data == 10 && rootRB.getDownRight().isBlack == false); 
		Assertions.assertTrue(rootRB.getDownRight().getDownRight().data == 15 && rootRB.getDownRight().getDownRight().isBlack == true);
		Assertions.assertTrue(rootRB.getDownRight().getDownLeft().data == 6 && rootRB.getDownRight().getDownLeft().isBlack == true);
		Assertions.assertTrue(rootRB.getDownLeft().data == 2 && rootRB.getDownLeft().isBlack == false);
		Assertions.assertTrue(rootRB.getDownLeft().getDownLeft().data == 1 && rootRB.getDownLeft().getDownLeft().isBlack == true);
		Assertions.assertTrue(rootRB.getDownLeft().getDownRight().data == 4 && rootRB.getDownLeft().getDownRight().isBlack == true);
		Assertions.assertTrue(rootRB.getDownLeft().getDownRight().getDownLeft().data == 3 && rootRB.getDownLeft().getDownRight().getDownLeft().isBlack == false);

	}
}
