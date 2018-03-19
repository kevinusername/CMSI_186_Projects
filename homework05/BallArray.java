public class BallArray {

    private SoccerBall[] ba;


    public BallArray(String[] args, int ballNumber) {

        ba = new SoccerBall[ballNumber];

        for (int i = 0, j = 0; i < ballNumber; i++, j += 4) {
            ba[i] = new SoccerBall(Double.parseDouble(args[j]), Double.parseDouble(args[j + 1]),
                    Double.parseDouble(args[j + 2]), Double.parseDouble(args[j + 3]));
        }
    }

    public static void main(String[] args) {
        String[] myArgs = new String[4];
        myArgs[0] = "0";
        myArgs[1] = "1";
        myArgs[2] = "2";
        myArgs[3] = "3";

        BallArray ba = new BallArray(myArgs, 1);
        System.out.println(ba[1]);
    }
}