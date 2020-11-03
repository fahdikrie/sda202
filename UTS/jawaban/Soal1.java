import java.io.*;
import java.util.*;

public class Soal1 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    private static int sizeA, sizeB;
    private static ArrayList<String> kelompokA, kelompokB, sortedKelompokC;
    private static Set<String> kelompokC;

    public static void main(String[] args) {
    	sizeA = in.nextInt();
    	sizeB = in.nextInt();

    	kelompokA = new ArrayList<String>();
        kelompokB = new ArrayList<String>();

    	for(int i = 0; i<sizeA; i++){
    		kelompokA.add(in.next());
    	}
    	for(int i = 0; i<sizeB; i++){
    		kelompokB.add(in.next());
    	}

    	String search = in.next();
        int result = Integer.MAX_VALUE;

        //implementasikan solusi Anda di sini

        // merge list a & b
        kelompokA.addAll(kelompokB);
        // convert merged a & b to set c
        kelompokC = new HashSet<String>(kelompokA);
        // convert set c to list so it can be sorted
        sortedKelompokC = new ArrayList<>(kelompokC);
        Collections.sort(sortedKelompokC);

        // for (String string : sortedKelompokC) {
        //     out.println(string);
        // }

        result = search(search);

        out.print(result);
        out.close();
    }

    public static int search(String search) {
        if (sortedKelompokC.contains(search)) {
            return sortedKelompokC.indexOf(search);
        }

        return -1;
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
 
    }
}