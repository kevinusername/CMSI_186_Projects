public class ClockSolver {

    public static double absTime(double angle) {
        return 21600 * angle * Math.PI / 180 / (11 * Math.PI);
    }

    public static void main(String[] args) {

        double angle = Double.parseDouble(args[0]) % 360;

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