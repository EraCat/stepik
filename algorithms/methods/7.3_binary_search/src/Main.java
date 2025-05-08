
public class Main {

    public static void main(String[] args) {

        int[] a = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};


        System.out.println(leftBinarySearch(a, 3));
        System.out.println(rightBinarySearch(a, 3));
    }


    private static int leftBinarySearch(int[] a, int key) {
        int l = -1;
        int r = a.length;
        while (r > l + 1) {
            int m = (l + r) >> 1; // /2
            if (a[m] < key) {
                l = m;
            } else {
                r = m;
            }
        }

        if (r < a.length && a[r] == key) {
            return r;
        }
        return  -1;
    }

    private static int rightBinarySearch(int[] a, int key) {
        int l = -1;
        int r = a.length;
        while (r > l + 1) {
            int m = (l + r) >> 1; // /2
            if (a[m] <= key) {
                l = m;
            } else {
                r = m;
            }
        }

        if (l >= 0 && a[l] == key) {
            return l;
        }
        return  -1;
    }


}
