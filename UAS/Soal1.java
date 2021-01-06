import java.io.*;
import java.util.*;

/*
* Class Graph memiliki properti int JUMLAH_VERTEX dan boolean[][] adjMatrix
*
* Vertex pada graf dilabeli menggunakan bilangan bulat 0 sampai dengan JUMLAH_VERTEX-1. 
* 
* Anda diperbolehkan menambahkan method lain pada class Graph dan memodifikasi definisi class graph
* 
* Nama: Fahdii Ajmalal Fikrie
* NPM: 1906398370
* Kelas: SDA C
*
*
*/
class Graph{
    private static int JUMLAH_VERTEX;
    private static boolean[][] adjMatrix; 
    
    Graph(int v){
        JUMLAH_VERTEX = v; 
        adjMatrix = new boolean[JUMLAH_VERTEX][JUMLAH_VERTEX]; 
    }

    void printGraph(){
        for(int i = 0; i < JUMLAH_VERTEX; i++){
            for(int j = 0; j < JUMLAH_VERTEX; j++){
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println("");
        }
    }

    void addEdge(int baris, int kolom){
        adjMatrix[baris][kolom] = true;
    } 

    int getJumlahVertex() {
        return JUMLAH_VERTEX;
    }

    boolean[][] getAdjMatrix() {
        return adjMatrix;
    }
}

public class Soal1{
    static InputReader in = new InputReader(System.in);
    static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args){
        int jmlVertex = in.nextInt();
        int jmlEdge = in.nextInt();
        Graph graf = new Graph(jmlVertex);

        for(int j = 0; j < jmlEdge; j++){
            graf.addEdge(in.nextInt(), in.nextInt());
        }
        int inDegree = in.nextInt();
        //to-do mencetak verteks dengan in-degree yang diberikan
        countInDegree(graf, inDegree);

        out.close();
    }

    private static void countInDegree(Graph graf, int numInDegree) {

        int[] inDegree = new int[graf.getJumlahVertex()];

        for (int i = 0; i < graf.getJumlahVertex(); i++) {
            for (int j = 0; j < graf.getJumlahVertex(); j++) {
                // kalo dia true, berarti ada edge i -> j
                if (graf.getAdjMatrix()[i][j]) {
                    inDegree[j] = inDegree[j] + 1;
                }
            }
        }

        boolean flag = false;

        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == numInDegree) {
                out.print(i + " ");
                flag = true;
            }
        }

        // kalo flag false, print -1
        if (!flag) out.println(-1);
    }

   // taken from https://codeforces.com/submissions/Petr
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

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

    }
} 