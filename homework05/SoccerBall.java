public class SoccerBall {

    double RADIUS = 4.45;
    double DIAMETER = 8.9;

    double xPosition = 0;
    double yPosition = 0;

    double xVelocity = 0;
    double yVelocity = 0;

    public SoccerBall(double xP, double yP, double xV, double yV) {
        if (-50 < xP && 50 > xP) {
            xPosition = xP;
        }
        
        if (-50 < yP && 50 > yP) {
            yPosition = yP;
        }

        xVelocity = xV;
        yVelocity = yV;
    }
}