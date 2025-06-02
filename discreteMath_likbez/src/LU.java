import java.io.FileNotFoundException;
import java.util.Scanner;

public class LU {


    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        double[][] matrix = new double[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m ; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }

        double[][] L = new double[n][m];
        for (int k = 0; k < Math.min(n, m); k++) {
            for (int i = k + 1; i < n; i++) {
                double factor = matrix[i][k] / matrix[k][k];
                L[i][k] = factor;
                for (int j = 0; j < m; j++) {
                    matrix[i][j] -= factor * matrix[k][j];
                }
            }
        }

        printArray(matrix);

        printArray(L);


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
