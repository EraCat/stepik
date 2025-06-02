import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Gauss {


    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("discreteMath_likbez/input.txt"));
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[][] matrix = new int[n][m + 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m + 1; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        double[][] solution = new double[n][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m + 1; j++) {
                solution[i][j] = matrix[i][j];
            }
        }

        for (int k = 0; k < Math.min(n, m); k++) {
            int maxRow = k;
            for (int i = k + 1; i < n; i++) {
                if (Math.abs(solution[i][k]) > Math.abs(solution[maxRow][k])) {
                    maxRow = i;
                }
            }
            if (Math.abs(solution[maxRow][k]) < 1e-9) {
                continue;
            }

            double[] tmp = solution[k];
            solution[k] = solution[maxRow];
            solution[maxRow] = tmp;

            for (int i = k + 1; i < n; i++) {
                double factor = solution[i][k] / solution[k][k];
                for (int j = k; j < m + 1; j++) {
                    solution[i][j] -= factor * solution[k][j];
                    if (Math.abs(solution[i][j]) < 1e-9) {
                        solution[i][j] = 0;
                    }
                }
            }
        }

//        printArray(solution);

        ResultState state = null;

        int zeroRows = 0;
        for (int i = 0; i < n; i++) {
            boolean allZero = true;
            for (int j = 0; j < m; j++) {
                if (Math.abs(solution[i][j]) > 1e-9) {
                    allZero = false;
                    break;
                }
            }
            if (allZero) {
                if (Math.abs(solution[i][m]) > 1e-9) {
                    state = ResultState.NO;
                    break;
                } else {
                    zeroRows++;
                }
            }
        }
        if (state == null && n - zeroRows < m) {
            state = ResultState.INF;
        }

        double[] results = new double[n];
        if (state == null) {
            for (int i = Math.min(n - 1, m - 1); i >= 0; i--) {
                double res = solution[i][m];
                for (int j = m - 1; j > i; j--) {
                    res -= solution[i][j] * results[j];
                }
                res = res / solution[i][Math.min(i, m - 1)];
                results[i] = res;
            }

            state = ResultState.YES;
        }


        System.out.println(state);
        if (state == ResultState.YES) {
            for (int i = 0; i < m; i++) {
                System.out.print(results[i] + " ");
            }
        }
    }

    enum ResultState {
        YES,
        NO,
        INF
    }

    public static void printArray(double[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

}
