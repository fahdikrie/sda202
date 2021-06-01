import java.io.*;
import java.util.*;

public class Lab1 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {
        // Getting the first row input (according to the problem)
        // Which consists of the variables L, N, & Q
        String[] units = {"L", "N", "Q"};
        Map<String, Integer> inpUnits = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            // Add input to map using units as Key, and input as Value
            inpUnits.put(units[i], in.nextInt());
        };

        // Getting the N-rows dimension of the wall
        Map<Integer, ArrayList<Integer>> inpWallDimensions = new HashMap<>();
        // First layer of iteration, iterate by N-operations
        for (int i = 0; i < inpUnits.get("N"); i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int K = in.nextInt();

            // Second layer of iteration, iterate by L-length (or width)
            for (int j = 1; j <= inpUnits.get("L"); j++) {
                ArrayList<Integer> inpColumnDimensions = new ArrayList<>();

                // Third layer of iteration, iterate by K-rows
                for (int k = 0; k < K; k++) {
                    if (a <= j && j <= b) {
                        inpColumnDimensions.add(j);
                    };
                };

                // Check if key J is already on hashmap
                if (inpWallDimensions.containsKey(j)) {
                    // if true, append j's value to inpColunmDimensions
                    inpColumnDimensions.addAll(inpWallDimensions.get(j));
                };

                inpWallDimensions.put(j, inpColumnDimensions);
            };
        };

        // Getting the Q-number of waterfall cases
        ArrayList<Integer> inpWaterPositions = new ArrayList<>();
        // Iterate by the nums of Q to get the x value of where the water will start flowing
        for (int i = 0; i < inpUnits.get("Q"); i++) {
            inpWaterPositions.add(in.nextInt());
        };

        for (Integer pos: inpWaterPositions) {
            int leftBorder = pos;
            int rightBorder = pos;

            // Iterate forward
            int tempRightHeight = inpWallDimensions.get(pos).size();
            if (pos < inpUnits.get("L")) {
                for (int a = pos + 1; a <= inpUnits.get("L"); a++) {
                    if (inpWallDimensions.get(a).size() <= tempRightHeight) {
                        rightBorder = a;
                        tempRightHeight = inpWallDimensions.get(a).size();
                    } else {
                        break;
                    };
                };
            };

            // Iterate backward
            int tempLeftHeight = inpWallDimensions.get(pos).size();
            if (pos > 1) {
                for (int a = pos - 1; a >= 1; a--) {
                    if (inpWallDimensions.get(a).size() <= tempLeftHeight) {
                        leftBorder = a;
                        tempLeftHeight = inpWallDimensions.get(a).size();
                    } else {
                        break;
                    };
                };
            };

            StringBuilder sb = new StringBuilder();
            sb.append(leftBorder).append(" ").append(rightBorder);
            System.out.println(sb.toString());
        };

        out.close();
    };

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