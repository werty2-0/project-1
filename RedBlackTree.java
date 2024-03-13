// == CS400 Spring 2024 File Header Information ==
// Name: Aja Sampath
// Email: asampath3@wisc.edu
// Lecturer: Gary Dahl
// Notes to Grader: NA
import java.nio.file.NotDirectoryException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {



  protected static class RBTNode<T> extends Node<T> {
    public boolean isBlack = false;

    public RBTNode(T data) {
      super(data);
    }

    public RBTNode<T> getUp() {
      return (RBTNode<T>) this.up;
    }

    public RBTNode<T> getDownLeft() {
      return (RBTNode<T>) this.down[0];
    }

    public RBTNode<T> getDownRight() {
      return (RBTNode<T>) this.down[1];
    }



  }

  /**
   * Resolves any red property violations that are introduced by inserting a new node into the
   * Red-Black Tree
   * 
   * @param redNode the red node that is being added and possibly violating red properties
   */
  protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> child) {
    // check that there is a red property being violated
    RBTNode<T> parent = child.getUp();
    if (parent != null && !parent.isBlack) {
      RBTNode<T> grand = parent.getUp();
      if (grand != null) {
        // right child aunt
        if (grand.getDownLeft() != null && grand.getDownLeft().equals(parent)) { // left child is
                                                                                 // parent
          RBTNode<T> aunt = grand.getDownRight();
          if (aunt == null) { // null aunt
            if (parent.getDownLeft() != null && parent.getDownLeft().equals(child)) { // black
                                                                                      // aunt line
              rotate(parent, grand);
              parent.isBlack = true;
              grand.isBlack = false;
            } else { // black aunt zag
              rotate(child, parent);
              enforceRBTreePropertiesAfterInsert(parent); // call again since the property is being
            } // violated
          } else if (aunt.isBlack) { // black aunt
            if (parent.getDownLeft() != null && parent.getDownLeft().equals(child)) { // black
                                                                                      // aunt line
              rotate(parent, grand);
              parent.isBlack = true;
              grand.isBlack = false;
            } else { // black aunt zag
              rotate(child, parent);
              enforceRBTreePropertiesAfterInsert(parent); // call again since the property is being
                                                          // violated
            }
          } else { // red aunt
            parent.isBlack = true;
            aunt.isBlack = true;
            grand.isBlack = false;
            if (grand.getUp() != null && !grand.getUp().isBlack) { // if the property is violated
                                                                   // again need to call recursively
              enforceRBTreePropertiesAfterInsert(grand);
            }
          }
        }
        // left child aunt
        else { // right child is parent
          RBTNode<T> aunt = grand.getDownLeft();
          if (aunt == null) { // null aunt
            if (parent.getDownRight() != null && parent.getDownRight().equals(child)) { // black
                                                                                        // aunt
              // line
              rotate(parent, grand);
              parent.isBlack = true;
              grand.isBlack = false;
            } else { // black aunt zag
              rotate(child, parent);
              enforceRBTreePropertiesAfterInsert(parent); // call again since the property is being
            } // violated
          } else if (aunt.isBlack) { // black aunt
            if (parent.getDownRight() != null && parent.getDownRight().equals(child)) { // black
                                                                                        // aunt line
              rotate(parent, grand);
              parent.isBlack = true;
              grand.isBlack = false;
            } else { // black aunt zag
              rotate(child, parent);
              enforceRBTreePropertiesAfterInsert(parent); // call again since the property is being
                                                          // violated
            }
          } else { // red aunt
            parent.isBlack = true;
            aunt.isBlack = true;
            grand.isBlack = false;
            if (grand.getUp() != null && !grand.getUp().isBlack) { // if the property is violated
                                                                   // again need to call recursively
              enforceRBTreePropertiesAfterInsert(grand);
            }
          }
        }
      } else { // just need to change the parent to black since it is the root
        parent.isBlack = true;
      }

    }
  }

  @Override
  public boolean insert(T data) throws NullPointerException {
    if (data == null)
      throw new NullPointerException("Cannot insert data value null into the tree.");
    RBTNode<T> newNode = new RBTNode<>(data);
    boolean didInsert = insertHelper(newNode);
    enforceRBTreePropertiesAfterInsert(newNode);
    // continues going to the top of the tree until the root is reached and sets it to black
    RBTNode<T> root = (RBTNode<T>) this.root;
    root.isBlack = true;
    return didInsert;
  }


  /**
   * Tester that checks that the root of the tree is black and everything is as expected
   * 
   * @return true is check passes, false if it fails
   */
  @Test
  public void tester1() {
    RedBlackTree<String> t1 = new RedBlackTree<>();

    t1.insert("d");
    t1.insert("b");
    t1.insert("c");
    t1.insert("a");


    // checks if the root node, which is b in this case, is black
    RBTNode<String> node = (RBTNode<String>) t1.findNode("d");
    boolean isBlack = node.isBlack;
    Assertions.assertTrue(isBlack);
    //System.out.println("test1 " + isBlack);
  }

  /**
   * Checks that b and c turn black and d red due to red aunt property violations
   * 
   * @return true is check passes, false if it fails
   */
  @Test
  public void tester2() {
    RedBlackTree<String> t1 = new RedBlackTree<>();
    t1.insert("b");
    t1.insert("a");
    t1.insert("c");
    t1.insert("d");
    // checks all colors and the level order
    RBTNode<String> node1 = (RedBlackTree.RBTNode<String>) t1.findNode("a");
    RBTNode<String> node2 = (RedBlackTree.RBTNode<String>) t1.findNode("c");
    RBTNode<String> node3 = (RedBlackTree.RBTNode<String>) t1.findNode("d");
    RBTNode<String> node4 = (RedBlackTree.RBTNode<String>) t1.findNode("b");
    boolean colors = node1.isBlack && node2.isBlack && !node3.isBlack && node4.isBlack;
    boolean order = t1.toLevelOrderString().equals("[ b, a, c, d ]");
    Assertions.assertTrue(colors && order);
    //System.out.println("test2 " + (colors && order));
  }

  /**
   * Checks the null aunt property for both zag and line
   * 
   * @return true is check passes, false if it fails
   */
  @Test
  public void tester3() {
    RedBlackTree<String> t1 = new RedBlackTree<>();
    t1.insert("c");
    t1.insert("a");
    t1.insert("b");

    // checks all colors and the level order
    RBTNode<String> node2 = (RBTNode<String>) t1.findNode("b");
    RBTNode<String> node3 = (RBTNode<String>) t1.findNode("a");
    RBTNode<String> node4 = (RBTNode<String>) t1.findNode("c");
    boolean order = t1.toLevelOrderString().equals("[ b, a, c ]");
    boolean colors = node2.isBlack && !node3.isBlack && !node4.isBlack;
    Assertions.assertTrue(colors && order);
    //System.out.println("test3 " + (colors && order));
  }

  /**
   * Tester that checks the black line and zag aunt property
   * 
   * @return true is check passes, false if it fails
   */
  @Test
  public void tester4() {
    RedBlackTree<String> t1 = new RedBlackTree<>();

    t1.insert("d");
    t1.insert("a");
    t1.insert("e");
    // sets "e" to black to let the black line aunt to work
    RBTNode<String> node1 = (RBTNode<String>) t1.findNode("e");
    node1.isBlack = true;
    t1.insert("b");


    // checks all colors and the level order
    RBTNode<String> node2 = (RBTNode<String>) t1.findNode("b");
    RBTNode<String> node3 = (RBTNode<String>) t1.findNode("a");
    RBTNode<String> node4 = (RBTNode<String>) t1.findNode("d");
    RBTNode<String> node5 = (RBTNode<String>) t1.findNode("e");
    boolean order = t1.toLevelOrderString().equals("[ b, a, d, e ]");
    boolean colors = node2.isBlack && !node3.isBlack && !node4.isBlack && node5.isBlack;
   Assertions.assertTrue(colors && order);
    //System.out.println("test4 " + (colors && order));
  }

  /**
   * Tester that checks the red aunt property with a recursion called
   * 
   * @return true is check passes, false if it fails
   */
  @Test
  public void tester5() {
    RedBlackTree<String> t1 = new RedBlackTree<>();

    t1.insert("j");
    t1.insert("h");
    t1.insert("k");
    t1.insert("e");
    RBTNode<String> node1 = (RBTNode<String>) t1.findNode("h");
    node1.isBlack = false;
    RBTNode<String> node = (RBTNode<String>) t1.findNode("e");
    node.isBlack = true;
    // set e to red since that will force a check of recursion when doing the red aunt property
    t1.insert("d");
    t1.insert("f");
    t1.insert("a");

    // checks all colors and the level order
    RBTNode<String> node2 = (RBTNode<String>) t1.findNode("h");
    RBTNode<String> node3 = (RBTNode<String>) t1.findNode("e");
    RBTNode<String> node4 = (RBTNode<String>) t1.findNode("j");
    RBTNode<String> node5 = (RBTNode<String>) t1.findNode("d");
    RBTNode<String> node6 = (RBTNode<String>) t1.findNode("f");
    RBTNode<String> node7 = (RBTNode<String>) t1.findNode("k");
    RBTNode<String> node8 = (RBTNode<String>) t1.findNode("a");
    boolean order = t1.toLevelOrderString().equals("[ h, e, j, d, f, k, a ]");
    boolean colors = node2.isBlack && !node3.isBlack && !node4.isBlack && node5.isBlack
        && node6.isBlack && node7.isBlack && !node8.isBlack;
    Assertions.assertTrue(colors && order);
    // System.out.println("test5 " + (colors && order));
  }

}
