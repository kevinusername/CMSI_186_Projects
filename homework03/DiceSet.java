
/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  DiceSet.java
 *  Purpose       :  Provides a class describing a set of dice
 *  Author        :  B.J. Johnson
 *  Date          :  2017-02-09
 *  Author        :  Kevin Peters
 *  Date          :  2018-02-06
 *  Description   :  This class provides everything needed (pretty much) to describe a set of dice.  The
 *                   idea here is to have an implementing class that uses the Die.java class.  Includes
 *                   the following:
 *                   public DiceSet( int k, int n );                  // Constructor for a set of k dice each with n-sides
 *                   public int sum();                                // Returns the present sum of this set of dice
 *                   public void roll();                              // Randomly rolls all of the dice in this set
 *                   public void rollIndividual( int i );             // Randomly rolls only the ith die in this set
 *                   public int getIndividual( int i );               // Gets the value of the ith die in this set
 *                   public String toString();                        // Returns a stringy representation of this set of dice
 *                   public static String toString( DiceSet ds );     // Classwide version of the preceding instance method
 *                   public boolean isIdentical( DiceSet ds );        // Returns true iff this set is identical to the set ds
 *                   public static void main( String[] args );        // The built-in test program for this class
 *
 *  Notes         :  Stolen from Dr. Dorin pretty much verbatim, then modified to show some interesting
 *                   things about Java, and to add this header block and some JavaDoc comments.
 *  Warnings      :  None
 *  Exceptions    :  IllegalArgumentException when the number of sides or pips is out of range
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-02-09  B.J. Johnson  Initial writing and release
 *  @version 2.0.0  2018-02-09  Kevin Peters  Initial working code for all methods
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

import java.util.Arrays;

public class DiceSet {

    /**
     * private instance data
     */
    private int count;
    private int sides;
    private Die[] ds;

    // public constructor:
    /**
     * constructor
     * @param  count int value containing total dice count
     * @param  sides int value containing the number of pips on each die
     * @throws IllegalArgumentException if one or both arguments don't make sense
     * @note   parameters are checked for validity; invalid values throw "IllegalArgumentException"
     */
    public DiceSet(int newCount, int newSides) {
        // Checks that input "count" and "sides" are within the valid range
        // Creates a DiceSet "ds" of size "count"
        // Loops to initialize each Die in the DiceSet via Die() constructor

        if (newCount > 0) {
            count = newCount;
        } else {
            System.out.println("Please enter an integer > 0");
        }

        if (newSides > 3) {
            sides = newSides;
        } else {
            System.out.println("Please enter an integer > 3");
        }

        ds = new Die[count];
        for (int i = 0; i < count; i++) {
            ds[i] = new Die(sides);
        }
    }

    /**
     * @return the sum of all the dice values in the set
     */
    public int sum() {
        // A simple loop that adds the value of each die to the total
        int runningTotal = 0;
        for (int i = 0; i < count; i++) {
            runningTotal += ds[i].getValue();
        }
        return runningTotal;
    }

    /**
     * Randomly rolls all of the dice in this set
     *  NOTE: you will need to use one of the "toString()" methods to obtain
     *  the values of the dice in the set
     */
    // Goes through and rolls each die
    public void roll() {
        for (int i = 0; i < count; i++) {
            ds[i].roll();
        }
    }

    /**
     * Randomly rolls a single die of the dice in this set indexed by 'dieIndex'
     * @param  dieIndex int of which die to roll
     * @return the integer value of the newly rolled die
     * @trhows IllegalArgumentException if the index is out of range
     */
    public int rollIndividual(int dieIndex) {
        return ds[dieIndex].roll();
    }

    /**
     * Gets the value of the die in this set indexed by 'dieIndex'
     * @param  dieIndex int of which die to roll
     * @trhows IllegalArgumentException if the index is out of range
     */
    public int getIndividual(int dieIndex) {
        return ds[dieIndex].getValue();
    }

    /**
     * @return Public Instance method that returns a String representation of the DiceSet instance
     */
    public String toString() {
        // Creates a StringBuilder and appends the current value of each die. Removes extra ", " then returns String
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(Integer.toString(ds[i].getValue()));
            result.append(", ");
        }
        result.delete(result.length() - 2, result.length() - 1);
        return result.toString();
    }

    /**
     * @return Class-wide version of the preceding instance method
     */
    public static String toString(DiceSet ds) {
        return ds.toString();
    }

    /**
     * @return  true iff this set is identical to the set passed as an argument
     */
    public boolean isIdentical(DiceSet test_ds) {
        // Takes each die is both DiceSets and makes the value of their pips into an int[]
        // If amount of Die is not the same between DiceSets, immediatly returns false
        if (count != test_ds.count) {
            return false;
        }
        int[] dsArray = new int[count];
        for (int i = 0; i < count; i++) {
            dsArray[i] = ds[i].getValue();
        }
        int[] test_dsArray = new int[count];
        for (int i = 0; i < count; i++) {
            test_dsArray[i] = test_ds.ds[i].getValue();
        }

        Arrays.sort(dsArray);
        Arrays.sort(test_dsArray);

        // Checks each value of the sorted arrays against each other, since comparing the two int[] does not seem to work
        boolean areSame = true;
        for (int i = 0; i < count; i++) {
            if (dsArray[i] != test_dsArray[i]) {
                areSame = false;
                break;
            }
        }
        return areSame;
    }

    /**
     * A little test main to check things out
     */
    public static void main(String[] args) {
        DiceSet mySet = new DiceSet(5, 5);
        DiceSet copySet = mySet;
        DiceSet set2 = new DiceSet(5, 5);

        System.out.println(mySet.toString());
        System.out.println(copySet.toString());

        System.out.println(mySet.isIdentical(copySet));
        System.out.println(mySet.isIdentical(set2));
    }
}