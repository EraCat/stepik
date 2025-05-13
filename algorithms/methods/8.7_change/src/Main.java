import java.util.Scanner;


class MinCoinChangeBU {
    int[] coins;
    int n;

    int[] D;

    public MinCoinChangeBU(int[] coins, int n) {
        this.coins = coins;
        this.n = n;
    }

    public int run () {
        D = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            D[i] = Integer.MAX_VALUE - 1;
            for (int coin : coins) {
                if (coin<=i) {
                    D[i] = Math.min(D[i - coin] + 1, D[i]);
                }
            }
        }

        return D[n];
    }
}

class MinCoinChangeTD {

    int[] coins;
    int n;

    int[] D;

    public MinCoinChangeTD(int[] coins, int n) {
        this.coins = coins;
        this.n = n;
    }

    public int run() {
        D = new int[n + 1];
        D[0] = 0;
        for (int i = 1; i <= n; i++) {
            D[i] = Integer.MAX_VALUE - 1;
        }

        return run(n);
    }

    private int run(int i) {
        if (i == 0) return 0;
        if (i > 0 && D[i] != Integer.MAX_VALUE - 1) return D[i];

        for (int coin : coins) {
            if (coin <= i) {
                D[i] = Math.min(D[i], run(i - coin) + 1);
            }
        }

        return D[i];
    }

    public void printD() {
        for (int i = 0; i < D.length; i++) {
            System.out.print(D[i] + " ");
        }
    }
}


public class Main {

    //https://stepik.org/lesson/13262/step/3?unit=3447
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int k = scanner.nextInt();
        int[] coins = new int[k];
        for (int i = 0; i < k; i++) {
            coins[i] = scanner.nextInt();
        }

        MinCoinChangeTD minCoinChangeTD = new MinCoinChangeTD(coins, n);
        System.out.println(minCoinChangeTD.run());

        MinCoinChangeBU minCoinChangeBU = new MinCoinChangeBU(coins, n);
        System.out.println(minCoinChangeBU.run());
    }



}
