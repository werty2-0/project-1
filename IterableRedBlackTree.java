import java.util.Iterator;
import java.util.Stack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.NoSuchElementException;

public class IterableRedBlackTree<T extends Comparable<T>> extends RedBlackTree<T>
    implements IterableSortedCollection<T> {

  private Comparable<T> itrStart = (T) -> -1;

  public void setIterationStartPoint(Comparable<T> startPoint) {
    itrStart = startPoint != null ? startPoint : (T) -> -1;
  }

  public Iterator<T> iterator() {
    return new RBTIterator<T>(this.root, itrStart);
  }

  /**
   * Performs a naive insertion into a binary search tree: adding the new node in a leaf position
   * within the tree. After this insertion, no attempt is made to restructure or balance the tree.
   * 
   * @param newNode the new node to be inserted
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
        // return false;}
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

  private static class RBTIterator<R> implements Iterator<R> {

    private Comparable<R> start;
    private Stack<Node<R>> stack;

    public RBTIterator(Node<R> root, Comparable<R> startPoint) {
      // create the stack and startpoint and call buildStackHelper
      stack = new Stack<>();
      start = startPoint;
      buildStackHelper(root);
    }

    /**
     * This method is a helper method to add nodes that are next in line to the stack, with
     * recursion being called at times
     * 
     * @param node the node being checked
     */
    private void buildStackHelper(Node<R> node) {
      // base case
      if (node == null) {
        return;
      } else {
        if (start.compareTo(node.data) > 0) { // right subtree because the start value is greater
                                              // than the node
          buildStackHelper(node.down[1]);
        } else { // the start is less than or equal to the node
          stack.push(node);
          buildStackHelper(node.down[0]);
        }
      }
    }

    /**
     * Checks if the stack is empty or not
     * 
     * @return true if the stack is not empty, false otherwise
     */
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    /**
     * Goes to the next element in the stack and does a recursion call if necessary
     * 
     * @return the next element in the stack
     */
    public R next() {
      if (stack.isEmpty()) {
        throw new NoSuchElementException("There is no element in the stack");
      }
      Node<R> answer = stack.pop();
      buildStackHelper(answer.down[1]); // does the recursion for the greater element, which is
                                        // always on the right
      return answer.data;
    }


  }


  /**
   * tests for duplicate values in the tree
   */
  @Test
  public void testing1() {
    IterableRedBlackTree<String> t1 = new IterableRedBlackTree<>();

    t1.insert("j");
    t1.insert("h");
    t1.insert("k");
    t1.insert("k"); // adding the duplicate value

    Iterator<String> itr = t1.iterator();
    Assertions.assertTrue(itr.next().equals("h"));
    Assertions.assertTrue(itr.next().equals("j"));
    Assertions.assertTrue(itr.next().equals("k"));
    Assertions.assertTrue(itr.next().equals("k"));

  }

  /**
   * iterator starts at a specified starting point rather than the default
   */
  @Test
  public void testing2() {
    IterableRedBlackTree<String> t1 = new IterableRedBlackTree<>();

    t1.insert("j");
    t1.insert("h");
    t1.insert("k");

    t1.setIterationStartPoint("j"); // sets the staring point
    Iterator<String> itr = t1.iterator();
    Assertions.assertTrue(itr.next().equals("j"));
    Assertions.assertTrue(itr.next().equals("k"));
  }

  /**
   * tests with integers rather than strings
   */
  @Test
  public void testing3() {
    IterableRedBlackTree<Integer> t1 = new IterableRedBlackTree<>(); // creating an integer tree
                                                                     // iterator

    t1.insert(2);
    t1.insert(1);
    t1.insert(3);

    Iterator<Integer> itr = t1.iterator();
    Assertions.assertTrue(itr.next().equals(1));
    Assertions.assertTrue(itr.next().equals(2));
    Assertions.assertTrue(itr.next().equals(3));
  }

  /**
   * tests with an integer big tree at a specific starting point that is not the root
   */
  @Test
  public void testing4() {
    IterableRedBlackTree<Integer> t1 = new IterableRedBlackTree<>();

    t1.insert(100);
    t1.insert(80);
    t1.insert(120);
    t1.insert(140);
    t1.insert(60);
    t1.insert(90);
    t1.insert(110);
    // creating tree with many values
    t1.setIterationStartPoint(90); // setting starting point
    Iterator<Integer> itr = t1.iterator();
    Assertions.assertTrue(itr.next().equals(90));
    Assertions.assertTrue(itr.next().equals(100));
    Assertions.assertTrue(itr.next().equals(110));
    Assertions.assertTrue(itr.next().equals(120));
    Assertions.assertTrue(itr.next().equals(140));
  }
}
