import java.util.Scanner;

public class Main {

    //https://stepik.org/lesson/13252/step/3?unit=3437
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        int[] m = new int[11];
        int[] input = new int[n];
        for (int i = 0; i < n; i++) {
            input[i] = scanner.nextInt();
            m[input[i]]++;
        }
        for (int i = 2; i < 11; i++) {
            m[i] += m[i - 1];
        }

        int[] result = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            result[m[input[i]] - 1] = input[i];
            m[input[i]]--;
        }
        for (int i = 0; i < n; i++) {
            System.out.print(result[i] + " ");
        }
    }
}
