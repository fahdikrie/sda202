/*
* Created: Jan 2, 2021
*
* Nama: Fahdii Ajmalal Fikrie
* NPM: 1906398370
* Kelas: SDA C
*
*/

import java.io.*;
import java.util.*;

public class Soal3 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {

        int n_vertex = in.nextInt();
        int i = n_vertex;
        String s = "";
        int vertex_id = 0;

        MyGraph mg = new MyGraph();
        while(i > 0)
        {
            s = in.next();
            if(s.equals(".")) { i--;  continue; } // stopper
            if(s.contains(",")){
                // edge
                mg.addEdge(vertex_id, Integer.parseInt(s.split(",")[0]), Integer.parseInt(s.split(",")[1]));
            }
            else {
                // vertex
                vertex_id = Integer.parseInt(s);
                mg.addVertex(vertex_id);
            }
        }
        List<Object> result = mg.kruskal_spanningtree();
        out.println("edge(s) of spanning tree: " + result.get(0));
        out.println("cost: "+ result.get(1));

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

class MyGraph{
    HashMap<Integer, Vertex> vertexMap = new HashMap<Integer, Vertex>();

    private Vertex getVertex(Integer id){
        Vertex v = vertexMap.get(id);
        if(v == null){
            v = new Vertex(id);
            vertexMap.put(id, v);
        }
        return v;
    }

    void addVertex(Integer id){
        vertexMap.put(id, new Vertex(id));
    }

    // for generate array in disjoint set
    int max_el = 0;

    PriorityQueue<Edge> edge_list = new PriorityQueue<Edge>();

    void addEdge(int source, int dest, int cost){
        Vertex x = getVertex(source);
        Vertex y = getVertex(dest);
        edge_list.add(new Edge(x, y, cost));

        // used for disjoint set
        if(max_el < (source < dest ? dest : source))
            max_el = (source < dest ? dest : source);
    }

    List<Object> kruskal_spanningtree(){
        List<String> edge_st = new LinkedList<String>();
        int cost = 0;
        if(edge_list.size() > 0){
            UnionFindDisjointSet ds = new UnionFindDisjointSet(max_el);
            Edge e = edge_list.remove();
            System.out.print(e + " (" + e.cost + ")");

            // loop if there is any edge to be checked and number of edges of spanning tree < number of vertex
            // note: number of edges of spanning tree = number of vertex - 1
            while(true){

                // IMPLEMENT your code here, to check cycle and save the result ("edge_st" and "cost")
                // ...

                // ATTRIBUTION
                // liat logic kruskal pakai approach union-find disini
                // https://www.programiz.com/dsa/kruskal-algorithm
                // alhamdulillah

                if (!ds.isSameSet(e.source.id, e.dest.id)) {
                    ds.unionSet(e.source.id, e.dest.id);
                    edge_st.add(e.source.toString() + "-" + e.dest.toString());
                    cost += e.cost;
                }
                // END OF IMPLEMENTATION

                // left this to print the edge list
                if(edge_list.isEmpty()) break;
                e = edge_list.remove();
                System.out.print(", " + e + " (" + e.cost + ")");
            }
        }
        System.out.println();

        // return the result
        List<Object> result = new LinkedList<Object>();
        result.add(edge_st);
        result.add(cost);
        return result;

    }

}

class Edge implements Comparable<Edge>{
    Vertex source;
    Vertex dest;
    int cost;

    Edge(Vertex source, Vertex dest, int cost){ this.source = source; this.dest = dest; this.cost = cost; }
    public int compareTo( Edge e )
    {
        // MODIFY this code to get the maximum spanning tree

        // this code sorts edges by cost first
        // then order by vertex id (source first, then destination)
        // e.g. edge list: 4-2 (cost 3), 2-3 (cost 5), 1-5 (cost 3), 4-1 (cost 3)
        //      will be sorted from minimum to maximum cost: 1-5 (cost 3), 4-1 (cost 3), 4-2 (cost 3), 2-3 (cost 5)
        // modification should result: 2-3 (cost 5), 1-5 (cost 3), 4-1 (cost 3), 4-2 (cost 3)

        // DONE: tuker return -1 dan 1
        if(this.cost < e.cost)
            return 1;
        else if(this.cost == e.cost)
            // order by vertex id (source first, then destination)
            if(this.source.id < e.source.id) return -1;
            else if(this.source.id == e.source.id) {
                if(this.dest.id < e.dest.id) return -1;
                else if(this.dest.id == e.dest.id) return 0;
                else return 1;
            }
            else return 1;
        else return -1;

    }
    public String toString(){ return source + "-" + dest; }
}

class Vertex{
    int id;
    Vertex(Integer id){this.id = id;}
    public String toString(){return "" + id ;}
}

// taken from course's slide
class UnionFindDisjointSet
{
    private int parent[];
    public UnionFindDisjointSet (int size) {
        parent = new int[size+1];
        for (int ii = 0; ii < parent.length; ii++) {
            parent[ii] = ii;
        }
    }
    private int findSet (int i) {
        if (parent[i] == i) {
            return i;
        } else {
            return parent[i] = findSet (parent[i]); // path compression
        }
    }
    void unionSet (int i, int j) {
        parent[findSet (i)] = findSet (j);
    }
    boolean isSameSet (int i, int j) {
        return findSet (i) == findSet (j);
    }
}