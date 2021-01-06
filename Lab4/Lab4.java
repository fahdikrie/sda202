import java.io.*;
import java.util.*;

// https://codereview.stackexchange.com/questions/186063/hash-table-implementation-using-java
// https://github.com/sideffectjava/Hashtable-DS/blob/master/hashing/HashChaining.java

/**
 * Acknowledgements
 * 1. Nanya ke Althof data structure yg ga dibatasin size untuk nyimpen hashnode
 * 2. Nanya ke Fairuza soal biar ga overflow
 * 3. Nanya ke Hugo soal biar ga overflow
 *
 * lesson learned => pake long untuk operasi yg jumbo2 dan kalo modulo jgn pake += *=
 */

public class Lab4 {

    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    private static HashTable hashtable;
    private static StringBuilder sbOut;
    private static String P;
    private static int X;
    private static int Y;

    public static void main(String[] args) {

        X = in.nextInt();
        Y = in.nextInt();
        P = in.next();

        hashtable = new HashTable(X, Y);
        sbOut = new StringBuilder();

        findAllSubstrings(P);

        out.println(sbOut);
        out.close();
        sbOut.setLength(0);
    }

    public static void findAllSubstrings(String P) {

        List<String> substrings = new ArrayList<>();

        // O(P^2)
        for (int i = 0; i < P.length(); i++) {
            for (int j = i + 1; j <= P.length(); j++) {
                substrings.add(P.substring(i, j));
            }
        }

        Set<String> setSubstrings = new HashSet<>(substrings);

        // O(S)
        for (String substring : setSubstrings) {
            hashtable.add(substring);
        }

        sbOut.append(hashtable.score());
    }


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

class HashTable {

    Map<Integer, HashNode> table;
    int size;
    int X;
    int Y;

    public HashTable(int X, int Y) {
        this.table = new HashMap<>();
        this.size = 0;
        this.X = X;
        this.Y = Y;
    }

    // O(S)
    public void add(String substring) {

        int index = hash(substring);


        if (table.get(index) != null) {
            // O(1)
            HashNode head = table.get(index);
            head.length += 1;

            // O(S)
            while (head.next != null) {
                head = head.next;
                head.length += 1;
            }

            head.next = new HashNode(substring);

        } else {
            table.put(index, new HashNode(substring));
        }
    }

    public int hash(String substring) {
        // UTF-16 'a' => 97

        long index = 0;
        long calcX = 1;

        // O(S)
        for (int i = 0; i < substring.length(); i++) {

            long val = ((int) substring.charAt(i)) - 96;
            index = (index + (val * calcX) % Y) % Y;

            calcX = (calcX * X) % Y;
        }

        return (int) index % Y;
    }

    public int score() {
        int score = 0;

        for (Map.Entry<Integer, HashNode> map : table.entrySet()) {
            // System.out.println(map.getKey() + " " + map.getValue().length);

            HashNode node = map.getValue();

            if (node.length > 1) {
                score += summation(node.length - 1);
            }
        }

        return score;
    }

    public int summation(int length) {

        // sigma length

        int result = 0;

        for (int i = length; i >= 1; i--) {
            result += i;
        }

        return result;
    }

    class HashNode {

        String substring;
        HashNode next = null;
        int length;

        public HashNode(String substring) {
            this.substring = substring;
            this.length = 1;
        }
    }
}