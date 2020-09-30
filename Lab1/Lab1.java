import java.io.*;
import java.util.*;

public class Lab1 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {
        // Getting the first row input (according to the problem)
        // Which consists of the variables L, N, & Q
        String[] units = {"L", "N", "Q"};
        Map<String, Integer> inpUnits = new HashMap<String, Integer>();
        for (int i = 0; i < 3; i++) {
            // Add input to map using units as Key, and input as Value
            inpUnits.put(units[i], in.nextInt());
        };

        // Getting the N-rows dimension of the wall
        int nRow = 1;
        Map<Integer, ArrayList<Integer>> inpWallDimensions = new HashMap<Integer, ArrayList<Integer>>();
        // First layer of iteration, iterate by N-operations
        for (int i = 0; i < inpUnits.get("N"); i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int K = in.nextInt();

            // Second layer of iteration, iterate by K-rows
            for (int j = 0; j < K; j++) {
                ArrayList<Integer> inpRowDimensions = new ArrayList<Integer>();

                // Third layer of iteration, add bricks to each row
                for (int k = a; k <= b; k++) {
                    inpRowDimensions.add(k);
                };

                // Add input to map using nRow as Key, and inpRowDimensions as Value
                inpWallDimensions.put(nRow, inpRowDimensions);
                nRow++;
            };
        };

        // Getting the Q-number of waterfall cases
        // Map<String, Integer> inpUnits = new HashMap<String, Integer>();
        ArrayList<Integer> inpWaterPositions = new ArrayList<Integer>();
        // Iterate by the nums of Q to get the x value of where the water will start flowing
        for (int i = 0; i < inpUnits.get("Q"); i++) {
            inpWaterPositions.add(in.nextInt());
        };

        System.out.println(inpUnits);
        System.out.println(inpWallDimensions);
        System.out.println(inpWaterPositions);

        out.close();
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