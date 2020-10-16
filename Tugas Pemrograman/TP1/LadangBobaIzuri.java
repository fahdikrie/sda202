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
    // static ArrayList<String> results;
    static String[] results;

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
            harvestDay(i, N, M, H);
        }

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
        izurisQuery = new LinkedList<>();
        visitorsQuery = new LinkedList<>();
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

    public static void harvestDay(int day, int N, int M, int H) {
        // print header "Hari ke-..." for each day
        System.out.println("Hari ke-" + day + ":");

        // check whether is it the first day of harvest or not
        if (day != 1) {
            // if it isn't the first day, handle services for izuki & visitors
            handleServices(day);
        }

        // print header "Hasil Panen"
        System.out.println("Hasil Panen");
        handleHarvest(day, N, M, H);
        if (day != H) System.out.println();
    }

    public static void handleServices(int day) {
        // print header "Permintaan yang dilayani"
        System.out.println("Permintaan yang dilayani");
        // Get the value of today's available services
        int availableService = availableServices[day - 2];

        // Iterate by the number of today's available services
        for (int i = 0; i < availableService; i++) {
            // poll query and then split it to array of string
            String[] queryset = visitorsQuery.poll().split(" ");
            // parse name from splitted query
            String name = queryset[0];
            // printout name
            System.out.print(name + " ");
            // then proceed to handle the visitor's query by passing the splitted query as param
            handleQuery(queryset);
        }

        // print "IZURI" since izuri's requests are always being handled everyday
        System.out.println("IZURI");

        // proceed to handle handle izuri's query
        String[] queryset = izurisQuery.poll().split(" ");
        handleQuery(queryset);
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
                int[] add = new int[]{addC, addF};
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
                if (!keranjang.containsKey(oldS) || keranjang.containsKey(newS)) break;

                // if oldS exists, change the key name by removing the old ones & putting the new key-value
                int[] value = keranjang.get(oldS);
                keranjang.remove(oldS);
                keranjang.put(newS, value);
                break;

            default:
                break;
        }
    }

    public static void handleHarvest(int day, int N, int M, int H) {
        // results = new ArrayList<>();
        results = new String[keranjang.size()];

        // method for handling for the dth day's harvest
        int index = 0;
        for (Map.Entry<String, int[]> entry : keranjang.entrySet()) {
            // parse informations for each keranjang from hashmap
            String S = entry.getKey();
            int C = entry.getValue()[0];
            int F = entry.getValue()[1];

            // number of harvested bobas on that day
            int harvestedBobas = 0;
            // 2D array to store all the possibilites using knapsack algorithm
            int[][] DP = new int[N][N + 1];
            // iterate through the N-number of ladang
            for (int i = 0; i < N; i++) {
                // maximum bobas relative to the current j-point for when i == 0
                int maxBobas = (
                    (ladang[0] <= C)
                        ? ladang[0]
                        : 0
                );

                // second dimensional iteration for knapsack problem
                for (int j = i; j < N + 1; j++) {
                    if (i == j) continue;

                    // different treatment when i == 0
                    if (i == 0) {
                        DP[i][j] = (
                            maxBobas
                        );

                    } else {
                        int previousVal = DP[i][j - 1];
                        int previousRowVal = DP[i - 1][j - 1];
                        int previousBobas = ladang[j - 1];

                        DP[i][j] = (
                            (previousVal + previousBobas >= previousRowVal)
                                ? (previousBobas + previousVal <= C)
                                    ? previousBobas + previousVal
                                    : C
                                : previousRowVal
                        );
                    }

                    // checking the highest maximum possible bobas that can be harvested
                    if (DP[i][j] > harvestedBobas) harvestedBobas = DP[i][j];

                    // change maxBobas for the next iteration if the condition meets
                    if ((j < N) &&
                        (ladang[j] > maxBobas) &&
                        !(ladang[j] > C)) {
                        maxBobas = ladang[j];
                    }
                }

                // increment capacity (C) with enlargement capability (F)
                C += F;
            }

            // add harvestedBobas after each iteration
            // results.add(S + " " + harvestedBobas);
            results[index++] = S + " " + harvestedBobas;
        }

        // sort the current results immediately
        // handleSort(results, results.size());
        handleSort(results, results.length);
        // handleSort(results, 0, results.size() - 1);
        // then print each results after being sorted
        for (String string : results) {
            System.out.println(string);
        }
        // System.out.println(Arrays.toString(results.toArray()));
    }

    public static void handleSort(String[] arr, int length) {
        // basecase condition
        if (length < 2) {
            return;
        }

        int mid = length / 2;
        String[] left = new String[mid];
        String[] right = new String[length - mid];

        for (int i = 0; i < mid; i++) {
            left[i] = arr[i];
        }

        for (int j = mid; j < length; j++) {
            right[j - mid] = arr[j];
        }

        handleSort(left, mid);
        handleSort(right, length - mid);
        handleMergeSort(arr, left, right, mid, length - mid);
    }

    public static void handleMergeSort(String[] arr, String[] left, String[] right, int l, int r) {
        // initialize pointers for arr, left, and right arrays
        int ii = 0, jj = 0, kk = 0;
        // start the while-loop using the condition below
        while (ii < l && jj < r) {
            // parse the value of harvested bobas from the string
            int leftBoba = Integer.parseInt(left[ii].split(" ")[1]);
            int rightBoba = Integer.parseInt(right[jj].split(" ")[1]);
            String leftName = left[ii].split(" ")[0];
            String rightName = right[jj].split(" ")[0];

            // compare the number of harvested bobas of left & right
            if (leftBoba > rightBoba) {
                arr[kk++] = left[ii++];
            } else if (leftBoba < rightBoba) {
                arr[kk++] = right[jj++];
            } else {
                // compare lexicographically if the value of harvested bobas
                // on left and right pointer are the same
                if (leftName.compareTo(rightName) < 0) {
                    // if leftName < rightName, then leftName goes first
                    arr[kk++] = left[ii++];
                } else {
                    // vice versa
                    arr[kk++] = right[jj++];
                }
            }

        }

        // clean the leftover values on both left & right array
        while (ii < l) {
            arr[kk++] = left[ii++];
        }
        while (jj < r) {
            arr[kk++] = right[jj++];
        }
    }

    // public static voleftid handleSort(ArrayList<String> arr, int left, int right) {
    //     // merge condition
    //     if (left < right) {
    //         int middle = (left + right) / 2;
    //         handleSort(arr, left, middle);
    //         handleSort(arr, middle + 1, right);
    //         handleMergeSort(arr, left, middle, right);
    //     }
    // }


    // public static void handleMergeSort(ArrayList<String> arr, int left, int middle, int right) {
    //     int leftN = middle - left + 1;
    //     int rightN = right - middle;

    //     int[] leftBobas = new int[leftN];
    //     int[] rightBobas = new int[rightN];
    //     String[] leftNames = new String[leftN];
    //     String[] rightNames = new String[rightN];

    //     for (int i = 0; i < leftN; ++i) {
    //         leftBobas[i] = Integer.valueOf(arr.get(left + i).split(" ")[1]);
    //         leftNames[i] = arr.get(left + i).split(" ")[0];
    //     }

    //     for (int i = 0; i < rightN; ++i) {
    //         rightBobas[i] = Integer.valueOf(arr.get(middle + 1 + i).split(" ")[1]);
    //         rightNames[i] = arr.get(middle + 1 + i).split(" ")[0];
    //     }

    //     int ii = 0, jj = 0, kk = 0;
    //     while (ii < leftN && jj < rightN) {
    //         if (leftBobas[ii] > rightBobas[jj]) {
    //             arr.set(kk, leftNames[ii] + " " + leftBobas[ii]);
    //             ii++;
    //             kk++;
    //         } else if (leftBobas[ii] < rightBobas[jj]) {
    //             arr.set(kk, rightNames[jj] + " " + rightBobas[jj]);
    //             jj++;
    //             kk++;
    //         } else {
    //             if (leftNames[ii].compareTo(rightNames[jj]) < 0) {
    //                 arr.set(kk, leftNames[ii] + " " + leftBobas[ii]);
    //                 ii++;
    //                 kk++;
    //             } else {
    //                 arr.set(kk, rightNames[jj] + " " + rightBobas[jj]);
    //                 jj++;
    //                 kk++;
    //             }
    //         }
    //     }

    //     while (ii < leftN) {
    //         arr.set(kk, leftNames[ii] + " " + leftBobas[ii]);
    //         ii++;
    //         kk++;
    //     }

    //     while (jj < rightN) {
    //         arr.set(kk, rightNames[jj] + " " + rightBobas[jj]);
    //         jj++;
    //         kk++;
    //     }
    // }

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