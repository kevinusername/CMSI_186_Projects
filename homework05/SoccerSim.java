public class SoccerSim {
    final double XMAX = 100;
    final double XMIN = -100;
    final double YMAX = 100;
    final double YMIN = -100;
    static boolean noCollision = true;

    static double timeSlice = 0;

    static int ballNumber = 0;

    public static void tick(SoccerBall ball) {
        ball.xPosition += ball.xVelocity * timeSlice;
        ball.yPosition += ball.yVelocity * timeSlice;

        ball.xVelocity -= ball.xVelocity * .01 * timeSlice;
        ball.yVelocity -= ball.yVelocity * .01 * timeSlice;
    }

    public static double distanceBetween(SoccerBall a, SoccerBall b) {
        double xDifference = a.xPosition - b.xPosition;
        double yDifference = a.yPosition - b.yPosition;
        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
    }

    public static void validateArgs(String[] args) {
        if (0 == (args.length - 1) % 4) {
            timeSlice = Double.parseDouble(args[args.length - 1]);
            ballNumber = (args.length - 1) / 4;
        } else if (0 == (args.length % 4)) {
            timeSlice = 1.0;
            ballNumber = (args.length) / 4;
        } else {
            System.out.println("Invalid amount of arguements");
            System.exit(1);
        }
    }

    public static void checkCollision(Ball allBalls) {

        for (int i = 0; i < ballNumber; i++) {
            for (int j = 0; j < ballNumber; j++)
                if ((distanceBetween(allBalls[i], allBalls[j]) <= SoccerBall.DIAMETER) && i != j) {
                    noCollision = false;
                }
        }
    }

    public static void main(String[] args) {
        BallArray allBalls = new BallArray(args, ballNumber);

        validateArgs(args);


        while(noCollision) {
            checkCollision(allBalls);
            for (int i = 0; i < ballNumber; i++) {
                tick(allBalls[i]);
            }
            break;
        }
    }
}