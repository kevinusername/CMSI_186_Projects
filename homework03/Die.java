/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  Die.java
 *  Purpose       :  Provides a class describing a single die that can be rolled
 *  @author       :  B.J. Johnson
 *  Date          :  2017-02-06
 *  @author       :  Kevin Peters
 * Date           :  2018-02-22
 *  Description   :  This class provides the data fields and methods to describe a single game die.  A
 *                   die can have "N" sides.  Sides are randomly assigned sequential pip values, from 1
 *                   to N, with no repeating numbers.  A "normal" die would thus has six sides, with the
 *                   pip values [spots] ranging in value from one to six.  Includes the following:
 *                   public Die( int nSides );                  // Constructor for a single die with "N" sides
 *                   public int roll();                         // Roll the die and return the result
 *                   public int getValue()                      // get the value of this die
 *                   public void setSides()                     // change the configuration and return the new number of sides
 *                   public String toString()                   // Instance method that returns a String representation
 *                   public static String toString()            // Class-wide method that returns a String representation
 *                   public static void main( String args[] );  // main for testing porpoises
 *
 *  Notes         :  Restrictions: no such thing as a "two-sided die" which would be a coin, actually.
 *                   Also, no such thing as a "three-sided die" which is a physical impossibility without
 *                   having it be a hollow triangular prism shape, presenting an argument as to whether
 *                   the inner faces are faces which then should be numbered.  Just start at four for
 *                   minimum number of faces.  However, be aware that a four-sided die dosn't have a top
 *                   face to provide a value, since it's a tetrahedron [pyramid] so you'll have to figure
 *                   out a way to get the value, since it won't end up on its point.
 *
 *  Warnings      :  None
 *  Exceptions    :  IllegalArgumentException when the number of sides or pips is out of range
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision History
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-02-06  B.J. Johnson  Initial writing and release
 *  @version 1.1.0  2017-02-17  B.J. Johnson  Filled in method code
 *  @version 2.0    2018-02-21  Kevin Peters  Final version for grading
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
public class Die {

    /**
     * private instance data
     */
    private int sides;
    private int pips;
    // I'm going with 4 as the minimum, with the dice being a pyramid and having to check the bottom side as the value
    // This is definitely not an ideal way to roll a die, but it works I suppose?
    private final int MINIMUM_SIDES = 4;

    // public constructor:
    /**
     * constructor
     * @param nSides int value containing the number of sides to build on THIS Die
     * @throws       IllegalArgumentException
     * Note: parameter must be checked for validity; invalid value must throw "IllegalArgumentException"
     */
    public Die(int nSides) {
        if (nSides >= MINIMUM_SIDES) {
            sides = nSides;
        } else {
            System.out.println("Please enter an integer greater than 3");
            System.exit(0);
        }
        roll();
    }

    /**
     * Roll THIS die and return the result
     * @return  integer value of the result of the roll, randomly selected
     */
    public int roll() {
        pips = (int) Math.ceil(Math.random() * sides);
        return pips;
    }

    /**
     * Get the value of THIS die to return to the caller; note that the way
     *  the count is determined is left as a design decision to the programmer
     *  For example, what about a four-sided die - which face is considered its
     *  "value"?
     * @return the pip count of THIS die instance
     */
    public int getValue() {
        return pips;
    }

    /**
     * @param  int  the number of sides to set/reset for this Die instance
     * @return      The new number of sides, in case anyone is looking
     * @throws      IllegalArgumentException
     */
    public void setSides(int newSides) {
        if (newSides >= MINIMUM_SIDES) {
            sides = newSides;
            roll();
        } else {
            System.out.println("Invalid number of sides\nPlease input a number greater than 3");
        }
    }

    /**
     * Public Instance method that returns a String representation of THIS die instance
     * @return String representation of this Die
     */
    public String toString() {
        StringBuilder report = new StringBuilder();
        report.append("Sides: ");
        report.append(Integer.toString(sides) + "\n");
        report.append("Pips: ");
        report.append(Integer.toString(pips) + "\n");
        return report.toString();
    }

    /** 
     * Class-wide method that returns a String representation of THIS die instance
     * @return String representation of this Die
     */
    public static String toString(Die d) {
        return d.toString();
    }

    /**
     * A little test main to check things out
     */
    public static void main(String[] args) {
        // Creates a new dice "d"
        Die d = new Die(5);

        // Tests valid and invalid inputs for setSides as well as the getValue() function
        System.out.println("Starting Die value: " + d.getValue());
        d.setSides(-10);
        System.out.println("Current Die value (Should remain 5): " + d.getValue());
        d.setSides(1);
        System.out.println("Current Die value (Should remain 5): " + d.getValue());
        d.setSides(6);
        System.out.println("Current Die value (Should change to 6): " + d.getValue());

        // Checks the toString() function as well as the roll() function
        System.out.println("\nChecking .toString() function:\n" + d.toString());
        System.out.println("Re-rolling d...");
        d.roll();
        System.out.println("Displaying new properties:\n" + d.toString());

        // Checks Dice properties with a few other side configurations
        System.out.println("Setting die sides to 10...");
        d.setSides(10);
        System.out.println(d.toString());
        System.out.println("Setting die sides to 20...");
        d.setSides(20);
        System.out.println(d.toString());
        System.out.println("Setting die sides to 1000...");
        d.setSides(1000);
        System.out.println(d.toString());
        System.out.println("Setting die sides to 11230...");
        d.setSides(11230);
        System.out.println(d.toString());
        System.out.println("Setting die sides to 123...");
        d.setSides(123);
        System.out.println(d.toString());
    }

}