import java.util.*;

import bGraph.AdjacencyList;

public class OdadingIzuri {

    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    private static Graph stores;
    private static int[] firstRow;

    static void main(String[] args) {

        // Get user input for N, M, E, Q
        handleFirstRowInput();
        // Instantiate store graph object
        stores = new Graph();
        // Get user input for store name
        handleStoreNameInput();
        // Get user input for store roads
        handleStoreRoadInput();

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
        // firstRow[1] => M
        for (int i = 0; i < firstRow[1]; i++) {
            String vertex1 = in.next();
            String vertex2 = in.next();
            int tempuh = in.nextInt();
            int kupon = in.nextInt();
            int tutup = in.nextInt();

            stores.addEdge(vertex1, vertex2, tempuh, kupon, tutup);;
        }

        // firstRow[2] => E
        for (int i = 0; i < firstRow[2]; i++) {
            String vertex1 = in.next();
            String vertex2 = in.next();
            int kupon = in.nextInt();
            int tutup = in.nextInt();

            stores.addExclusiveEdge(vertex1, vertex2, kupon, tutup);
        }
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

                break;

            case "TANYA_HUBUNG":

                int S1 = in.nextInt();
                int S2 = in.nextInt();

                break;

            case "TANYA_KUPON":

                int KuponS1 = in.nextInt();
                int KuponS2 = in.nextInt();

                break;

            case "TANYA_EX":

                int ExS1 = in.nextInt();
                int ExS2 = in.nextInt();

                break;

            case "TANYA_BIASA":

                int BiasaS1 = in.nextInt();
                int BiasaS2 = in.nextInt();

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
    Map<Vertex, AdjacencyList> vertices;
    Compute compute;

    public Graph() {
        this.vertices = new LinkedHashMap<>();
        this.compute = new Compute();
    }

    public void addVertex(String name) {
        Vertex vertex = new Vertex(name);

        vertices.put(vertex, new AdjacencyList(vertex));
    }

    public void addEdge(Vertex vertex1, Vertex vertex2, int tempuh, int kupon, int tutup) {
        Edge edge1 = new Edge(vertex1, vertex2, tempuh, kupon, tutup);
        Edge edge2 = new Edge(vertex2, vertex1, tempuh, kupon, tutup);

        vertices.get(vertex1).adjacentEdges.add(edge1);
        vertices.get(vertex2).adjacentEdges.add(edge2);

        vertices.get(vertex1).adjacentVertices.add(vertex2);
        vertices.get(vertex2).adjacentVertices.add(vertex2);
    }

    public void addExclusiveEdge(Vertex vertex1, Vertex vertex2, int kupon, int tutup) {
        Edge edge1 = new ExclusiveEdge(vertex1, vertex2, kupon, tutup);
        Edge edge2 = new ExclusiveEdge(vertex2, vertex1, kupon, tutup);

        vertices.get(vertex1).adjacentEdges.add(edge1);
        vertices.get(vertex2).adjacentEdges.add(edge2);

        vertices.get(vertex1).adjacentVertices.add(vertex2);
        vertices.get(vertex2).adjacentVertices.add(vertex2);
    }

    class Compute {
        // int traversables;

        public int traverseGraphAtXTime(int time) {
            for (AdjacencyList adj : vertices.values()) {
                adj.vertex.visited = false;
            }

            AdjacencyList source = vertices.entrySet().iterator().next().getValue();
            traversables = 0;

            Stack<AdjacencyList> stack = new Stack<>();
            stack.push(source)

            while (!stack.isEmpty()) {
                AdjacencyList adj = stack.peek();
                stack.pop();

                for (Edge edge : adj.adjacentEdges) {
                    if (edge.tutup < time) {
                        traversables++;
                    }

                    Vertex next = edge.vertex2;
                    if (!next.visited) {
                        next.visited = true;
                        stack.push(vertices.get(next));
                    }
                }
            }

            return traversables;
        }

        // source S2, destination S1
        public void traverseToSeeConnection(Vertex source, Vertex destination) {
            for (AdjacencyList adj : vertices.values()) {
                adj.vertex.visited = false;
            }

            AdjacencyList source = vertices.entrySet().iterator().next().getValue();
            traversables = 0;

            Stack<AdjacencyList> stack = new Stack<>();
            stack.push(source)

            while (!stack.isEmpty()) {
                AdjacencyList adj = stack.peek();
                stack.pop();

                for (Edge edge : adj.adjacentEdges) {
                    if (edge.tutup < time) {
                        traversables++;
                    }

                    Vertex next = edge.vertex2;
                    if (!next.visited) {
                        next.visited = true;
                        stack.push(vertices.get(next));
                    }
                }
            }

            return traversables;
        }

        // public int traverseGraphAtXTime(int time) {
        //     for (AdjacencyList adj : vertices.values()) {
        //         adj.vertex.visited = false;
        //     }

        //     AdjacencyList source = vertices.entrySet().iterator().next().getValue();
        //     traversables = 0;

        //     traverseGraphAtXTimeRec(source, time);

        //     return traversables;
        // }

        // public void traverseGraphAtXTimeRec(AdjacencyList adj, int time) {
        //     for (Edge edge : adj.adjacentEdges) {
        //         if (edge.tutup < time) {
        //             traversables++;
        //         }

        //         Vertex next = edge.vertex2;
        //         if (!next.visited) {
        //             next.visited = true;
        //             traverseGraphAtXTimeRec(vertices.get(next), time);
        //         }
        //     }
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

class Vertex {
    String name;
    boolean visited;

    public Vertex(String name) {
        this.name = name;
        this.visited = false;
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
        this.visited = false;
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

class Compute {

}

class bGraph {

    Map<String, AdjacencyList> adjacentVertices;
    Compute compute;

    public Graph() {
        this.adjacentVertices =  new LinkedHashMap<>();
        this.compute = new Compute();
    }

    public int traverseGraphAtXTime(int tempuh) {
        return compute.traverseGraphAtXTime(tempuh, adjacentVertices);
    }

    public void addVertex(String name) {
        this.getAdjacentVertices().add(new AdjacencyList(name));
    }

    public void addEdge(String vertex1, String vertex2, int tempuh, int kupon, int tutup) {
        Edge edge = new Edge(vertex1, vertex1, tempuh, kupon, tutup);

        this.getAdjacencyList(vertex1).addEdge(edge);
        this.getAdjacencyList(vertex2).addEdge(edge);
    }

    public void addExclusiveEdge(String vertex1, String vertex2, int kupon, int tutup) {
        ExclusiveEdge exclusiveEdge = new ExclusiveEdge(vertex1, vertex2, kupon, tutup);

        this.getAdjacencyList(vertex1).addEdge(exclusiveEdge);
        this.getAdjacencyList(vertex2).addEdge(exclusiveEdge);
    }

    public AdjacencyList getAdjacencyList(String vertex) {
        return this.adjacentVertices.get(vertex)
    }

    public Map<String, AdjacencyList> getAdjacentVertices() {
        return this.adjacentVertices;
    }

    class Compute {
        public int traverseGraphAtXTime(int tempuh, Map adjacentVertices) {
            // set all vertices isVisit to false
            for (AdjacencyList adjacencyList : getAdjacentVertices().values()) {
                adjacencyList.getVertex().setIsVisited(false);
            }

            // get first element of adjacentVertices
            AdjacencyList firstElement = adjacentVertices.entrySet().iterator().next();

            // counter for roads that are open
            int openRoads = 0;

            // recursive call
            traverseGraphAtXTimeRec(firstElement.getVertex(), tempuh);
        }

        private void traverseGraphAtXTimeRec(Vertex vertex, int tempuh) {
            vertex.setIsVisited(true);

            for (Edge edge : getAdjacencyList(vertex.getVertex())) {

            }
        }
    }

    class AdjacencyList {
        Vertex vertex;
        List<Edge> adjacentEdges;

        public AdjacencyList(String name) {
            this.vertex = new Vertex(name);
            this.adjacentEdges = new ArrayList<>();
        }

        public void addEdge(Edge edge) {
            this.adjacentEdges.add(edge);
        }

        public Vertex getVertex() {
            return vertex;
        }

        public String getVertexName() {
            return vertex.name;
        }

        public List<Edge> getAdjacentEdges() {
            return adjacentEdges;
        }
    }

    class Vertex {
        String name;
        boolean isVisited;

        public Vertex(String name) {
            this.name = name;
        }

        public void setIsVisited(boolean flag) {
            this.isVisited = flag;
        }
    }

    class Edge {
        Vertex vertex1;
        Vertex vertex2;
        boolean isVisited;
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

        public boolean getIsVisited() {
            return isVisited;
        }
    }

    class ExclusiveEdge extends Edge {
        public ExclusiveEdge(Vertex vertex1, Vertex vertex2, int kupon, int tutup) {
            super(vertex1, vertex2, 1, kupon, tutup);
        }
    }
}


class sGraph {
    Map<Vertex, List<Edge>> map;
    List<Vertex> vertices;
    Compute compute;

    public Graph() {
        this.map = new LinkedHashMap<>();
        this.vertices = new ArrayList<>();
        this.compute = new Compute();
    }

    public void addVertex(String name) {
        vertices.add(new Vertex(name));
    }

    public List<Vertex> getVertices() {
        return this.vertices;
    }

    class Compute {
        public void traverseGraphAtXTime(int time) {
            for (Vertex vertex : getVertices()) {
                vertex.setIsVisited(false);
            }

            int edgesCount = 0;

            traverseGraphAtXTimeRec(getVertices().get(0), time);
        }

        public void traverseGraphAtXTimeRec(Vertex vertex, int time) {
            for (Edge edge : vertex.getAdjacentEdges()) {
                if (time != edge.getTutup()) {
                    edgesCount++;
                }
            }
        }
    }

    class Vertex {
        String name;
        List<Edge> adjacentEdges;
        boolean isVisited;

        public Vertex(String name) {
            this.name = name;
            adjacentEdges = new ArrayList<>();
        }

        public void addEdge(Edge edge) {
            this.adjacentEdges.add(edge);
        }

        public void setIsVisited(boolean flag) {
            this.isVisited = flag;
        }

        public List<Edge> getAdjacentEdges() {
            return adjacentEdges;
        }
    }

    class Edge {
        Vertex vertex1;
        Vertex vertex2;
        boolean isVisited;
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
}