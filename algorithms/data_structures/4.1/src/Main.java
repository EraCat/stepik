import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Table {

    int id;
    int size;

    public Table(int id, int size) {
        this.id = id;
        this.size = size;
    }
}

class DisjointSet {

    List<Table> parents = new ArrayList<>();
    List<Integer> ranks = new ArrayList<>();

    int maxSize = 0;

    public void makeSet(Table table) {
        parents.add(table);
        ranks.add(0);
        maxSize = Math.max(maxSize, table.size);
    }

    public Table find(int tableId) {
        int i = tableId;
        while (i != parents.get(i).id) {
            i = parents.get(i).id;
        }

        return parents.get(i);
    }

    void union(int x, int y) {
        Table tableX = find(x);
        Table tableY = find(y);

        if (tableX.id == tableY.id) {
            return;
        }

        if (ranks.get(tableX.id) > ranks.get(tableY.id)) {
            parents.set(tableY.id, tableX);
            tableX.size += tableY.size;
            maxSize = Math.max(maxSize, tableX.size);
        } else {
            parents.set(tableX.id, tableY);
            if (ranks.get(tableX.id).equals(ranks.get(tableY.id))) {
                ranks.set(tableY.id, ranks.get(tableY.id) + 1);
            }
            tableY.size += tableX.size;
            maxSize = Math.max(maxSize, tableY.size);
        }
    }

}



class Main {

    //https://stepik.org/lesson/41560/step/3?unit=20013
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        DisjointSet ds = new DisjointSet();
        for (int i = 0; i < n; i++) {
            ds.makeSet(new Table(i, scanner.nextInt()));
        }

        for (int i = 0; i < m; i++) {
            int dest = scanner.nextInt() - 1;
            int source = scanner.nextInt() - 1;
            ds.union(source, dest);
            System.out.println(ds.maxSize);
        }
    }
}
