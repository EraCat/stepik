import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

class KnapsackTDKey {
    final int w;
    final int i;

    @Override
    public String toString() {
        return "KnapsackTDKey{" +
                "w=" + w +
                ", i=" + i +
                '}';
    }

    public KnapsackTDKey(int w, int i) {
        this.w = w;
        this.i = i;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        KnapsackTDKey that = (KnapsackTDKey) o;
        return w == that.w && i == that.i;
    }

    @Override
    public int hashCode() {
        return Objects.hash(w, i);
    }
}

class KnapsackTD {

    Map<KnapsackTDKey, Integer> H;

    int maxCapacity;
    int n;
    int[] weights;

    public KnapsackTD(int maxCapacity, int n, int[] weights) {
        this.maxCapacity = maxCapacity;
        this.n = n;
        this.weights = weights;
        H = new HashMap<>();
    }

    public int maxWeight() {
        for (int i = 0; i <= maxCapacity; i++) {
            H.put(new KnapsackTDKey(i, 0), 0);
        }
        for (int i = 0; i <= n; i++) {
            H.put(new KnapsackTDKey(0, i), 0);
        }

        return knapsackTD(new KnapsackTDKey(maxCapacity, n));
    }

    private int knapsackTD(KnapsackTDKey key) {
        if (H.containsKey(key)) {
            return H.get(key);
        }

        int v = knapsackTD(new KnapsackTDKey(key.w, key.i - 1));
        if (weights[key.i - 1] <= key.w) {
            v = Math.max(v, knapsackTD(new KnapsackTDKey(key.w - weights[key.i - 1], key.i - 1)) + weights[key.i - 1]);
        }
        H.put(key, v);

        return v;
    }

    public void printH() {
        int[][] D = new int[n + 1][maxCapacity + 1];

        H.forEach((key, value) -> D[key.i][key.w] = value);

        for (int i = 0; i < D.length; i++) {
            for (int j = 0; j < D[0].length; j++) {
                System.out.print(D[i][j]);
            }
            System.out.println();
        }
    }

}

class KnapsackBU {

    int maxWeight;
    int n;
    int[] w;

    public KnapsackBU(int[] w, int n, int maxWeight) {
        this.w = w;
        this.n = n;
        this.maxWeight = maxWeight;
    }

    int[][] D;

    public int run() {
        D = new int[n + 1][maxWeight + 1];

        for (int i = 0; i < D.length; i++) {
            D[i][0] = 0;
        }
        Arrays.fill(D[0], 0);

        for (int item = 1; item < D.length; item++) {
            for (int weight = 1; weight < D[0].length; weight++) {
                D[item][weight] = D[item - 1][weight];
                if (w[item - 1] <= weight) {
                    D[item][weight] = Math.max(D[item - 1][weight - w[item - 1]] + w[item - 1], D[item][weight]);
                }
            }
        }

        return D[D.length - 1][D[0].length - 1];
    }

    public void printD() {
        for (int i = 0; i < D.length; i++) {
            for (int j = 0; j < D[0].length; j++) {
                System.out.print(D[i][j]);
            }
            System.out.println();
        }
    }

}


public class Main {

    //https://stepik.org/lesson/13259/step/5?unit=3444
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int W = scanner.nextInt();
        int n = scanner.nextInt();

        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            weights[i] = scanner.nextInt();
        }

        KnapsackBU knapsackBU = new KnapsackBU(weights, n, W);
        System.out.println(knapsackBU.run());

        KnapsackTD knapsackTD = new KnapsackTD(W, n, weights);
        System.out.println(knapsackTD.maxWeight());
    }
}
