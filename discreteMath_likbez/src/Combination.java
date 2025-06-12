import java.util.LinkedList;
import java.util.Scanner;

public class Combination {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int k = scanner.nextInt();
        int n = scanner.nextInt();

        int[] combination = new int[k];

        combination(0, k, combination, 0, n);

    }

    private static void print(int[] combination) {
        for (int j : combination) {
            System.out.print(j + " ");
        }
        System.out.println();
    }


    private static void combination(int current, int k, int[] arr, int l, int r) {
        if (l == r || current >= k) {
            return;
        }

        while (l < r) {
            arr[current] = l;
            l++;
            combination(current+1, k, arr, l, r);
            if (current == k -1) {
                print(arr);
            }
        }

    }


}
