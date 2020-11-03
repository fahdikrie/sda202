import java.io.*;
import java.util.*;

public class Soal2 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    private static int n1, n2, n3;
    private static int[] arr1, arr2, arr3, arr;

    public static void main(String[] args) {
    	n1 = in.nextInt();
    	n2 = in.nextInt();
        n3 = in.nextInt();

    	arr1 = new int[n1];
        arr2 = new int[n2];
        arr3 = new int[n3];
        arr = new int[n1+n2+n3];

    	for(int i = 0; i<n1; i++){
    		arr1[i] = in.nextInt();
    	}

        for(int i = 0; i<n2; i++){
            arr2[i] = in.nextInt();
        }

        for(int i = 0; i<n3; i++){
            arr3[i] = in.nextInt();
        }
        
        arr = merging3(arr1, arr2, arr3);
        for(int i = 0; i < arr.length; i++){
            out.print(arr[i] + " ");
        }

        out.close();
    }

    static int[] merging3(int[] arr1, int[] arr2, int[] arr3){
        int[] arr = new int[n1+n2+n3];

        // jadi idenya pake mergesort
        // pertama merge arr1 dan arr2 dulu, disimpen di var merged1
        int[] merged1 = mergeSort(arr1, arr2, arr1.length, arr2.length);
        // hasil merged1 di merge lagi sama arr3
        arr = mergeSort(merged1, arr3, merged1.length, arr3.length);

        return arr;
    }

    // bikin method mergesort biasa
    // courtesy tp1 punya saya sendiri heheh
    static int[] mergeSort(int[] arr1, int[] arr2, int l, int r) {
        int[] result = new int[l + r];

        int ii = 0, jj = 0, kk = 0;

        while (ii < l && jj < r) {
            if (arr1[ii] < arr2[jj]) {
                result[kk++] = arr1[ii++];
            } else {
                result[kk++] = arr2[jj++];
            }
        }

        while (ii < l) {
            result[kk++] = arr1[ii++];
        }

        while (jj < r) {
            result[kk++] = arr2[jj++];
        }

        return result;
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