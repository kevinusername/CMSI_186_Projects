/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  ClockSolver.java
 *  Purpose       :  The main program for the ClockSolver class
 *  @see
 *  @author       :  B.J. Johnson
 *  Date written  :  2017-02-28
 *  Description   :  This class provides a bunch of methods which may be useful for the ClockSolver class
 *                   for Homework 4, part 1.  Includes the following:
 *
 *  Notes         :  None right now.  I'll add some as they occur.
 *  Warnings      :  None
 *  Exceptions    :  IllegalArgumentException when the input arguments are "hinky"
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-02-28  B.J. Johnson  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

public class ClockSolver {

    private final static double EPSILON_VALUE = 0.1; // small value for double-precision comparisons

    /**
     *  Constructor
     *  This just calls the superclass constructor, which is "Object"
     */
    public ClockSolver() {
        super();
    }

    /**
     *  The main program starts here
     *  remember the constraints from the project description
     *  @see  http://bjohnson.lmu.build/cmsi186web/homework04.html
     *  @param  args  String array of the arguments from the command line
     *                args[0] is the angle for which we are looking
     *                args[1] is the time slice; this is optional and defaults to 60 seconds
     */
    public static void main(String[] args) {
        Clock clock = new Clock(args);

        while (clock.getTotalSeconds() < 43200) {
            // Checks if current hand angle, or its compliment, is equal to targetAngle +- EPSILON VALUE
            // If conditutions are met, prints out String representation of current Clock time
            if (clock.getHandAngle() >= (clock.targetAngle - EPSILON_VALUE)
                    && clock.getHandAngle() <= (clock.targetAngle + EPSILON_VALUE)) {
                System.out.println(clock.toString());
            } else if ((360 - clock.getHandAngle()) >= (clock.targetAngle - EPSILON_VALUE)
                    && (360 - clock.getHandAngle()) <= (clock.targetAngle + EPSILON_VALUE)) {
                System.out.println(clock.toString());
            }
            clock.tick(); // Progresses clock to next tick
        }
        System.exit(0);
    }
}