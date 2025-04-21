import java.util.Scanner;

class DisjointSet {

    int[] parents;
    int[] ranks;

    public DisjointSet(int n) {
        parents = new int[n];
        ranks = new int[n];
    }

    public void makeSet(int x) {
        parents[x] = x;
        ranks[x] = 0;
    }

    public int find(int x) {
        if (x != parents[x]) {
            parents[x]= find(parents[x]);
        }

        return parents[x];
    }

    public void union(int x, int y) {
        int i = find(x);
        int j = find(y);

        if (i == j) {return;}

        if (ranks[i] > ranks[j]) {
            parents[j] = i;
        } else {
            parents[i] = j;
            if (ranks[i] == ranks[j]) {
                ranks[j]++;
            }
        }
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}


class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int e = scanner.nextInt();
        int d = scanner.nextInt();

        DisjointSet disjointSet = new DisjointSet(n);
        for (int i = 0; i < n; i++) {
            disjointSet.makeSet(i);
        }

        for (int i = 0; i < e; i++) {
            int i1 = scanner.nextInt() - 1;
            int i2 = scanner.nextInt() - 1;
            disjointSet.union(i1, i2);
        }

        boolean result = true;
        for (int i = 0; i < d; i++) {
            int i1 = scanner.nextInt() - 1;
            int i2 = scanner.nextInt() - 1;
            if (disjointSet.connected(i1, i2)) {
                result = false;
                break;
            }
        }
        if (result) {
            System.out.println("1");
        } else {
            System.out.println("0");
        }
    }
}
