import java.util.List;
import java.util.Scanner;

public class Frontend implements FrontendInterface {

  private BackendInterface backend;
  private String min = "min";
  private String max = "max";
  private String energy = "none";
  private boolean readFile = false;
  Scanner in = new Scanner(System.in);

  public Frontend(Scanner in, BackendInterface back) {
    backend = back;
    this.in = in;
  }

  /**
   * Repeated gives the user an opportunity to issue new commands until they select Q to quit.
   */
  @Override
  public void runCommandLoop() {

    displayMainMenu();

    String reading = in.nextLine();
    // loop stops recurring once Q is pressed
    if (!reading.equals("Q")) {
      if (reading.equals("R")) {
        readFile();
      } else if (reading.equals("G")) {
        getValues();
      } else if (reading.equals("F")) {
        setFilter();
      } else if (reading.equals("D")) {
        topFive();
      } else {
        // chose a command that is not one of the five allowed commands
        System.out.println("This is not a valid command");
      }
      runCommandLoop();
    }
    in.close();
  }

  /**
   * Displays the menu of command options to the user.
   */
  @Override
  public void displayMainMenu() {
    // copied straight from FrontendPlaceholder
    String menu = """

        ~~~ Command Menu ~~~
            [R]ead Data
            [G]et Songs by Danceability [min - max]
            [F]ilter New Songs (by Min Energy: none)
            [D]isplay Five Fastest
            [Q]uit
        Choose command:""";
    menu = menu.replace("min", min).replace("max", max).replace("none", energy);
    System.out.print(menu + " ");

  }

  /**
   * Provides text-based user interface and error handling for the [R]ead Data command.
   */
  @Override
  public void readFile() {
    System.out.print("Enter path to csv file to load: ");
    String reading = in.nextLine();
    // if the file is not a .csv, an exception is caught
    if (!reading.contains(".csv")) {
      System.out.println("This is not a .csv file");
    }
    else {
    try {
      backend.readData(reading);
      System.out.println("Done reading file.");
      readFile = true;
    } catch (Exception e) {
      System.out.println("This is not a .csv file");
    }
    }
  }

  /**
   * Provides text-based user interface and error handling for the [G]et Songs by Danceability
   * command.
   */
  @Override
  public void getValues() {
    int maximum, minimum;
    System.out.print("Enter range of values (MIN - MAX): ");
    String reading = in.nextLine();
    if (!reading.contains(" ")) { // checks if the range does not give spaces
      System.out.println("Your input needs spaces");
    } else {
      if (!reading.contains("-")) { // range is non-existent
        System.out.println("This is not a range");
      } else {
        // set max and min but first check a file exists
        if (!readFile) {
          System.out.println("There exists no file");
        } else
          try {
            int i = reading.indexOf("-");
            minimum = Integer.parseInt(reading.substring(0, i - 1));
            maximum = Integer.parseInt(reading.substring(i + 2));
            // print out all the results
            List<String> result = backend.getRange(minimum, maximum);
            min = reading.substring(0, i - 1);
            max = reading.substring(i + 2);
            System.out.println(
                "\n" + result.size() + " songs found between " + minimum + " - " + maximum + ":");
            for (int j = 0; j < result.size(); j++) {
              System.out.println(result.get(j));
            }
          } catch (Exception e) {
            // range is not a valid range (e.g. min is greater than max)
            System.out.println("You did not enter a valid range");
          }
      }
    }
  }


  /**
   * Provides text-based user interface and error handling for the [F]ilter Energetic Songs (by Min
   * Energy) command.
   */
  @Override
  public void setFilter() {
    if (!readFile) {
      System.out.println("There exists no file");
    } else {
      // need to check that a min and max exist
      if (min.equals("min") || max.equals("max")) {
        System.out.println("You need a range");
      } else {
        System.out.print("Enter minimum energy: ");
        String reading = in.nextLine();
        int newEnergy = 0;
        try {
          // checks if energy is indeed an integer and throws and exception otherwise
          newEnergy = Integer.parseInt(reading);
          // print everything out
          List<String> result = backend.filterEnergeticSongs(newEnergy);
          energy = reading;
          System.out.println(result.size() + " songs found between " + min + " - " + max
              + " with energy >= " + energy + ":");
          for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
          }
          // sets global variable energy to the input
          
        } catch (Exception e) {
          System.out.println("This input is not an integer");
        }
      }
    }
  }

  /**
   * Provides text-based user interface and error handling for the [D]isplay Five Fastest command.
   */
  @Override
  public void topFive() {
    if (!readFile) {
      System.out.println("There exists no file");
    } else {
      // need to check min, max and energy have been set
      if (min.equals("min") || max.equals("max")) {
        System.out.println("You need a range");
      } else {
        List<String> result = backend.fiveFastest();
        // need to check if energy exists so that printing energy doesn't give an error
        if (!energy.equals("none")) {
          System.out.println("\nTop Five songs found between " + min + " - " + max
              + " with energy >= " + energy + ":");
        } else {
          System.out.println("\nTop Five songs found between " + min + " - " + max);
        }
        for (int i = 0; i < result.size(); i++)
          System.out.println(result.get(i));
      }
    }
  }
}
