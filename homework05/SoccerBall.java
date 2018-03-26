public class SoccerBall {

    // All the values for a given SoccerBall
    static final double RADIUS = 4.45 / 12;
    static final double DIAMETER = 8.9 / 12;

    boolean inBounds = true;

    double xPosition = 0;
    double yPosition = 0;

    double xVelocity = 0;
    double yVelocity = 0;

    /**
     * Creates a SoccerBall from given inputs
     * Accepts any position, since the SoccerSim program will discard invalid SoccerBalls upon the first tick
     * Prevents killng SoccerSim due to out of bounds ball input
     */
    public SoccerBall(double xP, double yP, double xV, double yV) {
        xPosition = xP;
        yPosition = yP;

        xVelocity = xV;
        yVelocity = yV;
    }

    public static void main(String[] args) {
        /**
         * No tests here!
         * SoccerSim handles input arguements, thus any errors will be caught before they are fed to
         *  SoccerBall() constructor
         * 
         * Variet of tests can be found in "tests" folder of this directory
         */
    }
}