/**
 *  File name     :  CalendarStuff.java
 *  Purpose       :  Provides a class with supporting methods for CountTheDays.java program
 *  Author        :  B.J. Johnson (prototype)
 *  Date          :  2017-01-02 (prototype)
 *  Author        :  Kevin Peters
 *  Date          :  2018-01-18
 *  Description   :  This file provides the supporting methods for the CountTheDays program which will
 *                   calculate the number of days between two dates.  It shows the use of modularization
 *                   when writing Java code, and how the Java compiler can "figure things out" on its
 *                   own at "compile time".  It also provides examples of proper documentation, and uses
 *                   the source file header template as specified in the "Greeter.java" template program
 *                   file for use in CMSI 186, Spring 2017.
 *  Notes         :  None
 *  Warnings      :  None
 *  Exceptions    :  None
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision History
 *  ----------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-01-02  B.J. Johnson  Initial writing and release
 *  @version 1.0.1  2017-12-25  B.J. Johnson  Updated for Spring 2018
 *  @version 1.1    2018-1-25   Kevin Peters  Version submitted for grading
 */
public class CalendarStuff {

    /**
     * An array containing the number of days in each month
     *  NOTE: this excludes leap years, so those will be handled as special cases
     *  NOTE: this is optional, but suggested
     */
    private static final int[] days = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private static final int[] leap_Days = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    /**
     * The constructor for the class
     */
    public CalendarStuff() {
        //Wasn't quite sure what to do with this section, didn't seem to have anything to do with passing the tests?
        System.out.println("Constructor called..."); // replace this with the actual code
    }

    /**
     * A method to determine if the year argument is a leap year or not<br />
     *  Leap years are every four years, except for even-hundred years, except for every 400
     * @param    year  long containing four-digit year
     * @return         boolean which is true if the parameter is a leap year
     */
    public static boolean isLeapYear(long year) {
        if ((year % 4) == 0) {
            if ((year % 100) == 0) {
                if ((year % 400) == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * A method to calculate the days in a month, including leap years
     * @param    month long containing month number, starting with "1" for "January"
     * @param    year  long containing four-digit year; required to handle Feb 29th
     * @return         long containing number of days in referenced month of the year
     * notes: remember that the month variable is used as an indix, and so must
     *         be decremented to make the appropriate index value
     */
    public static long daysInMonth(long month, long year) {
        if (isLeapYear(year)) {
            return leap_Days[(int) month - 1];
        } else {
            return days[(int) month - 1];
        }
    }

    /**
     * A method to determine if two dates are exactly equal
     * @param    month1 long    containing month number, starting with "1" for "January"
     * @param    day1   long    containing day number
     * @param    year1  long    containing four-digit year
     * @param    month2 long    containing month number, starting with "1" for "January"
     * @param    day2   long    containing day number
     * @param    year2  long    containing four-digit year
     * @return          boolean which is true if the two dates are exactly the same
     */
    public static boolean dateEquals(long month1, long day1, long year1, long month2, long day2, long year2) {
        if ((year1 == year2) && (month1 == month2) && (day1 == day2)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * A method to compare the ordering of two dates
     * @param    month1 long   containing month number, starting with "1" for "January"
     * @param    day1   long   containing day number
     * @param    year1  long   containing four-digit year
     * @param    month2 long   containing month number, starting with "1" for "January"
     * @param    day2   long   containing day number
     * @param    year2  long   containing four-digit year
     * @return          int    -1/0/+1 if first date is less than/equal to/greater than second
     */
    public static int compareDate(long month1, long day1, long year1, long month2, long day2, long year2) {
        // Finds which year is greater and returns if difference is found, if not, continues to checks months
        if (year1 > year2) {
            return 1;
        } else if (year2 > year1) {
            return -1;
        }

        // Finds which month is greater and returns if a difference is found, if not, continues to check days
        if (month1 > month2) {
            return 1;
        } else if (month2 > month1) {
            return -1;
        }

        // Checks which day is greater and returns if difference is found, if not, returns 0
        if (day1 > day2) {
            return 1;
        } else if (day2 > day1) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * A method to return whether a date is a valid date
     * @param    month long    containing month number, starting with "1" for "January"
     * @param    day   long    containing day number
     * @param    year  long    containing four-digit year
     * @return         boolean which is true if the date is valid
     * notes: remember that the month and day variables are used as indices, and so must
     *         be decremented to make the appropriate index value
     */
    public static boolean isValidDate(long month, long day, long year) {
        if ((year > 9999) || (year < -9999)) { //checks if year is within 4 digit range
            return false;
        }

        else if ((month > 12) || (month < 1)) { //Checks if month is within the 12 valid months
            return false;
        }

        else if (day < 1) { //Checks minimum value of day
            return false;
        }

        //Checks array leap_Days to ensure that days do not excede days in given month (Accounts for leapyear due to February 29)
        if (isLeapYear(year)) {
            if (day > leap_Days[(int) month - 1]) {
                return false;
            } else {
                return true;
            }
        } else {
            if (day > days[(int) month - 1]) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * A method to return a string version of the month name
     * @param    month long   containing month number, starting with "1" for "January"
     * @return         String containing the string value of the month (no spaces)
     */
    public static String toMonthString(int month) {
        switch (month - 1) {
        case 0:
            return "January";
        case 1:
            return "February";
        case 2:
            return "March";
        case 3:
            return "April";
        case 4:
            return "May";
        case 5:
            return "June";
        case 6:
            return "July";
        case 7:
            return "August";
        case 8:
            return "September";
        case 9:
            return "October";
        case 10:
            return "November";
        case 11:
            return "December";

        default:
            throw new IllegalArgumentException("Illegal month value given to 'toMonthString()'.");
        }
    }

    /**
     * A method to return a string version of the day of the week name
     * @param    day int    containing day number, starting with "1" for "Sunday"
     * @return       String containing the string value of the day (no spaces)
     */
    public static String toDayOfWeekString(int day) {
        switch (day - 1) {
        case 0:
            return "Sunday";
        case 1:
            return "Monday";
        case 2:
            return "Tuesday";
        case 3:
            return "Wednesday";
        case 4:
            return "Thursday";
        case 5:
            return "Friday";
        case 6:
            return "Saturday";
        default:
            throw new IllegalArgumentException("Illegal day value given to 'toDayOfWeekString()'.");
        }
    }

    /**
     * A method to return a count of the total number of days between two valid dates
     * @param    month1 long   containing month number, starting with "1" for "January"
     * @param    day1   long   containing day number
     * @param    year1  long   containing four-digit year
     * @param    month2 long   containing month number, starting with "1" for "January"
     * @param    day2   long   containing day number
     * @param    year2  long   containing four-digit year
     * @return          long   count of total number of days
     */
    public static long daysBetween(long month1, long day1, long year1, long month2, long day2, long year2) {
        //Initial declaration of values
        //swap_ values used for switching dates if second date is greate than first
        long dayCount = 0;
        long swap_Year;
        long swap_Month;
        long swap_Day;

        //Returns 0 if dates are the same
        if (dateEquals(month1, day1, year1, month2, day2, year2)) {
            return 0;
        }

        //If the date year2-month2-day2 is later than year1-month1-day1, the values switch
        if (compareDate(month1, day1, year1, month2, day2, year2) == 1) {
            swap_Year = year1;
            swap_Month = month1;
            swap_Day = day1;

            year1 = year2;
            month1 = month2;
            day1 = day2;

            year2 = swap_Year;
            month2 = swap_Month;
            day2 = swap_Day;
        }

        //Adds days in year to dayCount, accounting for leap Years
        for (long i = year1; i < year2; i++) {
            if (isLeapYear(i)) {
                dayCount += 366;
            } else {
                dayCount += 365;
            }
        }

        //Adds days in year2 up until month2
        for (int i = 0; i < month2 - 1; i++) {
            if (isLeapYear(year2)) {
                dayCount += leap_Days[i];
            } else {
                dayCount += days[i];
            }
        }

        //Adds days in month2 to dayCount
        dayCount += day2;

        //Removes days in year1 before month1
        for (int i = 0; i < month1 - 1; i++) {
            if (isLeapYear(year1)) {
                dayCount -= leap_Days[i];
            } else {
                dayCount -= days[i];
            }
        }

        //Removes days in month1 up until day1
        dayCount -= day1;

        return dayCount;
    }

}
