public class SoccerSim {

    final static double XMAX = 100;
    final static double XMIN = -100;
    final static double YMAX = 100;
    final static double YMIN = -100;
    static boolean noCollision = true;

    static double timeSlice = 1.0;
    static double timeElapsed = 0;

    static int ballCount = 0;
    static int[] collided = new int[2];

    public static void tick(SoccerBall ball) {
        ball.xPosition += ball.xVelocity * timeSlice;
        ball.yPosition += ball.yVelocity * timeSlice;
        timeElapsed += timeSlice;

        // If the balls total speed > 1 inch per second, alter ball's speed accordingly
        if (Math.sqrt(Math.pow(ball.xVelocity, 2) + Math.pow(ball.yVelocity, 2)) > (1 / 12)) {
            ball.xVelocity -= ball.xVelocity * .01 * timeSlice;
            ball.yVelocity -= ball.yVelocity * .01 * timeSlice;
        } else {
            // If going slower than 1 inch per second, stop the ball
            ball.xVelocity = 0;
            ball.yVelocity = 0;
        }

        /**
         * If the ball goes out of the set boundries of the field, stop it and set inBounds to false
         * Having 0 speed stops it from messing with checkMotion() function
         * Having inBounds = false stops it from being counted in checkCollision() function
        */
        if (ball.xPosition > XMAX || ball.yPosition > YMAX) {
            ball.xVelocity = 0;
            ball.yVelocity = 0;
            ball.inBounds = false;
        }

    }

    /**
     * Find Distance between two balls using the distance formula
     * @returns the distance between balls' centers
     */
    public static double distanceBetween(SoccerBall a, SoccerBall b) {
        double xDifference = a.xPosition - b.xPosition;
        double yDifference = a.yPosition - b.yPosition;
        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
    }

    /**
     * Checks that each arguement is a valid Double
     * Checks that there is a valid amount of inputs
     * Sets timeSlice if given, or defaults to 1.0 second
     * determines number of SoccerBalls from amount of inputs
     */
    public static void validateArgs(String[] args) {
        // Ensures that every arguement can be parse as a double
        // If not, informs user arguements must be doubles and ends program
        for (int i = 0; i < args.length; i++) {
            try {
                Double.parseDouble(args[i]);
            } catch (NumberFormatException nfe) {
                System.out.println("\nInvalid input, please enter a real number for every arguement\n");
                System.exit(2);
            }
        }

        if (0 == (args.length - 1) % 4) { // Finds if args has 4 args for every ball plus a timeslice
            timeSlice = Double.parseDouble(args[args.length - 1]);
            ballCount = (args.length - 1) / 4;
        } else if (0 == (args.length % 4)) { // Finds if args has 4 args for every ball and NO timeslice
            timeSlice = 1.0;
            ballCount = (args.length) / 4;
        } else { // Handles if the amount of inputs is invalid
            System.out.println("\nInvalid amount of arguements");
            System.out.println(
                    "Please enter 4 arguements for every ball\nformat: [x position] [y position] [x velocity] [y velocity]\n");
            System.exit(1);
        }
    }

    /**
     * Compares Distance between all balls in the simulation
     * If any two have a distance between centers <= DIAMETER, sets noCollision to false
     */
    public static void checkCollision(SoccerBall[] allBalls) {
        for (int i = 0; i < ballCount; i++) { // Runs through each ball
            for (int j = 0; j < ballCount; j++) // For each iteration of above loop, loops through all balls again to compare
                if (allBalls[i].inBounds && allBalls[j].inBounds) {
                    if ((distanceBetween(allBalls[i], allBalls[j]) <= SoccerBall.DIAMETER) && (i != j)) {
                        noCollision = false;
                        // Stores which two balls were involved in collision
                        collided[0] = i;
                        collided[1] = j;
                    }
                }
        }
    }

    /**
     * Given the programs input "args", creates an array of SoccerBalls that have the input properties
     * @returns produced SoccerBall[]
     */
    public static SoccerBall[] createSoccerBalls(String[] args) {
        SoccerBall[] allBalls = new SoccerBall[ballCount];
        for (int i = 0, j = 0; i < ballCount; i++, j += 4) { // Pass sets of 4 args into SoccerBall constructor
            allBalls[i] = new SoccerBall(Double.parseDouble(args[j]), Double.parseDouble(args[j + 1]),
                    Double.parseDouble(args[j + 2]), Double.parseDouble(args[j + 3]));
        }
        return allBalls;
    }

    /**
     * Finds the sum of the absolute value of every SoccerBall's speed
     * If this sum is 0, then the balls are no longer moving
     * Useful to stop simulation in the event that there are no collisions
     * @returns boolean for is there is still any motion or not
     */
    public static boolean checkMotion(SoccerBall[] allBalls) {
        double velocitySum = 0;
        for (int i = 0; i < ballCount; i++) { // Adds each balls total velocity to the running sum
            velocitySum += (Math.abs(allBalls[i].xVelocity) + Math.abs(allBalls[i].yVelocity));
        }
        if (0 == velocitySum) {
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) {

        validateArgs(args);

        SoccerBall[] allBalls = createSoccerBalls(args);

        while (noCollision && checkMotion(allBalls)) {
            checkCollision(allBalls);
            if (noCollision) {
                for (int i = 0; i < ballCount; i++) {
                    tick(allBalls[i]);
                }
            }
        }

        if (noCollision) {
            System.out.println("There was no collision");
            System.exit(0);
        } else {
            System.out.println("There was a collision");
            System.out.println("Time:" + timeElapsed + " seconds");
            System.out.println("SoccerBall " + collided[0] + " at position x:" + allBalls[collided[0]].xPosition
                    + ", y:" + allBalls[collided[0]].yPosition);
            System.out.println("SoccerBall " + collided[1] + " at position x:" + allBalls[collided[1]].xPosition
                    + ", y:" + allBalls[collided[1]].yPosition);
            System.exit(0);
        }
    }
}