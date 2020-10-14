import java.io.*;
import java.util.*;

public class LadangBobaIzuri {
    static InputReader in;
    static PrintWriter out;
    static int[] ladang;
    static Map<String, int[]> keranjang;
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

        // Input-processing part;
        for (int i = 1; i <= H; i++) {
            harvestDay(i);
            System.out.println("TODAY IS DAY-" + i);
            System.out.println(keranjang.keySet());
            keranjang.values().forEach(n -> System.out.print(Arrays.toString(n) + " "));
            System.out.println();
            System.out.println(izurisQuery);
            System.out.println(visitorsQuery);
            System.out.println();
        }


        // System.out.println();
        // System.out.println(N);
        // System.out.println(M);
        // System.out.println(H);
        // System.out.println(Arrays.toString(ladang));
        // System.out.println(keranjang);
        // System.out.println(Arrays.toString(keranjang.get("KRJ2")));
        // System.out.println(izurisQuery);
        // System.out.println(izurisQuery.poll());
        // System.out.println(izurisQuery);
        // System.out.println(izurisQuery.poll());
        // System.out.println(visitorsQuery);
        // System.out.println(visitorsQuery.poll());
        // System.out.println(visitorsQuery);
        // System.out.println(visitorsQuery.poll());
        // System.out.println(Arrays.toString(availableServices));

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

            int[] keranjangDetails = {Ci, Fi};

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
                String addS = in.next(); // name
                int addC = in.nextInt(); // default capacity
                int addF = in.nextInt(); // enlargment capability

                // add param to queue
                if (subject == "IZURI") {
                    izurisQuery.add(
                        subject + " ADD " +
                        addS + " " +
                        addC + " " +
                        addF
                    );
                } else {
                    visitorsQuery.add(
                        subject + " ADD " +
                        addS + " " +
                        addC + " " +
                        addF
                    );
                }

                break;

            case "SELL":
                // param { S }
                String sellS = in.next(); // name

                // add param to queue
                if (subject == "IZURI") {
                    izurisQuery.add(
                        subject + " SELL " +
                        sellS
                    );
                } else {
                    visitorsQuery.add(
                        subject + " SELL " +
                        sellS
                    );
                }

                break;

            case "UPDATE":
                // param { S, C', F' }
                String updateS = in.next(); // name
                int updateC = in.nextInt(); // default capacity
                int updateF = in.nextInt(); // enlargment capability

                // add param to queue
                if (subject == "IZURI") {
                    izurisQuery.add(
                        subject + " UPDATE " +
                        updateS + " " +
                        updateC + " " +
                        updateF
                    );
                } else {
                    visitorsQuery.add(
                        subject + " UPDATE " +
                        updateS + " " +
                        updateC + " " +
                        updateF
                    );
                }

                break;

            case "RENAME":
                // param { S, S' }
                String oldS = in.next(); // old name
                String newS = in.next(); // new name

                // add param to queue
                if (subject == "IZURI") {
                    izurisQuery.add(
                        subject + " RENAME " +
                        oldS + " " +
                        newS
                    );
                } else {
                    visitorsQuery.add(
                        subject + " RENAME " +
                        oldS + " " +
                        newS
                    );
                }

                break;

            default:
                break;
        }
    }

    public static void harvestDay(int day) {
        // print header "Hari ke-..." for each day
        System.out.println("Hari ke-" + day + ":");

        // check whether is it the first day of harvest or not
        if (day != 1) {
            // if it isn't the first day, handle services for izuki & visitors
            handleServices(day);
        }
    }

    public static void handleServices(int day) {
        // print header "Permintaan yang dilayani"
        System.out.println("Permintaan yang dilayani");
        // Get the value of today's available services
        int availableService = availableServices[day - 2];

        // Iterate by the number of today's available services
        for (int i = 0; i < availableService; i++) {
            // poll query and then split it to array of string
            String[] service = visitorsQuery.poll().split(" ");
            // parse name from splitted query
            String name = service[0];
            // printout name
            System.out.print(name + " ");
            // then proceed to handle the query by passing the splitted query as param
            handleQuery(service);
        }

        // print "IZURI" since izuri's requests are always being handled everyday
        System.out.println("IZURI");

        // handle izuri's request
        String[] service = izurisQuery.poll().split(" ");
        handleQuery(service);
    }

    public static void handleQuery(String[] query) {
        switch(query[1]) {
            case "ADD":
                // parse the necessary information from query
                String addS = query[2]; // name
                int addC = Integer.valueOf(query[3]); // default capacity
                int addF = Integer.valueOf(query[4]); // enlargement capability

                // check whether addS (nama keranjang) exists already or not
                if (keranjang.containsKey(addS)) break;

                // if not, put addS keranjang and its value to hashmap
                int[] add = {addC, addF};
                keranjang.put(addS, add);
                break;

            case "SELL":
                // parse the necessery information from query
                String sellS = query[2]; // name

                // check whether addS (nama keranjang) is exist
                if (!keranjang.containsKey(sellS)) break;

                // if sellS exists, proceed to delete sellS from hashmap
                keranjang.remove(sellS);
                break;

            case "UPDATE":
                // parse the necessary information from query
                String updateS = query[2]; // name
                int updateC = Integer.valueOf(query[3]); // default capacity
                int updateF = Integer.valueOf(query[4]); // enlargement capability

                // check whether updateS (nama keranjang) is exist
                if (!keranjang.containsKey(updateS)) break;

                // if updateS exists, update the value of updateS on hashmap
                int[] update = {updateC, updateF};
                keranjang.put(updateS, update);
                break;

            case "RENAME":
                // parse the necessary information from query
                String oldS = query[2]; // old name
                String newS = query[3]; // new name

                // check whether oldS (nama keranjang) is exist
                if (!keranjang.containsKey(oldS)) break;

                // if oldS exists, change the key name by removing the old ones & putting the new key-value
                int[] value = keranjang.get(oldS);
                keranjang.remove(oldS);
                keranjang.put(newS, value);
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