public class SoccerSim {
    final static double XMAX = 100;
    final static double XMIN = -100;
    final static double YMAX = 100;
    final static double YMIN = -100;
    static boolean noCollision = true;

    static double timeSlice = 1.0;

    static int ballCount = 0;

    public static void tick(SoccerBall ball) {
        ball.xPosition += ball.xVelocity * timeSlice;
        ball.yPosition += ball.yVelocity * timeSlice;

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
     * Checks that there is a valid amount of inputs
     * Sets timeSlice if given, or defaults to 1.0 second
     * determines number of SoccerBalls from amount of inputs
     */
    public static void validateArgs(String[] args) {
        if (0 == (args.length - 1) % 4) {
            timeSlice = Double.parseDouble(args[args.length - 1]);
            ballCount = (args.length - 1) / 4;
        } else if (0 == (args.length % 4)) {
            timeSlice = 1.0;
            ballCount = (args.length) / 4;
        } else {
            System.out.println("Invalid amount of arguements");
            System.exit(1);
        }
    }

    /**
     * Compares Distance between all balls in the simulation
     * If any two have a distance between centers <= DIAMETER, sets noCollision to false
     */
    public static void checkCollision(SoccerBall[] allBalls) {

        for (int i = 0; i < ballCount; i++) {
            for (int j = 0; j < ballCount; j++)
                if (allBalls[i].inBounds && allBalls[j].inBounds) {
                    if ((distanceBetween(allBalls[i], allBalls[j]) <= SoccerBall.DIAMETER) && (i != j)) {
                        noCollision = false;
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
        for (int i = 0, j = 0; i < ballCount; i++, j += 4) {
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
        for (int i = 0; i < ballCount; i++) {
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
            for (int i = 0; i < ballCount; i++) {
                tick(allBalls[i]);
            }
        }

        if (true == noCollision) {
            System.out.println("There was no collision");
        } else {
            System.out.println("There was a collision");
        }
    }
}