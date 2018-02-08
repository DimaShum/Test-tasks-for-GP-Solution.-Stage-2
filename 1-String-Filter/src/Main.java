import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;

public class Main {



    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        ArrayList<String> result = new ArrayList<>();

        Boolean hasNext = true;

        Pattern pattern = Pattern.compile(" ");


        while (hasNext) {

            String str = reader.readLine();

            if (str.length() != 0) {

                String line = removeChar(str, ';');

                String[] words = pattern.split(line);

                for (String redex : args) {
                    for (String word : words) {
                        if (Pattern.matches(redex, word)) {
                            if (!result.contains(str))
                                result.add(str);
                        }
                    }
                }
            } else {
                hasNext = false;
            }
        }
        for (String str : result)
            System.out.println(str);
    }

    private static String removeChar(String s, char c) {
        String r = "";
        for (int i = 0; i < s.length(); i ++) {
            if (s.charAt(i) != c)
                r += s.charAt(i);
        }
        return r;
    }
}
