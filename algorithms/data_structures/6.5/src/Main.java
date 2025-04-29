import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class Node {
    Node parent;
    int w;
    Node left;
    Node right;
    String str;

    public Node(String str) {
        this.w = str.length();
        this.str = str;
    }

    public Node() {
    }

    public void fixWeight() {
        w = 0;
        if (str != null) {
            w = str.length();
        }
        if (left != null) {
            w += left.w;
        }
        if (right != null) {
            w += right.w;
        }
    }
}

class SplayTree {

    Node root;

    public Node find(int k) {
        Node node = findLast(root, k);
        splay(node);
        root = node;
        if (node.w == k) {
            return node;
        } else {
            return null;
        }
    }

    public void insert(int insertIndex, String c) {
        if (root == null) {
            root = new Node(c);
            return;
        }
        Node add = add(root, insertIndex, c);
        splay(add);
        root = add;
    }

    private Node findLast(Node node, int k) {
        if (k < node.w) {
            if (node.left != null) {
                return findLast(node.left, k);
            } else {
                return node;
            }
        } else if (k > node.w) {
            if (node.right != null) {
                return findLast(node.right, k);
            } else {
                return node;
            }
        } else {
            return node;
        }
    }

    private Node add(Node node, int i, String str) {
        Node[] trees = split(node, i);
        Node tree2 = new Node(str);
        return merge(merge(trees[0], tree2), trees[1]);
    }

    private void splay(Node node) {
        while (node.parent != null) {
            if (node == node.parent.left) {
                if (parentParent(node) == null) {
                    rotateRight(node.parent);
                } else if (node.parent == parentParent(node).left) {
                    rotateRight(parentParent(node));
                    rotateRight(node.parent);
                } else {
                    rotateRight(node.parent);
                    rotateLeft(node.parent);
                }
            } else {
                if (parentParent(node) == null) {
                    rotateLeft(node.parent);
                } else if (node.parent == parentParent(node).right) {
                    rotateLeft(parentParent(node));
                    rotateLeft(node.parent);
                } else {
                    rotateLeft(node.parent);
                    rotateRight(node.parent);
                }
            }
        }
    }

    private Node parentParent(Node node) {
        if (node == null || node.parent == null) return null;

        return node.parent.parent;
    }

    private void rotateLeft(Node node) {
        Node parent = node.parent;
        Node right = node.right;
        if (parent != null) {
            if (parent.left == node) {
                parent.left = right;
            } else {
                parent.right = right;
            }
        }
        Node tmp = right.left;
        right.left = node;
        node.right = tmp;
        node.parent = right;
        right.parent = parent;

        if (node.right != null) {
            node.right.parent = node;
        }
    }

    private void rotateRight(Node node) {
        Node parent = node.parent;
        Node left = node.left;
        if (parent != null) {
            if (parent.right == node) {
                parent.right = left;
            } else {
                parent.left = left;
            }
        }
        Node tmp = left.right;
        left.right = node;
        node.left = tmp;
        node.parent = left;
        left.parent = parent;
        if (node.left != null) {
            node.left.parent = node;
        }
    }

    public Node[] split(Node node, int i) {
        Node tree1 = new Node(), tree2 = new Node();
        if (node.left != null) {
            if (node.left.w >= i) {
                Node[] res = split(node.left, i);
                tree1 = res[0];
                tree2.left = res[1];
                tree2.right = node.right;
                tree2.fixWeight();
            } else {
                Node[] res = split(node.right, i - node.left.w);
                tree1.left = node.left;
                tree1.right = res[0];
                tree1.fixWeight();
                tree2 = res[1];
            }
        } else {
            if (i == 1) {
                tree1.str = String.valueOf(node.str.charAt(0));
            } else {
                tree1.str = node.str.substring(0, i);
            }
            tree2.str = node.str.substring(i);
            tree1.w = i;
            tree2.w = node.str.length() - i;
        }
        return new Node[]{tree1, tree2};
    }

    public Node merge(Node v1, Node v2) {
        Node node = new Node();
        node.left = v1;
        node.right = v2;
        node.fixWeight();

        return node;
    }

    public String getValue() {
        StringBuilder builder = new StringBuilder();
        valueIter(root, builder);
        return builder.toString();
    }

    public void value(Node node, StringBuilder builder) {
        if (node.left != null) {
            value(node.left, builder);
        }
        if (node.right != null) {
            value(node.right, builder);
        }
        if (node.str != null) {
            builder.append(node.str);
        }
    }

    public void valueIter(Node node, StringBuilder builder) {
        if (node == null) return;
        Node current = node;

        Stack<Node> s = new Stack<>();
        while (!s.isEmpty() || current != null) {
            if (current != null) {
                s.push(current);
                current = current.left;
            } else {
                current = s.pop();
                if (current.str != null) builder.append(current.str);
                current = current.right;
            }
        }
    }

    public Node remove(int begin, int end) {
        return remove(root, begin, end);
    }

    public Node remove(Node node, int begin, int end) {
        Node[] trees = split(node, begin);
        Node[] tree3 = split(trees[1], end - begin + 1);
        root = merge(trees[0], tree3[1]);
        return tree3[0];
    }

    private Node max(Node v1) {
        if (v1.right != null) {
            return max(v1.right);
        } else {
            return v1;
        }
    }

    @Override
    public String toString() {
        if (root == null) {
            return "null";
        }
        StringBuilder builder = new StringBuilder();
        Queue<Node> treeNodes = new LinkedList<>();
        builder.append(root.w).append("(").append(root.str).append(")").append("\n");
        treeNodes.add(root);
        while (!treeNodes.isEmpty()) {
            int size = treeNodes.size();
            for (int i = 0; i < size; i++) {
                Node poll = treeNodes.poll();
                if (poll == null) continue;
                if (poll.left != null) {
                    treeNodes.add(poll.left);
                    builder.append(poll.left.w).append("(").append(poll.left.str).append(")").append(" ");
                } else {
                    builder.append("?").append(" ");
                }
                if (poll.right != null) {
                    treeNodes.add(poll.right);
                    builder.append(poll.right.w).append("(").append(poll.right.str).append(")").append(" ");
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
    public static void main(String[] args) throws IOException {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        String str = scanner.readLine();
        int n = Integer.parseInt(scanner.readLine());

        SplayTree splayTree = new SplayTree();
        splayTree.insert(0, str);
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < n; i++) {
            String[] s = scanner.readLine().split(" ");
            int j1 = Integer.parseInt(s[0]);
            int j2 = Integer.parseInt(s[1]);
            int k = Integer.parseInt(s[2]);

            Node remove = splayTree.remove(j1, j2);

            builder.setLength(0);
            splayTree.valueIter(remove, builder);
            splayTree.insert(k, builder.toString());
        }

        System.out.println(splayTree.getValue());
    }


}
