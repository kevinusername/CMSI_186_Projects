import java.util.Arrays;
import java.util.Objects;

public class BrobInt {

    public static final BrobInt ZERO = new BrobInt("0"); /// Constant for "zero"
    public static final BrobInt ONE = new BrobInt("1"); /// Constant for "one"
    public static final BrobInt TEN = new BrobInt("10"); /// Constant for "ten"

    private String internalValue = "";
    private boolean isNegative = false;
    private byte[] byteVersion = null;
    private byte[] digitArray = null;
    private int length = 0;

    /**
    *  Constructor takes a string and assigns it to the internal storage, checks for a sign character
    *   and handles that accordingly;  it then checks to see if it's all valid digits, and reverses it
    *   for later use
    *  @param  value  String value to make into a BrobInt
    */
    public BrobInt(String value) {

        if (value.charAt(0) == '-') { // Handles Negative numbers
            isNegative = true;
            value = value.substring(1);
        }

        internalValue = trimZeros(value);

        length = internalValue.length();
        byteVersion = new byte[(int) Math.ceil((double) length / 2.0)];

        for (int i = 0, j = length; i < byteVersion.length; i++, j -= 2) {
            try {
                byteVersion[i] = Byte.parseByte(internalValue.substring(j - 2, j));
            } catch (StringIndexOutOfBoundsException sob) {
                byteVersion[i] = Byte.parseByte(internalValue.substring(j - 1, j));
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a valid Decimal only input");
                System.exit(1);
            }
        }
    }

    /**
     * Creates an byte array that holds one digit in each location
     * Planned use for multiplication and divison
     */
    public void createDigitArray() {
        digitArray = new byte[length];

        for (int i = 0; i < length; i++) {
            try {
                digitArray[i] = Byte.parseByte(internalValue.substring(i, i + 1));
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a valid Decimal only input");
                System.exit(1);
            }
        }
    }

    /**
    *  Method to add the value of a BrobIntk passed as argument to this BrobInt using byte array
    *  @param  gint         BrobInt to add to this
    *  @return BrobInt that is the sum of the value of this BrobInt and the one passed in
    *   */
    public BrobInt add(BrobInt gint) {

        boolean addSign = false;
        boolean finalCarry = false;

        if (isNegative && gint.isNegative) {
            addSign = true;
        } else if (isNegative && false == gint.isNegative) {
            return gint.subtract(new BrobInt(internalValue));
        } else if (false == isNegative && gint.isNegative) {
            return subtract(new BrobInt(gint.internalValue));
        }

        BrobInt[] tempBrobArray = setBigger(gint);
        BrobInt bigBrob = new BrobInt(tempBrobArray[0].toString());
        BrobInt littleBrob = new BrobInt(tempBrobArray[1].toString());

        byte[] sumArray = new byte[bigBrob.byteVersion.length];

        for (int i = 0; i < littleBrob.byteVersion.length; i++) {

            short tempSum = (short) (bigBrob.byteVersion[i] + littleBrob.byteVersion[i]);
            try {
                if (tempSum >= 100) {
                    tempSum -= 100;
                    bigBrob.byteVersion[i + 1] += 1;
                }
            } catch (ArrayIndexOutOfBoundsException aob) {
                finalCarry = true;
            }
            sumArray[i] = (byte) tempSum;
        }

        if (bigBrob.byteVersion.length > littleBrob.byteVersion.length) {
            for (int i = littleBrob.byteVersion.length; i < bigBrob.byteVersion.length; i++) {
                sumArray[i] = bigBrob.byteVersion[i];
            }
        }

        String answer = byteToString(sumArray);

        if (finalCarry) {
            answer = "1".concat(answer);
        }

        if (addSign) {
            answer = ("-".concat(answer));
        }

        return new BrobInt(answer);
    }

    /** 
    *  Method to subtract the value of a BrobIntk passed as argument to this BrobInt using bytes
    *  @param  gint         BrobInt to subtract from this
    *  @return BrobInt that is the difference of the value of this BrobInt and the one passed in
    *   */
    public BrobInt subtract(BrobInt gint) {

        if (equals(gint)) {
            return ZERO;
        }

        boolean addSign = false;

        if (isNegative && gint.isNegative) {
            return add(new BrobInt(gint.internalValue));
        } else if (isNegative && false == gint.isNegative) {
            return add(new BrobInt("-".concat(gint.internalValue)));
        } else if (false == isNegative && gint.isNegative) {
            return add(new BrobInt(gint.internalValue));
        }

        if (compareTo(gint) <= 0) {
            addSign = true;
        }

        BrobInt[] tempBrobArray = setBigger(gint);
        BrobInt bigBrob = new BrobInt(tempBrobArray[0].toString());
        BrobInt littleBrob = new BrobInt(tempBrobArray[1].toString());

        byte[] difArray = new byte[bigBrob.byteVersion.length];

        for (int i = 0; i < littleBrob.byteVersion.length; i++) {
            short tempDif = (short) (bigBrob.byteVersion[i] - littleBrob.byteVersion[i]);
            if (tempDif < 0) {
                tempDif += 100;
                bigBrob.byteVersion[i + 1] -= 1;
            }
            difArray[i] = (byte) tempDif;
        }

        if (bigBrob.byteVersion.length > littleBrob.byteVersion.length) {
            for (int i = littleBrob.byteVersion.length; i < bigBrob.byteVersion.length; i++) {
                difArray[i] = bigBrob.byteVersion[i];
            }
        }

        if (addSign) {
            return new BrobInt("-".concat(byteToString(difArray)));

        }

        return new BrobInt(byteToString(difArray));

    }

    /** 
    *  Method to multiply the value of a BrobIntk passed as argument to this BrobInt
    *  @param  gint         BrobInt to multiply by this
    *  @return BrobInt that is the product of the value of this BrobInt and the one passed in
    *   */
    public BrobInt multiply(BrobInt gint) {

        // BrobInt[] tempBrobArray = setBigger(gint);
        // BrobInt bigBrob = new BrobInt(tempBrobArray[0].toString());
        // BrobInt littleBrob = new BrobInt(tempBrobArray[1].toString());

        // bigBrob.createDigitArray();
        // littleBrob.createDigitArray();

        // byte[] productArray = new byte[bigBrob.length + littleBrob.length];

        // for (int i = 0; i < littleBrob.length; i++) {
        //     for (int j = 0; j < bigBrob.length; j++) {
        //         productArray[j] = (byte) (bigBrob.digitArray[j] * littleBrob.digitArray[i]);
        //         try {
        //             if (productArray[j] > 9) {
        //                 bigBrob.digitArray[j + 1] += bigBrob.digitArray[j] / littleBrob.digitArray[i];
        //                 productArray[j] = (byte) (productArray[j] % 10);
        //             }
        //         } catch (ArrayIndexOutOfBoundsException aob) {
        //             productArray[j] += bigBrob.digitArray[j] / littleBrob.digitArray[i];
        //             productArray[j + 1] = (byte) (productArray[j] % 10);
        //         }
        //     }
        // }
        // System.out.println(Arrays.toString(productArray));
        // return ZERO;
        throw new UnsupportedOperationException("\n         Sorry, that operation is not yet implemented.");

    }

    /** 
    *  Method to divide the value of this BrobIntk by the BrobInt passed as argument
    *  @param  gint         BrobInt to divide this by
    *  @return BrobInt that is the dividend of this BrobInt divided by the one passed in
    *   */
    public BrobInt divide(BrobInt gint) {
        throw new UnsupportedOperationException("\n         Sorry, that operation is not yet implemented.");
    }

    /** 
    *  Method to get the remainder of division of this BrobInt by the one passed as argument
    *  @param  gint         BrobInt to divide this one by
    *  @return BrobInt that is the remainder of division of this BrobInt by the one passed in
    *   */
    public BrobInt remainder(BrobInt gint) {
        throw new UnsupportedOperationException("\n         Sorry, that operation is not yet implemented.");
    }

    /** 
    *  Method to return a String representation of this BrobInt
    *  @return String  which is the String representation of this BrobInt
    *   */
    public String toString() {
        if (isNegative) {
            return "-".concat(internalValue);
        } else {
            return internalValue;
        }
    }

    /** 
    *  Method to compare a BrobInt passed as argument to this BrobInt
    *  @param  gint  BrobInt to add to this
    *  @return int   that is one of neg/0/pos if this BrobInt precedes/equals/follows the argument
    *  NOTE: this method does not do a lexicographical comparison using the java String "compareTo()" method
    *        It takes into account the length of the two numbers, and if that isn't enough it does a
    *        character by character comparison to determine
    *   */
    public int compareTo(BrobInt gint) {

        if (false == isNegative && gint.isNegative) {
            return 1;
        } else if (isNegative && false == gint.isNegative) {
            return -1;
        } else if (length < gint.length) {
            return -1;
        } else if (length > gint.length) {
            return 1;
        } else if (length == gint.length) {
            return this.internalValue.compareTo(gint.internalValue);
        } else {
            return -1000000000;
        }

    }

    /**
     * Creates a BrobInt array with location 0 being the bigger of the two and location 1 being the smaller
     * Used for keeping arthimatic operations simpler
     * @param gint BrobInt to compare to
     * @return BrobInt[] with order in respect to size
     */
    public BrobInt[] setBigger(BrobInt gint) {
        BrobInt[] tempBrobArray = new BrobInt[2];
        if (compareTo(gint) == 0 || compareTo(gint) > 0) {
            tempBrobArray[0] = this;
            tempBrobArray[1] = gint;
        } else {
            tempBrobArray[1] = this;
            tempBrobArray[0] = gint;
        }
        return tempBrobArray;
    }

    /**
     * takes an array of bytes (where each location has at most 2 digits)
     *  creates a string by placing each byte in reverse order
     * @return String representation of byteArray
     */
    public static String byteToString(byte[] bArray) {
        StringBuilder answer = new StringBuilder();
        for (int i = bArray.length - 1; i >= 0; i--) {
            if (bArray[i] < 10) {
                answer.append("0");
            }
            answer.append(Byte.toString(bArray[i]));
        }

        return answer.toString();
    }

    /** 
    *  Method to check if a BrobInt passed as argument is equal to this BrobInt
    *  @param  gint     BrobInt to compare to this
    *  @return boolean  that is true if they are equal and false otherwise
    *  NOTE: this method performs a similar lexicographical comparison as the "compareTo()" method above
    *        also using the java String "equals()" method -- THAT was easy, too..........
    *   */
    public boolean equals(Object x) {
        return this == x;
    }

    /** 
    *  Method to return a BrobInt given a long value passed as argument
    *  @param  value         long type number to make into a BrobInt
    *  @return BrobInt  which is the BrobInt representation of the long
    *   */
    public static BrobInt valueOf(long value) {
        return new BrobInt(Long.toString(value));
    }

    public static String trimZeros(String s) {
        StringBuilder answer = new StringBuilder(s);
        while (answer.charAt(0) == '0' && answer.length() > 1) {
            answer.deleteCharAt(0);
        }
        return answer.toString();
    }

    /** 
    *  the main method redirects the user to the test class
    *  @param  args  String array which contains command line arguments
    *  note:  we don't really care about these
    *   */
    public static void main(String[] args) {
        BrobInt myBrob = new BrobInt("55");
        BrobInt myBrob2 = new BrobInt("2");

    }
}