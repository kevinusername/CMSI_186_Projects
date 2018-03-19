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

        if (Math.sqrt(Math.pow(ball.xVelocity, 2) + Math.pow(ball.yVelocity, 2)) > (1 / 12)) {
            ball.xVelocity -= ball.xVelocity * .01 * timeSlice;
            ball.yVelocity -= ball.yVelocity * .01 * timeSlice;
        } else {
            ball.xVelocity = 0;
            ball.yVelocity = 0;
        }

        if (ball.xPosition > XMAX || ball.yPosition > YMAX) {
            ball.xVelocity = 0;
            ball.yVelocity = 0;
            ball.inBounds = false;
        }

    }

    public static double distanceBetween(SoccerBall a, SoccerBall b) {
        double xDifference = a.xPosition - b.xPosition;
        double yDifference = a.yPosition - b.yPosition;
        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
    }

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

    public static SoccerBall[] createSoccerBalls(String[] args) {
        SoccerBall[] allBalls = new SoccerBall[ballCount];
        for (int i = 0, j = 0; i < ballCount; i++, j += 4) {
            allBalls[i] = new SoccerBall(Double.parseDouble(args[j]), Double.parseDouble(args[j + 1]),
                    Double.parseDouble(args[j + 2]), Double.parseDouble(args[j + 3]));
        }
        return allBalls;
    }

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