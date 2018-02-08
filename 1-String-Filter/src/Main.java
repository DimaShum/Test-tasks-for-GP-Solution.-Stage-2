import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(" ");
        Boolean hasNext = true;

        while (hasNext) {
            String line = reader.readLine();

            if (line.length() != 0) {
                String str = removeChar(line, ';');
                String[] words = pattern.split(str);

                for (String redex : args) {
                    for (String word : words) {
                        if (Pattern.matches(redex, word)) {
                            if (!result.contains(line))
                                result.add(line);
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
