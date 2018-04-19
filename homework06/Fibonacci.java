public class Fibonacci {

    static BrobInt lastValue = BrobInt.ZERO;
    static BrobInt currentValue = BrobInt.ONE;

    static int count = 0;

    /**
     * Outputs the n'th term of the Fibonacci sequence using BrobInts
     *  Only stores last and current value, adding them with each loop
     *  Prints out the answer after the loop finishes
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please enter exactly one numeric value");
            System.exit(1);
        }

        count = Integer.parseInt(args[0]) - 2;

        for (int i = 0; i <= count; i++) {
            BrobInt placeHolder = currentValue;
            currentValue = currentValue.add(lastValue);
            lastValue = placeHolder;
        }
        System.out.println(currentValue.toString());
        System.exit(0);
    }
}