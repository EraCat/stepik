import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static long counter = 0;

    //https://stepik.org/lesson/13248/step/5?unit=3433
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        counter = 0;

        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = scanner.nextInt();
        }

//        int[] ints = mergeSort(A, 0, A.length - 1);
        mergeSort(A, 0, A.length - 1, new int[A.length]);
        System.out.println(Arrays.toString(A));

        System.out.println(counter);
    }

    // l - left index
    // r - right !INDEX!
    private static int[] mergeSort(int[] a, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            return merge(mergeSort(a, l, m), mergeSort(a, m + 1, r));
        }
        return new int[]{a[l]};
    }

    private static void mergeSort(int[] a, int l, int r, int[] result) {
        if (r <= l + 1) return;

        int m = (l + r) >> 1;
        mergeSort(a, l, m, result);
        mergeSort(a, m, r, result);
        merge(l, m, r, a, result);
    }

    private static int[] merge(int[] a1, int[] a2) {
        int i1 = 0;
        int i2 = 0;
        int[] result = new int[a1.length + a2.length];
        for (int i = 0; i < result.length; i++) {
            if (i1 < a1.length && i2 < a2.length) {
                if (a1[i1] <= a2[i2]) {
                    result[i] = a1[i1];
                    i1++;
                } else {
                    counter += a1.length - i1;
                    result[i] = a2[i2];
                    i2++;
                }
            } else if (i1 < a1.length) {
                result[i] = a1[i1];
                i1++;
            } else {
                result[i] = a2[i2];
                i2++;
            }
        }

        return result;
    }

    private static void merge(int l, int m, int r, int[] source, int[] result) {
        int i = l;
        int j = m;
        for (int k = l; k < r; k++) {
            if (j == r || (i < m && source[i] <= source[j])) {
                result[k] = source[i];
                i++;
            } else {
                result[k] = source[j];
                j++;
                counter += m - i;
            }
        }
        System.arraycopy(result, l, source, l, r - l);
    }

    private static long naive(int[] A) {
        long counter = 0;

        for (int i = 0; i < A.length; i++) {
            for (int j = i + 1; j < A.length; j++) {
                if (A[i] > A[j]) {
                    counter++;
                }
            }
        }

        return counter;
    }

}
