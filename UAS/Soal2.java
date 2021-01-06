import java.io.*;
import java.util.*;
/*
* Nama: Fahdii Ajmalal Fikrie
* NPM: 1906398370
* Kelas: SDA C
*/

class Node {
    Node left;
    Node right;
    int data;

    Node(int data) {
        this.data = data;
    }
}

class BinaryTree {
    Node root;
    long sum;

    BinaryTree() {
        root = null;
    }

    void insert(int data) {
        root = insert(root, data);
    }

    private Node insert(Node root, int data) {
        if (root == null) root = new Node(data);
        else if (root.data < data) root.right = insert(root.right, data);
        else if (root.data > data) root.left = insert(root.left, data);
        return root;
    }

    //To do
    long solve() {
        // referensi dari gfg
        // https://www.geeksforgeeks.org/sum-of-all-the-levels-in-a-binary-search-tree/

        // Method driver untuk ngitung sum
        calc(root, 0);

        return sum;
    }

    void calc(Node node, int level) {
        if (node == null) return;

        // data * (level + 1)
        sum += node.data * (level + 1);

        // recursive calls
        calc(node.left, level + 1);
        calc(node.right, level + 1);
    }

    // long height(Node root) {
    //     if (root.left == null && root.right == null) return 0;

    //     long left = 0;
    //     if (root.left != null) left = height(root.left);

    //     long right = 0;
    //     if (root.right != null) right = height(root.right);

    //     return (Math.max(left, right) + 1);
    // }

    // long solve() {
    //     // level order using queue mengambil referensi dari gfg
    //     // https://www.geeksforgeeks.org/level-order-tree-traversal/

    //     // kalo root null, return 1 (level 0 + 1)
    //     if (root == null) return 1;

    //     long level = 0;
    //     long sum = 0;

    //     Queue<Node> queue = new LinkedList<Node>();
    //     queue.add(root);

    //     while (!queue.isEmpty()) {
    //         Node pointer = queue.poll();
    //         // data + level + 1
    //         sum += pointer.data * (level + 1);

    //         if (pointer.left != null) queue.add(pointer.left);
    //         if (pointer.right != null) queue.add(pointer.right);

    //         level++;
    //     }


    //     return sum;
    // }

    // int maxDepth(Node node) {
    //     // find height mengambil referensi dari gfg juga
    //     // https://www.geeksforgeeks.org/write-a-c-program-to-find-the-maximum-depth-or-height-of-a-tree/

    //     if (node == null)
    //         return 0;
    //     else
    //     {
    //         /* compute the depth of each subtree */
    //         int lDepth = maxDepth(node.left);
    //         int rDepth = maxDepth(node.right);

    //         /* use the larger one */
    //         if (lDepth > rDepth)
    //             return (lDepth + 1);
    //          else
    //             return (rDepth + 1);
    //     }
    // }
}

public class Soal2 {
    private static InputReader in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new PrintWriter(System.out);

        int N = in.nextInt();
        BinaryTree t = new BinaryTree();

        for (int i = 0; i < N; i++) {
            int data = in.nextInt();
            t.insert(data);
        }
        out.println(t.solve());

        out.close();
    }

    static class InputReader {
        // taken from https://codeforces.com/submissions/Petr
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}

