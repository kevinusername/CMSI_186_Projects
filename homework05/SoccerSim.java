public class SoccerSim {
    final double XMAX = 50;
    final double XMIN = -50;
    final double YMAX = 50;
    final double YMIN = -50;

    double timeSlice = 0;

    public void tick(SoccerBall ball) {
        ball.xPosition += ball.xVelocity * timeSlice;
        ball.yPosition += ball.yVelocity * timeSlice;

        ball.xVelocity -= ball.xVelocity * .01 * timeSlice;
        ball.yVelocity -= ball.yVelocity * .01 * timeSlice;
    }

    public static void main(String[] args) {
        SoccerBall[] allBalls = new SoccerBall[5];
    }
}