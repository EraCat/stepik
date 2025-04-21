import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class HashChainTable {

    private static final int P = 1_000_000_007;
    private static final int X = 263;

    LinkedList<String>[] hashes;

    public HashChainTable(int m) {
        hashes = new LinkedList[m];
    }

    public void add(String str) {
        int hash = hash(str);
        if (hashes[hash] == null) {
            hashes[hash] = new LinkedList<>();
        }
        if (!hashes[hash].contains(str)) {
            hashes[hash].addFirst(str);
        }
    }

    public void del(String str) {
        int hash = hash(str);
        if (hashes[hash] == null) {
            return;
        }

        hashes[hash].remove(str);
    }

    public boolean find(String str) {
        int hash = hash(str);
        if (hashes[hash] != null && hashes[hash].contains(str)) {
            return true;
        }
        return false;
    }

    public List<String> check(int i) {
        List<String> result = hashes[i];
        if (result == null) {
            result = List.of();
        }
        return result;
    }

    private int hash(String str) {
        long hash = 0;
        for (int i = 0; i < str.length(); i++) {
            int code = str.charAt(i);
            hash = (hash + code * (powX(i) % P)) % P;
        }

        hash %= P;
        hash %= hashes.length;

        return Math.toIntExact(hash);
    }

    private long powX(int i) {
        long result = 1;
        for (int j = 0; j < i; j++) {
            result = (result * X) % P;
        }

        return result;
    }

}

//https://stepik.org/lesson/41562/step/2?unit=20016
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = Integer.parseInt(scanner.nextLine());
        int n = Integer.parseInt(scanner.nextLine());

        HashChainTable hashChainTable = new HashChainTable(m);

        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();

            String[] items = line.split(" ");
            if (line.startsWith("a")) {
                hashChainTable.add(items[1]);
            } else if (line.startsWith("c")) {
                List<String> check = hashChainTable.check(Integer.parseInt(items[1]));
                System.out.println(String.join(" ", check));
            } else if (line.startsWith("d")) {
                hashChainTable.del(items[1]);
            } else if (line.startsWith("f")) {
                boolean b = hashChainTable.find(items[1]);
                System.out.println(b ? "yes" : "no");
            }
        }

    }
}
