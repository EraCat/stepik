import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

//https://stepik.org/lesson/41562/step/3?unit=20016
public class Main {
    private static final int P = 1_000_000_007;
    private static final int X = 128;

    static HashMap<Integer, Long> cache = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String pattern = scanner.nextLine();
        long patternHash = hash(pattern, 0, pattern.length());


        String text = scanner.nextLine();
        long hash = hash(text, text.length() - pattern.length(), text.length());
        LinkedList<Integer> indexes = new LinkedList<>();

        if (patternHash == hash) {
            compare(pattern, text, text.length() - pattern.length(), indexes);
        }

        for (int i = text.length() - 2; i >= pattern.length() - 1; i--) {
            int beginIndex = i - pattern.length() + 1;
            int c0 = text.charAt(beginIndex);
            int endIndex = i;
            int cRight = text.charAt(endIndex + 1);

            hash = (((((hash - cRight * pow(pattern.length() - 1)) % P) * X) % P + P) % P + c0) % P;

            if (patternHash == hash) {
                compare(pattern, text, beginIndex, indexes);
            }
        }
        List<String> collect = indexes.stream().map(String::valueOf).toList();
        System.out.println(String.join(" ", collect));
    }

    private static void compare(String pattern, String text, int i, LinkedList<Integer> indexes) {
        boolean result = true;
        for (int j = 0; j < pattern.length(); j++) {
            if (text.charAt(i + j) != pattern.charAt(j)) {
                result = false;
                break;
            }
        }
        if (result) {
            indexes.addFirst(i);
        }

    }


    private static long hash(String str, int begin, int end) {
        long hash = 0;

        for (int i = 0; i < end - begin; i++) {
            int code = str.charAt(begin + i);
            hash = (hash + (code * pow(i)) % P) % P;
        }

        hash %= P;

        return hash;
    }

    private static long pow(int exponent) {
        long r = 1;
        if (cache.containsKey(exponent)) {
            return cache.get(exponent);
        }
        for (int i = exponent; i > 0; i--) {
            if (cache.containsKey(i)) {
                r = (r * cache.get(i)) % P;
                break;
            }
            r = (r * X) % P;
            cache.put(exponent - i + 1, r);
        }

        cache.put(exponent, r);

        return r;
    }

}
