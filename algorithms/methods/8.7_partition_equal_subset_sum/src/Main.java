import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

class PartitionEqualSubsetSum {

    int n;
    int[] originalSet;
    List<Integer> set;

    int[][] D;

    List<List<Integer>> subsets;
    Set<Integer> usedIndexes = new HashSet<>();

    public PartitionEqualSubsetSum(int n, int[] originalSet) {
        this.n = n;
        this.originalSet = originalSet;
        this.set = new ArrayList<>();
        for (int j : originalSet) {
            set.add(j);
        }
    }

    public boolean run(int subsetNumber) {
        int sum = 0;
        for (int i : originalSet) {
            sum += i;
        }
        if (sum % subsetNumber != 0) return false;
        int subsetSum = sum / subsetNumber;

        D = new int[n + 1][subsetSum + 1];

        for (int i = 0; i < D[0].length; i++) {
            D[0][i] = Integer.MAX_VALUE - 1;
        }
        for (int i = 0; i < D.length; i++) {
            D[i][0] = 0;
        }

        for (int i = 1; i < D.length; i++) {
            for (int j = 1; j < D[0].length; j++) {
                D[i][j] = Integer.MAX_VALUE - 1;
            }
        }

        subsets = new ArrayList<>();
        for (int i = 1; i < D.length; i++) {
            for (int j = 1; j < D[0].length; j++) {
                D[i][j] = D[i - 1][j];

                int number = originalSet[i - 1];
                if (number <= j) {
                    D[i][j] = D[i - 1][j - number] + 1;
                }
            }
        }

        findSubsets(subsetNumber, subsetSum);

        if (usedIndexes.size() != n) {
            return false;
        }
        return true;
    }


    private List<Integer> findSubset(int n, int subsum) {
        int i = n;
        int s = subsum;

        ArrayList<Integer> subset = new ArrayList<>();
        while (s > 0 && i > 0) {
            int number = originalSet[i - 1];
            if (s >= number ) {
                if (D[i - 1][s - number] == D[i][s] - 1 && !usedIndexes.contains(i)) {
                    subset.add(number);
                    usedIndexes.add(i);
                    s -= number;
                }
            }
            i = i - 1;
        }

        return subset;
    }

    public void findSubsets(int subsetNumber, int subsum) {
        for (int i = 0; i < subsetNumber; i++) {
            subsets.add(findSubset(n, subsum));
        }
    }

    public void printD() {
        for (int i = 0; i < D.length; i++) {
            for (int j = 0; j < D[0].length; j++) {
                System.out.print(D[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printSubsets() {
        for (List<Integer> subset : subsets) {
            for (Integer i : subset) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}


public class Main {

    //https://stepik.org/lesson/13262/step/6?unit=3447
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] set = new int[n];
        for (int i = 0; i < n; i++) {
            set[i] = scanner.nextInt();
        }

        Arrays.sort(set);
        PartitionEqualSubsetSum partitionEqualSubsetSum = new PartitionEqualSubsetSum(n, set);

        System.out.println(partitionEqualSubsetSum.run(5));
        partitionEqualSubsetSum.printD();
        partitionEqualSubsetSum.printSubsets();

        //10 12 1 2 10 7 5 19 13 1
        //3522 181 521 515 304 123 2512 312 922 407 146 1932 4037 2646 3871 269
    }
}
