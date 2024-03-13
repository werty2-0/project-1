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

  /**
   * checks that the readFile() method works properly for songs.csv
   */
  @Test
  public void testIntegration1() {
    String input = "songs.csv";
    TextUITester reader = new TextUITester(input);
    IterableSortedCollection<SongInterface> tree = new IterableRedBlackTree<>();
    Backend backend = new Backend(tree);
    Scanner in = new Scanner(System.in);
    FrontendInterface frontend = new Frontend(in, backend);

    frontend.readFile();
    String output = reader.checkOutput();
    in.close();
    // checking that the print output contains the valid statement
    if (!output.contains("Done reading file")) {
      Assertions.assertFalse(true);
    }
  }

  /**
   * checks that the setFilter() method outputs the need for a range despite already having a file
   * thanks to the backend part of the project
   */
  @Test
  public void testIntegration2() {
    String input = "songs.csv";
    TextUITester reader = new TextUITester(input);
    IterableSortedCollection<SongInterface> tree = new IterableRedBlackTree<>();
    Backend backend = new Backend(tree);
    Scanner in = new Scanner(System.in);
    FrontendInterface frontend = new Frontend(in, backend);
    // reading the file first so that a file exists when calling setFilter()
    frontend.readFile();
    
    frontend.setFilter();

    String output = reader.checkOutput();
    in.close();
    // checking that the setFilter failed despite the backend giving it a csv file

    if (!output.contains("You need a range")) {
      Assertions.assertFalse(true);
    }
  }

  /**
   * checks that the range from 80 - 81 works
   */
  @Test
  public void testPartner1() {   
    String input = "songs.csv\n80 - 81";
    TextUITester reader = new TextUITester(input);
    
    IterableSortedCollection<SongInterface> tree = new IterableRedBlackTree<>();
    Backend backend = new Backend(tree);
    Scanner in = new Scanner(input);
    FrontendInterface frontend = new Frontend(in, backend);
    // call both read file and get values
    frontend.readFile();
    frontend.getValues();
    String output = reader.checkOutput();
    in.close();
    // checking that the print output contains the valid statement
    if (!output.contains("19 songs found between 80 - 81:")) {
      Assertions.assertFalse(true);
    }
  }
  
  /**
   * checks that the topFive for 80 - 81 works
   */
  @Test
  public void testPartner2() {   
    String input = "songs.csv\n80 - 81";
    TextUITester reader = new TextUITester(input);
    
    IterableSortedCollection<SongInterface> tree = new IterableRedBlackTree<>();
    Backend backend = new Backend(tree);
    Scanner in = new Scanner(input);
    FrontendInterface frontend = new Frontend(in, backend);
    // call both read file and get values
    frontend.readFile();
    frontend.getValues();
    frontend.topFive();
    String output = reader.checkOutput();
    System.out.println(output);
    in.close();
    // checking that the print output contains one of the correct songs
    if (!output.contains("Meet Me Halfway")) {
      Assertions.assertFalse(true);
    }
  }
  

}
