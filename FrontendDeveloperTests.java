import org.junit.jupiter.api.Test;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;

public class FrontendDeveloperTests {

 
  /**
   * Tests the getValues() method when the input given by the user does not create a range of values
   */

  @Test
  public void tester1() {
    String input = "80 90";
    TextUITester reader = new TextUITester(input);
    Scanner in = new Scanner(System.in);
    FrontendInterface frontend = new Frontend(in, new BackendPlaceholder(null));

    frontend.getValues();
    // catches the absence of a dash in the input
    String output = reader.checkOutput();
    in.close();
    if (output.equals("Enter range of values (MIN - MAX): This is not a range\r\n")) {
      Assertions.assertTrue(true);
    }



  }

  /**
   * checks that read file produces an error when a non-csv file is inputed by the user
   */
  @Test
  public void tester2() {
    String input = "gunky.csg";
    TextUITester reader = new TextUITester(input);
    Scanner in = new Scanner(System.in);
    FrontendInterface frontend = new Frontend(in, new BackendPlaceholder(null));

    frontend.readFile();
    // checks that .csg is caught since it is not .csv
    String output = reader.checkOutput();
    in.close();
    if (output.equals("Enter path to csv file to load: This is not a .csv file\r\n"))
      Assertions.assertTrue(true);
  }


  /**
   * checks if the input from the user for the setFilter method is not a number and an error is
   * correctly produced
   */
  @Test
  public void tester3() {
    String input = "4D";
    TextUITester reader = new TextUITester(input);
    Scanner in = new Scanner(System.in);
    FrontendInterface frontend = new Frontend(in, new BackendPlaceholder(null));

    frontend.setFilter();
    // confirms that output is not an integer and the error is caught in Frontend
    String output = reader.checkOutput();
    in.close();
    if (output.equals("Enter minimum energy: This input is not an integer\r\n")) {
      Assertions.assertTrue(true);
    }
  }

  /**
   * Tests the getValues() method when the input given by the user is a range where there are no
   * spaces
   */
  @Test
  public void tester4() {
    String input = "90-80";
    TextUITester reader = new TextUITester(input);
    Scanner in = new Scanner(System.in);
    FrontendInterface frontend = new Frontend(in, new BackendPlaceholder(null));

    frontend.getValues();
    // checking that the tester figures out the input contains no spaces
    String output = reader.checkOutput();
    in.close();
    if (output.equals("Enter range of values (MIN - MAX): Your input needs spaces\r\n")) {
      Assertions.assertTrue(true);
    }
  }

  /**
   * checks that the displayMainMenu() works
   */
  @Test
  public void tester5() {
    String input = "A";
    TextUITester reader = new TextUITester(input);
    Scanner in = new Scanner(System.in);
    FrontendInterface frontend = new Frontend(in, new BackendPlaceholder(null));

    frontend.displayMainMenu();

    String output = reader.checkOutput();
    in.close();
    // comparing output with menu so menu needs to be created
    String menu = """

        ~~~ Command Menu ~~~
            [R]ead Data
            [G]et Songs by Danceability [min - max]
            [F]ilter New Songs (by Min Energy: none)
            [D]isplay Five Fastest
            [Q]uit
        Choose command:""";
    if (output.equals(menu + " ")) {
      Assertions.assertTrue(true);
    }
  }

  /**
   * checks that the getValues() method works for input "80 - 90"
   */
  @Test
  public void testIntegration1() {
    String input = "80 - 90";
    TextUITester reader = new TextUITester(input);
    BackendInterface backend = new BackendPlaceholder(null);
    Scanner in = new Scanner(System.in);
    FrontendInterface frontend = new Frontend(in, backend);

    frontend.getValues();

    String output = reader.checkOutput();
    in.close();
    // comparing output with menu so menu needs to be created
    if (output.equals("""
        5 songs found between 80 - 90:
        Baby
        Dynamite
        Secrets
        Empire State of Mind (Part II) Broken Down
        Only Girl (In The World)""")) {
      Assertions.assertTrue(true);
    }
  }
  
  /**
   * checks that the setFilter() method works for input "85"
   */
  @Test
  public void testIntegration2() {
    String input = "85";
    TextUITester reader = new TextUITester(input);
    BackendInterface backend = new BackendPlaceholder(null);
    Scanner in = new Scanner(System.in);
    FrontendInterface frontend = new Frontend(in, backend);

    frontend.setFilter();

    String output = reader.checkOutput();
    in.close();
    // comparing output with menu so menu needs to be created
    if (output.equals("""
        2 songs found between 80 - 90 with energy >= 85:
        Baby
        Only Girl (In The World)""")) {
      Assertions.assertTrue(true);
    }
  }


}
