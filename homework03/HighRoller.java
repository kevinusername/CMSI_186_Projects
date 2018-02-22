/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  HighRoller.java
 *  Purpose       :  Use a textual user interface to simulate rolling an input set of die and perform
 *                   several actions on it
 *  @author       :  Kevin Peters
 * Date           :  2018-02-22
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision History
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0    2018-02-21  Kevin Peters  Final version for grading
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
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
        System.out.println("\n   ENTER 'Q' TO QUIT \n");
    }

    /**
     * The actual program that reads user's input, does appropriate functions, and returns the expected results
     * Does not except input from "args", instead uses StringBuffer each instance
     */
    public static void main(String[] args) {

        DiceSet ds = null;

        // Creates DiceSet with given arguements in the form <number of dice> <number of sides>
        // Catches invalid outputs and prompts user to try again
        try {
            ds = new DiceSet(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        } catch (NumberFormatException nf) {
            System.out
                    .println("Invalid arguements, please enter two integers\nRun program again with valid arguements");
            System.exit(1);
        } catch (ArrayIndexOutOfBoundsException aiob) {
            System.out
                    .println("Invalid arguements, please enter two integers\nRun program again with valid arguements");
            System.exit(2);
        }
        // Initialize command linen input variable and highScore
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int highScore = 0;

        // Displays the options menu
        optionsMenu();

        // Loop that keeps program running until user chooses to exit
        while (true) {
            // Displays value of all Dice in the DiceSet
            System.out.println("\nCurrent Dice Set:  " + ds.toString());
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
                    System.out.println("Which die do you wish to roll?\n**Note that the index starts at 0**");
                    System.out.print(">>");
                    try {
                        inputLine = input.readLine();
                        ds.rollIndividual(Integer.parseInt(inputLine));
                    } catch (ArrayIndexOutOfBoundsException aiob) {
                        System.out.println("Please enter an integer index within the range of the DiceSet");
                    } catch (NumberFormatException nf) {
                        System.out.println("Please enter an integer index within the range of the DiceSet");
                    }
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