import java.io.*;
import java.util.*;

public class LadangBobaIzuri {
    static InputReader in;
    static PrintWriter out;
    static int[] ladang;
    static Map<String, ArrayList<Integer>> keranjang;
    static Queue<String> izurisQuery;
    static Queue<String> visitorsQuery;
    static int[] availableServices;

    public static void main(String[] args) {
        // Instantiate I/O object instances
        in = new InputReader(System.in);
        out = new PrintWriter(System.out);

        // First part of input; asks how many ladangs are available, represented as N
        int N = inputLadang();

        // Second part of input; asks how many starting keranjangs are available, represented as M
        int M = inputKeranjang();

        // Third part of input; asks how many days will the harvests take place, represented as H
        int H = inputHari();

        System.out.println();
        System.out.println(N);
        System.out.println(M);
        System.out.println(H);
        System.out.println(Arrays.toString(ladang));
        System.out.println(keranjang);
        System.out.println(izurisQuery);
        System.out.println(izurisQuery.poll());
        System.out.println(izurisQuery);
        System.out.println(izurisQuery.poll());
        System.out.println(visitorsQuery);
        System.out.println(visitorsQuery.poll());
        System.out.println(visitorsQuery);
        System.out.println(visitorsQuery.poll());
        System.out.println(Arrays.toString(availableServices));

        out.close();
    }

    public static int inputLadang() {
        // First part of input; asks how many ladangs are available as N
        int N = in.nextInt();
        ladang = new int[N];
        // Iterate by N-times, asking how many bobas are available on each ladang
        for (int i = 0; i < N; i++) {
            ladang[i] = in.nextInt();
        };

        // Returns N
        return N;
    }

    public static int inputKeranjang() {
        // Second part of input; asks how many starting keranjangs are available as M
        int M = in.nextInt();
        keranjang = new HashMap<>();
        // Iterate by M-times, asking the details of each keranjang
        for (int i = 0; i < M; i++) {
            String Si = in.next();
            int Ci = in.nextInt();
            int Fi = in.nextInt();

            ArrayList<Integer> keranjangDetails = new ArrayList<>();
            keranjangDetails.add(Integer.valueOf(Ci));
            keranjangDetails.add(Integer.valueOf(Fi));

            // append S, C, and F into Hashmap, with S as keys
            keranjang.put(Si, keranjangDetails);
        };

        // Returns S
        return M;
    }

    public static int inputHari() {
        // Third part of input; asks how many days the harvests take place as H
        int H = in.nextInt();
        // Iterate by H-times, asking about queries for Izuri & other visitors
        izurisQuery = new ArrayDeque<>();
        visitorsQuery = new ArrayDeque<>();
        availableServices = new int[H - 1];
        for (int i = 0; i < H - 1; i++) {
            // Asks for Izuri's query, using query type as parameter
            inputQuery(in.next(), "IZURI");

            // Asks how many visitor is available on each day, represented as Y
            int Y = in.nextInt();
            // Iterate by Y-times, asking the details of the query from each visitor
            for (int j = 0; j < Y; j++) {
                // Asks for visitor's name
                String name = in.next();
                // Then asks the visitor's query as a param for inputQuery
                inputQuery(in.next(), name);
            }
            // Finally, asks for the number of available services for each day
            availableServices[i] = in.nextInt();
        }

        // Returns H
        return H;
    }

    public static void inputQuery(String query, String subject) {
        switch(query) {
            case "ADD":
                // param { S, C', F' }
                String Sc = in.next(); // name
                int Cc = in.nextInt(); // default capacity
                int Fc = in.nextInt(); // enlargment capability

                // add param to queue
                if (subject == "IZURI") {
                    izurisQuery.add("ADD " + Sc + " " + Cc + " " + Fc);
                } else {
                    visitorsQuery.add(subject + " ADD " + Sc + " " + Cc + " " + Fc);
                }

                break;

            case "SELL":
                // param { S }
                String S = in.next(); // name

                // add param to queue
                if (subject == "IZURI") {
                    izurisQuery.add("SELL " + S);
                } else {
                    visitorsQuery.add(subject + " SELL " + S);
                }

                break;

            case "UPDATE":
                // param { S, C', F' }
                String Si = in.next(); // name
                int Ci = in.nextInt(); // default capacity
                int Fi = in.nextInt(); // enlargment capability

                // add param to queue
                if (subject == "IZURI") {
                    izurisQuery.add("UPDATE " + Si + " " + Ci + " " + Fi);
                } else {
                    visitorsQuery.add(subject + " UPDATE " + Si + " " + Ci + " " + Fi);
                }

                break;

            case "RENAME":
                // param { S, S' }
                String oldS = in.next(); // old name
                String newS = in.next(); // new name

                // add param to queue
                if (subject == "IZURI") {
                    izurisQuery.add("RENAME " + oldS + " " + newS);
                } else {
                    visitorsQuery.add(subject + " RENAME " + oldS + " " + newS);
                }

                break;

            default:
                break;
        }
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