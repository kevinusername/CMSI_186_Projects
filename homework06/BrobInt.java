
/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  BrobInt.java
 * Purpose    :  Learning exercise to implement arbitrarily large numbers and their operations
 * @author    :  B.J. Johnson
 * Date       :  2017-04-04
 * Description:  @see <a href='http://bjohnson.lmu.build/cmsi186web/homework06.html'>Assignment Page</a>
 * Notes      :  None
 * Warnings   :  None
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Revision History
 * ================
 *   Ver      Date     Modified by:  Reason for change or modification
 *  -----  ----------  ------------  ---------------------------------------------------------------------
 *  1.0.0  2017-04-04  B.J. Johnson  Initial writing and begin coding
 *  1.1.0  2017-04-13  B.J. Johnson  Completed addByt, addInt, compareTo, equals, toString, Constructor,
 *                                     validateDigits, two reversers, and valueOf methods; revamped equals
 *                                     and compareTo methods to use the Java String methods; ready to
 *                                     start work on subtractByte and subtractInt methods
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.util.Arrays;
import java.util.Objects;

public class BrobInt {

    /// These are the internal fields
    private String internalValue; // internal String representation of this BrobInt
    private int length;
    private boolean isNegative = false; // "0" is positive, "1" is negative
    private String reversed; // the backwards version of the internal String representation
    private byte[] byteVersion; // byte array for storing the string values; uses the reversed string
    private int numBytes;

    /**
     *  Constructor takes a string and assigns it to the internal storage, checks for a sign character
     *   and handles that accordingly;  it then checks to see if it's all valid digits, and reverses it
     *   for later use
     *  @param  value  String value to make into a BrobInt
     */
    public BrobInt(String value) {
        if (value == "0") {
            internalValue = value;
            length = 1;
            reversed = "0";
            numBytes = 1;
            byteVersion = new byte[numBytes];
            toByteArray();
        } else {
            internalValue = removeLeadingZeros(value);
            determineSign();
            length = internalValue.length();
            reversed = reverse();
            numBytes = (int) (Math.ceil((double) internalValue.length() / 2.0));
            byteVersion = new byte[numBytes];

            toByteArray();
        }
    }

    public void determineSign() {
        if (internalValue.charAt(0) == '-') {
            isNegative = true;
            internalValue = internalValue.substring(1);
        }
    }

    public void toByteArray() {

        String tempstring; //Initial declaration
        try {
            for (int i = 0, j = numBytes - 1; i < reversed.length(); i += 2, j--) {
                if (reversed.length() % 2 != 0 && reversed.length() - 1 == i) {
                    tempstring = String.valueOf(reversed.charAt(i));
                } else {
                    tempstring = new StringBuilder(reversed.substring(i, i + 2)).reverse().toString(); // Takes the substring and reverses it
                }
                byteVersion[j] = Byte.parseByte(tempstring);
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Error, please enter only decimal digits");
            System.exit(2);
        }
    }

    public BrobInt determineBigger(BrobInt gint) { // Make "this" into the larger of the two BrobInts, and return the other one

        if (length == gint.length && Character.getNumericValue(internalValue.charAt(0)) < Character
                .getNumericValue(gint.internalValue.charAt(0))) {
            String tempString = internalValue;
            inheritProperties(gint);
            return new BrobInt(tempString);
        }

        if (length < gint.length) { // ensures gint is the shorter of the two BrobInts
            String tempString = internalValue;
            inheritProperties(gint);
            return new BrobInt(tempString);
        } else {
            return gint;
        }
    }

    public void inheritProperties(BrobInt gint) { // Inherits the properties of another BrobInt
        internalValue = gint.internalValue;
        reversed = gint.reversed;
        length = gint.length;
        isNegative = gint.isNegative;
        byteVersion = gint.byteVersion;
        numBytes = gint.numBytes;
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to reverse the value of this BrobInt
     *  @return BrobInt that is the reverse of the value of this BrobInt
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public String reverse() {
        return new StringBuilder(internalValue).reverse().toString();
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to reverse the value of a BrobIntk passed as argument
     *  Note: static method
     *  @param  gint         BrobInt to reverse its value
     *  @return BrobInt that is the reverse of the value of the BrobInt passed as argument
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public static BrobInt reverser(BrobInt gint) {
        throw new UnsupportedOperationException("\n         Sorry, that operation is not yet implemented.");
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to add the value of a BrobIntk passed as argument to this BrobInt using int array
     *  @param  gint         BrobInt to add to this
     *  @return BrobInt that is the sum of the value of this BrobInt and the one passed in
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public BrobInt add(BrobInt gint) {

        if (false == isNegative && gint.isNegative) {
            gint.isNegative = false;
            return subtract(gint);
        }

        byte finalcarry = 0; // local var for if sum exceeds array cappacity

        gint = determineBigger(gint); // ensure "gint" is the shorter of the two (in terms of decimal places)

        byte[] sumByteArray = new byte[byteVersion.length]; // Array of bytes for the sum with same length at the longer BrobInt

        for (int i = gint.byteVersion.length - 1, j = byteVersion.length - 1; i >= 0; i--, j--) {
            short tempSum = (short) (byteVersion[j] + gint.byteVersion[i]); // sum of bytes in same decimal palces
            if (tempSum >= 100) { // Accounts for the process of carrying when the sum exceeds 100 for a given 2 decimal places
                tempSum -= 100;
                if (length == gint.length && i == 0) { // Handles the special case when a new decimal place beyond the biggest BrobInt is needed
                    finalcarry = 1;
                } else {
                    byteVersion[j - 1] += 1; // increases the value of the next decimal place by 1 (i.e. carrying the 1)
                }
            }
            sumByteArray[j] = (byte) tempSum; // Adds this value as the digits for the sum
        }

        if (length > gint.length) { // For digit places that don't exist in the smaller BrobInt, place those of the larger BrobInt (since they are unchanged)
            for (int n = (byteVersion.length - gint.byteVersion.length - 1); n >= 0; n--) { // n = the first decimal place in the larger BrobInt where the other number has no digit
                sumByteArray[n] = byteVersion[n];
            }
        }

        StringBuilder sumString = new StringBuilder();
        if (finalcarry == 1) { // Adds on the extra decimal place without having to shift the entire array
            sumString.append(finalcarry);
        }
        sumString.append(toString(sumByteArray));

        return new BrobInt(sumString.toString());
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to subtract the value of a BrobIntk passed as argument to this BrobInt using int array
     *  @param  gint         BrobInt to subtract from this
     *  @return BrobInt that is the difference of the value of this BrobInt and the one passed in
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public BrobInt subtract(BrobInt gint) {

        if (Objects.equals(internalValue, gint.internalValue)) {
            return new BrobInt("0");
        }

        if (isNegative == false && gint.isNegative) {
            gint.isNegative = false;
            return add(gint);
        } else if (isNegative && gint.isNegative) {
            isNegative = false;
            gint.isNegative = false;
            return add(gint);
        }

        boolean addSign = false;

        if (compareTo(gint) < 0) {
            addSign = true;
        }

        gint = determineBigger(gint);

        byte[] difByteArray = new byte[byteVersion.length];

        for (int i = gint.byteVersion.length - 1, j = byteVersion.length - 1; i >= 0; i--, j--) {
            short tempDif = (short) (byteVersion[j] - gint.byteVersion[i]); // sum of bytes in same decimal palces
            if (tempDif < 0) { // Accounts for the process of carrying when the sum exceeds 100 for a given 2 decimal places
                tempDif += 100;
                byteVersion[j - 1] -= 1; // increases the value of the next decimal place by 1 (i.e. carrying the 1)
            }
            difByteArray[j] = (byte) tempDif; // Adds this value as the digits for the sum
        }

        if (length > gint.length) { // For digit places that don't exist in the smaller BrobInt, place those of the larger BrobInt (since they are unchanged)
            for (int n = (byteVersion.length - gint.byteVersion.length - 1); n >= 0; n--) { // n = the first decimal place in the larger BrobInt where the other number has no digit
                difByteArray[n] = byteVersion[n];
            }
        }

        if (addSign) {
            return new BrobInt("-".concat(toString(difByteArray)));
        }

        return new BrobInt(toString(difByteArray));
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to multiply the value of a BrobIntk passed as argument to this BrobInt
     *  @param  gint         BrobInt to multiply by this
     *  @return BrobInt that is the product of the value of this BrobInt and the one passed in
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public BrobInt multiply(BrobInt gint) {
        throw new UnsupportedOperationException("\n         Sorry, that operation is not yet implemented.");
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to divide the value of this BrobIntk by the BrobInt passed as argument
     *  @param  gint         BrobInt to divide this by
     *  @return BrobInt that is the dividend of this BrobInt divided by the one passed in
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public BrobInt divide(BrobInt gint) {
        throw new UnsupportedOperationException("\n         Sorry, that operation is not yet implemented.");
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to get the remainder of division of this BrobInt by the one passed as argument
     *  @param  gint         BrobInt to divide this one by
     *  @return BrobInt that is the remainder of division of this BrobInt by the one passed in
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public BrobInt remainder(BrobInt gint) {
        throw new UnsupportedOperationException("\n         Sorry, that operation is not yet implemented.");
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to compare a BrobInt passed as argument to this BrobInt
     *  @param  gint  BrobInt to add to this
     *  @return int   that is one of neg/0/pos if this BrobInt precedes/equals/follows the argument
     *  NOTE: this method performs a lexicographical comparison using the java String "compareTo()" method
     *        THAT was easy.....
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public int compareTo(BrobInt gint) {
        return internalValue.compareTo(gint.internalValue);
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to check if a BrobInt passed as argument is equal to this BrobInt
     *  @param  gint     BrobInt to compare to this
     *  @return boolean  that is true if they are equal and false otherwise
     *  NOTE: this method performs a similar lexicographical comparison as the "compareTo()" method above
     *        also using the java String "equals()" method -- THAT was easy, too..........
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public boolean equals(BrobInt gint) {
        return Objects.equals(this.internalValue, gint.internalValue)
                && Objects.equals(this.isNegative, gint.isNegative);
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to return a BrobInt given a long value passed as argument
     *  @param  value         long type number to make into a BrobInt
     *  @return BrobInt  which is the BrobInt representation of the long
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public static BrobInt valueOf(long value) throws NumberFormatException {
        return new BrobInt(Long.toString(value));
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to return a String representation of this BrobInt
     *  @return String  which is the String representation of this BrobInt
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public String toString(byte[] bArray) {
        StringBuilder outputString = new StringBuilder();
        for (int i = 0; i < bArray.length; i++) {
            if (bArray[i] < 10 && i != 0) {
                outputString.append(0); // Fills in zeros for middle decimal places with value of 0
            }
            outputString.append(bArray[i]);
        }

        return removeLeadingZeros(outputString.toString());

    }

    public void print() {
        if (false == isNegative) {
            System.out.println(internalValue);
        } else {
            System.out.println("-" + internalValue);
        }
    }

    public String removeLeadingZeros(String s) {
        StringBuilder outputString = new StringBuilder(s);
        while (outputString.charAt(0) == '0') { // remove any unnecessary leading zero's
            outputString.deleteCharAt(0);
        }
        return outputString.toString();
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  Method to display an Array representation of this BrobInt as its bytes
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public void toArray(byte[] d) {
        System.out.println(Arrays.toString(d));
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *  the main method redirects the user to the test class
     *  @param  args  String array which contains command line arguments
     *  note:  we don't really care about these
     *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public static void main(String[] args) {

        BrobInt myBrob = new BrobInt("238947");
        // System.out.println(Arrays.toString(myBrob.byteVersion));

        BrobInt myBrob2 = new BrobInt("-23934543634546");
        // System.out.println(Arrays.toString(myBrob2.byteVersion));

        BrobInt myBrob3 = new BrobInt("-23934546");

        // myBrob.subtract(myBrob2).print();

        myBrob.add(myBrob2).print();

        myBrob.subtract(myBrob3).print();

        System.exit(0);
    }
}
