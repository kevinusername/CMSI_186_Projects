import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HighRoller {

    /**
     * Prints out all the options for what user can inputo to access each function of the program
     */
    public static void optionsMenu() {
        System.out.println("1. ROLL ALL THE DICE");
        System.out.println("2. ROLL A SINGLE DIE");
        System.out.println("3. CALCULATE THE SCORE FOR THIS SET");
        System.out.println("4. SAVE THIS SCORE AS HIGH SCORE");
        System.out.println("5. DISPLAY THE HIGH SCORE");
        System.out.println("6. ENTER 'Q' TO QUIT \n");
    }

    /**
     * The actual program that reads user's input, does appropriate functions, and returns the expected results
     * Does not except input from "args", instead uses StringBuffer each instance
     */
    public static void main(String[] args) {
        // Displays the options menu
        // Initializes input, DiceSet, and highScore
        optionsMenu();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        DiceSet ds = new DiceSet(5, 6);
        int highScore = 0;

        // Loop that keeps program running until user chooses to exit
        while (true) {
            // Displays value of all Dice in the DiceSet
            System.out.println("Current Dice Set:  " + ds.toString());
            // Indicates input and clears/initializes inputLine value
            System.out.print(">>");
            String inputLine = null;

            try {
                // Tells user to enter text if input left blank
                inputLine = input.readLine();
                System.out.print("\n");
                if (0 == inputLine.length()) {
                    System.out.println("enter some text!:");
                    continue;
                }

                if (1 < inputLine.length()) {
                    System.out.println("Please enter only a single character \n");
                    continue;
                }

                // Different functions based on what user inputs
                switch (inputLine.charAt(0)) {
                case '1':
                    // Re-rolls all die
                    ds.roll();
                    System.out.println("Dice have been re-rolled \n");
                    break;
                case '2':
                    // Re-rolls a single die as indicated by the user
                    System.out.println("Which die do you wish to roll?");
                    System.out.print(">>");
                    inputLine = input.readLine();
                    ds.rollIndividual(Integer.parseInt(inputLine));
                    break;
                case '3':
                    // Returns sum of current DiceSet
                    System.out.println("Score for this set:  " + ds.sum() + "\n");
                    break;
                case '4':
                    // Sets the highScore and informs the user
                    highScore = ds.sum();
                    System.out.println("High score set to " + ds.sum() + "\n");
                    break;
                case '5':
                    // Displays current highScore
                    System.out.println("Current High Score: " + highScore + "\n");
                    break;
                case 'Q':
                    // Exits program
                    System.out.println("Exiting...");
                    System.exit(0);
                case 'q':
                    // Exits program
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    // If none of the above cases are met, prompts user to try again
                    System.out.println("Invalid input, please try again \n");
                    continue;
                }
            } catch (IOException ioe) {
                System.out.println("Caught IOException");
            }
        }
    }
}