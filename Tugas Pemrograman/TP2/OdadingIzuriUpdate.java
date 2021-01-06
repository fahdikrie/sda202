import java.io.*;
import java.util.*;

/**
 * Acknowledgements
 * 1. GFG dfs algo (https://www.geeksforgeeks.org/iterative-depth-first-traversal/)
 *    ambil referensi traverse using dfs tanpa rekursi
 * 2. Java2Blog dijkstra implementation (https://java2blog.com/dijkstra-java/)
 *    ambil referensi dijkstra. link tau dari Althof dan Gita
 * 3. Bertanya ke Gita untuk memastikan pemilihan algoritma yang sesuai dan ideal
 *    untuk masing-masing query (14/12)
 * 4. Bertanya ke Althof mengenai query tanya_kupon: bagaimana implementasi modulu 10^9 + 7
 *    dan implementasi a^b pada operasinya (15/12)
 * 5. Dijelaskan kembali mengenai query tanya_kupon, ex, dan biasa sama Ahmad Harori, Irfan Junaidi,
 *    dan Rheznandya untuk bagian (16/12)
 * 6. Dijelaskan mengenai implementasi tanya_ex dan tanya_biasa oleh Fairuza (17/12)
 */

public class OdadingIzuri {

    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    private static StringBuilder sbOut;
    private static int[] firstRow;
    private static Graph stores;

    public static void main(String[] args) {

        // Instantiate stringbuilder to store outputs
        sbOut = new StringBuilder();

        // Get user input for N, M, E, Q
        // O(1)
        handleFirstRowInput();

        // Instantiate store graph object
        stores = new Graph();

        // Get user input for store name
        // O(N)
        handleStoreNameInput();

        // Get user input for store roads
        // O(M + E)
        handleStoreRoadInput();

        // Get user input for queries
        // O(Q * Query)
        handleQueryInput();

        // Print all outputs
        out.println(sbOut);
        out.close();
        sbOut.setLength(0);
    }

    static void handleFirstRowInput() {

        // Containing first row inputs {N, M, E, Q}
        firstRow = new int[4];

        // N => Number of stores
        firstRow[0] = in.nextInt();
        // M => Number of roads between the stores (edges)
        firstRow[1] = in.nextInt();
        // E => Number of exclusive roads
        firstRow[2] = in.nextInt();
        // Q => Number of queries
        firstRow[3] = in.nextInt();
    }

    static void handleStoreNameInput() {

        // firstRow[0] => N
        for (int i = 0; i < firstRow[0]; i++) {
            stores.addVertex(in.next());
        }
    }

    static void handleStoreRoadInput() {

        // Minimum kupon value
        int minKupon = Integer.MAX_VALUE;

        // firstRow[1] => M
        for (int i = 0; i < firstRow[1]; i++) {
            String vertex1 = in.next();
            String vertex2 = in.next();
            int tempuh = in.nextInt();
            int kupon = in.nextInt();
            int tutup = in.nextInt();

            stores.addEdge(vertex1, vertex2, tempuh, kupon, tutup);

            if (kupon < minKupon && kupon > 1) minKupon = kupon;
        }

        // firstRow[2] => E
        for (int i = 0; i < firstRow[2]; i++) {
            String vertex1 = in.next();
            String vertex2 = in.next();
            int kupon = in.nextInt();
            int tutup = in.nextInt();

            stores.addExclusiveEdge(vertex1, vertex2, kupon, tutup);

            if (kupon < minKupon && kupon > 1) minKupon = kupon;
        }

        stores.couponBase = calculateBase(minKupon);
    }

    static void handleQueryInput() {

        // firstRow[3] => Q
        for (int i = 0; i  < firstRow[3]; i++) {
            String query = in.next();

            processQuery(query);
        }
    }

    static void processQuery(String query) {

        switch (query) {

            case "TANYA_JALAN":

                int X = in.nextInt();

                sbOut.append(Integer.toString(stores.traversableGraphAtXTime(X)));
                sbOut.append("\n");

                break;

            case "TANYA_HUBUNG":

                String S1 = in.next();
                String S2 = in.next();

                if (stores.traverseToSeeConnection(S2, S1)) {
                    sbOut.append("YA");
                } else {
                    sbOut.append("TIDAK");
                }
                sbOut.append("\n");

                break;

            case "TANYA_KUPON":

                String KuponS1 = in.next();
                String KuponS2 = in.next();

                sbOut.append(stores.traverseToCountMinCoupun(KuponS2, KuponS1));
                sbOut.append("\n");

                break;

            case "TANYA_EX":

                String ExS1 = in.next();
                String ExS2 = in.next();

                sbOut.append(stores.traverseToSeeMaxDepartureTimeEx(ExS2, ExS1));
                sbOut.append("\n");

                break;

            case "TANYA_BIASA":

                String BiasaS1 = in.next();
                String BiasaS2 = in.next();

                sbOut.append(stores.traverseToSeeMaxDepartureTimeReg(BiasaS2, BiasaS1));
                sbOut.append("\n");

                break;

            default:

                break;
        }
    }

    static int calculateBase(int minKupon) {

        // Tercantum pada constraint soal bahwa kupon dapat dinyatakan dengan a^b
        // Mencari nilai a sebagai basis seluruh nilai kupon pada setiap edge
        // Attribution: Ahmad Harori

        int base = minKupon;

        for (int i = 2; i <= 316; i++) {
            for (int j = 0; j <= 16; j++) {
                if (Math.pow(i, j) == minKupon) {
                    base = (int) Math.pow(i, j);
                    return base;
                }
            }
        }

        return base;
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

class Graph {
    Map<String, AdjacencyList> vertices;
    Compute compute;
    int couponBase;

    public Graph() {
        this.vertices = new LinkedHashMap<>();
        this.compute = new Compute();
    }

    public void addVertex(String name) {
        Vertex vertex = new Vertex(name);

        vertices.put(name, new AdjacencyList(vertex));
    }

    public void addEdge(String v1, String v2, int tempuh, int kupon, int tutup) {
        Vertex vertex1 = vertices.get(v1).vertex;
        Vertex vertex2 = vertices.get(v2).vertex;

        Edge edge1 = new Edge(vertex1, vertex2, tempuh, kupon, tutup);

        vertices.get(v1).adjacentEdges.add(edge1);
        vertices.get(v2).adjacentEdges.add(edge1);

        vertices.get(v1).adjacentVertices.add(vertex2);
        vertices.get(v2).adjacentVertices.add(vertex1);
    }

    public void addExclusiveEdge(String v1, String v2, int kupon, int tutup) {
        Vertex vertex1 = vertices.get(v1).vertex;
        Vertex vertex2 = vertices.get(v2).vertex;

        Edge edge1 = new ExclusiveEdge(vertex1, vertex2, kupon, tutup);

        vertices.get(v1).adjacentEdges.add(edge1);
        vertices.get(v2).adjacentEdges.add(edge1);

        vertices.get(v1).adjacentVertices.add(vertex2);
        vertices.get(v2).adjacentVertices.add(vertex1);
    }

    public int traversableGraphAtXTime(int time) {
        // Pemanggilan method pada obj compute untuk query TANYA_JALAN

        return compute.traversableGraphAtXTime(time);
    }

    public boolean traverseToSeeConnection(String source, String destination) {
        // Pemanggilan method pada obj compute untuk query TANYA_HUBUNG

        return compute.traverseToSeeConnection(source, destination);
    }

    public long traverseToCountMinCoupun(String source, String destination) {
        // Pemanggilan method pada obj compute untuk query TANYA_KUPON

        return (long) compute.traverseToCountMinCoupun(source, destination);
    }

    public int traverseToSeeMaxDepartureTimeEx(String source, String destination) {
        // Pemanggilan method pada obj compute untuk query TANYA_EX

        return compute.traverseToSeeMaxDepartureTimeEx(source, destination);
    }

    public int traverseToSeeMaxDepartureTimeReg(String source, String destination) {
        // Pemanggilan method pada obj compute untuk query TANYA_BIASA

        return compute.traverseToSeeMaxDepartureTimeReg(source, destination);
    }

    class Compute {

        // O(NM + NM) => O(NM)
        public int traversableGraphAtXTime(int time) {
            int traversables = 0;

            // O(NM)
            for (AdjacencyList adj : vertices.values()) {
                for (Edge edge : adj.adjacentEdges) {
                    if (!edge.visited) {
                        edge.visited = true;

                        if (time < edge.tutup) traversables++;
                    }
                }
            }

            // O(NM)
            for (AdjacencyList adj : vertices.values()) {
                for (Edge edge : adj.adjacentEdges) {
                    edge.visited = false;
                }
            }

            return traversables;
        }

        2

        // O(N + N + M + E) => (N + M + E)
        public int traverseGraphAtXTime(int time) {

            // O(N)
            for (AdjacencyList adj : vertices.values()) {
                adj.vertex.visited = false;
            }

            AdjacencyList source = vertices.entrySet().iterator().next().getValue();
            int traversables = 0;

            Stack<AdjacencyList> stack = new Stack<>();
            stack.push(source);

            // O(N + (M + E))
            while (!stack.isEmpty()) {
                AdjacencyList adj = stack.pop();
                adj.vertex.visited = true;

                System.out.println("VERTEX " + adj.vertex.name);

                for (Edge edge : adj.adjacentEdges) {

                    if (edge.vertex1 == adj.vertex) {
                        if (time < edge.tutup) {
                            System.out.println(edge.vertex1.name + " " + edge.vertex2.name);
                            traversables++;
                        }
                    }

                    Vertex next = edge.vertex2;
                    if (!next.visited) {
                        next.visited = true;
                        stack.push(vertices.get(next.name));
                    }
                }
            }

            return traversables;
        }

        // O (N + N + M + E) => O (N + M + E)
        public boolean traverseToSeeConnection(String source, String destination) {
            for (AdjacencyList adj : vertices.values()) {
                adj.vertex.visited = false;
            }

            Stack<AdjacencyList> stack = new Stack<>();
            stack.push(vertices.get(source));

            while (!stack.isEmpty()) {
                AdjacencyList adj = stack.pop();
                adj.vertex.visited = true;

                // System.out.println(adj.vertex.name);

                if (adj.vertex == vertices.get(destination).vertex) return true;

                for (Edge edge : adj.adjacentEdges) {

                    Vertex next = edge.vertex2;
                    if (adj.vertex == edge.vertex2) {
                        next = edge.vertex1;
                    }

                    if (!next.visited) {
                        next.visited = true;
                        stack.push(vertices.get(next.name));
                    }
                }
            }

            return false;
        }

        // O(N + N + ((M + E) * log N) + K)
        public int traverseToCountMinCoupun(String source, String destination) {
            // O(N)
            for (AdjacencyList adj : vertices.values()) {
                adj.vertex.visited = false;
                adj.vertex.spKupon = Integer.MAX_VALUE;
            }

            AdjacencyList src = vertices.get(source);
            src.vertex.spKupon = 0;

            PriorityQueue<AdjacencyList> grey = new PriorityQueue<>(vertices.size(), new KuponComparator());
            grey.add(src);

            int verticesSeen = 0;

            // O(N + ((M + E) * log N))
            while (!grey.isEmpty() && verticesSeen < vertices.size()) {

                AdjacencyList adj = grey.poll();

                if (adj.vertex.visited) continue;
                adj.vertex.visited = true;
                verticesSeen++;

                for (Edge edge : adj.adjacentEdges) {

                    Vertex next = edge.vertex2;
                    if (adj.vertex == edge.vertex2) {
                        next = edge.vertex1;
                    }

                    int coupon = log(edge.kupon, couponBase);

                    int sp = adj.vertex.spKupon + coupon;
                    if (sp < next.spKupon) {
                        next.spKupon = sp;
                        grey.add(vertices.get(next.name));
                    }
                }
            }

            // O(K + L)
            if (vertices.get(destination).vertex.visited) {
                return pow(couponBase, vertices.get(destination).vertex.spKupon) % 1000000007;
            } else {
                return -1;
            }
        }

        // O(N + N + ((M + E) * log N))
        public int traverseToSeeMaxDepartureTimeEx(String source, String destination) {
            for (AdjacencyList adj : vertices.values()) {
                adj.vertex.visited = false;
                adj.vertex.spTempuh = -1;
            }

            AdjacencyList src = vertices.get(destination);
            src.vertex.spTempuh = Integer.MAX_VALUE;

            PriorityQueue<AdjacencyList> grey = new PriorityQueue<>(vertices.size(), new TempuhComparator());
            grey.add(src);

            // int maxDepartureTimeEx = -1;
            int verticesSeen = 0;

            while (!grey.isEmpty() && verticesSeen < vertices.size()) {

                AdjacencyList adj = grey.poll();

                if (adj.vertex.visited) continue;
                adj.vertex.visited = true;
                verticesSeen++;

                for (Edge edge : adj.adjacentEdges) {

                    if (edge.getClass() != ExclusiveEdge.class) continue;

                    Vertex next = edge.vertex2;
                    if (adj.vertex == edge.vertex2) {
                        next = edge.vertex1;
                    }

                    int sp = Math.min(adj.vertex.spTempuh, edge.tutup) - 1;
                    if (next.spTempuh < sp) {
                        next.spTempuh = sp;
                        grey.add(vertices.get(next.name));
                    }
                }
            }

            if (vertices.get(source).vertex.visited) {
                return vertices.get(source).vertex.spTempuh;
            } else {
                return -1;
            }
        }

        // O(Q * ((2 * (N + (M + E)) + 3(N + (M + E) * log N) + K)
        // O(Q * ((N + M + E) + N + ((M + E) * log N) + K))
        // O(Q * (N + M + E (MlogN + ElogN) + K))

        // O(N + N + ((M + E) * log N)) => O(N + ((M + E) * log N))
        public int traverseToSeeMaxDepartureTimeReg(String source, String destination) {
            for (AdjacencyList adj : vertices.values()) {
                adj.vertex.visited = false;
                adj.vertex.spTempuh = -1;
            }

            AdjacencyList src = vertices.get(destination);
            src.vertex.spTempuh = Integer.MAX_VALUE;

            PriorityQueue<AdjacencyList> grey = new PriorityQueue<>(vertices.size(), new TempuhComparator());
            grey.add(src);

            // int maxDepartureTimeEx = -1;
            int verticesSeen = 0;

            while (!grey.isEmpty() && verticesSeen < vertices.size()) {

                AdjacencyList adj = grey.poll();

                if (adj.vertex.visited) continue;
                adj.vertex.visited = true;
                verticesSeen++;

                for (Edge edge : adj.adjacentEdges) {

                    if (edge.getClass() == ExclusiveEdge.class) continue;

                    Vertex next = edge.vertex2;
                    if (adj.vertex == edge.vertex2) {
                        next = edge.vertex1;
                    }

                    int sp = Math.min(adj.vertex.spTempuh, edge.tutup) - edge.tempuh;
                    if (next.spTempuh < sp) {
                        next.spTempuh = sp;
                        grey.add(vertices.get(next.name));
                    }
                }
            }

            if (vertices.get(source).vertex.visited) {
                return vertices.get(source).vertex.spTempuh;
            } else {
                return -1;
            }
        }

        public int log(int value, int base) {
            return (int) (Math.log(value) / Math.log(base));
        }

        public int pow(int base, int exp) {

            long result = 1;

            for (int i = 0; i < exp; i++) {
                result = (result * base) % 1000000007;
            }

            return (int) result;
        }

        class KuponComparator implements Comparator<AdjacencyList> {
            public int compare(AdjacencyList adj1, AdjacencyList adj2) {
                if (adj1.vertex.spKupon < adj2.vertex.spKupon) {
                    return -1;
                } else if (adj1.vertex.spKupon > adj2.vertex.spKupon) {
                    return 1;
                }

                return 0;
            }
        }

        class TempuhComparator implements Comparator<AdjacencyList> {
            public int compare(AdjacencyList adj1, AdjacencyList adj2) {
                if (adj1.vertex.spTempuh < adj2.vertex.spTempuh) {
                    return 1;
                } else if (adj1.vertex.spTempuh > adj2.vertex.spTempuh) {
                    return -1;
                }

                return 0;
            }
        }
    }
}

class AdjacencyList {
    Vertex vertex;
    LinkedHashSet<Edge> adjacentEdges;
    LinkedHashSet<Vertex> adjacentVertices;

    public AdjacencyList(Vertex vertex) {
        this.vertex = vertex;
        this.adjacentEdges = new LinkedHashSet<>();
        this.adjacentVertices = new LinkedHashSet<>();
    }
}

class Vertex {
    String name;
    boolean visited;
    int spTempuh;
    int spKupon;

    public Vertex(String name) {
        this.name = name;
        this.visited = false;
        this.spTempuh = Integer.MAX_VALUE;
        this.spKupon = Integer.MAX_VALUE;
    }
}

class Edge {
    Vertex vertex1;
    Vertex vertex2;
    boolean visited;
    int tempuh;
    int kupon;
    int tutup;

    public Edge(Vertex vertex1, Vertex vertex2, int tempuh, int kupon, int tutup) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.tempuh = tempuh;
        this.kupon = kupon;
        this.tutup = tutup;
    }
}

class ExclusiveEdge extends Edge {
    public ExclusiveEdge(Vertex vertex1, Vertex vertex2, int kupon, int tutup) {
        super(vertex1, vertex2, 1, kupon, tutup);
    }
}