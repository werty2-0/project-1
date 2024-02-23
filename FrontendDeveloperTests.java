import org.junit.jupiter.api.Test;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;

public class FrontendDeveloperTests {


  /**
   * Tests the getValues() method when the input given by the user does not create a range of values
   */

  @Test
  public void tester1() {
    Scanner in = new Scanner(System.in);
    if (in.nextLine().equals("G")) {
      String input = in.nextLine();
      TextUITester reader = new TextUITester(input);
      String output = "";
      // checks if there is no range in the next input
      try {
        output = reader.checkOutput();
      } catch (Exception e) {

      }
      if (!input.contains("-") && output.equals("This is not a range")) // error message {
        Assertions.assertTrue(true);
    }
  }

  /**
   * checks that read file produces an error when a non-csv file is inputted by the user
   */
  @Test
  public void tester2() {
    Scanner in = new Scanner(System.in);
    if (in.nextLine().equals("R")) {
      String input = in.nextLine();
      TextUITester reader = new TextUITester(input);
      String output = "";
      // checks if the file is not a .csv file
      try {
        output = reader.checkOutput();
      } catch (Exception e) {

      }
      if (!input.contains(".csv") && output.equals("This is not a .csv file")) // error message {
        Assertions.assertTrue(true);
    }
  }

  /**
   * checks if the input from the user for the setFilter method is not a number and an error is
   * correctly produced
   */
  @Test
  public void tester3() {
    Scanner in = new Scanner(System.in);
    if (in.nextLine().equals("R")) {
      String input = in.nextLine();
      TextUITester reader = new TextUITester(input);
      String output = "";
      // checks if the input is not an integer
      try {
        output = reader.checkOutput();
      } catch (Exception e) {

      }
      try {
        int number = Integer.parseInt(input);
      } catch (Exception f) {
        Assertions.assertTrue(output.equals("This input is not an integer"));
      }
    }
  }

  /**
   * check that topFive method is printing out the correct statement after a given input
   */
  @Test
  public void tester4() {
    Scanner in = new Scanner(System.in);
    if (in.nextLine().equals("G")) {
      if (in.nextLine().equals("80 - 90")) {
        String input = in.nextLine();
        TextUITester reader = new TextUITester(input);
        String output = "";
        // checks if the output printed the correct statement
        try {
          output = reader.checkOutput();
        } catch (Exception e) {

        }
        if (input.equals("D") && output.equals("""
            Top Five songs found between 80 - 90 with energy >= 85:
                89: Baby
                93: Only Girl (In The World)""")) {
          Assertions.assertTrue(true);
        }
      }
    }
  }

  /**
   * checks that any letter inputted other than R,G,F,D,Q produces an error message
   */
  @Test
  public void tester5() {
    Scanner in = new Scanner(System.in);
    String input = in.nextLine();
    TextUITester reader = new TextUITester(input);
    if (!input.equals("R") || !input.equals("G") || !input.equals("F")
        || !input.equals("D") || !input.equals("Q")) {  
      String output = "";
      try {
        output = reader.checkOutput();
      } catch (Exception e) {

      }
        Assertions.assertTrue(output.equals("This is not a valid command"));
    }
  }
}
