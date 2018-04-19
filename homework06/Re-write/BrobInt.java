import java.util.Arrays;

public class BrobInt {

    private String internalValue = "";
    private boolean isNegative = false;
    private byte[] byteVersion = null;
    private int length = 0;

    public BrobInt(String value) {

        if (value.charAt(0) == '-') { // Handles Negative numbers
            isNegative = true;
            value = value.substring(1);
        }

        internalValue = value;
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
        BrobInt[] tempBrobArray = setBigger(gint);
        BrobInt bigBrob = tempBrobArray[0];
        BrobInt littleBrob = tempBrobArray[1];

        byte[] sumArray = new byte[bigBrob.byteVersion.length];

        for (int i = 0; i < littleBrob.byteVersion.length; i++) {
            short tempSum = (short) (bigBrob.byteVersion[i] + littleBrob.byteVersion[i]);
            if (tempSum > 100) {
                tempSum -= 100;
                bigBrob.byteVersion[i + 1] += 1;
            }
            sumArray[i] = (byte) tempSum;
        }

        if (bigBrob.byteVersion.length > littleBrob.byteVersion.length) {
            for (int i = bigBrob.byteVersion.length
                    - littleBrob.byteVersion.length; i < bigBrob.byteVersion.length; i++) {
                sumArray[i] = bigBrob.byteVersion[i];
            }
        }

        return new BrobInt(byteToString(sumArray));
    }

    // public BrobInt subtract(BrobInt value) {

    // }

    // public BrobInt multiply(BrobInt value) {

    // }

    // public BrobInt divide(BrobInt gint) {

    // }

    // public BrobInt remainder(BrobInt gint) {

    // }

    // public String toString() {

    // }

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
            answer.append(Byte.toString(bArray[i]));
        }
        return answer.toString();
    }

    // public boolean equals(Object x) {

    // }

    // public static BrobInt valueOf(long value) {

    // }

    public static void main(String[] args) {
        BrobInt myBrob = new BrobInt("125343845734958345345734095734573498523");
        BrobInt myBrob2 = new BrobInt("199942342369999");

        System.out.println(Arrays.toString(myBrob.byteVersion));
        System.out.println(Arrays.toString(myBrob2.byteVersion));

        // System.out.println(byteToString(myBrob.byteVersion));

        System.out.println(myBrob.add(myBrob2).internalValue);
    }
}