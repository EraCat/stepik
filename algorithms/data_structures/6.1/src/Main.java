import java.util.Scanner;

class TreeNode {
    int key;
    int left;
    int right;
}


public class Main {

    static TreeNode[] tree;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        tree = new TreeNode[n];
        for (int i = 0; i < n; i++) {
            int key = scanner.nextInt();
            int left = scanner.nextInt();
            int right = scanner.nextInt();

            TreeNode node = new TreeNode();
            node.key = key;
            node.left = left;
            node.right = right;

            tree[i] = node;
        }

        TreeNode root = tree[0];

        inOrder(root);
        System.out.println();
        preOrder(root);
        System.out.println();
        postOrder(root);
        System.out.println();
    }

    private static void inOrder(TreeNode node) {
        if (node == null) return;

        if (node.left != -1) {
            inOrder(tree[node.left]);
        }

        System.out.print(node.key);
        System.out.print(" ");

        if (node.right != -1) {
            inOrder(tree[node.right]);
        }
    }

    private static void preOrder(TreeNode node) {
        if (node == null) return;

        System.out.print(node.key);
        System.out.print(" ");
        if (node.left != -1) {
            preOrder(tree[node.left]);
        }
        if (node.right != -1) {
            preOrder(tree[node.right]);
        }
    }

    private static void postOrder(TreeNode node) {
        if (node == null) return;

        if (node.left != -1) {
            postOrder(tree[node.left]);
        }
        if (node.right != -1) {
            postOrder(tree[node.right]);
        }
        System.out.print(node.key);
        System.out.print(" ");
    }


}
