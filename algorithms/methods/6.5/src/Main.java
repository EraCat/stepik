import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {

    private static final Random Random = new Random();

    //https://stepik.org/lesson/13249/step/6?unit=3434
    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String l = reader.readLine();
            String[] s = l.split(" ");
            int n = Integer.parseInt(s[0]);
            int m = Integer.parseInt(s[1]);

            int[] starts = new int[n];
            int[] ends = new int[n];
            for (int i = 0; i < n; i ++) {
                String[] split = reader.readLine().split(" ");
                int ai = Integer.parseInt(split[0]);
                int bi = Integer.parseInt(split[1]);
                starts[i] = ai;
                ends[i] = bi;
            }

            quickSort(starts, 0, n - 1);
            quickSort(ends, 0, n - 1);

//            System.out.println(Arrays.toString(starts));
//            System.out.println(Arrays.toString(ends));

            for (String tmp : reader.readLine().split(" ")) {
                int point = Integer.parseInt(tmp);
                int a = findMaxLess(starts,  point);
                if (a == -1) a = 0;
                int b = findMaxLess(ends, point - 1);
                if (b == -1) b = 0;

                System.out.print(a - b + " ");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int findMaxLess(int[] arr, int key) {
        int l = 0;
        int r = arr.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (arr[m] <= key) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        return l;
    }

    private static void quickSort(int[] in, int left, int right) {
        if (left >= right) return;

        int m = partition(in, left, right);
        quickSort(in, left, m - 1);
        quickSort(in, m + 1, right);
    }

    private static int partition(int[] in, int left, int right) {
        int x = in[left];
        int j = left;
        for (int i = left + 1; i <= right; i++) {
            if (in[i] < x) {
                j++;
                int tmp = in[i];
                in[i] = in[j];
                in[j] = tmp;
            }
        }

        int tmp = in[left];
        in[left] = in[j];
        in[j] = tmp;

        return j;
    }
}
