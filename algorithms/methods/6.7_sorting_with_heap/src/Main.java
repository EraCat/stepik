
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;


class MinHeap {

    int[] arr;
    int size = 0;

    public MinHeap(int[] arr) {
        this.arr = arr;
        size = arr.length;
    }

    public static MinHeap sortWithHeap(int[] A) {
        MinHeap minHeap = new MinHeap(A);

        int size1 = minHeap.size();
        for (int i = size1 / 2; i >= 0; i--) {
            minHeap.siftDown(i);
        }

        for (int i = size1; i >= 2; i--) {

            int temp = minHeap.arr[0];
            minHeap.arr[0] = minHeap.arr[minHeap.size - 1];
            minHeap.arr[minHeap.size - 1] = temp;
            minHeap.size--;
            minHeap.siftDown(0);
        }

        return minHeap;
    }

    public int extract() {
        int result = arr[0];
        arr[0] = arr[size - 1];
        size--;
        siftDown(0);
        return result;
    }

    public void insert(int v) {
        arr[size] = v;
        size++;
        siftUp(size() - 1);
    }

    public int size() {
        return size;
    }

    private void siftUp(int i) {
        int j = i;
        while (j > 1 && arr[parent(j)] > arr[j]) {
            int temp = arr[parent(j)];
            arr[parent(j)] = arr[j];
            arr[j] = temp;
            j = parent(j);
        }
    }

    private void siftDown(int i) {
        int max = i;
        int l = left(i);
        int r = right(i);
        if (l < size() && arr[l] > arr[max]) {
            max = l;
        }
        if (r < size() && arr[r] > arr[max]) {
            max = r;
        }
        if (max != i) {
            int temp = arr[i];
            arr[i] = arr[max];
            arr[max] =  temp;
            siftDown(max);
        }
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int left(int i) {
        return 2 * i + 1;
    }

    private int right(int i) {
        return 2 * i + 2;
    }

    @Override
    public String toString() {
        return Arrays.toString(arr);
    }
}

record Item(int sum, int a, int ia) {}

public class Main {


    //https://stepik.org/lesson/13251/step/4?unit=3436
    //https://stepik.org/lesson/13251/step/5?unit=3436
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine());
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = scanner.nextInt();
        }
        int[] B = new int[n];
        for (int i = 0; i < n; i++) {
            B[i] = scanner.nextInt();
        }

        MinHeap minHeapA = MinHeap.sortWithHeap(A);
        MinHeap minHeapB = MinHeap.sortWithHeap(B);

        PriorityQueue<Item> items = new PriorityQueue<>(Comparator.comparingInt(Item::sum));

        for (int i = 0; i < n; i++) {
            int ia = 0;
            items.add(new Item(A[i] + B[ia], A[i], ia));
        }

        while (!items.isEmpty()) {
            Item poll = items.poll();
            System.out.print(poll.sum() + " ");
            int ia = poll.ia();
            ia++;
            if (ia < n) {
                items.add(new Item(poll.a() + B[ia], poll.a(), ia));
            }
        }

    }
}
