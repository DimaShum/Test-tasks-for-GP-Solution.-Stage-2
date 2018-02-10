import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        Calc calc = new Calc();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println(calc.estimate(reader.readLine()));
            //System.out.println(calc.estimate("(2+2)*2"));
            //System.out.println(calc.estimate("(4+3)*2^-2"));
            //System.out.println(calc.estimate("5+1/0"));
            //System.out.println(calc.estimate("(17^4+5*974^3 3+2.24*4.75)^0"));
            //System.out.println(calc.estimate("(17 ^ 4 + 5 * 974 ^ 33 + 2,24 * 4,75) ^ 0"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
