import java.util.Iterator;
import java.util.Stack;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * This class models a red black tree in the specific way you are supposed to iterate through the tree in a depth first traversal.
 * It follows the properties that a red black tree can only have either red or black nodes, a red node must have a black child,
 * and that every path from the root to the leaf nodes must have the same number of black nodes in it. 
 */
public class IterableRedBlackTree<T extends Comparable<T>>
    extends RedBlackTree<T> implements IterableSortedCollection<T> {

	// Keeps track of start point in iterator. On default compareTo is set to -1 so that the iterator goes through every node in the tree
	private Comparable<T> startPoint = new Comparable<>(){
		@Override
		public int compareTo(T o){
			return -1;
		}};
	
       /**
	* This method sets a starting point for the iterator of a red black tree that this class implements. 
	* When a non-null start point is set, the first value returned by this
	* iterator will be the smallest value in the collection that is greater than
	* or equal to the specified start point.  When no (or a null) start point is
	* set, the iterator will step through all values in the collection.
	*
	* @param startPoint - startpoint of the iterator. If null, start point will be smallest value in the tree.
	*/
	public void setIterationStartPoint(Comparable<T> startPoint) {

		if(startPoint != null) this.startPoint = startPoint;

		// if inputed start point is null, make class's start point smallest value in tree
		else this.startPoint = new Comparable<>(){
                @Override
                public int compareTo(T o){
                        return -1;
                }};

	}

	/**
	 * This method returns an iterator of our red black tree.
	 *
	 * @return Iterator<T> where T is a data type specified by the user 
	 */
	public Iterator<T> iterator() {
	
		return new RBTIterator<T>(this.root, this.startPoint);
	
	}

	/**
	 * This class models a iterator that iterates through a red black tree. The red black tree that it iterates through follows the common red black tree properties
	 * that a red black tree can only have either red or black nodes, a red node must have a black child,
         * and that every path from the root to the leaf nodes must have the same number of black nodes in it. 
	 */
	private static class RBTIterator<R extends Comparable<R>> implements Iterator<R> {
	
		// starting value for the iterator
		private Comparable<R> iterStartPoint;

		//stack used to keep track of ancestor nodes in depth first traversal 
		private Stack<Node<R>> stack;
	
		/**
		 * Constructs an iterator for the red black tree by initating the starting point for the iterator, creating the stack for the traversal, and by puting the intial nodes in the stack.
		 *
		 * @param root - root of tree that is going to be iterated on
		 * @param startpoint - starting value of the iterator of the red black tree
		 */
		public RBTIterator(Node<R> root, Comparable<R> startPoint) {
			this.iterStartPoint = startPoint;
			this.stack = new Stack<>();
			this.buildStackHelper(root);
		}

		/**
		 * This method builds the stack of the iterator by puting all ancestor nodes on the stack of the given node that was inputed.
		 *
		 * @param node - node to build on the stack and traverse
		 */
		private void buildStackHelper(Node<R> node) {
			
			// Base case: there is no more nodes so we can return
			if(node == null) return;

			// Recursive case 1: the node we are on is less than the start point. If this is the case, we must recurse on the right subtree.
			else if(this.iterStartPoint.compareTo(node.data) >  0){
				buildStackHelper(node.down[1]);

			}

			// Recursive case 2: the node we are on is equal or greater than the start point. 
			// If this is the case, we must push that node on the stack as an ancestor node and recurse on its left subtree. 
			else if(this.iterStartPoint.compareTo(node.data) <= 0){
				stack.push(node);
				buildStackHelper(node.down[0]);

			}

		}
		
		/**
		 * This method returns true if the iterator of the red black tree has a next value and false it it doesn't have a next value.
		 *
		 * @return true if there is as next value in the iterator and false otherwise.
		 */
		public boolean hasNext() {
			if(stack.isEmpty()){
				return false;
			}

			return true;	
		}

		/**
		 * This method returns the data of the next node in iterator and then advances the iterator to the next node.
		 *
		 * @return data in the next node in the iterator
		 * @throws NoSuchElementException - throws exception if there are no more elements in the iterator
		 */
		public R next() { 

			// There is a next value in the stack
			if(this.hasNext()){

				// remove node off of top of stack
				Node<R> removed = stack.pop();

				// rebuild the stack with the nodes right subtree 
				buildStackHelper(removed.down[1]);

				return removed.data;
			}
		
			// throw a exception if there no more elements left in the stack
			throw new NoSuchElementException();
		}
    	}

	/**
   	 * Performs a naive insertion into a binary search tree: adding the new node in a leaf position
   	 * within the tree. After this insertion, no attempt is made to restructure or balance the tree.
   	 * 
  	 * @param node the new node to be inserted
   	 * @return true if the value was inserted 
   	 * @throws NullPointerException when the provided node is null
   	 */
	@Override
	protected boolean insertHelper(Node<T> newNode) throws NullPointerException {
    		if (newNode == null)
      			throw new NullPointerException("new node cannot be null");

    		if (this.root == null) {
      			// add first node to an empty tree
      			root = newNode;
      			size++;
      			return true;
    		} else {
      			// insert into subtree
      			Node<T> current = this.root;
      			while (true) {
        			int compare = newNode.data.compareTo(current.data);
				// if (compare == 0) {
       				 	// return false;
       				// }
				if (compare <= 0) {
          				// insert in left subtree
          				if (current.down[0] == null) {
            					// empty space to insert into
            					current.down[0] = newNode;
            					newNode.up = current;
            					this.size++;
            					return true;
          				} else {
            					// no empty space, keep moving down the tree
      			      			current = current.down[0];
      			    		}
        			} else {
          				// insert in right subtree
          				if (current.down[1] == null) {
            					// empty space to insert into
            					current.down[1] = newNode;
            					newNode.up = current;
            					this.size++;
            					return true;
          				} else {
            					// no empty space, keep moving down the tree
            					current = current.down[1];
          				}
        			}
      			}
    		}
  	}
	





	/**
	 * This testor method test's the iterator of the Red Black Tree in its ability to properly iterate over different data types. It specifically tests the Integer and String data types.
	 */
	@Test	
	public  void differentDataTypeTest(){
	
		// create red black tree of integers
		IterableRedBlackTree<Integer> treeInt = new IterableRedBlackTree<>();
		
		// insert nodes
		treeInt.insert(4);
		treeInt.insert(3);
		treeInt.insert(5);
		treeInt.insert(6);
		treeInt.insert(2);
		
		// create iterator of tree
		Iterator<Integer> iteratorInt =  treeInt.iterator();

		// create array of expected values that the iterator should get
                int[] expectedValuesInt = {2, 3, 4, 5, 6};

		// go through the iterator and make sure that it gets the right values 
                for(int i = 0; i < expectedValuesInt.length;i++){
			int iteration = iteratorInt.next();
                        Assertions.assertEquals(expectedValuesInt[i], iteration);

                }
		
		// make sure that their is not a next value in the iterator after going through it
		Assertions.assertFalse(iteratorInt.hasNext());

		// create  red black tree of strings
		IterableRedBlackTree<String> treeStr = new IterableRedBlackTree<>();

		// insert nodes into tree
		treeStr.insert("d");
		treeStr.insert("c");
		treeStr.insert("e");
	  	treeStr.insert("f");
		treeStr.insert("b");

		// create iterator of tree
		Iterator<String> iteratorStr =  treeStr.iterator();

		// create array of expected values that the iterator should get
		String[] expectedValuesStr = {"b", "c", "d", "e", "f"};

		// go through the iterator and make sure that it gets the right values 
		for(int i = 0; i < expectedValuesStr.length;i++){
			 String iteration = iteratorStr.next();
			 Assertions.assertEquals(expectedValuesStr[i], iteration);
		}
		
		// make sure that their is not a next value in the iterator after going through it
		Assertions.assertFalse(iteratorStr.hasNext());
	}

	/**
	 * This testor method test's the iterator of the Red Black Tree class  in it's ability to handle and iterate over duplicate values in the tree.
	 * This method first's tests a red black tree without duplicates and then tests one with duplicates. 
	 */
	@Test
	public void duplicateDataTest(){

		// create red black tree. This red black tree will not have any duplicate nodes
		IterableRedBlackTree<Integer> treeNoDup = new IterableRedBlackTree<>();

		// insert nodes into tree
		treeNoDup.insert(4);
		treeNoDup.insert(3);
		treeNoDup.insert(5);
		treeNoDup.insert(2);
		treeNoDup.insert(8);
		treeNoDup.insert(7);

		// create iterator of tree
		Iterator<Integer> iteratorNoDup = treeNoDup.iterator();

		// create array of expected values that the iterator should get
		int[] expectedValuesNoDup = {2,3,4,5,7,8};

		// go through the iterator and make sure that it gets the right values 
		for(int i = 0; i < expectedValuesNoDup.length;i++) {
			int iteration = iteratorNoDup.next();
			Assertions.assertEquals(expectedValuesNoDup[i], iteration);
		}

		// make sure that their is not a next value in the iterator after going through it
		Assertions.assertFalse(iteratorNoDup.hasNext());

		// create red black tree. This red black tree will have multiple duplicate nodes
		IterableRedBlackTree<Integer> treeDup = new IterableRedBlackTree<>();

		// insert nodes into tree
		treeDup.insert(4);
		treeDup.insert(4);
                treeDup.insert(3);
                treeDup.insert(5);
                treeDup.insert(2);
                treeDup.insert(8);
		treeDup.insert(8);
                treeDup.insert(7);

		// create iterator of tree
		Iterator<Integer> iteratorDup = treeDup.iterator();

		// create array of expected values that the iterator should get
		int[] expectedValuesDup = {2,3,4,4,5,7,8,8};
		
		// go through the iterator and make sure that it gets the right values 
		for(int i = 0; i < expectedValuesDup.length;i++){
			int iteration = iteratorDup.next();
			Assertions.assertEquals(expectedValuesDup[i], iteration);
		}

		// make sure that their is not a next value in the iterator after going through it
		Assertions.assertFalse(iteratorDup.hasNext());
	}

	/**
	 * This testor method test's the iterator of the red black tree class in its ability to iterate over only a section of the tree when the user sets a start point in the tree.
	 * It firsts tests a red black tree that does not have a starting point in the iterator and then tests a red black tree that has a starting point in the iterator.
	 */
	@Test
	public void startingPointTest(){
		
		// create red black tree. This red black tree will have the default starting point in the tree at the root of the node.
		IterableRedBlackTree<Integer> treeNoStart = new IterableRedBlackTree<>();

		// insert nodes into tree
		treeNoStart.insert(4);
                treeNoStart.insert(3);
                treeNoStart.insert(5);
                treeNoStart.insert(2);
                treeNoStart.insert(8);
                treeNoStart.insert(7);

		//make the tree start without a specified starting point
		treeNoStart.setIterationStartPoint(null);	
	
		// create iterator of tree
		Iterator<Integer> iteratorNoStart = treeNoStart.iterator();
		
		// create array of expected values that the iterator should get
		int[] expectedValuesNoStart = {2,3,4,5,7,8};

		// go through the iterator and make sure that it gets the right values 
		for(int i = 0; i < expectedValuesNoStart.length;i++){
			int iteration = iteratorNoStart.next();
			Assertions.assertEquals(expectedValuesNoStart[i], iteration);
		}

		// make sure that their is not a next value in the iterator after going through it
		Assertions.assertFalse(iteratorNoStart.hasNext());

		// create red black tree. This red black tree will have its iterator start at a certain position within the tree
		IterableRedBlackTree<Integer> treeStart = new IterableRedBlackTree<>();

		// insert nodes into tree
                treeStart.insert(4);
                treeStart.insert(3);
                treeStart.insert(5);
                treeStart.insert(2);
                treeStart.insert(8);
                treeStart.insert(7);

                // set starting iteration point to a node that is within the tree
        	treeStart.setIterationStartPoint(7);

		// create iterator of tree
                Iterator<Integer> iteratorStart = treeStart.iterator();

		// create array of expected values that the iterator should get
		int[] expectedValuesStart = {7, 8};

		// go through the iterator and make sure that it gets the right values 
		for(int i = 0; i < expectedValuesStart.length;i++){
			int iteration = iteratorStart.next();
			Assertions.assertEquals(expectedValuesStart[i], iteration);
		}
		
		// make sure that their is not a next value in the iterator after going through it
		Assertions.assertFalse(iteratorStart.hasNext());
	}
}
