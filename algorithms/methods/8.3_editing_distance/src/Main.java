import java.util.Scanner;

class EditDistanceBU {
    char[] A;
    char[] B;

    int[][] D;

    public EditDistanceBU(char[] a, char[] b) {
        A = a;
        B = b;
        D = new int[b.length + 1][a.length + 1];
    }

    public int run() {

        for (int i = 0; i < D.length; i++) {
            D[i][0] = i;
        }
        for (int i = 0; i < D[0].length; i++) {
            D[0][i] = i;
        }

        for (int i = 1; i < D[0].length; i++) {
            for (int j = 1; j < D.length; j++) {
                int insert = D[j][i - 1] + 1;
                int delete = D[j - 1][i] + 1;
                int substitution = D[j - 1][i - 1] + diff(A[i - 1], B[j - 1]); // a[i -1] and b[j-1] because i and j have offset in 1 cell

                D[j][i] = Math.min(insert, Math.min(delete, substitution));
            }
        }

        return D[D.length - 1][D[0].length - 1];
    }

    private int diff(char a, char b) {
        return a == b ? 0 : 1;
    }


    public void printRestored() {
        int m = D.length - 1;
        int n = D[0].length - 1;

        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();

        while (n > 0 || m > 0) {
            if (n == 0) {
                first.append("-");
                second.append(B[m - 1]);
                m--;
            } else if (m == 0) {
                first.append(A[n - 1]);
                second.append("-");
                n--;
            } else {
                int ins = D[m][n - 1] + 1;
                int del = D[m - 1][n] + 1;
                int sub = D[m - 1][n - 1] + diff(A[n - 1], B[m - 1]);

                if (ins <= del && ins <= sub) {
                    first.append("-");
                    second.append(B[m - 1]);
                    n--;
                } else {
                    if (del <= sub) {
                        first.append("+");
                        second.append(B[m - 1]);
                        m--;
                    } else {
                        first.append("=");
                        second.append(B[m - 1]);
                        n--;
                        m--;
                    }
                }
            }
        }

        System.out.println(first.reverse());
        System.out.println(second.reverse());
    }
}

class EditDistanceTD {

    char[] A;
    char[] B;

    int[][] D;

    public EditDistanceTD(char[] a, char[] b) {
        A = a;
        B = b;
        D = new int[a.length + 1][b.length + 1];
        for (int i = 0; i <= a.length; i++) {
            for (int j = 0; j <= b.length; j++) {
                D[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    public int run() {
        return editDistanceTD(A.length, B.length);
    }

    private int editDistanceTD(int i, int j) {
        if (D[i][j] != Integer.MAX_VALUE) return D[i][j];

        if (i == 0) {
            D[i][j] = j;
        } else if (j == 0) {
            D[i][j] = i;
        } else {
            int insert = editDistanceTD(i, j - 1) + 1;
            int delete = editDistanceTD(i - 1, j) + 1;
            int sub = editDistanceTD(i - 1, j - 1) + diff(A[i - 1], B[j - 1]);
            D[i][j] = Math.min(insert, Math.min(delete, sub));
        }

        return D[i][j];
    }

    private int diff(char a, char b) {
        return a == b ? 0 : 1;
    }

    public void printD() {
        for (int i = 0; i < D.length; i++) {
            for (int j = 0; j < D[0].length; j++) {
                System.out.printf("%5d ", D[i][j]);
            }
            System.out.println();
        }
    }
}

public class Main {

    // https://stepik.org/lesson/13258/step/8?unit=3443
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String a = scanner.nextLine();
        String b = scanner.nextLine();

//        EditDistanceTD editDistanceTD = new EditDistanceTD(a.toCharArray(), b.toCharArray());
//        System.out.println(editDistanceTD.run());

        EditDistanceBU editDistanceBU = new EditDistanceBU(a.toCharArray(), b.toCharArray());
        System.out.println(editDistanceBU.run());

        editDistanceBU.printRestored();
    }
}
