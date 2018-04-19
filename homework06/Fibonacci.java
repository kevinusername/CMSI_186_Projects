public class Fibonacci {

    static BrobInt lastValue = BrobInt.ZERO;
    static BrobInt currentValue = BrobInt.ONE;

    static int count = 0;

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