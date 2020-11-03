import java.io.*;
import java.util.*;

// import org.graalvm.compiler.graph.Node;

public class Soal3 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {

        int x = 0;
        MyList list1 = new MyList();
        while((x = in.nextInt()) != 0) list1.add(x); // value > 0
        MyList list2 = new MyList();
        while((x = in.nextInt()) != 0) list2.add(x); // value > 0
        
        out.println(MyList.myFunc(list1, list2));
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

class MyList{
    Node head;
    MyList(){ head = null; }
    
    static MyList myFunc(MyList list1, MyList list2){ // A - B, not a symmetric difference (list1 dan list2 sorted)
        MyList result = new MyList();

        // pointing whats on list1
        Node pointer1 = list1.head;

        // iterasi konten LL list1
        while (pointer1 != null) {

            // pointing whats on list2
            Node pointer2 = list2.head;
            // flag buat ngecek ada yg sama ga dlm 1 iterasi
            boolean flag = false;

            while (pointer2 !=  null) {
                // kalo ada yg sama, set flag to true
                if (pointer1.value == pointer2.value) {
                    flag = true;
                }

                pointer2 = pointer2.next;
            }

            // kalo pointer1.val gaada di list2, append ke result
            if (!flag) result.add(pointer1.value);
            // increment pointer
            pointer1 = pointer1.next;
        }
    
        return result;
        // alhamdulillah kelar
    }

    void add(int val){
        if(head == null) head = new Node(val, null);
        else{
            Node temp = head;
            while(temp.next != null){
                temp = temp.next;
            }
            temp.next = new Node(val, null);
        } 
    }
    public String toString(){
        String s = "";
        Node temp = head;
        while(temp != null){
            s += temp.value;
            temp = temp.next;
            if(temp != null)
                 s += ( " " );
        }
        return s;
    }
}

class Node{
    int value;
    Node next;
    Node(int v, Node n){
        value = v;
        next = n;
    }
}