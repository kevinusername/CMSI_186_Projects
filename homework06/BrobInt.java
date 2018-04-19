import java.util.Arrays;
import java.util.Objects;

public class BrobInt {

    public static final BrobInt ZERO = new BrobInt("0"); /// Constant for "zero"
    public static final BrobInt ONE = new BrobInt("1"); /// Constant for "one"
    public static final BrobInt TEN = new BrobInt("10"); /// Constant for "ten"

    private String internalValue = "";
    private boolean isNegative = false;
    private byte[] byteVersion = null;
    private int length = 0;

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

    public BrobInt multiply(BrobInt gint) {
        throw new UnsupportedOperationException("\n         Sorry, that operation is not yet implemented.");
    }

    public BrobInt divide(BrobInt gint) {
        throw new UnsupportedOperationException("\n         Sorry, that operation is not yet implemented.");
    }

    public BrobInt remainder(BrobInt gint) {
        throw new UnsupportedOperationException("\n         Sorry, that operation is not yet implemented.");
    }

    public String toString() {
        if (isNegative) {
            return "-".concat(internalValue);
        } else {
            return internalValue;
        }
    }

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

    public boolean equals(Object x) {
        return this == x;
    }

    public static BrobInt valueOf(long value) {
        throw new UnsupportedOperationException("\n         Sorry, that operation is not yet implemented.");
    }

    public static String trimZeros(String s) {
        StringBuilder answer = new StringBuilder(s);
        while (answer.charAt(0) == '0' && answer.length() > 1) {
            answer.deleteCharAt(0);
        }
        return answer.toString();
    }

    public static void main(String[] args) {
        BrobInt myBrob = new BrobInt("-238572387509237523");
        BrobInt myBrob2 = new BrobInt("-74563457457345734573457345");

        System.out.println(new BrobInt("12586269025").add(new BrobInt("20365011074")).internalValue);

    }
}