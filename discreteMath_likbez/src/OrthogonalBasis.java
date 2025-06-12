import java.util.Scanner;

public class OrthogonalBasis {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        double[][] matrix = new double[n][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m + 1; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }



    }

    private double scalar(double[] a, double[] b) {
        double result =0;
        for (int i = 0; i <a.length; i++) {
            result += a[i]*b[i];
        }

        return result;
    }
}
