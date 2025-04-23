import java.util.Scanner;

class TreeNode {
    long value;
    int left;
    int right;
}

public class Main {

    static TreeNode[] tree;

    //https://stepik.org/lesson/45970/step/3
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        tree = new TreeNode[n];

        for (int i = 0; i < n; i++) {
            long value = scanner.nextLong();
            int left = scanner.nextInt();
            int right = scanner.nextInt();
            TreeNode node = new TreeNode();
            node.value = value;
            node.left = left;
            node.right = right;

            tree[i] = node;
        }

        if (n == 0 || isSearchTree(tree[0], Long.MIN_VALUE, Long.MAX_VALUE)) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }
    }

    private static boolean isSearchTree(TreeNode node, long min, long max) {
        if (node == null) return true;

        boolean result = node.value > min && node.value < max;
        if (result && node.left != -1) {
            result = isSearchTree(tree[node.left], min, node.value);
        }
        if (result && node.right != -1) {
            result = isSearchTree(tree[node.right], node.value - 1, max);
        }
        return result;
    }
}
