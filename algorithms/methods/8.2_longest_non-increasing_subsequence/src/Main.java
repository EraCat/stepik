import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

class Length {
    int value;
    int index;

    public Length(int value, int index) {
        this.value = value;
        this.index = index;
    }

    public int value() {
        return value;
    }

    public int index() {
        return index;
    }

    @Override
    public String toString() {
        return "Length{" +
                "value=" + value +
                ", index=" + index +
                '}';
    }
}

class Pair {
    int left;
    int right;

    public Pair(int left, int right) {
        this.left = left;
        this.right = right;
    }
}

public class Main {

    static int[] d;
    static int[] p; // index from A in D array
    static int[] prev; // index from A on previous index in the subsequence

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        int[] a = new int[n];
        String[] s = reader.readLine().split(" ");
        for (int i = 0; i < s.length; i++) {
            a[i] = Integer.parseInt(s[i]);
        }

        Pair pair = longestNonincreasingSubsequence(a);

        System.out.println(pair.left);
        restore(a, pair.left);
    }

    private static PriorityQueue<Length> longestNonincreasingSubsequenceIndex(int[] a) {
        d = new int[a.length];

        PriorityQueue<Length> pq = new PriorityQueue<>(Comparator.comparingInt(Length::value).reversed().thenComparing(Comparator.comparingInt(Length::index).reversed()));
        Stack<Length> stack = new Stack<>();

        for (int i = 0; i < a.length; i++) {
            int cl = 1;
            while (!pq.isEmpty() && cl == 1) {
                Length l = pq.poll();
                stack.push(l);
                if (a[i] <= a[l.index]) {
                    cl = l.value + 1;
                }
            }
            while (!stack.isEmpty()) {
                pq.add(stack.pop());
            }

            pq.add(new Length(cl, i));
        }

        return pq;
    }

    private static Pair longestNonincreasingSubsequence(int[] a) {
        d = new int[a.length + 1];
        p = new int[a.length + 1];
        prev = new int[a.length];
        d[0] = Integer.MAX_VALUE;
        for (int i = 1; i < d.length; i++) {
            d[i] = Integer.MIN_VALUE;
        }

        int maxLength = 0;
        for (int i = 0; i < a.length; i++) {
            int j = binarySearchRightGreat(d, a[i]);

            if ((d[j - 1] >= a[i] && a[i] > d[j])) {
                d[j] = a[i];
                prev[i] = p[j - 1];
                p[j] = i;
                maxLength = Math.max(maxLength, j);
            }
        }

        return new Pair(maxLength, 0);
    }


    //[2147483647, -2147483648,-2147483648] v = 10
    // resIndex = 1
    private static int binarySearchRightGreat(int[] a, int v) {
        int l = -1;
        int r = a.length;
        while (r > l + 1) {
            int m = (l + r) >> 1;
            if (a[m] < v) {
                r = m;
            } else {
                l = m;
            }
        }

        return r;
    }

    private static void restore(int[] a, PriorityQueue<Length> pq) {
        Length prev = pq.poll();
        System.out.println(prev.value);
        int[] indexes = new int[prev.value];
        int index = prev.value - 1;
        indexes[index] = prev.index;
        while (!pq.isEmpty()) {
            Length curr = pq.poll();
            if (curr.value == prev.value - 1 && curr.index < prev.index && a[curr.index] >= a[prev.index]) {
                index--;
                indexes[index] = curr.index;
                prev = curr;
            }
        }

        for (int i = 0; i < indexes.length; i++) {
            System.out.print(indexes[i] + 1 + " ");
        }
    }


    private static void restore(int[] a, int maxLength) {
        Stack<Integer> stack = new Stack<>();

        int index = p[maxLength];
        for (int i = maxLength; i >= 1; i--) {
            stack.push(index);
            index = prev[index];
        }
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + 1 + " ");
        }
    }

}
