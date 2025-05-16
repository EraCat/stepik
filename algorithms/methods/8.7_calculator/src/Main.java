import java.util.ArrayList;
import java.util.Scanner;

class CalculatorBU {

    int n;

    int[] D;

    public CalculatorBU(int n) {
        this.n = n;
    }

    public int run() {
        D = new int[n + 1];
        D[0] = 0;
        D[1] = 0;
        for (int i = 2; i <= n; i++) {
            D[i] = D[i - 1];
            if (i % 2 == 0) {
                D[i] = Math.min(D[i], D[i/2]);
            }
            if (i % 3 == 0) {
                D[i] = Math.min(D[i], D[i / 3]);
            }
            D[i] += 1;
        }
        return D[n];
    }

    public void printRestore() {
        int[] numbers = new int[D[n] + 1];

        int current = n;
        numbers[numbers.length-1] = n;
        for (int i = numbers.length - 2; i >= 0; i--) {
            int prev = current - 1;
            if (current % 3 == 0) {
                if (D[current / 3] < D[prev]) {
                    prev = current / 3;
                }
            }
            if (current % 2 == 0) {
                if (D[current / 2] <= D[prev]) {
                    prev = current / 2;
                }
            }

            current = prev;
            numbers[i] = current;
        }

        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }
    }
}

//todo limit the depth or recursion
class CalculatorTD {

    int n;

    int[] D;
    ArrayList<Integer> prev;

    public CalculatorTD(int n) {
        this.n = n;
    }

    public int run() {
        prev = new ArrayList<>();
        D = new int[n + 1];
        D[0] = 0;
        D[1] = 0;
        for (int i = 2; i < D.length; i++) {
            D[i] = Integer.MAX_VALUE;
        }

        return calculate(n);
    }

    private int calculate(int i) {
        if (D[i] != Integer.MAX_VALUE) return D[i];

        D[i] = calculate(i - 1);
        int x2 = Integer.MAX_VALUE;
        if (i % 2 == 0) {
            x2 = calculate(i / 2);
        }
        int x3 = Integer.MAX_VALUE;
        if (i % 3 == 0) {
            x3 = calculate(i / 3);
        }

        if (x2 <= x3) {
            if (x2 < D[i]) {
                prev.add(i / 2);
            } else {
                prev.add(i - 1);
            }
        } else {
            if (x3 < D[i]) {
                prev.add(i / 3);
            } else {
                prev.add(i - 1);
            }
        }

        D[i] = Math.min(D[i], Math.min(x2, x3)) + 1;
        return D[i];
    }

    public void printRestore() {
        for (int i = 1; i < prev.size(); i++) {
            System.out.print(prev.get(i) + " ");
        }
        System.out.print(n + " ");
    }

}


public class Main {

    //https://stepik.org/lesson/13262/step/5?unit=3447
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

//        CalculatorTD calculatorTD = new CalculatorTD(n);
//        System.out.println(calculatorTD.run());
//        calculatorTD.printRestore();
//
        CalculatorBU calculatorBU = new CalculatorBU(n);
        System.out.println(calculatorBU.run());
        calculatorBU.printRestore();

    }
}
