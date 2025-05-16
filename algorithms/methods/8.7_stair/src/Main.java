import java.util.Scanner;


class MaxSumStairTD {

    int n;
    int[] costs;

    int[] D;

    public MaxSumStairTD(int n, int[] costs) {
        this.n = n;
        this.costs = costs;
    }

    public int run() {
        D = new int[n + 1];
        D[0] = 0;
        D[1] = costs[0];

        for (int i = 2; i < D.length; i++) {
            D[i] = Integer.MIN_VALUE;
        }

        return sum(n);
    }

    private int sum(int i) {
        if (D[i] != Integer.MIN_VALUE) return D[i];
        D[i] = Math.max(sum(i - 1), sum(i - 2)) + costs[i - 1];
        return D[i];
    }

}


class MaxSumStairBU {

    int n;
    int[] costs;

    int[] D;

    public MaxSumStairBU(int n, int[] costs) {
        this.n = n;
        this.costs = costs;
    }

    public int run() {
        D = new int[n + 1];
        D[0] = 0;
        D[1] = costs[0];

        for (int i = 2; i <= n; i++) {
            D[i] = Math.max(D[i - 1], D[i - 2]) + costs[i - 1];
        }

        return D[D.length - 1];
    }
}


public class Main {


    //https://stepik.org/lesson/13262/step/4?unit=3447
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] costs = new int[n];
        for (int i = 0; i < n; i++) {
            costs[i] = scanner.nextInt();
        }

        MaxSumStairBU maxSumStairBU = new MaxSumStairBU(n, costs);
        System.out.println(maxSumStairBU.run());

        MaxSumStairTD maxSumStairTD = new MaxSumStairTD(n, costs);
        System.out.println(maxSumStairTD.run());
    }
}
