public class SoccerSim {

    /**
     * Defining class variables and constants
     */
    final static double XMAX = 1000;    // These MIN/MAX values are the dimensions of the soccer field
    final static double XMIN = -1000;
    final static double YMAX = 1000;
    final static double YMIN = -1000;
    final static double minVelocity = (double) 1 / (double) 12; // The minimum velocity a ball can be at before being forced to stop
    static boolean noCollision = true; // A boolean of whether a collision has happened or not so far

    static double timeSlice = 1.0;  // Amount of time that passes between each "tick"
    static double timeElapsed = 0;  // Total time elapsed since start of simulation

    static int ballCount = 0;   // Total of how many balls are in the simulation
    static int[] collided = new int[2]; // A small array holding the name (number) of the two balls that collided

    static SoccerBall pole = new SoccerBall(10, 10, 0, 0);  // A stationary pole that can collide with the other balls
                                                            // Has the radius of a normal SoccerBall, positioned at <10,10>


    /**
     * Does all the neccessary calculations for when time passes
     * Including: moving balls, accounting for friction, and progressing time
     */
    public static void tick(SoccerBall[] allBalls) {

        timeElapsed += timeSlice;   // Account for time passing

        // If the balls total speed > 1 inch per second, alter ball's speed accordingly
        for (int i = 0; i < ballCount; i++) {

            if (allBalls[i].inBounds) { // Don't bother with out of bounds balls

                SoccerBall ball = allBalls[i]; // For convience sake

                ball.xPosition += ball.xVelocity * timeSlice;   //Increase x/y position accordingly
                ball.yPosition += ball.yVelocity * timeSlice;

                if (Math.sqrt(Math.pow(ball.xVelocity, 2) + Math.pow(ball.yVelocity, 2)) > minVelocity) {
                    ball.xVelocity -= ball.xVelocity * .01 * timeSlice; // Slow by 1% per second for both x and y velocity
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
                if (ball.xPosition > XMAX || ball.xPosition < XMIN || ball.yPosition < YMIN || ball.yPosition > YMAX) {
                    ball.xVelocity = 0;
                    ball.yVelocity = 0;
                    ball.inBounds = false;
                }
            }
        }

    }

    /**
     * Find Distance between two balls using the distance formula
     * @returns the distance between balls' centers
     */
    public static double distanceBetween(SoccerBall a, SoccerBall b) {
        double xDifference = a.xPosition - b.xPosition;
        double yDifference = a.yPosition - b.yPosition;
        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2)); //Distance formula
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
        if (0 == args.length) {
            System.out.println(
                    "Please enter 4 arguements for every ball\nformat: [x position] [y position] [x velocity] [y velocity]\n");
            System.out.println("An optional final arguement for timeSlice can be put at end of input as well");
            System.exit(1);
        }

        for (int i = 0; i < args.length; i++) {
            try {
                Double.parseDouble(args[i]);
            } catch (NumberFormatException nfe) {
                System.out.println("\nInvalid input, please enter a real number for every arguement\n");
                System.exit(2);
            }
            if (Double.POSITIVE_INFINITY == Double.parseDouble(args[i]) || Double.NEGATIVE_INFINITY == Double.parseDouble(args[i])) {
                System.out.println("\nYou can't just place a ball at Infinity.....");
                System.out.println("Please enter a FINITE location\n");
                System.exit(10000);
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
            for (int j = 0; j < ballCount; j++) {// For each iteration of above loop, loops through all balls again to compare
                if (allBalls[i].inBounds && allBalls[j].inBounds) {
                    if ((distanceBetween(allBalls[i], allBalls[j]) <= SoccerBall.DIAMETER) && (i != j)) {
                        noCollision = false;
                        // Stores which two balls were involved in collision
                        collided[0] = i;
                        collided[1] = j;
                    }
                }
            }
            if ((distanceBetween(allBalls[i], pole)) <= SoccerBall.DIAMETER) { // Check for collision with pole
                noCollision = false;
                collided[0] = i;
                collided[1] = -1;
            }
        }
    }

    /**
     * Given the program's input "args", creates an array of SoccerBalls that have the input properties
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

    /**
     * Reports the current time in the form hh:mm:ss
     *  then reports current position and velocity of every ball in format <xP,yP>, <xV,yV>
     */
    public static void report(SoccerBall[] allBalls) {
        System.out.format("Time: %02.0f:%02.0f:%.2f:\n", Math.floor(timeElapsed / 360), Math.floor(timeElapsed / 60),
                timeElapsed % 60);
        for (int i = 0; i < allBalls.length; i++) {
            System.out.format("Ball %d: position: <%.4f,%.4f>, velocity: <%.4f,%.4f>\n", i, allBalls[i].xPosition,
                    allBalls[i].yPosition, allBalls[i].xVelocity, allBalls[i].yVelocity);
        }
        System.out.print("\n");
    }

    /**
     * Uses all the above methods to simulate a field with any amount of soccerballs with input properties
     * Will print out which objects collided, where, and when upon first collision
     * If no collision occurs, will print out message and exit
     * 
     * In the event of no collision, stops when no ball is in motion
     */
    public static void main(String[] args) {

        validateArgs(args);

        SoccerBall[] allBalls = createSoccerBalls(args);

        while (noCollision && checkMotion(allBalls)) { // Ensures that the simuation should still be running
            report(allBalls);
            checkCollision(allBalls); // Checks if collision occurs with current values
            if (noCollision) { // If there is no collision, increment all values
                tick(allBalls);
            }
        }

        if (noCollision) {
            System.out.println("There was no collision");
            System.exit(0);
        } else { // If there was a collision, print out the properties of collided objects
            System.out.println("There was a collision");
            System.out.format("Time: %.4f seconds\n", timeElapsed);
            System.out.format("SoccerBall %d: Position:<%.4f, %.4f>\n", collided[0], allBalls[collided[0]].xPosition,
                    allBalls[collided[0]].yPosition);
            if (-1 == collided[1]) { // Special case for if pole was involved in collision
                System.out.format("Pole: Position:<%.4f, %.4f>\n", pole.xPosition, pole.yPosition);
            } else { // Case where only SoccerBalls were involved
                System.out.format("SoccerBall %d: Position:<%.4f, %.4f>\n", collided[1],
                        allBalls[collided[1]].xPosition, allBalls[collided[1]].yPosition);
            }
            System.exit(0);
        }
    }
}