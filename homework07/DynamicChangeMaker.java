import java.util.Arrays;

/**
 * DynamicChangeMaker is a class that takes in a set of monetary denominations
 * as well as a target value and attempts to make the optimal amount of change
 * for this target value using the given denominations.
 * 
 * @author Kevin Peters
 * @version 1.0.0
 */
public class DynamicChangeMaker {
    /**
     * Checks that the input integers are positive
     * @param coins     the <code>int[]</code> of denominations
     * @param target    the <code>int</code> containing value to make out of denominations
     * @return          <code>false</code> if values are not all positive
     *                  <code>true</code> if all values are positive
     */
    public static boolean checkArguements(int[] coins, int target) {

        // Loops through coins and chekcs if negative
        for (int i = 0; i < coins.length; i++) {
            if (coins[i] <= 0) {
                System.out.println("BAD DATA:  Denominations must be positive integers");
                return false;
            }

            // Loops through coins and checks if same as other coins
            for (int j = 0; j < coins.length; j++) {
                if (j != i && coins[i] == coins[j]) {
                    System.out.println("BAD DATA:  No duplicate denominations allowed");
                    return false;
                }
            }
        }
        if (target <= 0) {
            System.out.println("BAD DATA:  target must be a positive integer");
            return false;
        }

        // Otherwise, return true
        return true;
    }

    /**
     * Finds the optimal combination of coins to create target value.
     * In this case, optimal means using the smallest number of coins
     * @param coins     the <code>int[]</code> of denominations
     * @param target    the <code>int</code> containing value to make out of denominations
     * @return          A {@link Tuple} that contains the number of each coin in each slot
     */
    public static Tuple makeChangeWithDynamicProgramming(int[] coins, int target) {

        // Check validity of arguements and return IMPOSSIBLE Tuple if invalid
        if (!checkArguements(coins, target)) {
            return Tuple.IMPOSSIBLE;
        }

        /*
         *pArray stores a the optimal tuple for a given value using given denominations
         * optimalSolution is the tuple that can create the target value using the least
         * amount of coins
         */
        Tuple[][] pArray;
        Tuple optimalSolution = Tuple.IMPOSSIBLE;

        /*
         * each row is a subset of denominations
         * each column is a integer less than target
         */
        int rowCount = coins.length;
        int columnCount = target + 1;
        pArray = new Tuple[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {

                // Special case for column zero for all rows
                if (j == 0) {
                    pArray[i][j] = Tuple.IMPOSSIBLE;
                    continue;
                }

                // check to see if we CAN take ONE thing out of the current value;
                //  if we CAN'T take one of the denominations out of the value of "j"
                //   impossible, at least temporarily
                if ((j - coins[i]) < 0) {

                    pArray[i][j] = Tuple.IMPOSSIBLE;

                    // look backward to see if there is a valid/impossible solution
                    //  if there is, copy it over and add/replace the one that is there

                    // if this is NOT row zero we need to look above to see if there is
                    //  a better/non-impossible solution; if so, copy it down
                    if (i != 0) {

                        // else if the cell above has a total that is less than the current
                        //  cell, copy it down
                        if (pArray[i - 1][j] != Tuple.IMPOSSIBLE) {
                            pArray[i][j] = new Tuple(Arrays.copyOf(pArray[i - 1][j].data, i + 1));
                        }
                    }
                }

                // ELSE -- we *CAN* take one current denomination out
                else {

                    // make a new tuple with a one in the current demonimation index
                    pArray[i][j] = new Tuple(i + 1);
                    pArray[i][j].setElement(i, 1);

                    // look backward to see if there is a valid/impossible solution
                    if ((j - coins[i]) > 0) {

                        // if it's IMPOSSIBLE, mark the current cell IMPOSSIBLE, too
                        if (pArray[i][j - coins[i]] == Tuple.IMPOSSIBLE) {
                            pArray[i][j] = Tuple.IMPOSSIBLE;
                        } else { // else, add the previous cell to the current cell
                            pArray[i][j] = pArray[i][j].add(pArray[i][j - coins[i]]);
                        }

                    }

                    // if this is NOT row zero we need to look above to see if there is
                    //  a better/non-impossible solution; if so, copy it down
                    if (i != 0) {

                        // if the cell above is impossible, basically do nothing since
                        //  this the current cell is already IMPOSSIBLE

                        // else if the cell above has a total that is less than the current
                        //  cell, copy it down
                        if (pArray[i][j] != Tuple.IMPOSSIBLE) {
                            if (pArray[i - 1][j].total() < pArray[i][j].total()
                                    && pArray[i - 1][j] != Tuple.IMPOSSIBLE) {
                                pArray[i][j] = new Tuple(Arrays.copyOf(pArray[i - 1][j].data, i + 1));
                            }
                        } else if (pArray[i - 1][j] != Tuple.IMPOSSIBLE) {
                            pArray[i][j] = new Tuple(Arrays.copyOf(pArray[i - 1][j].data, i + 1));
                        }
                    }
                }
                if (j == target) {
                    if (pArray[i][j].total() <= optimalSolution.total() || optimalSolution == Tuple.IMPOSSIBLE) {
                        optimalSolution = pArray[i][j];
                    }
                }
            }
        }
        return optimalSolution;

    }

    /**
     * Allows for commandline input for {@link makeChangeWithDynamicProgramming}
     * @param args      A <code>String[]</code> with two elements
     *                  <code>args[0]</code> is the denominations seperated by commas
     *                  in the form <code>d1,d2,...,dn</code>
     *                  <p>
     *                  <code>args[1]</code> is the target value
     */
    public static void main(String[] args) {

        // If wrong amount of arguements, give error and exit
        if (args.length != 2) {
            System.out.println("Please enter 2 arguemnts in the format:\n$java DynamicChangeMaker d1,d2,...,dn target");
            System.exit(1);
        }
        String[] denominations = args[0].split(",");
        int[] coins = new int[denominations.length];

        // Checks that each denomination is a valid integer
        for (int i = 0; i < denominations.length; i++) {
            try {
                coins[i] = Integer.parseInt(denominations[i]);
            } catch (NumberFormatException nfe) {
                System.out.println("BAD DENOMINATIONS: All inputs must valid positive integers");
                System.exit(2);
            }
        }

        int target = 0;
        try {
            target = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("BAD TARGET: All inputs must valid positive integers");
            System.exit(3);

        }

        DynamicChangeMaker DCM = new DynamicChangeMaker();
        System.out.println(DCM.makeChangeWithDynamicProgramming(coins, target));
    }
}