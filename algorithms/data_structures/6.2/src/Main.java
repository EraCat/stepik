import java.util.Scanner;

class TreeNode {
    int value;
    int left;
    int right;
}

public class Main {

    static TreeNode[] tree;

    //https://stepik.org/lesson/45970/step/2?unit=24123
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        tree = new TreeNode[n];

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int value = scanner.nextInt();
            int left = scanner.nextInt();
            int right = scanner.nextInt();
            TreeNode node = new TreeNode();
            node.value = value;
            node.left = left;
            node.right = right;

            if (value > max) {
                max = value;
            }
            if (value < min) {
                min = value;
            }

            tree[i] = node;
        }

        if (n == 0 || isSearchTree(tree[0], min - 1, max)) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }

    }


    private static boolean isSearchTree(TreeNode node, int min, int max) {
        if (node == null) return true;

        boolean result = node.value > min && node.value <= max;
        if (result && node.left != -1) {
            result = isSearchTree(tree[node.left], min, node.value);
        }
        if (result && node.right != -1) {
            result = isSearchTree(tree[node.right], node.value, max);
        }
        return result;
    }
}
