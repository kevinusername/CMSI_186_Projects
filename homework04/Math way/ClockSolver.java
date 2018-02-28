public class ClockSolver {

    /**
     * The main calculation for this program.
     * Uses a concept of "absolute time" in seconds to represent any given clock configuration
     * Given an angle, it will find the absolute time at which that angle is made by the hands
     * on the clock.
     * 
     * How it works:
     *  Takes in an angle in degrees, converts it into radians then uses a formula I derived
     *  by solving for time from an equation to find the angle based on a time.
     */
    public static double absTime(double angle) {
        return 21600 * angle * Math.PI / 180 / (11 * Math.PI);
    }

    public static void main(String[] args) {

        // Takes the input angle in degrees and forms a double (mod 360)
        double angle = Double.parseDouble(args[0]) % 360;

        /* Converts absolute time into the format: X-hours Y-minutes Z-seconds
        Additionally uses a second line for each to find when the complementary (hence equivalent)
        angle is created by clock hands */
        for (double i = angle, j = 0; i < (11 * 360); i += 360, j++) {

            int currentHour = (int) Math.floor(absTime(i) / 60 / 60);
            int currentHour2 = (int) Math.floor(absTime(360 - angle + j * 360) / 60 / 60);

            int currentMinute = (int) Math.floor(absTime(i) / 60) % 60;
            int currentMinute2 = (int) Math.floor(absTime(360 - angle + j * 360) / 60) % 60;

            int currentSecond = (int) Math.floor(absTime(360 - angle + j * 360)) % 60;
            int currentSecond2 = (int) Math.floor(absTime(i)) % 60;

            System.out.println(currentHour + " hours " + currentMinute + " minutes " + currentSecond + " seconds");
            System.out.println(currentHour2 + " hours " + currentMinute2 + " minutes " + currentSecond2 + " seconds");

        }
    }
}