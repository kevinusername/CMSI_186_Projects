
/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  Clock.java
 *  Purpose       :  Provides a class defining methods for the ClockSolver class
 *  @author       :  B.J. Johnson
 *  Date written  :  2017-02-28
 *  @author       :  Kevin Peters
 *  Date written   :  2018-03-01
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
 *  @version 2.0.0  2018-03-01  Kevin Peters  Initial working version
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

public class Clock {
    /**
     *  Class field definintions go here
     */
    private static final double MAXIMUM_DEGREE_VALUE = 360.0;

    private double totalSeconds = 0;
    private double timeSlice = 0;
    public double targetAngle = 0;

    /**
     *  Creates a new clock with given inputs
     *  Excepts one or two inputs, gives error if any other value
     *  Checks validity of input angle and timeSlice and sets values if tests pass
     */
    public Clock(String[] args) {

        if (1 == args.length) {
            try {
                targetAngle = validateAngleArg(args[0]);
                timeSlice = 60;
            } catch (NumberFormatException e) {
                System.out.println("\nError!\nAngle must be between 0.0 and 360.0 degrees\n");
                System.exit(2);
            }
        } else if (2 == args.length) {
            try {
                targetAngle = validateAngleArg(args[0]);
                timeSlice = validateTimeSliceArg(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter exactly two inputs\nFormat: ClockSolver [angle] [timeSlice]\n");
                System.out.println(
                        "\nError!\nAngle must be between 0.0 and 360.0 degrees\ntimeSlice must a positive real under 1800.0\n");
                System.exit(3);
            }
        } else {
            System.out.println("\nPlease enter exactly two inputs\nFormat: ClockSolver [angle] [timeSlice]\n");
            System.exit(1);
        }
    }

    /**
     *  Methods go here
     *
     *  Method to calculate the next tick from the time increment
     *  @return double-precision value of the current clock tick
     */
    public double tick() {
        totalSeconds += timeSlice;
        return totalSeconds;
    }

    /**
     *  Method to validate the angle argument
     *  @param   argValue  String from the main programs args[0] input
     *  @return  double-precision value of the argument
     *  @throws  NumberFormatException
     */
    public double validateAngleArg(String argValue) throws NumberFormatException {
        if (Double.parseDouble(argValue) <= MAXIMUM_DEGREE_VALUE && Double.parseDouble(argValue) >= 0) {
            return Double.parseDouble(argValue);
        } else {
            throw new NumberFormatException();
        }
    }

    /**
     *  Method to validate the optional time slice argument
     *  @param  argValue  String from the main programs args[1] input
     *  @return double-precision value of the argument or -1.0 if invalid
     *  note: if the main program determines there IS no optional argument supplied,
     *         I have elected to have it substitute the string "60.0" and call this
     *         method anyhow.  That makes the main program code more uniform, but
     *         this is a DESIGN DECISION, not a requirement!
     *  note: remember that the time slice, if it is small will cause the simulation
     *         to take a VERY LONG TIME to complete!
     */
    public double validateTimeSliceArg(String argValue) throws NumberFormatException {
        if (Double.parseDouble(argValue) >= 0 && Double.parseDouble(argValue) <= 1800) {
            timeSlice = Double.parseDouble(argValue);
            return Double.parseDouble(argValue);
        } else {
            throw new NumberFormatException();
        }
    }

    /**
     *  Method to calculate and return the current position of the hour hand
     *  @return double-precision value of the hour hand location
     */
    public double getHourHandAngle() {
        return totalSeconds / 60 / 60 * 30;
    }

    /**
     *  Method to calculate and return the current position of the minute hand
     *  @return double-precision value of the minute hand location
     */
    public double getMinuteHandAngle() {
        return ((totalSeconds / 60) % 60) * 6;
    }

    /**
     *  Method to calculate and return the angle between the hands
     *  @return double-precision value of the angle between the two hands
     */
    public double getHandAngle() {
        return Math.abs(getHourHandAngle() - getMinuteHandAngle());
    }

    /**
     *  Method to fetch the total number of seconds
     *   we can use this to tell when 12 hours have elapsed
     *  @return double-precision value the total seconds private variable
     */
    public double getTotalSeconds() {
        return totalSeconds;
    }

    /**
     *  Method to return a String representation of this clock
     *  @return String value of the current clock
     */
    public String toString() {
        StringBuilder timeString = new StringBuilder();
        timeString.append(Math.floor(totalSeconds / 60 / 60) + " Hours ");
        timeString.append(Math.floor((totalSeconds / 60) % 60) + " Minutes ");
        timeString.append(Math.round(totalSeconds % 60) + " Seconds");
        return timeString.toString();
    }

    /**
     *  The main program starts here
     *  remember the constraints from the project description
     *  @see  http://bjohnson.lmu.build/cmsi186web/homework04.html
     *  be sure to make LOTS of tests!!
     *  remember you are trying to BREAK your code, not just prove it works!
     */
    public static void main(String[] args) {

        System.out.println("\nCLOCK CLASS TESTER PROGRAM\n" + "--------------------------\n");
        System.out.println("  Creating a new clock: ");
        Clock clock = new Clock(args);
        System.out.println(clock.targetAngle + " " + clock.timeSlice);
        System.out.println("    New clock created: " + clock.toString());
        System.out.println("    Testing validateAngleArg()....");
        System.out.print("      sending '  0 degrees', expecting double value   0.0");
        try {
            System.out.println((0.0 == clock.validateAngleArg("0.0")) ? " - got 0.0" : " - no joy");
        } catch (Exception e) {
            System.out.println(" - Exception thrown: " + e.toString());
        }
    }
}
