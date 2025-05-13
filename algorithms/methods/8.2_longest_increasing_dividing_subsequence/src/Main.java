import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        System.out.println(longestIncreasingSubsequence(a));
    }

    private static int longestIncreasingSubsequence(int[] a) {
        int[] d = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            d[i] = 1;
            for (int j = 0; j < i; j++) {
                if (a[i] % a[j] == 0 && d[i] < (d[j] + 1)) {
                    d[i] = d[j] + 1;
                }
            }
        }

        int max = 0;
        for (int i = 0; i < a.length; i++) {
            max = Math.max(max, d[i]);
        }

        return max;
    }

}
