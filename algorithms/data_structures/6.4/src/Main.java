import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Scanner;

class TreeNode {
    int key;
    TreeNode left;
    TreeNode right;
    int height;
    long sum;

    public TreeNode(int key) {
        this.key = key;
        height = 1;
        fixSum();
    }

    public void fixSum() {
        sum = key;
        if (left != null) {
            sum += left.sum;
        }
        if (right != null) {
            sum += right.sum;
        }
    }

    public void fixHeight() {
        height = 1;
        if (left != null) {
            height += left.height;
        }
        if (right != null) {
            height = Math.max(height, right.height + 1);
        }
        fixSum();
    }

    public int balance() {
        int balance = 0;
        if (left != null) {
            balance += left.height;
        }
        if (right != null) {
            balance -= right.height;
        }

        return balance;
    }

    @Override
    public String toString() {
        return "%s (%s - %s)".formatted(key, Optional.ofNullable(left).map(n -> n.key).orElse(null), Optional.ofNullable(right).map(n -> n.key).orElse(null));
    }
}

class AvlTree {

    TreeNode root;
    long sum = 0;

    public AvlTree() {
    }

    public AvlTree(TreeNode root) {
        this.root = root;
    }

    public void insert(int k) {
        if (root == null) {
            root = new TreeNode(k);
            return;
        }
        root = addNode(root, k);
    }

    private TreeNode addNode(TreeNode node, int k) {
        if (k > node.key) {
            if (node.right == null) {
                node.right = new TreeNode(k);
            } else {
                node.right = addNode(node.right, k);
            }
        } else {
            if (node.key == k) return node;
            if (node.left == null) {
                node.left = new TreeNode(k);
            } else {
                node.left = addNode(node.left, k);
            }
        }
        node.fixHeight();
        return rebalance(node);
    }

    public void remove(int k) {
        root = removeNode(root, k);
    }

    private TreeNode removeNode(TreeNode node, int k) {
        if (node == null) return null;

        if (k > node.key) {
            node.right = removeNode(node.right, k);
        } else if (k < node.key) {
            node.left = removeNode(node.left, k);
        } else {
            if (node.left != null && node.right != null) {
                node.key = min(node.right);
                node.right = removeNode(node.right, node.key);
            } else if (node.left != null) {
                node = node.left;
            } else if (node.right != null) {
                node = node.right;
            } else {
                return null;
            }
        }

        node.fixHeight();
        return rebalance(node);
    }

    public TreeNode find(int k) {
        return findNode(root, k);
    }

    private TreeNode findNode(TreeNode node, int k) {
        if (node == null) return null;

        if (k > node.key) {
            return findNode(node.right, k);
        } else if (k < node.key) {
            return findNode(node.left, k);
        } else {
            return node;
        }
    }

    public long sum(int l, int r) {
        int i = getKey(l);
        int j = getKey(r);

        if (i > j) {
            System.out.println("WTF IS THIS " + l + " : " + r + " | " + i + " : " + j);
        }
        sum = sumInternal(i, j);
        return sum;
    }

    public int getKey(int x) {
        return Math.toIntExact((x + sum) % 1_000_000_001);
    }

    public int min(TreeNode node) {
        if (node.left == null) {
            return node.key;
        }
        return min(node.left);
    }

    public TreeNode max(TreeNode node) {
        if (node.right == null) {
            return node;
        }
        return max(node.right);
    }

    private long sumInternal(int i, int j) {
        TreeNode[] split = split(root, i - 1);
        TreeNode[] split1 = split(split[1], j);
        long result = 0;
        if (split1[0] != null) {
            result = split1[0].sum;
        }
        TreeNode merge = merge(split1[0], split1[1]);
        root = merge(split[0], merge);

        return result;
    }

    public TreeNode[] split(TreeNode v, int k) {
        if (v == null) return new TreeNode[]{null, null};

        if (k >= v.key) {
            TreeNode[] split = split(v.right, k);
            split[0] = mergeAvlWithRoot(v.left, split[0], v);
            return split;
        } else {
            TreeNode[] split = split(v.left, k);
            split[1] = mergeAvlWithRoot(split[1], v.right, v);
            return split;
        }
    }

    public TreeNode merge(TreeNode v1, TreeNode v2) {
        if (v1 == null) return v2;
        if (v2 == null) return v1;

        TreeNode max = max(v1);
        v1 = removeNode(v1, max.key);

        return mergeAvlWithRoot(v1, v2, max);
    }

    public TreeNode mergeAvlWithRoot(TreeNode v1, TreeNode v2, TreeNode root) {
        if (v1 == null || v2 == null || Math.abs(v1.height - v2.height) <= 1) {
            root = mergeWithRoot(v1, v2, root);
            root.fixHeight();
            return root;
        } else if (v1.height > v2.height) {
            v1.right = mergeAvlWithRoot(v1.right, v2, root);
            v1.fixHeight();
            return rebalance(v1);
        } else {
            v2.left = mergeAvlWithRoot(v1, v2.left, root);
            v2.fixHeight();
            return rebalance(v2);
        }
    }

    public TreeNode mergeWithRoot(TreeNode v1, TreeNode v2, TreeNode root) {
        root.left = v1;
        root.right = v2;
        return root;
    }

    private TreeNode rebalance(TreeNode node) {
        if (node.balance() == -2 && node.right.balance() != 1) {
            return rotateLeft(node);
        } else if (node.balance() == 2 && node.left.balance() != -1) {
            return rotateRight(node);
        } else if (node.balance() == -2 && node.right.balance() == 1 && node.right.left.balance() >= -1 && node.right.left.balance() <= 1) {
            return bigRotateLeft(node);
        } else if (node.balance() == 2 && node.left.balance() == 1 && node.right.left.balance() >= -1 && node.right.left.balance() <= 1) {
            return bigRotateRight(node);
        }

        return node;
    }

    private TreeNode rotateLeft(TreeNode a) {
        TreeNode b = a.right;
        a.right = b.left;
        b.left = a;

        a.fixHeight();
        b.fixHeight();
        return b;
    }

    private TreeNode rotateRight(TreeNode a) {
        TreeNode b = a.left;
        a.left = b.right;
        b.right = a;

        a.fixHeight();
        b.fixHeight();
        return b;
    }

    private TreeNode bigRotateLeft(TreeNode a) {
        a.right = rotateRight(a.right);
        return rotateLeft(a);
    }

    private TreeNode bigRotateRight(TreeNode a) {
        a.left = rotateLeft(a.left);
        return rotateRight(a);
    }

    @Override
    public String toString() {
        if (root == null) {
            return "null";
        }
        StringBuilder builder = new StringBuilder();
        Queue<TreeNode> treeNodes = new LinkedList<>();
        builder.append(root.key).append("(").append(root.height).append(")").append("(").append(root.sum).append(")").append("\n");
        treeNodes.add(root);
        while (!treeNodes.isEmpty()) {
            int size = treeNodes.size();
            for (int i = 0; i < size; i++) {
                TreeNode poll = treeNodes.poll();
                if (poll == null) continue;
                if (poll.left != null) {
                    treeNodes.add(poll.left);
                    builder.append(poll.left.key).append("(").append(poll.left.height).append(")").append("(").append(poll.left.sum).append(")").append(" ");
                } else {
                    builder.append("?").append(" ");
                }
                if (poll.right != null) {
                    treeNodes.add(poll.right);
                    builder.append(poll.right.key).append("(").append(poll.right.height).append(")").append("(").append(poll.right.sum).append(")").append(" ");
                } else {
                    builder.append("?").append(" ");
                }
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine());
        AvlTree avlTree = new AvlTree();
        for (int i = 0; i < n; i++) {
            String[] s = scanner.nextLine().split(" ");
            switch (s[0]) {
                case "+" -> avlTree.insert(avlTree.getKey(Integer.parseInt(s[1])));
                case "?" -> {
                    TreeNode treeNode = avlTree.find(avlTree.getKey(Integer.parseInt(s[1])));
                    System.out.println(treeNode == null ? "Not found" : "Found");
                }
                case "-" -> avlTree.remove(avlTree.getKey(Integer.parseInt(s[1])));
                case "s" -> System.out.println(avlTree.sum(Integer.parseInt(s[1]), Integer.parseInt(s[2])));
            }
        }

    }
}
