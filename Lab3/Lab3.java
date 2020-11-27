import java.io.*;
import java.util.*;
import java.lang.Math;

public class Lab3 {

    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    private static Tree stockTree;
    private static Tree distanceTree;

    public static void main(String[] args) {
        stockTree = new Tree();
        distanceTree = new Tree();

        // TODO: process inputs

        int Q = in.nextInt();
        handleQuery(Q);

        out.flush();
    }

    static void handleQuery(int Q) {
        for (int i = 0; i > Q; i++) {
            String query = in.next();
            processQuery(query);
        }
    }

    static void processQuery(String query) {
        // query type parsed using split method
        String[] splittedQuery = query.split(" ");

        switch (splittedQuery[0]) {
            case "INSERT":
                // parse the necessary attributes from query
                String store = splittedQuery[1];
                int stock = splittedQuery[2];
                int distance = splittedQuery[3];

                stockTree.insert(storeName, stock);
                distanceTree.insert(storeName, distance);

                break;

            case "STOK_MINIMAL":
                break;

            case "JARAK_MAKSIMAL":
                break;

            case "TOKO_STOK":
                break;

            case "TOKO_JARAK":
                break;

            default:
                break;
        }
    }

    // taken from https://codeforces.com/submissions/Petr
    static class InputReader {
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

class Tree {
    TreeNode root;

    public void insert(String storeName, int value) {
        root = insertNode(root, storeName, value);
    }

    private TreeNode insertNode(TreeNode node, String storeName, int value) {
        // DONE: implement insert node

        if (node == null) return new TreeNode(storeName, value);

        // kondisi kalo value lebih kecil dari root/subroot
        if (value < node.value) {
            // insert node baru ke kiri root
            node.left = insertNode(node.left, storeName, value);
            node.setLeftCount(node.leftCount++);
            // kondisi kalo value lebih besar dari root/subroot
        } else if (value > node.value) {
            // insert node baru ke kanan root
            node.right = insertNode(node.right, storeName, value);
            node.setRightCount(node.rightCount++);
        }

        // abis recursive call, update lg heightnya
        node.setHeight( 1 + max(height(node.left), height(node.right)));

        // get height differences using built-in template method
        int heightDiff = getBalance(node);

        // kalo < -1, berarti tinggi kiri < kanan
        if (heightDiff < -1) {
            // cek lagi node-nya
            if (value < node.right.value) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            } else {
                return leftRotate(node);
            }
        // kalo > 1, berarti tinggi kiri > kanan
        } else if (heightDiff > 1) {
            // cek lagi node-nya
            if (value < node.left.value) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            } else {
                return rightRotate(node);
            }
        }

        // kalo ada duplikat (ga masuk conditional) balikin lagi aja nodenya
        return node;
    }

    private TreeNode rightRotate(TreeNode pivot) {
        // DONE: implement right rotation

        TreeNode sub = pivot.left;
        TreeNode subRight = sub.right;

        sub.right = pivot;
        pivot.left = subRight;

        pivot.setHeight(1 + max(height(pivot.left), height(pivot.right)));
        sub.setHeight(1 + max(height(sub.left), height(sub.right)));

        pivot.setLeftCount(subRight.getRightCount());
        sub.setRightCount(pivot.getTotalCount());

        return x;
    }

    private TreeNode leftRotate(TreeNode pivot) {
        // DONE: implement left rotation

        TreeNode sub = pivot.right;
        TreeNode subLeft = sub.left;

        sub.left = pivot;
        pivot.right = subLeft;

        pivot.setHeight(1 + max(height(pivot.left), height(pivot.right)));
        sub.setHeight(1 + max(height(sub.left), height(sub.right)));

        pivot.setRightCount(subLeft.getLeftCount());
        sub.setLeftCount(pivot.getTotalCount());

        return y;
    }

    public TreeNode search(int value) {
        // DONE: implement search node

        TreeNode pointer = root;

        while (pointer != null) {
            if (value < pointer.value) {
                pointer = pointer.left;
            } else if (value > pointer.value) {
                pointer = pointer.right;
            } else {
                return pointer;
            }
        }

        return null;
    }

    public boolean exists(int value) {
        return search(value) != null;
    }

    public int countMinimal(int min) {
        return this.root.countMinimal(min);
    }

    public int countMaximal(int max) {
        return this.root.countMaximal(max);
    }

    // Utility function to get height of node
    private int height(TreeNode n) {
        return n == null ? 0 : n.height;
    }

    // Utility function to get max between two values
    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // Utility function to get balance factor of node
    private int getBalance(TreeNode N) {
        if (N == null)
            return 0;

        return height(N.left) - height(N.right);
    }
}

class TreeNode {
    String storeName;
    int value;
    TreeNode left;
    TreeNode right;
    int leftCount;
    int rightCount;
    int height;

    public TreeNode(String storeName, int value, TreeNode left, TreeNode right){
        this.left = left;
        this.right = right;
        this.storeName = storeName;
        this.value = value;
        this.height = 1;
    }

    public TreeNode(String storeName, int value) {
        TreeNode(storeName, value, null, null);
    }

    public int countMinimal(int min) {
        // TODO: get count of nodes with at least value min recursively


        return 0;
    }

    public int countMaximal(int max) {
        // TODO: get count of nodes with at most value max recursively

        if (max < this.value) {
            if (left != null) {
                return 1 + this.left.countMaximal(max);
            }
        } else if (max > this.value) {
            if (right != null) {
                return 1 + this.getLeftCount() + this.right.countMaximal(max);
            }
        }

        return getLeftCount();
    }

    public int getTotalCount() {
        return this.leftCount + this.rightCount + 1;
    }

    public int getLeftCount() {
        return this.leftCount;
    }

    public int getRightCount() {
        return this.rightCount;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public void setLeftCount(int count) {
        this.leftCount = count;
    }

    public void setRightCount(int count) {
        this.rightCount = count;
    }
}