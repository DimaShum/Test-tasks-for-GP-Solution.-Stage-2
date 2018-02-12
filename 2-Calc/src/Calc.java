import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calc {

    public BigDecimal estimate(String s) throws Exception {

        if (!hasSpaceBetweenNumbers(s)) {
            s = s.replace(",", ".");
            s = s.replaceAll("\\s+", "");

            Answer answer = PlusMinus(s);
            if (!answer.rest.isEmpty()) {
                System.err.println("Error: can't full parse");
                System.err.println("rest: " + answer.rest);
            }

            return answer.acc;
        } else {
            throw new Exception("Invalid data: String has space between numbers");
        }
    }

    private boolean hasSpaceBetweenNumbers(String s) {
        Pattern pattern = Pattern.compile("\\d+\\s+\\d+");
        Matcher matcher = pattern.matcher(s);

        return matcher.find();
    }

    private Answer PlusMinus(String s) throws Exception {
        Answer currentAnswer = MulDiv(s);
        BigDecimal acc = currentAnswer.acc;

        while (!currentAnswer.rest.isEmpty()) {
            if (!(currentAnswer.rest.charAt(0) == '+' || currentAnswer.rest.charAt(0) == '-')) break;

            char sign = currentAnswer.rest.charAt(0);
            String next = currentAnswer.rest.substring(1);

            currentAnswer = MulDiv(next);
            if (sign == '+') {
                acc = acc.add(currentAnswer.acc);
            } else {
                acc = acc.subtract(currentAnswer.acc);
            }
        }
        return new Answer(acc, currentAnswer.rest);
    }

    private Answer MulDiv(String s) throws Exception {

        Answer current = degree(s);
        BigDecimal acc = current.acc;

        while (true) {

            if (current.rest.length() == 0) {
                return current;
            }

            char sign = current.rest.charAt(0);

            if ((sign != '*' && sign != '/'))
                return current;

            String next = current.rest.substring(1);
            Answer right = degree(next);

            if (sign == '*') {
                acc = acc.multiply(right.acc);
            } else {
                acc = acc.divide(right.acc);
            }

            current = new Answer(acc, right.rest);
        }
    }

    private Answer degree(String s) throws Exception {
        Answer current = Bracket(s);
        BigDecimal acc = current.acc;

        while (!current.rest.isEmpty()) {
            char sign = current.rest.charAt(0);

            if (sign == '^') {
                String restString = current.rest.substring(1);
                Answer rightPart = Bracket(restString);

                acc = new BigDecimal(Math.pow(acc.doubleValue(), rightPart.acc.doubleValue()));
                current = new Answer(acc, rightPart.rest);
            } else {
                return current;
            }
        }

        return current;
    }

    private Answer Bracket(String s) throws Exception
    {
        char zeroChar = s.charAt(0);
        if (zeroChar == '(') {
            Answer r = PlusMinus(s.substring(1));
            if (!r.rest.isEmpty() && r.rest.charAt(0) == ')') {
                r.rest = r.rest.substring(1);
            } else {
                throw new Exception("Invalid data: String hasn't close bracket");
            }
            return r;
        }
        return Num(s);
    }

    private Answer Num(String s) throws Exception {
        int i = 0;
        int dot_cnt = 0;
        boolean negative = false;
        // число также может начинаться с минуса
        if( s.charAt(0) == '-' ){
            negative = true;
            s = s.substring( 1 );
        }
        // разрешаем только цифры и точку
        while (i < s.length() && (Character.isDigit(s.charAt(i)) || (s.charAt(i) == '.') || s.charAt(i) == ',')) {
            // но также проверям, что в числе может быть только одна точка!
            if ((s.charAt(i) == '.' || s.charAt(i) == ',') && ++dot_cnt > 1) {
                throw new Exception("Invalid data: String has too much '.' in numbers");
            }
            i++;
        }

        if( i == 0 ){ // что-либо похожее на число мы не нашли
            throw new Exception("Invalid data");
        }

        BigDecimal dPart =  new BigDecimal(s.substring(0, i));

        if( negative ) dPart = dPart.negate();
        String restPart = s.substring(i);

        return new Answer(dPart, restPart);
    }
}
