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
 * 5. Dijelaskan kembali mengenai soal tanya_kupon sama Ahmad Harori, Irfan Junaidi,
 *    dan Rheznandya untuk bagian (16/12)
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
        handleFirstRowInput();
        // Instantiate store graph object
        stores = new Graph();
        // Get user input for store name
        handleStoreNameInput();
        // Get user input for store roads
        handleStoreRoadInput();
        // Get user input for queries
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

        // System.out.println(firstRow[0]);
    }

    static void handleStoreRoadInput() {
        // firstRow[1] => M
        for (int i = 0; i < firstRow[1]; i++) {
            String vertex1 = in.next();
            String vertex2 = in.next();
            int tempuh = in.nextInt();
            int kupon = in.nextInt();
            int tutup = in.nextInt();

            stores.addEdge(vertex1, vertex2, tempuh, kupon, tutup);;
        }
        // System.out.println(firstRow[1]);

        // firstRow[2] => E
        for (int i = 0; i < firstRow[2]; i++) {
            String vertex1 = in.next();
            String vertex2 = in.next();
            int kupon = in.nextInt();
            int tutup = in.nextInt();

            stores.addExclusiveEdge(vertex1, vertex2, kupon, tutup);
        }
        // System.out.println(firstRow[2]);
    }

    static void handleQueryInput() {
        // System.out.println(firstRow[3]);
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

                sbOut.append(Integer.toString(stores.traverseGraphAtXTime(X)));
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

                break;

            case "TANYA_EX":

                String ExS1 = in.next();
                String ExS2 = in.next();

                break;

            case "TANYA_BIASA":

                String BiasaS1 = in.next();
                String BiasaS2 = in.next();

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

class Graph {
    Map<String, AdjacencyList> vertices;
    Compute compute;

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
        // Edge edge2 = new Edge(vertex2, vertex1, tempuh, kupon, tutup);

        vertices.get(v1).adjacentEdges.add(edge1);
        vertices.get(v2).adjacentEdges.add(edge1);

        vertices.get(v1).adjacentVertices.add(vertex2);
        vertices.get(v2).adjacentVertices.add(vertex1);
    }

    public void addExclusiveEdge(String v1, String v2, int kupon, int tutup) {
        Vertex vertex1 = vertices.get(v1).vertex;
        Vertex vertex2 = vertices.get(v2).vertex;

        Edge edge1 = new ExclusiveEdge(vertex1, vertex2, kupon, tutup);
        // Edge edge2 = new ExclusiveEdge(vertex2, vertex1, kupon, tutup);

        vertices.get(v1).adjacentEdges.add(edge1);
        vertices.get(v2).adjacentEdges.add(edge1);

        vertices.get(v1).adjacentVertices.add(vertex2);
        vertices.get(v2).adjacentVertices.add(vertex1);
    }

    public int traverseGraphAtXTime(int time) {
        return compute.traverseGraphAtXTime(time);
    }

    public boolean traverseToSeeConnection(String source, String destination) {
        return compute.traverseToSeeConnection(source, destination);
    }

    public int traverseToCountMinCoupun(String source, String destination) {
        return compute.traverseToCountMinCoupun(source, destination);
    }

    class Compute {

        public int traverseGraphAtXTime(int time) {
            for (AdjacencyList adj : vertices.values()) {
                adj.vertex.visited = false;
            }

            AdjacencyList source = vertices.entrySet().iterator().next().getValue();
            int traversables = 0;

            Stack<AdjacencyList> stack = new Stack<>();
            stack.push(source);

            while (!stack.isEmpty()) {
                AdjacencyList adj = stack.pop();
                adj.vertex.visited = true;

                for (Edge edge : adj.adjacentEdges) {
                    if (edge.vertex1 == adj.vertex) {
                        if (time < edge.tutup) {
                            // System.out.println(edge.vertex1.name + " " + edge.vertex2.name);
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

        public int traverseToCountMinCoupun(String source, String destination) {
            for (AdjacencyList adj : vertices.values()) {
                adj.vertex.spKupon = Integer.MAX_VALUE;
            }

            int minCoupon = -1;

            AdjacencyList src = vertices.get(source);
            src.vertex.spKupon = 0;

            PriorityQueue<AdjacencyList> grey = new PriorityQueue<>(vertices.size(), new KuponComparator());
            Set<AdjacencyList> green = new HashSet<>();
            grey.add(src);

            while (grey.size() >= 1) {
                AdjacencyList adj = grey.poll();
                minCoupon = adj.vertex.spKupon;
                green.add(adj);

                // System.out.println("VERTEX" + adj.vertex.name);

                if (adj.vertex == vertices.get(destination).vertex) return minCoupon % 1000000007;

                for (Edge edge : adj.adjacentEdges) {

                    Vertex next = edge.vertex2;
                    if (adj.vertex == edge.vertex2) {
                        next = edge.vertex1;
                    }

                    if (!green.contains(vertices.get(next.name))) {
                        next.visited = true;

                        if (adj.vertex.spKupon == 0) {
                            if (next.spKupon > (1 * edge.kupon)) {
                                next.spKupon = 1 * edge.kupon;
                            }
                        } else {
                            if (next.spKupon > (adj.vertex.spKupon * edge.kupon)) {
                                next.spKupon = adj.vertex.spKupon * edge.kupon;
                            }
                        }

                        if (!grey.contains(vertices.get(next.name))) {
                            grey.add(vertices.get(next.name));
                        }
                    }
                }
            }

            return -1;
        }

        // public int traverseToSeeMinDepartureTimeEx(String source, String destination) {
        //     for (AdjacencyList adj : vertices.values()) {
        //         adj.vertex.visited = false;
        //         adj.vertex.spTempuh = 0;
        //     }

        //     int minDepartureTimeEx = -1;

        //     AdjacencyList src = vertices.get(source);
        //     src.vertex.spTempuh = 0;
        //     src.vertex.visited = true;

        //     PriorityQueue<AdjacencyList> grey = new PriorityQueue<>(vertices.size(), new KuponComparator());
        //     Set<AdjacencyList> green = new HashSet<>();
        //     grey.add(src);

        //     while (grey.size() >= 1) {
        //         AdjacencyList adj = grey.poll();
        //         green.add(adj);

        //         // System.out.println("VERTEX" + adj.vertex.name);

        //         if (adj.vertex == vertices.get(destination).vertex) return minDepartureTimeEx;

        //         for (Edge edge : adj.adjacentEdges) {

        //             Vertex next = edge.vertex2;
        //             if (adj.vertex == edge.vertex2) {
        //                 next = edge.vertex1;
        //             }

        //             if (!green.contains(vertices.get(next.name))) {

        //                 if (!grey.contains(vertices.get(next.name))) {
        //                     grey.add(vertices.get(next.name));
        //                 }

                        
        //             }

        //         }
        //     }

        //     return -1;
        // }

        // public int traverseToSeeMinDepartureTimeReg(String source, String destination) {
        //     for (AdjacencyList adj : vertices.values()) {
        //         adj.vertex.visited = false;
        //         adj.vertex.spTempuh = 0;
        //     }

        //     int minDepartureTimeEx = -1;

        //     AdjacencyList src = vertices.get(source);
        //     src.vertex.spTempuh = 0;
        //     src.vertex.visited = true;

        //     PriorityQueue<AdjacencyList> grey = new PriorityQueue<>(vertices.size(), new KuponComparator());
        //     Set<AdjacencyList> green = new HashSet<>();
        //     grey.add(src);

        //     while (grey.size() >= 1) {
        //         AdjacencyList adj = grey.poll();
        //         green.add(adj);

        //         // System.out.println("VERTEX" + adj.vertex.name);

        //         if (adj.vertex == vertices.get(destination).vertex) return minDepartureTimeEx;

        //         for (Edge edge : adj.adjacentEdges) {

        //             Vertex next = edge.vertex2;
        //             if (adj.vertex == edge.vertex2) {
        //                 next = edge.vertex1;
        //             }

        //             if (!green.contains(vertices.get(next.name))) {

        //                 if (!grey.contains(vertices.get(next.name))) {
        //                     grey.add(vertices.get(next.name));
        //                 }

                        
        //             }

        //         }
        //     }

        //     return -1;
        // }

        // public int binarySearch() {

        // }
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

// class AdjacencyList {
//     Vertex vertex;
//     LinkedHashSet<Edge> adjacentEdges;
//     LinkedHashSet<Vertex> adjacentVertices;

//     public AdjacencyList(Vertex vertex) {
//         this.vertex = vertex;
//         this.adjacentEdges = new LinkedHashSet<>();
//         this.adjacentVertices = new LinkedHashSet<>();
//     }
// }

// class Vertex {
//     String name;
//     boolean visited;

//     public Vertex(String name) {
//         this.name = name;
//         this.visited = false;
//     }
// }

// class Edge {
//     Vertex vertex1;
//     Vertex vertex2;
//     boolean visited;
//     int tempuh;
//     int kupon;
//     int tutup;

//     public Edge(Vertex vertex1, Vertex vertex2, int tempuh, int kupon, int tutup) {
//         this.vertex1 = vertex1;
//         this.vertex2 = vertex2;
//         this.visited = false;
//         this.tempuh = tempuh;
//         this.kupon = kupon;
//         this.tutup = tutup;
//     }
// }

// class ExclusiveEdge extends Edge {
//     public ExclusiveEdge(Vertex vertex1, Vertex vertex2, int kupon, int tutup) {
//         super(vertex1, vertex2, 1, kupon, tutup);
//     }
// }

// class Compute {

// }

// class bGraph {

//     Map<String, AdjacencyList> adjacentVertices;
//     Compute compute;

//     public Graph() {
//         this.adjacentVertices =  new LinkedHashMap<>();
//         this.compute = new Compute();
//     }

//     public int traverseGraphAtXTime(int tempuh) {
//         return compute.traverseGraphAtXTime(tempuh, adjacentVertices);
//     }

//     public void addVertex(String name) {
//         this.getAdjacentVertices().add(new AdjacencyList(name));
//     }

//     public void addEdge(String vertex1, String vertex2, int tempuh, int kupon, int tutup) {
//         Edge edge = new Edge(vertex1, vertex1, tempuh, kupon, tutup);

//         this.getAdjacencyList(vertex1).addEdge(edge);
//         this.getAdjacencyList(vertex2).addEdge(edge);
//     }

//     public void addExclusiveEdge(String vertex1, String vertex2, int kupon, int tutup) {
//         ExclusiveEdge exclusiveEdge = new ExclusiveEdge(vertex1, vertex2, kupon, tutup);

//         this.getAdjacencyList(vertex1).addEdge(exclusiveEdge);
//         this.getAdjacencyList(vertex2).addEdge(exclusiveEdge);
//     }

//     public AdjacencyList getAdjacencyList(String vertex) {
//         return this.adjacentVertices.get(vertex)
//     }

//     public Map<String, AdjacencyList> getAdjacentVertices() {
//         return this.adjacentVertices;
//     }

//     class Compute {
//         public int traverseGraphAtXTime(int tempuh, Map adjacentVertices) {
//             // set all vertices isVisit to false
//             for (AdjacencyList adjacencyList : getAdjacentVertices().values()) {
//                 adjacencyList.getVertex().setIsVisited(false);
//             }

//             // get first element of adjacentVertices
//             AdjacencyList firstElement = adjacentVertices.entrySet().iterator().next();

//             // counter for roads that are open
//             int openRoads = 0;

//             // recursive call
//             traverseGraphAtXTimeRec(firstElement.getVertex(), tempuh);
//         }

//         private void traverseGraphAtXTimeRec(Vertex vertex, int tempuh) {
//             vertex.setIsVisited(true);

//             for (Edge edge : getAdjacencyList(vertex.getVertex())) {

//             }
//         }
//     }

//     class AdjacencyList {
//         Vertex vertex;
//         List<Edge> adjacentEdges;

//         public AdjacencyList(String name) {
//             this.vertex = new Vertex(name);
//             this.adjacentEdges = new ArrayList<>();
//         }

//         public void addEdge(Edge edge) {
//             this.adjacentEdges.add(edge);
//         }

//         public Vertex getVertex() {
//             return vertex;
//         }

//         public String getVertexName() {
//             return vertex.name;
//         }

//         public List<Edge> getAdjacentEdges() {
//             return adjacentEdges;
//         }
//     }

//     class Vertex {
//         String name;
//         boolean isVisited;

//         public Vertex(String name) {
//             this.name = name;
//         }

//         public void setIsVisited(boolean flag) {
//             this.isVisited = flag;
//         }
//     }

//     class Edge {
//         Vertex vertex1;
//         Vertex vertex2;
//         boolean isVisited;
//         int tempuh;
//         int kupon;
//         int tutup;

//         public Edge(Vertex vertex1, Vertex vertex2, int tempuh, int kupon, int tutup) {
//             this.vertex1 = vertex1;
//             this.vertex2 = vertex2;
//             this.tempuh = tempuh;
//             this.kupon = kupon;
//             this.tutup = tutup;
//         }

//         public boolean getIsVisited() {
//             return isVisited;
//         }
//     }

//     class ExclusiveEdge extends Edge {
//         public ExclusiveEdge(Vertex vertex1, Vertex vertex2, int kupon, int tutup) {
//             super(vertex1, vertex2, 1, kupon, tutup);
//         }
//     }
// }


// class sGraph {
//     Map<Vertex, List<Edge>> map;
//     List<Vertex> vertices;
//     Compute compute;

//     public Graph() {
//         this.map = new LinkedHashMap<>();
//         this.vertices = new ArrayList<>();
//         this.compute = new Compute();
//     }

//     public void addVertex(String name) {
//         vertices.add(new Vertex(name));
//     }

//     public List<Vertex> getVertices() {
//         return this.vertices;
//     }

//     class Compute {
//         public void traverseGraphAtXTime(int time) {
//             for (Vertex vertex : getVertices()) {
//                 vertex.setIsVisited(false);
//             }

//             int edgesCount = 0;

//             traverseGraphAtXTimeRec(getVertices().get(0), time);
//         }

//         public void traverseGraphAtXTimeRec(Vertex vertex, int time) {
//             for (Edge edge : vertex.getAdjacentEdges()) {
//                 if (time != edge.getTutup()) {
//                     edgesCount++;
//                 }
//             }
//         }
//     }

//     class Vertex {
//         String name;
//         List<Edge> adjacentEdges;
//         boolean isVisited;

//         public Vertex(String name) {
//             this.name = name;
//             adjacentEdges = new ArrayList<>();
//         }

//         public void addEdge(Edge edge) {
//             this.adjacentEdges.add(edge);
//         }

//         public void setIsVisited(boolean flag) {
//             this.isVisited = flag;
//         }

//         public List<Edge> getAdjacentEdges() {
//             return adjacentEdges;
//         }
//     }

//     class Edge {
//         Vertex vertex1;
//         Vertex vertex2;
//         boolean isVisited;
//         int tempuh;
//         int kupon;
//         int tutup;

//         public Edge(Vertex vertex1, Vertex vertex2, int tempuh, int kupon, int tutup) {
//             this.vertex1 = vertex1;
//             this.vertex2 = vertex2;
//             this.tempuh = tempuh;
//             this.kupon = kupon;
//             this.tutup = tutup;
//         }
//     }
// }