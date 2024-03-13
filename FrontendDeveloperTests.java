import org.junit.jupiter.api.Test;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;

public class FrontendDeveloperTests {


  /**
   * Tests the getValues() method when the input given by the user does not create a range of values
   */

  @Test
  public void tester1() {
    String input = "80   90";
    TextUITester reader = new TextUITester(input);
    Scanner in = new Scanner(System.in);
    FrontendInterface frontend = new Frontend(in, new BackendPlaceholder(null));

    frontend.getValues();
    String output = reader.checkOutput();
    // catches the absence of a dash in the input

    in.close();
    if (!output.contains("This is not a range")) {
      Assertions.assertFalse(true);
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
    if (!output.trim().contains("Enter path to csv file to load: This is not a .csv file"))
      Assertions.assertFalse(true);
  }


  /**
   * checks if the input from the user for the setFilter method does not have a .csv file set
   * beforehand
   */
  @Test
  public void tester3() {
    String input = "42";
    TextUITester reader = new TextUITester(input);
    Scanner in = new Scanner(System.in);
    FrontendInterface frontend = new Frontend(in, new BackendPlaceholder(null));

    frontend.setFilter();
    // confirms that output is valid but that there does not exist a csv file
    String output = reader.checkOutput();
    in.close();
    if (!output.contains("There exists no file")) {
      Assertions.assertFalse(true);
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
    if (!output.contains("Your input needs spaces")) {
      Assertions.assertFalse(true);
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
    if (!output.equals(menu + " ")) {
      Assertions.assertFalse(true);
    }
  }

}
