import java.io.*;
import java.util.*;

/**
 * Acknowledgements
 *   1. Insertion mengambil referensi dari slide AVL dan BST SDA (Courtesy Pak Denny)
 *   2. geeksforgeeks (avl insertion logic, inorder traversal method. level order traversal method)
 */

public class Lab3 {

    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    private static Tree stockTree;
    private static Tree distanceTree;
    private static StringBuilder sbOut;

    public static void main(String[] args) {
        stockTree = new Tree();
        distanceTree = new Tree();
        sbOut = new StringBuilder();

        // DONE: process inputs

        int Q = in.nextInt();
        handleQuery(Q);

        out.println(sbOut);
        out.flush();
    }

    static void handleQuery(int Q) {
        for (int i = 0; i < Q; i++) {
            String query = in.next();
            processQuery(query);
        }
    }

    static void processQuery(String query) {
        // query type parsed using split method

        switch (query) {
            case "INSERT":
                // parse the necessary attributes from query
                String insertStore = in.next();
                int insertStock = in.nextInt();
                int insertDistance = in.nextInt();

                stockTree.insert(insertStore, insertStock);
                distanceTree.insert(insertStore, insertDistance);

                break;

            case "STOK_MINIMAL":
                // parse the necessary attributes from query
                int minStock = in.nextInt();

                sbOut.append(stockTree.countMinimal(minStock));
                sbOut.append("\n");

                break;

            case "JARAK_MAKSIMAL":
                // parse the necessary attributes from query
                int maxDistance = in.nextInt();

                sbOut.append(distanceTree.countMaximal(maxDistance));
                sbOut.append("\n");

                break;

            case "TOKO_STOK":
                // parse the necessary attributes from query
                int searchStock = in.nextInt();

                sbOut.append(stockTree.exists(searchStock));
                sbOut.append("\n");

                break;

            case "TOKO_JARAK":
                // parse the necessary attributes from query
                int searchDistance = in.nextInt();

                sbOut.append(distanceTree.exists(searchDistance));
                sbOut.append("\n");

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

    static void stockTreeHelper() {
        // pilih inorder/levelorder
        // stockTree.printInOrder(stockTree.root);
        stockTree.printLevelOrder();

        // print main tree
        out.println(stockTree.getTree());

        // print children (l/r count) of each nodes
        out.print("[");
        for (Integer[] child : stockTree.getChildren()) {
            out.print(Arrays.toString(child) + " ");
        }
        out.println("]");

        // empty tree & children
        stockTree.emptyTree();
        stockTree.emptyChildren();

        // newline & flush
        out.println("");
        out.flush();
    }

    static void distanceTreeHelper() {
        // pilih inorder/levelorder
        // distanceTree.printInOrder(distanceTree.root);
        distanceTree.printLevelOrder();

        // print main tree
        out.println(distanceTree.getTree());

        // print children (l/r count) of each nodes
        out.print("[");
        for (Integer[] child : distanceTree.getChildren()) {
            out.print(Arrays.toString(child) + " ");
        }
        out.println("]");

        // empty tree & children
        distanceTree.emptyTree();
        distanceTree.emptyChildren();

        // newline & flush
        out.println("");
        out.flush();
    }
}

class Tree {
    TreeNode root;
    List<Integer> tree = new ArrayList<>();
    List<Integer[]> children = new ArrayList<>();

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
            node.setLeftCount(node.getLeftCount() + 1);
            // kondisi kalo value lebih besar dari root/subroot
        } else if (value > node.value) {
            // insert node baru ke kanan root
            node.right = insertNode(node.right, storeName, value);
            node.setRightCount(node.getRightCount() + 1);
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
            if (value > node.left.value) {
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
        if (sub == null) return sub;

        TreeNode subRight = sub.right;
        int subRightCount = (
            (subRight != null)
                ? subRight.getTotalCount()
                : 0
        );

        sub.right = pivot;
        pivot.left = subRight;

        pivot.setHeight(1 + max(height(pivot.left), height(pivot.right)));
        sub.setHeight(1 + max(height(sub.left), height(sub.right)));

        pivot.setLeftCount(subRightCount);
        sub.setRightCount(pivot.getTotalCount());

        return sub;
    }

    private TreeNode leftRotate(TreeNode pivot) {
        // DONE: implement left rotation

        TreeNode sub = pivot.right;
        if (sub == null) return sub;

        TreeNode subLeft = sub.left;
        int subLeftCount = (
            (subLeft != null)
                ? subLeft.getTotalCount()
                : 0
        );

        sub.left = pivot;
        pivot.right = subLeft;

        pivot.setHeight(1 + max(height(pivot.left), height(pivot.right)));
        sub.setHeight(1 + max(height(sub.left), height(sub.right)));

        pivot.setRightCount(subLeftCount);
        sub.setLeftCount(pivot.getTotalCount());

        return sub;
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

    public void printInOrder(TreeNode N) {
        if (N == null) return;

        printInOrder(N.left);

        tree.add(N.value);
        children.add(new Integer[]{N.getLeftCount(), N.getRightCount()});

        printInOrder(N.right);
    }

    public void printLevelOrder() {
        for (int i = 1; i <= root.height; i++) {
            printGivenLevel(root, i);
        }
    }

    public void printGivenLevel(TreeNode N, int level) {
        if (N == null) return;

        if (level == 1) {
            tree.add(N.value);
            children.add(new Integer[]{N.getLeftCount(), N.getRightCount()});
        } else if (level > 1) {
            printGivenLevel(N.left, level - 1);
            printGivenLevel(N.right, level - 1);
        }
    }

    public List<Integer> getTree() {
        return tree;
    }

    public List<Integer[]> getChildren() {
        return children;
    }

    public void emptyTree() {
        this.tree.clear();
    }

    public void emptyChildren() {
        this.children.clear();
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
        this(storeName, value, null, null);
    }

    public int countMinimal(int min) {
        // DONE: get count of nodes with at least value min recursively

        // min < this.value
        if (this.value > min) {
            if (left != null) {
                return 1 + this.getRightCount() + this.left.countMinimal(min);
            }
        } else if (this.value < min) {
            if (right != null) {
                return this.right.countMinimal(min);
            } else {
                return 0;
            }
        }

        // kalo min = this.value/root.value, langsung ambil kanannya aja
        return 1 + this.getRightCount();
    }

    public int countMaximal(int max) {
        // DONE: get count of nodes with at most value max recursively

        // max < this.value
        if (this.value > max) {
            if (left != null) {
                return this.left.countMaximal(max);
            } else {
                return 0;
            }
        // max > this.value
        } else if (this.value < max) {
            if (right != null) {
                return 1 + this.getLeftCount() + this.right.countMaximal(max);
            }
        }

        // kalo max = this.value/root.value, langsung ambil kirinya aja
        return 1 + this.getLeftCount();
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